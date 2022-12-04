import static org.junit.Assert.assertEquals;

import brokerage.PortfolioBrokerageModel;
import brokerage.PortfolioModel;
import java.io.IOException;
import java.time.LocalDate;

import org.junit.Test;

/**
 * This class is testing the model functionalities for Inflexible Portfolio. It contains the test
 * cases for the PortfolioBrokerageModel.
 */
public class PortfolioBrokerageModelTest {

  // Test whether it allows to create portfolio manually by entering
  // portfolio name, ticker and quantity for one stock
  @Test
  public void testCreatePortfolioForOneStock() {
    PortfolioModel pm = new PortfolioBrokerageModel();
    String portfolioName = "PF1";
    String ticker = "AAPL";
    int quantity = 10;
    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("\nPortfolio successfully created!!!");
    expectedOutput.append("\nPortfolio Name: PF1");
    expectedOutput.append("\nList of Stocks in PF1 portfolio are:");
    expectedOutput.append("\nStock Ticker: AAPL");
    expectedOutput.append("\nStock Quantity: 10");
    pm.createListOfStock(ticker, quantity, LocalDate.now());
    String result = pm.createPortfolio(portfolioName);
    assertEquals(expectedOutput.toString(), result);
  }

  // Test whether it allows to create portfolio manually by entering
  // portfolio name, ticker and quantity for multiple stock
  @Test
  public void testCreatePortfolioForMultipleStock() {
    PortfolioModel pm = new PortfolioBrokerageModel();
    String portfolioName = "PF2";
    String ticker1 = "AAPL";
    int quantity1 = 10;
    String ticker2 = "TSLA";
    int quantity2 = 10;
    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("\nPortfolio successfully created!!!");
    expectedOutput.append("\nPortfolio Name: PF2");
    expectedOutput.append("\nList of Stocks in PF2 portfolio are:");
    expectedOutput.append("\nStock Ticker: AAPL");
    expectedOutput.append("\nStock Quantity: 10");
    expectedOutput.append("\nStock Ticker: TSLA");
    expectedOutput.append("\nStock Quantity: 10");
    pm.createListOfStock(ticker1, quantity1, LocalDate.now());
    pm.createListOfStock(ticker2, quantity2, LocalDate.now());
    String result = pm.createPortfolio(portfolioName);
    assertEquals(expectedOutput.toString(), result);
  }

  // test whether it allows to create multiple portfolios.
  @Test
  public void testCreateMultiplePortfolios() {
    PortfolioModel pm1 = new PortfolioBrokerageModel();
    PortfolioModel pm2 = new PortfolioBrokerageModel();
    String portfolioName1 = "PF3";
    String portfolioName2 = "PF4";
    String ticker1 = "AAPL";
    int quantity1 = 10;
    String ticker2 = "TSLA";
    int quantity2 = 10;
    StringBuilder expectedOutput1 = new StringBuilder();
    expectedOutput1.append("\nPortfolio successfully created!!!");
    expectedOutput1.append("\nPortfolio Name: PF3");
    expectedOutput1.append("\nList of Stocks in PF3 portfolio are:");
    expectedOutput1.append("\nStock Ticker: AAPL");
    expectedOutput1.append("\nStock Quantity: 10");

    StringBuilder expectedOutput2 = new StringBuilder();
    expectedOutput2.append("\nPortfolio successfully created!!!");
    expectedOutput2.append("\nPortfolio Name: PF4");
    expectedOutput2.append("\nList of Stocks in PF4 portfolio are:");
    expectedOutput2.append("\nStock Ticker: TSLA");
    expectedOutput2.append("\nStock Quantity: 10");

    pm1.createListOfStock(ticker1, quantity1, LocalDate.now());
    String result1 = pm1.createPortfolio(portfolioName1);
    assertEquals(expectedOutput1.toString(), result1);

    pm2.createListOfStock(ticker2, quantity2, LocalDate.now());
    String result2 = pm2.createPortfolio(portfolioName2);
    assertEquals(expectedOutput2.toString(), result2);

  }


  // test whether it prevents from editing existing portfolio
  @Test
  public void testCannotEditExistingPortfolio() throws IOException {
    PortfolioModel pm = new PortfolioBrokerageModel();
    String portfolioName1 = "PF1";
    String ticker1 = "AAPL";
    int quantity1 = 10;
    String ticker2 = "TSLA";
    int quantity2 = 10;

    pm.createListOfStock(ticker1, quantity1, LocalDate.now());
    pm.createPortfolio(portfolioName1);
    pm.createListOfStock(ticker2, quantity2, LocalDate.now());
    int i = pm.validatePortfolioExist(portfolioName1);
    assertEquals(1, i);

  }

  // test it allows to create portfolio through a file upload
  @Test
  public void testCreatePortfolioThroughFileUpload() {
    PortfolioModel pm = new PortfolioBrokerageModel();
    StringBuilder expectedOutput = new StringBuilder();
    String filePath = "uploadfile1.csv";
    String portfolioName = "PF1";

    expectedOutput.append("\nPortfolio successfully created!!!");
    expectedOutput.append("\nPortfolio Name: PF1");
    expectedOutput.append("\nList of Stocks in PF1 portfolio are:");
    expectedOutput.append("\nStock Ticker: AAPL");
    expectedOutput.append("\nStock Quantity: 5");

    String output = pm.createPortfolioThroughFile(portfolioName, filePath);
    assertEquals(expectedOutput.toString(), output);
  }

  // test whether it allows to create portfolio through a file upload
  @Test
  public void testExaminePortfolio() {
    PortfolioModel pm = new PortfolioBrokerageModel();
    String portfolioName = "testPortfolio";
    String ticker = "AAPL";
    int quantity = 10;
    pm.createListOfStock(ticker, quantity, LocalDate.now());
    pm.createPortfolio(portfolioName);
    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("\n============= INFLEXIBLE PORTFOLIOS ===================");
    expectedOutput.append("\nPortfolio\tTicker\tQuantity");
    expectedOutput.append("\ntestPortfolio\t\t\t\tAAPL\t\t\t\t10");
    String result = pm.examineAllPortfolios(portfolioName);
    assertEquals(expectedOutput.toString(), result);
  }

}