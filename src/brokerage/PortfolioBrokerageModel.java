package brokerage;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.io.FileReader;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * this class implements the Inflexible portfolio interface. This model represents operations for
 * the Inflexible portfolio. A Inflexible portfolio will have these features. Add Stock to the
 * portfolio. Determine the value of a portfolio on a specific date. Examine the composition of the
 * portfolio.
 */
public class PortfolioBrokerageModel implements PortfolioModel {

  //define list of stocks.
  List<Stock> listOfStock = new ArrayList<>();
  //define hashmap that maps a particular portfolio to list of stocks.
  Map<String, List<Stock>> portfolioMap = new HashMap<>();

  private final String stockListFilePath = "stocklist.csv";

  private final String stockPriceFilePath = "stockprice.csv";

  @Override
  public int createListOfStock(String ticker, int quantity, LocalDate purchaseDate) {
    Stock stock = new Stock();
    stock.setTicker(ticker);
    stock.setQuantity(quantity);
    listOfStock.add(stock);
    return 1;
  }

  @Override
  public String createPortfolio(String portfolioName) {
    StringBuilder str = new StringBuilder();
    portfolioMap = populatePortfolioMap(portfolioName, listOfStock);
    downloadPortfolio();
    clearList();
    str.append("\nPortfolio successfully created!!!");
    str.append(examineParticularPortfolio(portfolioName));
    return str.toString();
  }

  @Override
  public String createPortfolioThroughFile(String portfolioName, String filePath) {
    String result = "";
    String line = "";
    String splitBy = ",";
    try {
      //parsing a CSV file into BufferedReader class constructor
      BufferedReader br = new BufferedReader(new FileReader(filePath));
      while ((line = br.readLine()) != null) {
        String[] stock = line.split(splitBy);
        String tr = stock[0];
        if (validateTickerExist(tr) == 1) {
          return "Ticker mentioned in the file doesn't exist. Provide correct ticker in the file";
        }
        int qty = Integer.parseInt(stock[1]);
        createListOfStock(tr, qty, LocalDate.now());
      }
      result = createPortfolio(portfolioName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }


  @Override
  public String examineAllPortfolios(String portfolioName) {
    StringBuilder str = new StringBuilder();
    String line = "";
    String splitBy = ",";
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("InFlexiblePortfolioPurchaseHistory.csv"));
      str.append("\n============= INFLEXIBLE PORTFOLIOS ===================");
      str.append("\nPortfolio\tTicker\tQuantity");
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        if (data[0].equals(portfolioName)) {
          str.append("\n" + data[0] + "\t\t\t\t" + data[1] + "\t\t\t\t" + data[2]);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return str.toString();
  }

  @Override
  public String displayPortfolioValue(String portfolioName, LocalDate valueOnDate) {
    int qty = 0;
    float currentPrice = 0;
    float value = 0;

    String line = "";
    String splitBy = ",";
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("InFlexiblePortfolioPurchaseHistory.csv"));
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        if (data[0].equals(portfolioName)) {
          String str = data[1];
          qty = Integer.parseInt(data[2]);
          currentPrice = Float.parseFloat(stockCurrentValueFromValidate(str, valueOnDate));
          value = value + (qty * currentPrice);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "The total value of the " + portfolioName + " on date " + valueOnDate + " is: " + value;
  }

  @Override
  public String downloadPortfolio() {
    String[][] downloadStream = convertToArrayString();
    try {
      File csvFile = new File("InflexiblePortfolioPurchaseHistory.csv");
      FileWriter fileWriter = new FileWriter(csvFile, true);
      for (String[] data : downloadStream) {
        StringBuilder line = new StringBuilder();
        line.append("\n");
        for (int i = 0; i < data.length; i++) {
          line.append(data[i]);
          if (i != data.length - 1) {
            line.append(',');
          }
        }
        fileWriter.write(line.toString());
      }
      fileWriter.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
    return "File downloaded successfully!";
  }

  @Override
  public int validatePortfolioExist(String portfolioName) {
    int result = 0;
    String line = "";
    String splitBy = ",";
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("InflexiblePortfolioPurchaseHistory.csv"));
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
   * converts the list of stocks into an arraylist for display.
   *
   * @return an array list of stocks
   */
  protected ArrayList listOfStocks() {
    String line = "";
    String splitBy = ",";
    ArrayList<String> stocks = new ArrayList<>();
    try {
      //parsing a CSV file into BufferedReader class constructor
      BufferedReader br = new BufferedReader(new FileReader(stockListFilePath));
      while ((line = br.readLine()) != null) {
        String[] stock = line.split(splitBy);
        stocks.add(stock[0]);
      }
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    return stocks;
  }


  /**
   * helper method to clear the list of stocks.
   */
  protected void clearList() {
    listOfStock = new ArrayList<>();
  }

  /**
   * helper method to populate the hashmap to key and value pair.
   *
   * @param portfolioName name of the portfolio.
   * @param listOfStock   list of stocks.
   * @return returns the hashmap of portfolio.
   */
  protected Map<String, List<Stock>> populatePortfolioMap(String portfolioName,
      List<Stock> listOfStock) {
    portfolioMap.put(portfolioName, listOfStock);
    return portfolioMap;
  }

  /**
   * a helper method to get the count of the total stocks in all the portfolios.
   *
   * @return the count of total stocks.
   */
  protected int getStockCount() {
    int counter = 0;
    for (Map.Entry<String, List<Stock>> entry : portfolioMap.entrySet()) {
      String key = entry.getKey();
      for (Stock st : entry.getValue()) {
        counter++;
      }
    }
    return counter;
  }

  /**
   * a helper method that traverses through a file to get the current price of the stock.
   *
   * @param ticker takes stock's ticker as an argument.
   * @return returns the current price of the stock from the file.
   */
  private String stockCurrentValueFromValidate(String ticker, LocalDate valueOnDate) {
    String line = "";
    String splitBy = ",";
    String stockCurrentValue = "";
    try {
      //parsing a CSV file into BufferedReader class constructor
      BufferedReader br = new BufferedReader(new FileReader(stockPriceFilePath));
      while ((line = br.readLine()) != null) {
        String[] stock = line.split(splitBy);
        if (ticker.equals(stock[0]) && valueOnDate.isEqual(
            LocalDate.parse(stock[2], DateTimeFormatter.ofPattern("MM/dd/yyyy")))) {
          stockCurrentValue = stock[1];
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stockCurrentValue;
  }


  /**
   * helper method to examine a particular portfolio.
   *
   * @param portfolioName takes portfolio name as input parameter.
   * @return returns the stock composition of the portfolio in terms of string.
   */
  private String examineParticularPortfolio(String portfolioName) {
    StringBuilder str = new StringBuilder();
    for (Map.Entry<String, List<Stock>> entry : portfolioMap.entrySet()) {
      String key = entry.getKey();
      if (key.equals(portfolioName)) {
        str.append("\n" + "Portfolio Name: " + key);
        str.append("\nList of Stocks in " + key + " portfolio are:");
        for (Stock st : entry.getValue()) {
          str.append("\nStock Ticker: " + st.getTicker());
          str.append("\nStock Quantity: " + st.getQuantity());
        }
      }
    }
    return str.toString();
  }

  /**
   * a helper method that converts the content of portfolio map into a string array. this is
   * required for writing the content of the portfolio map to a file.
   *
   * @return the two-dimensional array of string.
   */
  private String[][] convertToArrayString() {
    String[][] downloadStream = new String[getStockCount()][3];
    int temp = 0;
    for (Map.Entry<String, List<Stock>> entry : portfolioMap.entrySet()) {
      String key = entry.getKey();
      for (Stock st : entry.getValue()) {
        downloadStream[temp][0] = key;
        downloadStream[temp][1] = st.getTicker();
        downloadStream[temp][2] = "" + st.getQuantity();
        temp = temp + 1;
      }
    }
    return downloadStream;
  }

}