package brokerage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This class implements the portfolio strategy interface. It represents the various strategies a
 * user can have in a portfolio. It has following two strategies in this implementation: user can
 * create a weighted portfolio with specified weights for the stocks. User can also set up a dollar
 * cost averaging where a specified amount is invested between two dates.
 */
public class PortfolioStrategyModelImpl extends PortfolioFlexBrokerageModel implements
    PortfolioStrategyModel {

  private final String stockListFilePath = "stocklist.csv";
  List<Stock> listOfStock = new ArrayList<>();

  //define hashmap that maps a particular portfolio to list of stocks.
  Map<String, List<Stock>> portfolioMap = new HashMap<>();

  @Override
  public String createWeightedPf(String portfolioName, Map<String, Float> stockWeightMap) {
    float totalWeight = 0;
    String[] stock = null;
    for (Map.Entry<String, Float> entry : stockWeightMap.entrySet()) {
      float x = entry.getValue();
      totalWeight = totalWeight + x;
    }
    if (validateTotalWeight(totalWeight) == 1) {
      return "Total weight cannot be greater than 100. Weighted portfolio cannot be created!!!";
    }
    float assignWeight = 100 / stock.length;
    if (totalWeight == 0) {
      for (Map.Entry<String, Float> entry : stockWeightMap.entrySet()) {
        stockWeightMap.put(entry.getKey(), assignWeight);
      }
    }
    int size = stockWeightMap.size();
    String line = "";
    String splitBy = ",";
    float weightFromFile = 0;
    try {
      BufferedReader br = new BufferedReader(new FileReader("PortfolioStrategy.csv"));
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        if (data[0].equals(portfolioName)) {
          weightFromFile = Float.parseFloat(data[2]) + weightFromFile;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (validateTotalWeight(weightFromFile + totalWeight) == 1) {
      return "Adding stocks to " + portfolioName + " portfolio will make the total weight "
          + "greater than 100. Cannot add stock to this portfolio. Try again!";
    }

    int i = 0;
    String[][] writeStream = new String[size][3];
    while (i < size) {
      for (Map.Entry<String, Float> entry : stockWeightMap.entrySet()) {
        writeStream[i][0] = portfolioName;
        writeStream[i][1] = entry.getKey();
        writeStream[i][2] = "" + entry.getValue();
        i = i + 1;
      }
    }
    fileWriter("PortfolioStrategy.csv", writeStream, true);
    return portfolioName + " successfully created!";
  }

  @Override
  public String displayWeightedPf(String portfolioName) {
    StringBuilder str = new StringBuilder();
    String line = "";
    String splitBy = ",";
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("PortfolioStrategy.csv"));
      br.readLine();
      str.append("\nPortfolio Name: " + portfolioName);
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        if (data[0].equals(portfolioName)) {
          str.append("\nTicker: " + data[1] + ", Weight: " + data[2]);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return str.toString();
  }

  @Override
  public String investInWeightedPf(String portfolioName, LocalDate investDate,
      float amount, float purchaseCom, API api) {
    StringBuilder result = new StringBuilder();
    Map<String, Float> stockWeightMap = new HashMap();
    String line = "";
    String splitBy = ",";
    try {
      BufferedReader br = new BufferedReader(new FileReader("PortfolioStrategy.csv"));
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        if (data[0].equals(portfolioName)) {
          stockWeightMap.put(data[1], Float.parseFloat(data[2]));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    int size = stockWeightMap.size();
    for (Map.Entry<String, Float> entry : stockWeightMap.entrySet()) {
      String ticker = entry.getKey();
      float weight = entry.getValue();
      double purchasePrice = api.stockCurrentValueFromAPI(ticker, investDate);
      while (purchasePrice == 0) {
        investDate = investDate.plusDays(1);
        purchasePrice = api.stockCurrentValueFromAPI(ticker, investDate);
      }
      double qty = ((amount * weight) / 100) / purchasePrice;
      float quantity = (float) qty;
      quantity = parseToTwoDecimal(quantity);
      float pCom = purchaseCom / size;
      pCom = parseToTwoDecimal(pCom);
      createStockList(ticker, quantity, investDate, pCom, purchasePrice);
    }
    createStrategyPortfolio(portfolioName);
    result.append("$" + amount + " successfully invested in " + portfolioName);
    result.append((examineFlexPortfolio(portfolioName)));
    return result.toString();
  }

  @Override
  public String setUpDollarCostAverage(String portfolioName, Map<String, Float> stockWeightMap,
      LocalDate startDate, LocalDate endDate, int frequency,
      float amount, double purchaseCom, API api) {
    float totalWeight = 0;
    for (Map.Entry<String, Float> entry : stockWeightMap.entrySet()) {
      float x = entry.getValue();
      totalWeight = totalWeight + x;
    }

    if (validateTotalWeight(totalWeight) == 1) {
      return "Total weight cannot be greater than 100. Weighted portfolio cannot be created!!!";
    }

    int size = stockWeightMap.size();
    if (startDate.isAfter(endDate)) {
      return "Start Date: " + startDate + " cannot be after End Date: " + endDate
          + ". Weighted portfolio cannot be created!!!";
    }
    Period period = Period.between(startDate, endDate);
    int diff = Math.abs(period.getDays());
    if (frequency > diff) {
      return "Difference between start date and end date is: " + diff
          + ". Frequency provided is: " + frequency + ". Weighted portfolio cannot be created!!!";
    }
    String line = "";
    String splitBy = ",";
    float weightFromFile = 0;
    try {
      BufferedReader br = new BufferedReader(new FileReader("DollarCostAverageData.csv"));
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        if (data[0].equals(portfolioName)) {
          weightFromFile = Float.parseFloat(data[2]) + weightFromFile;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (validateTotalWeight(weightFromFile + totalWeight) == 1) {
      return "Adding stocks to " + portfolioName + " portfolio will make the total weight "
          + "greater than 100. Cannot add stock to this portfolio. Try again!";
    }

    float pCom = (float) purchaseCom;
    pCom = parseToTwoDecimal(pCom / size);
    int i = 0;
    String[][] writeStream = new String[size][10];
    while (i < size) {
      for (Map.Entry<String, Float> entry : stockWeightMap.entrySet()) {
        writeStream[i][0] = portfolioName;
        writeStream[i][1] = entry.getKey();
        writeStream[i][2] = "" + entry.getValue();
        writeStream[i][3] = "'" + startDate;
        writeStream[i][4] = "'" + endDate;
        writeStream[i][5] = "" + frequency;
        writeStream[i][6] = "" + amount;
        writeStream[i][7] = "" + pCom;
        writeStream[i][8] = "null";
        writeStream[i][9] = UUID.randomUUID().toString();
        i = i + 1;
      }
    }
    fileWriter("DollarCostAverageData.csv", writeStream, true);
    String[][] writeStream1 = new String[size][3];
    int j = 0;
    while (j < size) {
      for (Map.Entry<String, Float> entry : stockWeightMap.entrySet()) {
        writeStream1[j][0] = portfolioName;
        writeStream1[j][1] = entry.getKey();
        writeStream1[j][2] = "" + entry.getValue();
        j = j + 1;
      }
    }
    fileWriter("PortfolioStrategy.csv", writeStream1, true);
    return portfolioName + " portfolio successfully scheduled for dollar cost averaging!";
  }

  @Override
  public String displayDollarCostAverage(String portfolioName) {
    StringBuilder str = new StringBuilder();
    String startDate = "";
    String endDate = "";
    String frequency = "";
    String amount = "";
    String line = "";
    String splitBy = ",";
    try {
      BufferedReader br = new BufferedReader(new FileReader("DollarCostAverageData.csv"));
      br.readLine();
      str.append("\nPortfolio Name: " + portfolioName);
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        if (data[0].equals(portfolioName)) {
          str.append("\nTicker: " + data[1] + ", Weight: " + data[2]);
          startDate = data[3].substring(1);
          endDate = data[4].substring(1);
          frequency = data[5];
          amount = data[6];
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    str.append("\nStart Date: " + startDate + ", End Date: " + endDate + ", Frequency in Days: "
        + frequency);
    str.append("\nInvestment Amount: $" + amount);
    return str.toString();

  }

  @Override
  public int validateStrategyPfExists(String portfolioName) {
    int result = 0;
    String line = "";
    String splitBy = ",";
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("PortfolioStrategy.csv"));
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        String pf = data[0];
        if (pf.equals(portfolioName)) {
          result = 1;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * a method which re-balances the portfolio according to their weights.
   *
   * @param portfolioName the name of the portfolio
   * @param date          the date on which the portfolio has to be re-balanced
   * @return returns a successful message or a failure message according to the input data.
   */
  @Override
  public String reBalancePortfolio(String portfolioName, LocalDate date, Map<String, Double> weights, API api) {
    double totalWeight = 0.0;
    if(this.validateFlexPortfolioExist(portfolioName)==0){
      return "No portfolio exist";
    }
    for(String stock : weights.keySet()){
      totalWeight += weights.get(stock);
    }
    if(totalWeight > 100 || totalWeight < 100){
      return "The Total % of weights were not equal to 100%. Please provide proper weightage.";
    }
    String stringValue = this.displayFlexPortfolioValue(portfolioName, date, api);
    int indexOfDollar = stringValue.lastIndexOf("$");
    Double valueOfPortfolio = Double.parseDouble(stringValue.substring(indexOfDollar+1));
    if(valueOfPortfolio==0.0){
      return "Cannot re-balance on the given date. Try some other date!!";
    }
    String compostion = this.examinePortfolioByDate(portfolioName, date);
    String[] compositionSplit = compostion.split("\n");
    HashMap<String, Double> hm = new HashMap<>();
    for(int i=4; i<compositionSplit.length;i++){
      String[] tickerRow = compositionSplit[i].split("\t");
      hm.put(tickerRow[0], hm.getOrDefault(tickerRow[0],0.0)+Double.parseDouble(tickerRow[1]));
    }
    if(!hm.keySet().equals(weights.keySet())){
      return "All the stocks were not weighted";
    }
    for(String stock : hm.keySet()){
      Double price = api.stockCurrentValueFromAPI(stock, date);
      if(price==0){
        return "Cannot re-balance portfolio on holiday";
      }
      Double stockRebalanceQuantity = (weights.get(stock)*(valueOfPortfolio)*(0.01))/price;
      Double actualStockQuantity = hm.get(stock);
      if(actualStockQuantity>stockRebalanceQuantity){
        String s = this.sellStockFromFlexPF(portfolioName,stock, actualStockQuantity-stockRebalanceQuantity,date,api);
        if(!s.contains("Stock successfully sold")){
          return "Cannot re-balance portfolio";
        }
      }else{
        PortfolioStrategyModel p = new PortfolioStrategyModelImpl();
        int j = p.createListOfStockForFlex(stock, stockRebalanceQuantity-actualStockQuantity, date, 0, api);
        if(j!=1){
          return "Cannot re-balance portfolio";
        }
        String s  = p.createPortfolio(portfolioName);
        if(!s.contains("Flexible Portfolio successfully created")){
          return "Cannot re-balance portfolio";
        }
      }
    }
    return "Re-balancing done successfully";
  }

  @Override
  public int validateTickerExist(String ticker) {
    String line = "";
    String splitBy = ",";
    try {
      //parsing a CSV file into BufferedReader class constructor
      BufferedReader br = new BufferedReader(new FileReader(stockListFilePath));
      while ((line = br.readLine()) != null) {
        String[] stock = line.split(splitBy);
        if (ticker.equals(stock[0])) {
          return 0;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 1;
  }

  @Override
  public String displayListOfStocks() {
    StringBuilder sb = new StringBuilder();
    sb.append("Please enter the ticker from these available ticker only:\n");
    Iterator itr = listOfStocks().iterator();
    while (itr.hasNext()) {
      sb.append(itr.next() + "\n");
    }
    return sb.toString();
  }

  /**
   * a method to validate whether the total weights for the stock exceeds 100.
   *
   * @param totalWeight total weight of all the stocks within the portfolio.
   * @return returns 1 or 0 depending upon whether the total weight is 100 or not.
   */
  private int validateTotalWeight(float totalWeight) {
    if (totalWeight > 100) {
      return 1;
    }
    return 0;
  }


  /**
   * a method to create a list of stock object.
   *
   * @param ticker        the ticker symbol of the stock.
   * @param quantity      the quantity of the stock.
   * @param purchaseDate  the date when the stock was purchased.
   * @param purchaseCom   the purchase commission of the stock.
   * @param purchasePrice the purchase price of the stock.
   */
  private void createStockList(String ticker, float quantity, LocalDate purchaseDate,
      double purchaseCom, double purchasePrice) {
    String stringDate = purchaseDate.toString();
    LocalDate date = parseDate(stringDate);
    Stock stock = new Stock();
    stock.setTicker(ticker);
    stock.setQuantity(quantity);
    stock.setPurchaseDate(date);
    stock.setPurchasePrice(purchasePrice);
    stock.setPurchaseCom(Math.abs(purchaseCom));
    listOfStock.add(stock);
  }

  /**
   * a helper method to get today's date in a specified format.
   *
   * @return returns today's date in a desired format.
   */
  private LocalDate getTodayDate() {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String todayDate = dateFormat.format(LocalDate.now());
    return parseDate(todayDate);
  }

  /**
   * a helper method to parse a number to a two decimal precision.
   *
   * @param number the number that needs to be converted to decimal precision.
   * @return the number with two decimal precision.
   */
  private float parseToTwoDecimal(float number) {
    DecimalFormat deci = new DecimalFormat("0.00");
    number = Float.parseFloat(deci.format(number));
    return number;
  }

  /**
   * a helper method to create a map between portfolio and the list of stock.
   *
   * @param portfolioName name of the portfolio.
   * @return returns a message whether portfolio composition.
   */
  private String createStrategyPortfolio(String portfolioName) {
    StringBuilder str = new StringBuilder();
    portfolioMap = populatePortfolioMap(portfolioName, listOfStock);
    downloadPortfolio();
    clearList();
    str.append("\nFlexible Portfolio successfully created!!!");
    str.append(examineFlexPortfolio(portfolioName));
    return str.toString();
  }


  /**
   * a method to examine the portfolio dynamically once it is created before storing into the file.
   *
   * @param portfolioName name of the portfolio.
   * @return returns the composition of the newly created/updated portfolio.
   */

  protected String examineFlexPortfolio(String portfolioName) {
    StringBuilder str = new StringBuilder();
    for (Map.Entry<String, List<Stock>> entry : portfolioMap.entrySet()) {
      String key = entry.getKey();
      if (key.equals(portfolioName)) {
        str.append("\n" + "Portfolio Name: " + key);
        str.append("\nList of Stocks in " + key + " portfolio are:");
        for (Stock st : entry.getValue()) {
          str.append("\nTicker: " + st.getTicker());
          str.append("\nQuantity: " + parseToTwoDecimal(st.getQuantity()));
          str.append("\nPurchase Price: $" + parseToTwoDecimal((float) st.getPurchasePrice()));
          str.append("\nPurchase Date: " + st.getPurchaseDate());
          str.append(
              "\nPurchase Commission: $" + parseToTwoDecimal((float) st.getPurchaseCom()) + "\n");
        }
      }
    }
    return str.toString();
  }

}