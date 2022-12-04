import static org.junit.Assert.assertEquals;

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

}