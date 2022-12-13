import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import brokerage.API;
import brokerage.APIImpl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.Test;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioStrategyModelImpl;

/**
 * This class is testing the model functionalities of Portfolio strategy. It contains the test cases
 * for the PortfolioStrategyModelImpl.
 */

public class PortfolioStrategyModelImplTest {

  // create a weighted portfolio with multiple stocks
  @Test
  public void testCreateWeightedPF() {
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    String portfolioName = "testWeightedPF1";
    String ticker1 = "AAPL";
    String ticker2 = "GOOGL";
    String ticker3 = "TSLA";
    float weight1 = 40;
    float weight2 = 30;
    float weight3 = 30;

    Map<String, Float> stockWeightMap = new HashMap<>();
    stockWeightMap.put(ticker1, weight1);
    stockWeightMap.put(ticker2, weight2);
    stockWeightMap.put(ticker3, weight3);

    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("\nPortfolio Name: " + portfolioName);
    expectedOutput.append("\nTicker: " + ticker2 + ", Weight: " + weight2);
    expectedOutput.append("\nTicker: " + ticker1 + ", Weight: " + weight1);
    expectedOutput.append("\nTicker: " + ticker3 + ", Weight: " + weight3);
    pm.createWeightedPf(portfolioName, stockWeightMap);

    String result = pm.displayWeightedPf(portfolioName);
    assertEquals(expectedOutput.toString(), result);
  }

  // Test that weighted portfolio total weight cannot be greater than 100.
  @Test
  public void testCreateWeightedPFWeightCannotBeMoreThan100() {
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    String portfolioName = "testWeightedPF";
    String ticker1 = "AAPL";
    String ticker2 = "GOOGL";
    String ticker3 = "TSLA";
    float weight1 = 40;
    float weight2 = 30;
    float weight3 = 31;

    Map<String, Float> stockWeightMap = new HashMap<>();
    stockWeightMap.put(ticker1, weight1);
    stockWeightMap.put(ticker2, weight2);
    stockWeightMap.put(ticker3, weight3);
    assertEquals("Total weight cannot be greater than 100. "
            + "Weighted portfolio cannot be created!!!",
        pm.createWeightedPf(portfolioName, stockWeightMap));
  }

  // test that weights can be fractional
  @Test
  public void testCreateWeightedPFAcceptFractionalWeight() {
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    String portfolioName = "testWeightedPF3";
    String ticker1 = "AAPL";
    String ticker2 = "GOOGL";
    String ticker3 = "TSLA";
    float weight1 = 40;
    float weight2 = 30.75f;
    float weight3 = 29.25f;

    Map<String, Float> stockWeightMap = new HashMap<>();
    stockWeightMap.put(ticker1, weight1);
    stockWeightMap.put(ticker2, weight2);
    stockWeightMap.put(ticker3, weight3);

    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("\nPortfolio Name: " + portfolioName);
    expectedOutput.append("\nTicker: " + ticker2 + ", Weight: " + weight2);
    expectedOutput.append("\nTicker: " + ticker1 + ", Weight: " + weight1);
    expectedOutput.append("\nTicker: " + ticker3 + ", Weight: " + weight3);
    pm.createWeightedPf(portfolioName, stockWeightMap);

    String result = pm.displayWeightedPf(portfolioName);
    assertEquals(expectedOutput.toString(), result);
  }

  // invest into a weighted portfolio
  @Test
  public void testInvestIntoWeightedPortfolio() {
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    API api = new APIImpl();
    String portfolioName = "testWeightedPF1";
    String result = pm.investInWeightedPf(portfolioName, parseDate("2022-11-28"),
        2000, 100, api);
    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("$2000.0 successfully invested in testWeightedPF1");
    expectedOutput.append("\n" + "Portfolio Name: " + portfolioName);
    expectedOutput.append("\nList of Stocks in " + portfolioName + " portfolio are:");
    expectedOutput.append("\nTicker: GOOGL");
    expectedOutput.append("\nQuantity: 6.25");
    expectedOutput.append("\nPurchase Price: $96.05");
    expectedOutput.append("\nPurchase Date: 2022-11-28");
    expectedOutput.append("\nPurchase Commission: $33.33\n");
    expectedOutput.append("\nTicker: AAPL");
    expectedOutput.append("\nQuantity: 5.55");
    expectedOutput.append("\nPurchase Price: $144.22");
    expectedOutput.append("\nPurchase Date: 2022-11-28");
    expectedOutput.append("\nPurchase Commission: $33.33\n");
    expectedOutput.append("\nTicker: TSLA");
    expectedOutput.append("\nQuantity: 3.28");
    expectedOutput.append("\nPurchase Price: $182.92");
    expectedOutput.append("\nPurchase Date: 2022-11-28");
    expectedOutput.append("\nPurchase Commission: $33.33\n");
    assertEquals(expectedOutput.toString(), result);
  }


  // set up dollar cost averaging
  @Test
  public void setUpDollarCostAverage() {
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    API api = new APIImpl();
    String portfolioName = "TEST";
    String ticker1 = "AAPL";
    String ticker2 = "TSLA";
    float weight1 = 50;
    float weight2 = 50;
    LocalDate startDate = parseDate("2022-11-01");
    LocalDate endDate = parseDate("2022-11-15");
    int frequency = 5;
    float amount = 1000;
    double purchaseCom = 10;
    Map<String, Float> stockWeightMap = new HashMap<>();
    stockWeightMap.put(ticker1, weight1);
    stockWeightMap.put(ticker2, weight2);
    String result = pm.setUpDollarCostAverage(portfolioName, stockWeightMap, startDate,
        endDate, frequency, amount, purchaseCom, api);
    assertEquals(portfolioName +
        " portfolio successfully scheduled for dollar cost averaging!", result);
  }

  // set up dollar cost averaging - Start date cannot be after end date
  @Test
  public void setUpDollarCostAverageStartDateAfterEndDate() {
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    API api = new APIImpl();
    String portfolioName = "TEST";
    String ticker1 = "AAPL";
    String ticker2 = "TSLA";
    float weight1 = 50;
    float weight2 = 50;
    LocalDate startDate = parseDate("2022-11-15");
    LocalDate endDate = parseDate("2022-11-14");
    int frequency = 5;
    float amount = 1000;
    double purchaseCom = 10;
    Map<String, Float> stockWeightMap = new HashMap<>();
    stockWeightMap.put(ticker1, weight1);
    stockWeightMap.put(ticker2, weight2);
    String result = pm.setUpDollarCostAverage(portfolioName, stockWeightMap, startDate,
        endDate, frequency, amount, purchaseCom, api);
    assertEquals("Start Date: 2022-11-15 cannot be after End Date: 2022-11-14. "
        + "Weighted portfolio cannot be created!!!", result);
  }

  // dollar cost averaging - frequency cannot be less than difference between start and end date
  @Test
  public void setUpDollarCostAverageFreqGreaterThanDifference() {
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    API api = new APIImpl();
    String portfolioName = "TEST";
    String ticker1 = "AAPL";
    String ticker2 = "TSLA";
    float weight1 = 50;
    float weight2 = 50;
    LocalDate startDate = parseDate("2022-11-01");
    LocalDate endDate = parseDate("2022-11-10");
    int frequency = 10;
    float amount = 1000;
    double purchaseCom = 10;
    Period period = Period.between(startDate, endDate);
    int diff = Math.abs(period.getDays());
    Map<String, Float> stockWeightMap = new HashMap<>();
    stockWeightMap.put(ticker1, weight1);
    stockWeightMap.put(ticker2, weight2);
    String result = pm.setUpDollarCostAverage(portfolioName, stockWeightMap, startDate,
        endDate, frequency, amount, purchaseCom, api);
    assertEquals("Difference between start date and end date is: 9. "
        + "Frequency provided is: 10. Weighted portfolio cannot be created!!!", result);
  }

  // dollar cost averaging - total weight cannot be greater than 100.
  @Test
  public void setUpDollarCostAverageWeightGreaterThanDifference() {
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    API api = new APIImpl();
    String portfolioName = "TEST";
    String ticker1 = "AAPL";
    String ticker2 = "TSLA";
    float weight1 = 50;
    float weight2 = 51;
    LocalDate startDate = parseDate("2022-11-01");
    LocalDate endDate = parseDate("2022-11-10");
    int frequency = 11;
    float amount = 1000;
    double purchaseCom = 10;
    Period period = Period.between(startDate, endDate);
    int diff = Math.abs(period.getDays());
    Map<String, Float> stockWeightMap = new HashMap<>();
    stockWeightMap.put(ticker1, weight1);
    stockWeightMap.put(ticker2, weight2);
    String result = pm.setUpDollarCostAverage(portfolioName, stockWeightMap, startDate,
        endDate, frequency, amount, purchaseCom, api);
    assertEquals("Total weight cannot be greater than 100. "
        + "Weighted portfolio cannot be created!!!", result);
  }

  // get cost basis of a dollar cost average at a certain date
  @Test
  public void getCostBasisDollarCostAverageOnDate1() {
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    String result = pm.getCostBasis("TEST", parseDate("2022-11-28"));
    assertEquals("The cost basis of portfolio TEST on date 2022-11-28 is: $3029.77", result);
  }

  @Test
  public void getCostBasisDollarCostAverageDate2() {
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    String result = pm.getCostBasis("TEST", parseDate("2022-11-15"));
    assertEquals("The cost basis of portfolio TEST on date 2022-11-15 is: $3029.77", result);
  }

  // get value of a dollar cost average at a certain date
  @Test
  public void getValueDollarCostAverage() {
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    API api = new APIImpl();
    String result = pm.displayFlexPortfolioValue("TEST", parseDate("2022-11-28"), api);
    assertEquals("The total value of the TEST portfolio on date 2022-11-28 is: $2828.48", result);
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



  @Test
  public void testRebalancing(){
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    API api = new APIImpl();
    pm.createListOfStockForFlex("AAPL", 20, parseDate("2022-11-01"), 10, api);
    pm.createListOfStockForFlex("GOOGL",20,parseDate("2022-11-01"),10,api);
    pm.createPortfolio("test re-balance");
    String stringValue = pm.displayFlexPortfolioValue("test re-balance", parseDate("2022-12-09"), api);
    int indexOfDollar = stringValue.lastIndexOf("$");
    Double valueOfPortfolio = Double.parseDouble(stringValue.substring(indexOfDollar+1));
    HashMap<String, Double> hm = new HashMap<>();
    hm.put("GOOGL",50.0);
    hm.put("AAPL",50.0);
    PortfolioStrategyModel pm1 = new PortfolioStrategyModelImpl();
    pm1.reBalancePortfolio("test re-balance",parseDate("2022-12-09"),hm,api);
    String stringValueAfter = pm.displayFlexPortfolioValue("test re-balance", parseDate("2022-12-09"), api);
    int indexOfDollarAfter = stringValueAfter.lastIndexOf("$");
    Double valueOfPortfolioAfter = Double.parseDouble(stringValueAfter.substring(indexOfDollarAfter+1));
    assertEquals(valueOfPortfolio, valueOfPortfolioAfter, 0.5);
  }

  @Test
  public void testRebalancingStockQuantity(){
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    API api = new APIImpl();
    pm.createListOfStockForFlex("AAPL", 20, parseDate("2022-11-01"), 10, api);
    pm.createListOfStockForFlex("GOOGL",20,parseDate("2022-11-01"),10,api);
    pm.createPortfolio("test re-balance1");
    String stringValue = pm.displayFlexPortfolioValue("test re-balance1", parseDate("2022-12-09"), api);
    int indexOfDollar = stringValue.lastIndexOf("$");
    Double valueOfPortfolio = Double.parseDouble(stringValue.substring(indexOfDollar+1));
    HashMap<String, Double> hm = new HashMap<>();
    hm.put("GOOGL",50.0);
    hm.put("AAPL",50.0);
    PortfolioStrategyModel pm1 = new PortfolioStrategyModelImpl();
    pm1.reBalancePortfolio("test re-balance1",parseDate("2022-12-09"),hm,api);
    String stringValueAfter = pm.displayFlexPortfolioValue("test re-balance1", parseDate("2022-12-09"), api);
    int indexOfDollarAfter = stringValueAfter.lastIndexOf("$");
    Double valueOfPortfolioAfter = Double.parseDouble(stringValueAfter.substring(indexOfDollarAfter+1));
    assertEquals(valueOfPortfolio, valueOfPortfolioAfter, 0.5);
    String compostion = pm.examinePortfolioByDate("test re-balance1", parseDate("2022-12-09"));
    String[] compositionSplit = compostion.split("\n");
    HashMap<String, Double> hm1 = new HashMap<>();
    for(int i=4; i<compositionSplit.length;i++){
      String[] tickerRow = compositionSplit[i].split("\t");
      hm1.put(tickerRow[0], hm1.getOrDefault(tickerRow[0],0.0)+Double.parseDouble(tickerRow[1]));
    }
    assertEquals(hm1.get("GOOGL"), 25.31,0.1);
    assertEquals(hm1.get("AAPL"), 16.5,0.1);
    assertEquals(hm1.get("GOOGL")* api.stockCurrentValueFromAPI("GOOGL",parseDate("2022-12-09")),valueOfPortfolioAfter/2,1);
    assertEquals(hm1.get("AAPL")* api.stockCurrentValueFromAPI("AAPL",parseDate("2022-12-09")),valueOfPortfolioAfter/2,1);
  }

  @Test
  public void testRebalancingStockQuantityDifferentWeights(){
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    API api = new APIImpl();
    pm.createListOfStockForFlex("AAPL", 20, parseDate("2022-11-01"), 10, api);
    pm.createListOfStockForFlex("GOOGL",20,parseDate("2022-11-01"),10,api);
    pm.createPortfolio("test re-balance2");
    String stringValue = pm.displayFlexPortfolioValue("test re-balance2", parseDate("2022-12-09"), api);
    int indexOfDollar = stringValue.lastIndexOf("$");
    Double valueOfPortfolio = Double.parseDouble(stringValue.substring(indexOfDollar+1));
    HashMap<String, Double> hm = new HashMap<>();
    hm.put("GOOGL",25.0);
    hm.put("AAPL",75.0);
    PortfolioStrategyModel pm1 = new PortfolioStrategyModelImpl();
    pm1.reBalancePortfolio("test re-balance2",parseDate("2022-12-09"),hm,api);
    String stringValueAfter = pm.displayFlexPortfolioValue("test re-balance2", parseDate("2022-12-09"), api);
    int indexOfDollarAfter = stringValueAfter.lastIndexOf("$");
    Double valueOfPortfolioAfter = Double.parseDouble(stringValueAfter.substring(indexOfDollarAfter+1));
    assertEquals(valueOfPortfolio, valueOfPortfolioAfter, 1);
    String compostion = pm.examinePortfolioByDate("test re-balance2", parseDate("2022-12-09"));
    String[] compositionSplit = compostion.split("\n");
    HashMap<String, Double> hm1 = new HashMap<>();
    for(int i=4; i<compositionSplit.length;i++){
      String[] tickerRow = compositionSplit[i].split("\t");
      hm1.put(tickerRow[0], hm1.getOrDefault(tickerRow[0],0.0)+Double.parseDouble(tickerRow[1]));
    }
    assertEquals(hm1.get("GOOGL"), 12.6,0.1);
    assertEquals(hm1.get("AAPL"), 24.79,0.1);
    assertEquals(hm1.get("GOOGL")* api.stockCurrentValueFromAPI("GOOGL",parseDate("2022-12-09")),valueOfPortfolioAfter*(0.25),1);
    assertEquals(hm1.get("AAPL")* api.stockCurrentValueFromAPI("AAPL",parseDate("2022-12-09")),valueOfPortfolioAfter*(0.75),1);
  }

  @Test
  public void testRebalancingStockQuantityDifferentWeightsManyStocks(){
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    API api = new APIImpl();
    pm.createListOfStockForFlex("AAPL", 20, parseDate("2022-11-01"), 10, api);
    pm.createListOfStockForFlex("GOOGL",20,parseDate("2022-11-01"),10,api);
    pm.createListOfStockForFlex("TSLA",20,parseDate("2022-11-01"),10,api);
    pm.createListOfStockForFlex("MSFT",20,parseDate("2022-11-01"),10,api);
    pm.createPortfolio("test re-balance3");
    String stringValue = pm.displayFlexPortfolioValue("test re-balance3", parseDate("2022-12-09"), api);
    int indexOfDollar = stringValue.lastIndexOf("$");
    Double valueOfPortfolio = Double.parseDouble(stringValue.substring(indexOfDollar+1));
    HashMap<String, Double> hm = new HashMap<>();
    hm.put("GOOGL",20.0);
    hm.put("AAPL",30.0);
    hm.put("TSLA",15.0);
    hm.put("MSFT",35.0);
    PortfolioStrategyModel pm1 = new PortfolioStrategyModelImpl();
    pm1.reBalancePortfolio("test re-balance3",parseDate("2022-12-09"),hm,api);
    String stringValueAfter = pm.displayFlexPortfolioValue("test re-balance3", parseDate("2022-12-09"), api);
    int indexOfDollarAfter = stringValueAfter.lastIndexOf("$");
    Double valueOfPortfolioAfter = Double.parseDouble(stringValueAfter.substring(indexOfDollarAfter+1));
    assertEquals(valueOfPortfolio, valueOfPortfolioAfter, 1);
    String compostion = pm.examinePortfolioByDate("test re-balance3", parseDate("2022-12-09"));
    String[] compositionSplit = compostion.split("\n");
    HashMap<String, Double> hm1 = new HashMap<>();
    for(int i=4; i<compositionSplit.length;i++){
      String[] tickerRow = compositionSplit[i].split("\t");
      hm1.put(tickerRow[0], hm1.getOrDefault(tickerRow[0],0.0)+Double.parseDouble(tickerRow[1]));
    }
    assertEquals(hm1.get("GOOGL"), 28.42,0.1);
    assertEquals(hm1.get("AAPL"), 27.83,0.1);
    assertEquals(hm1.get("TSLA"), 11.04,0.1);
    assertEquals(hm1.get("MSFT"), 18.8,0.1);
    assertEquals(hm1.get("GOOGL")* api.stockCurrentValueFromAPI("GOOGL",parseDate("2022-12-09")),valueOfPortfolioAfter*(0.2),1);
    assertEquals(hm1.get("AAPL")* api.stockCurrentValueFromAPI("AAPL",parseDate("2022-12-09")),valueOfPortfolioAfter*(0.30),1);
    assertEquals(hm1.get("TSLA")* api.stockCurrentValueFromAPI("TSLA",parseDate("2022-12-09")),valueOfPortfolioAfter*(0.15),1);
    assertEquals(hm1.get("MSFT")* api.stockCurrentValueFromAPI("MSFT",parseDate("2022-12-09")),valueOfPortfolioAfter*(0.35),1);
  }


  @Test
  public void testRebalancingNoPortfolio(){
    API api = new APIImpl();
    HashMap<String, Double> hm = new HashMap<>();
    hm.put("GOOGL",50.0);
    hm.put("AAPL",50.0);
    PortfolioStrategyModel pm1 = new PortfolioStrategyModelImpl();
    String s = pm1.reBalancePortfolio("klsjfcpwp",parseDate("2022-12-09"),hm,api);
    assertEquals("No portfolio exist", s);
  }

  @Test
  public void testRebalancingOnHoliday(){
    API api = new APIImpl();
    HashMap<String, Double> hm = new HashMap<>();
    hm.put("GOOGL",50.0);
    hm.put("AAPL",50.0);
    PortfolioStrategyModel pm1 = new PortfolioStrategyModelImpl();
    String s = pm1.reBalancePortfolio("FANG",parseDate("2022-12-10"),hm,api);
    assertNotEquals("Re-balancing done successfully", s);
  }

  @Test
  public void testRebalancingStocksNotPresent(){
    API api = new APIImpl();
    HashMap<String, Double> hm = new HashMap<>();
    hm.put("GOOGL",50.0);
    hm.put("VZ",50.0);
    PortfolioStrategyModel pm1 = new PortfolioStrategyModelImpl();
    String s = pm1.reBalancePortfolio("FANG",parseDate("2022-12-10"),hm,api);
    assertEquals("All the stocks were not weighted", s);
  }

  @Test
  public void testRebalancingWeightsNotEqual100(){
    API api = new APIImpl();
    HashMap<String, Double> hm = new HashMap<>();
    hm.put("GOOGL",50.0);
    PortfolioStrategyModel pm1 = new PortfolioStrategyModelImpl();
    String s = pm1.reBalancePortfolio("FANG",parseDate("2022-12-10"),hm,api);
    assertEquals("The Total % of weights were not equal to 100%. Please provide proper weightage.", s);
  }



}