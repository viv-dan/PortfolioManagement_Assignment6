import static org.junit.Assert.assertEquals;

import brokerage.API;
import brokerage.APIImpl;
import brokerage.PortfolioFlexBrokerageModel;
import brokerage.PortfolioFlexModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * This class is testing the model functionalities for Flexible Portfolio. It contains the test
 * cases for the PortfolioFlexBrokerageModel.
 */
public class PortfolioFlexBrokerageModelTest {

  // test to create portfolio with one stock
  @Test
  public void testCreatePortfolioWithOneStock() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    API api = new APIImpl();
    String portfolioName = "testPortfolio1";
    String ticker = "AAPL";
    int quantity = 10;
    LocalDate purchaseDate = LocalDate.parse("2022-11-16",
        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Double purchaseCom = 10.05;
    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("\n============= FLEXIBLE PORTFOLIOS ===================");
    expectedOutput.append("\nPortfolio\tTicker\tQuantity\tPurchase Price\tPurchase Date");
    expectedOutput.append("\ntestPortfolio1\t\t\t\tAAPL\t\t\t\t10\t\t\t\t148.79\t\t\t\t2022-11-16");
    pm.createListOfStockForFlex(ticker, quantity, purchaseDate, purchaseCom, api);
    pm.createPortfolio(portfolioName);
    String result = pm.examineAllPortfolios(portfolioName);
    assertEquals(expectedOutput.toString(), result);
  }

  // test with invalid purchase date
  @Test
  public void testCreatePortfolioInvalidDate() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    API api = new APIImpl();
    String portfolioName = "testPortfolio1";
    String ticker = "AAPL";
    int quantity = 10;
    LocalDate purchaseDate = LocalDate.parse("11/16/2022",
        DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    Double purchaseCom = 10.05;
    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("\n============= FLEXIBLE PORTFOLIOS ===================");
    expectedOutput.append("\nPortfolio\tTicker\tQuantity\tPurchase Price\tPurchase Date");
    expectedOutput.append("\ntestPortfolio1\t\t\t\tAAPL\t\t\t\t10\t\t\t\t148.79\t\t\t\t2022-11-16");
    pm.createListOfStockForFlex(ticker, quantity, purchaseDate, purchaseCom, api);
    pm.createPortfolio(portfolioName);
    String result = pm.examineAllPortfolios(portfolioName);
    assertEquals(expectedOutput.toString(), result);
  }

  // test to create portfolio with multiple stock
  @Test
  public void testCreatePortfolioWithMultipleStock() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    API api = new APIImpl();
    String portfolioName = "testPortfolio2";
    String ticker1 = "AAPL";
    int quantity1 = 10;
    LocalDate purchaseDate1 = LocalDate.parse("2022-11-16",
        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Double purchaseCom1 = 10.05;
    String ticker2 = "TSLA";
    int quantity2 = 10;
    LocalDate purchaseDate2 = LocalDate.parse("2022-11-16",
        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Double purchaseCom2 = 11.05;

    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("\n============= FLEXIBLE PORTFOLIOS ===================");
    expectedOutput.append("\nPortfolio\tTicker\tQuantity\tPurchase Price\tPurchase Date");
    expectedOutput.append("\ntestPortfolio2\t\t\t\tAAPL\t\t\t\t10\t\t\t\t148.79\t\t\t\t2022-11-16");
    expectedOutput.append("\ntestPortfolio2\t\t\t\tTSLA\t\t\t\t10\t\t\t\t186.92\t\t\t\t2022-11-16");
    pm.createListOfStockForFlex(ticker1, quantity1, purchaseDate1, purchaseCom1, api);
    pm.createListOfStockForFlex(ticker2, quantity2, purchaseDate2, purchaseCom2, api);
    pm.createPortfolio(portfolioName);
    String result = pm.examineAllPortfolios(portfolioName);
    assertEquals(expectedOutput.toString(), result);
  }

  // test to create multiple portfolios
  @Test
  public void testCreateMultiplePortfolios() {
    PortfolioFlexModel pm1 = new PortfolioFlexBrokerageModel();
    PortfolioFlexModel pm2 = new PortfolioFlexBrokerageModel();
    API api = new APIImpl();
    String portfolioName1 = "testPortfolio3";
    String portfolioName2 = "testPortfolio4";
    String ticker1 = "AAPL";
    int quantity1 = 10;
    LocalDate purchaseDate1 = LocalDate.parse("2022-11-16",
        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Double purchaseCom1 = 10.05;
    String ticker2 = "TSLA";
    int quantity2 = 10;
    LocalDate purchaseDate2 = LocalDate.parse("2022-11-16",
        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Double purchaseCom2 = 10.05;

    StringBuilder expectedOutput1 = new StringBuilder();
    StringBuilder expectedOutput2 = new StringBuilder();
    expectedOutput1.append("\n============= FLEXIBLE PORTFOLIOS ===================");
    expectedOutput1.append("\nPortfolio\tTicker\tQuantity\tPurchase Price\tPurchase Date");
    expectedOutput1.append(
        "\ntestPortfolio3\t\t\t\tAAPL\t\t\t\t10\t\t\t\t148.79\t\t\t\t2022-11-16");
    expectedOutput2.append("\n============= FLEXIBLE PORTFOLIOS ===================");
    expectedOutput2.append("\nPortfolio\tTicker\tQuantity\tPurchase Price\tPurchase Date");
    expectedOutput2.append(
        "\ntestPortfolio4\t\t\t\tTSLA\t\t\t\t10\t\t\t\t186.92\t\t\t\t2022-11-16");

    pm1.createListOfStockForFlex(ticker1, quantity1, purchaseDate1, purchaseCom1, api);
    pm1.createPortfolio(portfolioName1);
    pm2.createListOfStockForFlex(ticker2, quantity2, purchaseDate2, purchaseCom2, api);
    pm2.createPortfolio(portfolioName2);
    String result1 = pm1.examineAllPortfolios(portfolioName1);
    String result2 = pm2.examineAllPortfolios(portfolioName2);
    assertEquals(expectedOutput1.toString(), result1);
    assertEquals(expectedOutput2.toString(), result2);
  }

  // test to purchase and add stock to a portfolio
  @Test
  public void testPurchaseAndAddStockToPortfolio() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    API api = new APIImpl();
    String portfolioName = "testPortfolio1";
    String ticker = "TSLA";
    int quantity = 10;
    LocalDate purchaseDate = LocalDate.parse("2022-11-16",
        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Double purchaseCom = 11.05;
    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("\n============= FLEXIBLE PORTFOLIOS ===================");
    expectedOutput.append("\nPortfolio\tTicker\tQuantity\tPurchase Price\tPurchase Date");
    expectedOutput.append("\ntestPortfolio1\t\t\t\tAAPL\t\t\t\t10\t\t\t\t148.79\t\t\t\t2022-11-16");
    expectedOutput.append("\ntestPortfolio1\t\t\t\tTSLA\t\t\t\t10\t\t\t\t186.92\t\t\t\t2022-11-16");
    pm.createListOfStockForFlex(ticker, quantity, purchaseDate, purchaseCom, api);
    pm.createPortfolio(portfolioName);
    String result = pm.examineAllPortfolios(portfolioName);
    assertEquals(expectedOutput.toString(), result);
  }

  // test to get the cost basis of portfolio at a certain date
  @Test
  public void testCostBasisOfPortfolio() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    API api = new APIImpl();
    String portfolioName = "testPortfolio5";
    String ticker = "AAPL";
    int quantity = 10;
    LocalDate purchaseDate = LocalDate.parse("2022-11-16",
        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Double purchaseCom = 5.05;
    pm.createListOfStockForFlex(ticker, quantity, purchaseDate, purchaseCom, api);
    pm.createPortfolio(portfolioName);
    String result = pm.getCostBasis(portfolioName,
        LocalDate.parse("2022-11-16", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    String expectedOutput = "The cost basis of portfolio testPortfolio5 "
        + "on date 2022-11-16 is: $1502.78";
    assertEquals(expectedOutput, result);
  }

  // test to validate that the
  // cost basis of the portfolio is $0.00 before the date of its first purchase
  @Test
  public void testCostBasisOfPortfolioBeforePurchase() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    String portfolioName = "testPortfolio5";
    String result = pm.getCostBasis(portfolioName,
        LocalDate.parse("2022-11-15", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    String expectedOutput = "The cost basis of portfolio "
        + "testPortfolio5 on date 2022-11-15 is: $0.00";
    assertEquals(expectedOutput, result);
  }

  // test to get the value of portfolio at a certain date
  @Test
  public void testGetPortfolioValue() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    API api = new APIImpl();
    String portfolioName = "testPortfolio6";
    String ticker = "AAPL";
    int quantity = 10;
    LocalDate purchaseDate = LocalDate.parse("2022-11-16",
        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Double purchaseCom = 11.05;
    pm.createListOfStockForFlex(ticker, quantity, purchaseDate, purchaseCom, api);
    pm.createPortfolio(portfolioName);
    String result = pm.displayFlexPortfolioValue(portfolioName,
        LocalDate.parse("2022-11-16", DateTimeFormatter.ofPattern("yyyy-MM-dd")), api);
    String expectedOutput = "The total value of the testPortfolio6 on date 2022-11-16 is: $1487.9";
    assertEquals(expectedOutput, result);
  }

  // test to examine the portfolio value shows $0.00 before the date of its first purchase
  @Test
  public void testGetPortfolioValueBeforePurchase() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    API api = new APIImpl();
    String portfolioName = "testPortfolio6";
    String result = pm.displayFlexPortfolioValue(portfolioName,
        LocalDate.parse("2022-11-15", DateTimeFormatter.ofPattern("yyyy-MM-dd")), api);
    String expectedOutput = "The total value of the testPortfolio6 on date 2022-11-15 is: $0.00";
    assertEquals(expectedOutput, result);
  }

  // test to examine the composition of portfolio by a particular portfolio
  @Test
  public void testExaminePortfolioComposition() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    String portfolioName = "testPortfolio1";
    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("\n============= FLEXIBLE PORTFOLIOS ===================");
    expectedOutput.append("\nPortfolio\tTicker\tQuantity\tPurchase Price\tPurchase Date");
    expectedOutput.append("\ntestPortfolio1\t\t\t\tAAPL\t\t\t\t10\t\t\t\t148.79\t\t\t\t2022-11-16");
    expectedOutput.append("\ntestPortfolio1\t\t\t\tTSLA\t\t\t\t10\t\t\t\t186.92\t\t\t\t2022-11-16");
    String result = pm.examineAllPortfolios(portfolioName);
    assertEquals(expectedOutput.toString(), result);
  }

  // test to examine the portfolio composition at a particular date
  @Test
  public void testExaminePortfolioCompositionByDate() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    String portfolioName = "testPortfolio1";
    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("\n============= FLEXIBLE PORTFOLIOS ===================");
    expectedOutput.append("\nPortfolio\tTicker\tQuantity\tPurchase Price\tPurchase Date");
    expectedOutput.append("\ntestPortfolio1\t\t\t\tAAPL\t\t\t\t10\t\t\t\t148.79\t\t\t\t2022-11-16");
    expectedOutput.append("\ntestPortfolio1\t\t\t\tTSLA\t\t\t\t10\t\t\t\t186.92\t\t\t\t2022-11-16");
    String result = pm.examinePortfolioByDate(portfolioName,
        LocalDate.parse("2022-11-16", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    assertEquals(expectedOutput.toString(), result);
  }

  // test that the portfolio shows no stock exists before the date of its first purchase
  @Test
  public void testExaminePortfolioCompositionByDateBeforePurchase() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    String portfolioName = "testPortfolio1";
    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("\n============= FLEXIBLE PORTFOLIOS ===================");
    expectedOutput.append("\nPortfolio\tTicker\tQuantity\tPurchase Price\tPurchase Date");
    expectedOutput.append("\nNo Stock exist in portfolio testPortfolio1 on date 2022-11-15");
    String result = pm.examinePortfolioByDate(portfolioName,
        LocalDate.parse("2022-11-15", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    assertEquals(expectedOutput.toString(), result);
  }

  // test to validate that you can sell a stock after its purchase date.
  @Test
  public void testSellStockByDate() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    String portfolioName = "testPortfolio1";
    API api = new APIImpl();
    int result = pm.validateSellableQuantity(portfolioName,
        LocalDate.parse("2022-11-17", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        "AAPL", 5, api);
    assertEquals(0, result);
  }

  // test to validate that you cannot sell a stock before its purchase date.
  @Test
  public void testSellStockByDateBeforePurchase() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    String portfolioName = "testPortfolio1";
    API api = new APIImpl();
    int result = pm.validateSellableQuantity(portfolioName,
        LocalDate.parse("2022-11-15", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        "AAPL", 5, api);
    assertEquals(1, result);
  }

  // test to validate that you cannot sell a stock more than the available quantity
  @Test
  public void testSellStockQuantityInvalid() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    String portfolioName = "testPortfolio1";
    API api = new APIImpl();
    int result = pm.validateSellableQuantity(portfolioName,
        LocalDate.parse("2022-11-17", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        "AAPL", 50, api);
    assertEquals(1, result);
  }

  @Test
  public void testSellStockInputDateNotValid() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    String portfolioName = "testPortfolio1";
    API api = new APIImpl();
    int result = pm.validateSellableQuantity(portfolioName,
        LocalDate.parse("2022-11-15", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        "AAPL", 5, api);
    assertEquals(1, result);
  }

  @Test
  public void testPortfolioPerformance() {
    PortfolioFlexModel pm = new PortfolioFlexBrokerageModel();
    API api = new APIImpl();
    String portfolioName = "PF1";
    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("Performance of portfolio PF1 from 2022-01-01 to 2022-11-01 \n");
    expectedOutput.append("\nJAN  2022:   *************");
    expectedOutput.append("\nFEB  2022:   ************************");
    expectedOutput.append("\nMAR  2022:   *************************");
    expectedOutput.append("\nAPR  2022:   ***********************");
    expectedOutput.append("\nMAY  2022:   ***********************");
    expectedOutput.append("\nJUN  2022:   ********************");
    expectedOutput.append("\nJUL  2022:   **********************");
    expectedOutput.append("\nAUG  2022:   **********************");
    expectedOutput.append("\nSEP  2022:   ********************");
    expectedOutput.append("\nOCT  2022:   *********************\n");
    expectedOutput.append("\nScale    :   * = $1000\n");
    String result = pm.plotPerformanceOfPortfolio(portfolioName,
        LocalDate.parse("01/01/2022", DateTimeFormatter.ofPattern("MM/dd/yyyy")),
        LocalDate.parse("11/01/2022", DateTimeFormatter.ofPattern("MM/dd/yyyy")), api);
    assertEquals(expectedOutput.toString(), result);

  }

}