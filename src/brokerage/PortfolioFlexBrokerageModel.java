package brokerage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import brokerage.gui.PortfolioGUIVew;

/**
 * this class implements the flexible portfolio interface. This model represents operations for the
 * flexible portfolio. A flexible portfolio will have these features. Add Stock to the portfolio.
 * Sell stock from the portfolio. Determine the cost basis by a specific date. Determine the value
 * of a portfolio on a specific date. plot the performance of the portfolio in the form of bar
 * chart.
 */
public class PortfolioFlexBrokerageModel extends PortfolioBrokerageModel implements
    PortfolioFlexModel {

  List<Stock> listOfStock = new ArrayList<>();
  //define hashmap that maps a particular portfolio to list of stocks.
  Map<String, List<Stock>> portfolioMap = new HashMap<>();
  private final String stockListFilePath = "stocklist.csv";
  private PortfolioGUIVew view;

  @Override
  public int createListOfStockForFlex(String ticker, double quantity, LocalDate purchaseDate,
      double purchaseCom, API api) {
    String stringDate = purchaseDate.toString();
    LocalDate date = parseDate(stringDate);
    Stock stock = new Stock();
    stock.setTicker(ticker);
    float d  = (float)quantity;
    stock.setQuantity(d);

    double purchasePrice = api.stockCurrentValueFromAPI(ticker, purchaseDate);
    while (purchasePrice == 0) {
      purchaseDate = purchaseDate.plusDays(1);
      purchasePrice = api.stockCurrentValueFromAPI(ticker, purchaseDate);
    }
    stock.setPurchasePrice(purchasePrice);
    stock.setPurchaseDate(date);
    stock.setPurchaseCom(Math.abs(purchaseCom));
    listOfStock.add(stock);
    return 1;
  }

  @Override
  public String createPortfolio(String portfolioName) {
    StringBuilder str = new StringBuilder();
    portfolioMap = populatePortfolioMap(portfolioName, listOfStock);
    downloadPortfolio();
    clearList();
    str.append("\nFlexible Portfolio successfully created!!!");
    str.append(examineFlexPortfolio(portfolioName));
    portfolioMap.clear();
    return str.toString();
  }

  @Override
  public String examineAllPortfolios(String portfolioName) {
    makeAutoPurchase();
    StringBuilder str = new StringBuilder();
    String line = "";
    String splitBy = ",";
    str.append("\n============= FLEXIBLE PORTFOLIOS ===================");
    str.append("\nPortfolio Name: " + portfolioName);
    str.append("\nTicker\tQuantity\tPurchase Price\tPurchase Date");
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("FlexiblePortfolioCurrentComposition.csv"));

      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        if (data[0].equals(portfolioName)) {
          str.append(
              "\n" + data[1] + "\t" + data[2] + "\t" + data[3]
                  + "\t" + data[4].substring(1));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return str.toString();
  }

  @Override
  public String sellStockFromFlexPF(String portfolioName, String ticker, double quantity,
      LocalDate sellDate, API api) {
    Map<String, Double> updateData = new HashMap<>();
    double qtyToSell = quantity;
    String result = "";
    String line = "";
    String splitBy = ",";

    try {
      BufferedReader br = new BufferedReader(
          new FileReader("FlexiblePortfolioCurrentComposition.csv"));
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        if (data[0].equals(portfolioName)) {
          if (LocalDate.parse(data[4].substring(1), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
              .isBefore(sellDate)) {
            if (data[1].equals(ticker)) {
              if (qtyToSell == (int) Float.parseFloat(data[2])) {
                updateData.put(data[6], 0.0);
                // write into sell history file
                writeSellHistory(data[0], data[1], data[2], data[3], data[4], data[5], data[6],
                    sellDate, api);
                result = "Stock successfully sold";
                break;

              } else if (qtyToSell > (int) Float.parseFloat(data[2])) {
                qtyToSell = qtyToSell - (int) Float.parseFloat(data[2]);

                //make the quantity in FlexiblePortfolioCurrentComposition.csv
                updateData.put(data[6], 0.0);

                // write into sell history file
                writeSellHistory(data[0], data[1], data[2], data[3], data[4], data[5], data[6],
                    sellDate, api);

              } else if (qtyToSell < (int) Float.parseFloat(data[2])) {
                double newStockQty = (int) Float.parseFloat(data[2]) - qtyToSell;

                //update the new quantity in FlexiblePortfolioCurrentComposition.csv
                updateData.put(data[6], newStockQty);

                // write into sell history file
                writeSellHistory(data[0], data[1], "" + qtyToSell, data[3], data[4], data[5],
                    data[6], sellDate, api);

                result = "Stock successfully sold!";
                break;
              }
            }
          }
        }
      }
      updateCurrentComposition(updateData);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public int validateSellableQuantity(String portfolioName, LocalDate sellDate, String ticker,
      int quantity, API api) {
    makeAutoPurchase();
    if (api.stockCurrentValueFromAPI(ticker, sellDate) == 0) {
      view.outputForFeatures("Cannot sell stocks on public holiday or weekend. "
          + "Please input another date!");
      return 1;
    }
    int totalQtyAvailableForSale = 0;
    String result = "";
    String key = "";
    String line = "";
    String splitBy = ",";
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("FlexiblePortfolioCurrentComposition.csv"));
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        if (data[0].equals(portfolioName)) {
          if (LocalDate.parse(data[4].substring(1), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
              .isBefore(sellDate)) {
            if (data[1].equals(ticker)) {
              totalQtyAvailableForSale = (int) Float.parseFloat(data[2]) + totalQtyAvailableForSale;
            }
          }
        }
      }
      if (totalQtyAvailableForSale < quantity) {
        view.outputForFeatures(
            "Total quantity available for " + ticker + " on date " + sellDate + " is: "
                + totalQtyAvailableForSale + ". Please input again!");
        return 1;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

  @Override
  public String displayFlexPortfolioValue(String portfolioName, LocalDate valueOnDate, API api) {
    makeAutoPurchase();
    LocalDate date = null;
    float qty = 0;
    double currentPrice = 0;
    double value = 0;
    String line = "";
    String splitBy = ",";
    int counter = 0;
    String result = "";
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("FlexiblePortfolioCurrentComposition.csv"));
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        date = parseDate(data[4].substring(1));
        if (data[0].equals(portfolioName)) {
          if (date.isBefore(valueOnDate) || date.isEqual(valueOnDate)) {
            String str = data[1];
            qty = Float.parseFloat(data[2]);
            currentPrice = api.stockCurrentValueFromAPI(str, valueOnDate);
            while (currentPrice == 0) {
              valueOnDate = valueOnDate.plusDays(1);
              currentPrice = api.stockCurrentValueFromAPI(data[1], valueOnDate);
            }
            value = value + (qty * currentPrice);
            value = (double) Math.round(value * 100) / 100;
            counter = counter + 1;
          }
        }
      }
      if (counter > 0) {
        result = "The total value of the " + portfolioName + " portfolio on date " + valueOnDate
            + " is: $"
            + value;
      } else {
        result =
            "The total value of the " + portfolioName + " portfolio on date " + valueOnDate
                + " is: $0.00";
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public String downloadPortfolio() {
    String[][] downloadStream = convertToArrayStringForFlex();
    fileWriter("FlexiblePortfolioPurchaseHistory.csv", downloadStream, true);
    fileWriter("FlexiblePortfolioCurrentComposition.csv", downloadStream, true);
    return "File downloaded successfully!";
  }

  @Override
  public int validateFlexPortfolioExist(String portfolioName) {
    int result = 0;
    String line = "";
    String splitBy = ",";
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("FlexiblePortfolioPurchaseHistory.csv"));
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

  @Override
  public String getCostBasis(String portfolioName, LocalDate queryDate) {
    makeAutoPurchase();
    float qty = 0;
    double purchasePrice = 0;
    double purchaseCom = 0;
    double sellCom = 0;
    double cost = 0;
    double totalCost = 0;
    double costBasis = 0;
    LocalDate date = null;
    int counter = 0;
    ArrayList<String> uniqueID = new ArrayList<String>();
    StringBuilder str = new StringBuilder();

    String line = "";
    String splitBy = ",";
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("FlexiblePortfolioPurchaseHistory.csv"));
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        String pf = data[0];
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = LocalDate.parse(data[4].substring(1), dateFormat);
        if (pf.equals(portfolioName)) {
          if (date.equals(queryDate) || date.isBefore(queryDate)) {
            qty = Float.parseFloat(data[2]);
            purchasePrice = Double.parseDouble(data[3]);
            purchaseCom = Math.abs(Double.parseDouble(data[5]));
            cost = (qty * purchasePrice) + purchaseCom;
            counter = counter + 1;
            uniqueID.add(data[6]);
          }
        }
        totalCost = totalCost + cost + sellCom;
        cost = 0;
      }
      costBasis = totalCost;
      costBasis = (double) Math.round(costBasis * 100) / 100;

    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      BufferedReader br = new BufferedReader(new FileReader("FlexiblePortfolioSellHistory.csv"));
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        String sellId = data[6];
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = LocalDate.parse(data[7].substring(1), dateFormat);
        for (int i = 0; i < uniqueID.size(); i++) {
          if (sellId.equals(uniqueID.get(i))) {
            if (date.equals(queryDate) || date.isBefore(queryDate)) {
              double x = Double.parseDouble(data[9]);
              sellCom = sellCom + x;
            }
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    costBasis = costBasis + sellCom;
    if (counter == 0) {
      str.append(
          "The cost basis of portfolio " + portfolioName + " on date " + queryDate + " is: $0.00");
    } else {
      str.append("The cost basis of portfolio " + portfolioName + " on date " + queryDate + " is: $"
          + costBasis);
    }
    return str.toString();
  }

  @Override
  public String examinePortfolioByDate(String portfolioName, LocalDate queryDate) {
    makeAutoPurchase();
    StringBuilder str = new StringBuilder();
    int counter = 0;
    LocalDate date = null;
    String line = "";
    String splitBy = ",";
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("FlexiblePortfolioCurrentComposition.csv"));
      str.append("\n============= FLEXIBLE PORTFOLIOS ===================");
      str.append("\nPortfolio Name: " + portfolioName);
      str.append("\nTicker\tQuantity\tPurchase Price\tPurchase Date");
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = LocalDate.parse(data[4].substring(1), dateFormat);
        if (data[0].equals(portfolioName)) {
          if (date.equals(queryDate) || date.isBefore(queryDate)) {
            str.append(
                "\n" + data[1] + "\t" + data[2] + "\t" + data[3]
                    + "\t" + data[4].substring(1));
            counter = counter + 1;
          }
        }
      }
      if (counter == 0) {
        str.append("\nNo Stock exist in portfolio " + portfolioName + " on date " + queryDate);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return str.toString();
  }

  @Override
  public String plotPerformanceOfPortfolio(String portfolioName, LocalDate startDate,
      LocalDate endDate, API api) {
    Map<String, String> mp = new LinkedHashMap<>();
    int astericValue = 1000;
    mp.clear();
    StringBuilder strBr = new StringBuilder();
    strBr.append(
        "Performance of portfolio " + portfolioName + " from " + startDate + " to " + endDate
            + " \n");
    strBr.append("\n");
    Period diff = Period.between(startDate, endDate);
    int daysDiff = diff.getDays() + (diff.getMonths() * 30);
    int value = diff.getMonths() + (diff.getYears() * 12);
    int dateDiff = startDate.getDayOfMonth();
    if (value > 5 && value <= 30) {
      startDate = startDate.plusDays(30 - dateDiff);
      while (startDate.isBefore(endDate)) {
        for (int i = 0; i < 30 && startDate.isBefore(endDate); i++) {
          Double db = getFlexPortfolioValue(portfolioName, startDate, api);
          String cFinal = astricSymbol(db, astericValue);
          String month = String.valueOf(startDate.getMonth());
          String strFinal = month.substring(0, 3) + "  " + startDate.getYear();
          mp.put(strFinal, cFinal);
          startDate = startDate.plusMonths(1);
        }
      }
    } else if (diff.getYears() >= 5 && diff.getYears() <= 30) {
      if (startDate.getYear() < endDate.getYear()) {
        int days = startDate.getDayOfMonth() + (startDate.getMonthValue() * 30);
        int diffYears = endDate.getYear() - startDate.getYear();
        startDate = startDate.plusDays(365 - days);
        for (int i = 0; i < diffYears; i++) {
          Double db = getFlexPortfolioValue(portfolioName, startDate, api);
          String cFinal = astricSymbol(db, astericValue);
          String year = String.valueOf(startDate.getYear());
          mp.put(year, cFinal);
          startDate = startDate.plusYears(1);
        }
      }
    } else if (daysDiff >= 5 && diff.getMonths() < 6) {
      float ft = daysDiff / 25;
      int noOfItr = (int) Math.ceil(ft);
      if (daysDiff > 25) {
        for (int i = 0; i < 25; i++) {
          if (startDate.isAfter(endDate)) {
            break;
          } else {
            Double db = getFlexPortfolioValue(portfolioName, startDate.plusDays(noOfItr), api);
            String a = astricSymbol(db, astericValue);
            startDate = startDate.plusDays(noOfItr);
            String str = String.valueOf(startDate.plusDays(noOfItr));
            mp.put(str, a);
          }
          if (daysDiff % 25 != 0 && i == 24) {
            Double db = getFlexPortfolioValue(portfolioName, endDate, api);
            String cFinal = astricSymbol(db, astericValue);
            String strFinal = String.valueOf(endDate);
            mp.put(strFinal, cFinal);
          }
        }
      } else {
        for (int i = 0; i < daysDiff && startDate.isBefore(endDate); i++) {
          Double db = getFlexPortfolioValue(portfolioName, startDate.plusDays(1), api);
          String b = astricSymbol(db, astericValue);
          String str1 = String.valueOf(startDate);
          mp.put(str1, b);
          startDate = startDate.plusDays(1);
        }
      }
    } else {
      return "the difference between two dates for plotting month wise "
          + "should have a difference of minimum 6 months";
    }
    mp.put("\nScale    ", "* = $1000");
    String preValue = "*************";
    for (Map.Entry<String, String> entry : mp.entrySet()) {
      if (entry.getValue().isEmpty()) {
        strBr.append(entry.getKey() + ":   " + preValue + "\n");
      } else {
        if (entry.getValue().length() < 50) {
          strBr.append(entry.getKey() + ":   " + entry.getValue() + "\n");
        } else if (entry.getValue().length() > 50) {
          astericValue = astericValue * 10;
        } else {
          astericValue = astericValue / 10;
        }
      }
      preValue = entry.getValue();
    }
    return strBr.toString();
  }

  /**
   * helper method for printing the asterisk symbols.
   *
   * @param db           the value of the portfolio.
   * @param astericValue the value used for division of the portfolio value.
   * @return the output in the form of asterisks.
   */
  protected String astricSymbol(double db, int astericValue) {
    int x = (int) (db / astericValue);
    String str = "";
    if (db > 0) {
      for (int i = 0; i < x; i++) {
        str += "*";
      }
    } else {
      str = "";
    }
    return str;
  }

  /**
   * a helper method to write the record of the sell into a file.
   *
   * @param pf            name of the portfolio.
   * @param ticker        ticker symbol of the stock.
   * @param quantity      quantity of the shares to be sold.
   * @param purchasePrice purchase price of the stock.
   * @param purchaseDate  purchase date of the stock.
   * @param purchaseCom   purchase commission of the stock.
   * @param purchaseId    purchase transaction ID of the stock.
   * @param sellDate      sell date for the stock.
   * @param api           API call object to obtain the price of the stock at a particular date.
   */
  protected void writeSellHistory(String pf, String ticker, String quantity, String purchasePrice,
      String purchaseDate, String purchaseCom, String purchaseId, LocalDate sellDate, API api) {
    Double sellPrice = api.stockCurrentValueFromAPI(ticker, sellDate);

    Double qtyInDouble = Double.parseDouble(quantity);
    String[][] downloadStream = new String[1][11];
    downloadStream[0][0] = pf;
    downloadStream[0][1] = ticker;
    downloadStream[0][2] = quantity;
    downloadStream[0][3] = purchasePrice;
    downloadStream[0][4] = "'" + purchaseDate;
    downloadStream[0][5] = purchaseCom;
    downloadStream[0][6] = purchaseId;
    downloadStream[0][7] = "'" + sellDate;
    downloadStream[0][8] = "" + sellPrice;
    downloadStream[0][9] = "" + ((sellPrice * qtyInDouble) * 0.01);
    downloadStream[0][10] = UUID.randomUUID().toString();
    fileWriter("FlexiblePortfolioSellHistory.csv", downloadStream, true);
  }

  /**
   * method to update the current composition of the portfolio on the basis of stock sold.
   *
   * @param updateData a mapping of purchase transaction ID and the new quantity of the stock to be
   *                   updated after a sell is made.
   */

  protected void updateCurrentComposition(Map<String, Double> updateData) {
    int counter = 0;
    int i = 1;
    String result = "";
    String line = "";
    String splitBy = ",";
    LocalDate date;
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("FlexiblePortfolioCurrentComposition.csv"));
      while ((line = br.readLine()) != null) {
        counter = counter + 1;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    String[][] writeStream = new String[counter][7];
    writeStream[0][0] = "Portfolio Name";
    writeStream[0][1] = "Ticker";
    writeStream[0][2] = "Quantity";
    writeStream[0][3] = "Purchase Price";
    writeStream[0][4] = "Purchase Date";
    writeStream[0][5] = "Purchase Commission";
    writeStream[0][6] = "UniqueTransactionID";
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("FlexiblePortfolioCurrentComposition.csv"));
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = LocalDate.parse(data[4].substring(1), dateFormat);
        writeStream[i][0] = data[0];
        writeStream[i][1] = data[1];
        writeStream[i][2] = data[2];
        writeStream[i][3] = data[3];
        writeStream[i][4] = "'" + date;
        writeStream[i][5] = data[5];
        writeStream[i][6] = data[6];
        for (Map.Entry<String, Double> entry : updateData.entrySet()) {
          String key = entry.getKey();
          if (key.equals(data[6])) {
            writeStream[i][2] = "" + entry.getValue();
          }
        }
        i = i + 1;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    fileWriterForSell("FlexiblePortfolioCurrentComposition.csv", writeStream, false);
  }

  /**
   * a method to convert the data into a string array for writing into a csv file.
   *
   * @return returns a two-dimensional string array that can be written into a file.
   */
  protected String[][] convertToArrayStringForFlex() {
    String[][] downloadStream = new String[getStockCount()][7];
    int temp = 0;
    for (Map.Entry<String, List<Stock>> entry : portfolioMap.entrySet()) {
      String key = entry.getKey();
      for (Stock st : entry.getValue()) {
        downloadStream[temp][0] = key;
        downloadStream[temp][1] = st.getTicker();
        downloadStream[temp][2] = "" + parseToTwoDecimal(st.getQuantity());
        downloadStream[temp][3] = "" + parseToTwoDecimal((float) st.getPurchasePrice());
        downloadStream[temp][4] = "'" + st.getPurchaseDate();
        downloadStream[temp][5] = "" + parseToTwoDecimal((float) st.getPurchaseCom());
        downloadStream[temp][6] = UUID.randomUUID().toString();
        temp = temp + 1;
      }
    }
    return downloadStream;
  }

  /**
   * a method to write the two-dimensional string array into a csv file.
   *
   * @param fileName       name of the file to be written
   * @param downloadStream the two-dimensional string array to be written.
   * @param flag           whether append or overwrite.
   */
  protected void fileWriter(String fileName, String[][] downloadStream, boolean flag) {
    try {
      File csvFile = new File(fileName);
      FileWriter fileWriter = new FileWriter(csvFile, flag);
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
  }

  /**
   * a method that writes into the sell history file.
   *
   * @param fileName       name of the file.
   * @param downloadStream the two-dimensional string array to be written.
   * @param flag           whether append or overwrite.
   */
  protected void fileWriterForSell(String fileName, String[][] downloadStream, boolean flag) {
    try {
      int counter = 1;
      File csvFile = new File(fileName);
      FileWriter fileWriter = new FileWriter(csvFile, flag);
      for (String[] data : downloadStream) {
        if (counter < downloadStream.length) {
          StringBuilder line = new StringBuilder();
          for (int i = 0; i < data.length; i++) {
            line.append(data[i]);
            if (i != data.length - 1) {
              line.append(',');
            }
          }
          line.append("\n");
          fileWriter.write(line.toString());
        } else {
          StringBuilder line = new StringBuilder();
          for (int i = 0; i < data.length; i++) {
            line.append(data[i]);
            if (i != data.length - 1) {
              line.append(',');
            }
          }
          fileWriter.write(line.toString());
        }
        counter = counter + 1;
      }
      fileWriter.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
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

  /**
   * a method to get the value of the portfolio for plotting the bar chart.
   *
   * @param portfolioName name of the portfolio.
   * @param valueOnDate   value of the portfolio on a particular date.
   * @param api           API call object to obtain the price of the stock at a particular date.
   * @return returns the value of the portfolio on a particular date.
   */

  protected double getFlexPortfolioValue(String portfolioName, LocalDate valueOnDate, API api) {
    int qty = 0;
    double currentPrice;
    double value = 0;
    String headerLine = "";
    String line = "";
    String splitBy = ",";
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("FlexiblePortfolioPurchaseHistory.csv"));
      headerLine = br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        if (data[0].equals(portfolioName)) {
          String str = data[1];
          qty = (int) Float.parseFloat(data[2]);
          currentPrice = api.stockCurrentValueFromAPI(str, valueOnDate);
          while (currentPrice == 0) {
            valueOnDate = valueOnDate.plusDays(1);
            currentPrice = api.stockCurrentValueFromAPI(str, valueOnDate);
          }
          value = value + (qty * currentPrice);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return value;
  }

  /**
   * helper method to parse the date into proper format.
   *
   * @param date input date.
   * @return parsed date.
   */
  protected LocalDate parseDate(String date) {
    try {
      return LocalDate.parse(date);
    } catch (Exception e) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm/dd/YYYY");
      return LocalDate.parse(date, formatter);
    }
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
   * a helper method to make the transactions based on the dollar cost average setup.
   */
  private void makeAutoPurchase() {
    API api = new APIImpl();
    String[][] writeStream = new String[1][7];
    Map<String, LocalDate> updateData = new HashMap<>();
    updateData.clear();
    String line = "";
    String splitBy = ",";
    LocalDate startDate = null;
    LocalDate endDate = null;
    LocalDate tempDate = null;
    int frequency;
    int flag = 0;

    try {
      BufferedReader br = new BufferedReader(new FileReader("DollarCostAverageData.csv"));
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        if (data[8].equals("null")) {
          startDate = parseDate(data[3].substring(1));
          endDate = parseDate(data[4].substring(1));
          tempDate = startDate;
          frequency = Integer.parseInt(data[5]);
          LocalDate transactionDate = null;
          while (tempDate.isBefore(getTodayDate()) && tempDate.isBefore(endDate)) {
            double purchasePrice = api.stockCurrentValueFromAPI(data[1], tempDate);
            while (purchasePrice == 0) {
              tempDate = tempDate.plusDays(1);
              if(tempDate.isBefore(getTodayDate()) && tempDate.isBefore(endDate)){
                purchasePrice = api.stockCurrentValueFromAPI(data[1], tempDate);
              }else{
                break;
              }
            }
            if(purchasePrice==0){
              break;
            }
            float amount = Float.parseFloat(data[6]);
            float weight = Float.parseFloat(data[2]);
            double qty = ((amount * weight) / 100) / purchasePrice;
            float quantity = (float) qty;

            quantity = parseToTwoDecimal(quantity);

            writeStream[0][0] = data[0]; //pf
            writeStream[0][1] = data[1]; //ticker
            writeStream[0][2] = "" + quantity; //quantity
            writeStream[0][3] = "" + purchasePrice; //purchase price
            writeStream[0][4] = "'" + tempDate; //purchase date
            writeStream[0][5] = data[7]; //purchase comm
            writeStream[0][6] = UUID.randomUUID().toString(); //unique ID
            fileWriter("FlexiblePortfolioCurrentComposition.csv", writeStream, true);
            fileWriter("FlexiblePortfolioPurchaseHistory.csv", writeStream, true);

            transactionDate = tempDate;
            tempDate = tempDate.plusDays(frequency);
            flag = 1;
          }

          // store the update in hashmap and update the DollarCostAverageData.csv with new
          // LastTransaction date = transactionDate;
          if (flag == 1) {
            updateData.put(data[9], transactionDate);
          }
        }
        if (!data[8].equals("null")) {
          startDate = parseDate(data[3].substring(1));
          endDate = parseDate(data[4].substring(1));
          frequency = Integer.parseInt(data[5]);
          tempDate = parseDate(data[8].substring(1));
          tempDate = tempDate.plusDays(frequency);
          LocalDate transactionDate = parseDate(data[8].substring(1));
          while (tempDate.isBefore(getTodayDate()) && tempDate.isBefore(endDate)) {
            double purchasePrice = api.stockCurrentValueFromAPI(data[1], tempDate);
            while (purchasePrice == 0) {
              tempDate = tempDate.plusDays(1);
              if(tempDate.isBefore(getTodayDate()) && tempDate.isBefore(endDate)){
                purchasePrice = api.stockCurrentValueFromAPI(data[1], tempDate);
              }else{
                break;
              }
            }
            if(purchasePrice==0){
              break;
            }
            int amount = Integer.parseInt(data[6]);
            int weight = Integer.parseInt(data[2]);
            double qty = ((amount * weight) / 100) / purchasePrice;
            float quantity = (float) qty;
            quantity = parseToTwoDecimal(quantity);

            writeStream[0][0] = data[0]; //pf
            writeStream[0][1] = data[1]; //ticker
            writeStream[0][2] = "" + quantity; //quantity
            writeStream[0][3] = "" + purchasePrice; //purchase price
            writeStream[0][4] = "'" + tempDate; //purchase date
            writeStream[0][5] = data[7]; //purchase comm
            writeStream[0][6] = UUID.randomUUID().toString(); //unique ID
            fileWriter("FlexiblePortfolioCurrentComposition.csv", writeStream, true);
            fileWriter("FlexiblePortfolioPurchaseHistory.csv", writeStream, true);

            transactionDate = tempDate;
            tempDate = tempDate.plusDays(frequency);
            flag = 1;
          }
          // store the update in hashmap and update the DollarCostAverageData.csv with new
          // LastTransaction date = transactionDate;
          if (flag == 1) {
            updateData.put(data[9], transactionDate);
          }
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    updateDollarCostAverageCSV(updateData);
  }

  /**
   * a helper method to update the dollar cost average last transaction date. it updates the last
   * transaction date based on the dollar cost average setup.
   *
   * @param updateData the data that needs to be udpated.
   */
  private void updateDollarCostAverageCSV(Map<String, LocalDate> updateData) {
    int counter = 0;
    int i = 1;
    String result = "";
    String line = "";
    String splitBy = ",";
    LocalDate date;
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("DollarCostAverageData.csv"));
      while ((line = br.readLine()) != null) {
        counter = counter + 1;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    String[][] writeStream = new String[counter][10];
    writeStream[0][0] = "Portfolio Name";
    writeStream[0][1] = "Ticker";
    writeStream[0][2] = "Weight";
    writeStream[0][3] = "Start Date";
    writeStream[0][4] = "End Date";
    writeStream[0][5] = "Frequency";
    writeStream[0][6] = "Amount";
    writeStream[0][7] = "Purchase Comm";
    writeStream[0][8] = "Last Transaction Date";
    writeStream[0][9] = "Unique ID";

    try {
      BufferedReader br = new BufferedReader(
          new FileReader("DollarCostAverageData.csv"));
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        writeStream[i][0] = data[0];
        writeStream[i][1] = data[1];
        writeStream[i][2] = data[2];
        writeStream[i][3] = data[3];
        writeStream[i][4] = data[4];
        writeStream[i][5] = data[5];
        writeStream[i][6] = data[6];
        writeStream[i][7] = data[7];
        writeStream[i][8] = data[8];
        writeStream[i][9] = data[9];

        for (Map.Entry<String, LocalDate> entry : updateData.entrySet()) {
          String key = entry.getKey();
          if (key.equals(data[9])) {
            writeStream[i][8] = "'" + entry.getValue();
          }
        }
        i = i + 1;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    fileWriterForSell("DollarCostAverageData.csv", writeStream, false);

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

}


