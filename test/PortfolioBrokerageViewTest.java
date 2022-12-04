import static org.junit.Assert.assertEquals;

import brokerage.PortfolioBrokerageView;
import brokerage.PortfolioView;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;

/**
 * testing the PortfolioBrokerageView.
 */
public class PortfolioBrokerageViewTest {

  OutputStream outStream = new ByteArrayOutputStream();

  @Before
  public void setUpPrintStreams() {

    System.setOut(new PrintStream(outStream));
  }

  @Test
  public void testInputChoice() {
    PortfolioView pv = new PortfolioBrokerageView(new PrintStream(System.out));
    pv.inputChoice();
    StringBuilder expected = new StringBuilder();
    expected.append("\n=================== INFLEXIBLE PORTFOLIO ===============================");
    expected.append("\nPress 1 to create a new Inflexible Portfolio manually.");
    expected.append("\nPress 2 to create a new Inflexible Portfolio through a file upload.");
    expected.append("\nPress 3 to examine the composition of the Inflexible Portfolio.");
    expected.append("\nPress 4 to get the value of a Inflexible Portfolio at a certain date.");
    expected.append("\nPress 5 to download your portfolios into a file.");
    expected.append("\nPress 6 to exit from the menu.");
    expected.append("\n\n=================== FLEXIBLE PORTFOLIO ===============================");
    expected.append("\nPress 7 to create and/or add stocks to a Flexible Portfolio.");
    expected.append("\nPress 8 to sell stocks from an existing Flexible Portfolio.");
    expected.append("\nPress 9 to know the cost basis of a Flexible Portfolio.");
    expected.append("\nPress 10 to plot the performance of a Flexible Portfolio.");
    expected.append("\nPress 11 to examine the composition of the Flexible Portfolio.");
    expected.append("\nPress 12 to get the value of a Flexible portfolio at a certain date.");
    expected.append(
        "\nPress 13 to examine the composition of Flexible portfolio at a certain date.");
    expected.append("\n\nEnter your selection from above available options:\n");
    assertEquals(expected.toString(), outStream.toString());
  }

  @Test
  public void testInputNoOfStocksToAdd() {
    PortfolioView pv = new PortfolioBrokerageView(new PrintStream(System.out));
    pv.inputNoOfStocksToAdd();
    assertEquals("Enter the number of stocks:\n", outStream.toString());
  }

  @Test
  public void testInputPortfolioName() {
    PortfolioView pv = new PortfolioBrokerageView(new PrintStream(System.out));
    pv.inputPortfolioName();
    assertEquals("Enter the name of the portfolio:\n", outStream.toString());
  }

  @Test
  public void testInputTicker() {
    PortfolioView pv = new PortfolioBrokerageView(new PrintStream(System.out));
    pv.inputTicker();
    assertEquals("Enter the ticker of the stock:\n", outStream.toString());
  }

  @Test
  public void testInputQuantity() {
    PortfolioView pv = new PortfolioBrokerageView(new PrintStream(System.out));
    pv.inputQuantity();
    assertEquals("Enter the quantity of the stock:\n", outStream.toString());
  }

  @Test
  public void testInputFilePath() {
    PortfolioView pv = new PortfolioBrokerageView(new PrintStream(System.out));
    pv.inputFilePath();
    assertEquals("Enter the file path:\n", outStream.toString());
  }

  @Test
  public void testInputDateToValidate() {
    PortfolioView pv = new PortfolioBrokerageView(new PrintStream(System.out));
    pv.inputDateToValidate();
    assertEquals("Enter the date in yyyy-MM-dd format:\n", outStream.toString());
  }

  @Test
  public void testPurchaseDate() {
    PortfolioView pv = new PortfolioBrokerageView(new PrintStream(System.out));
    pv.inputPurchaseDate();
    assertEquals("Enter the purchase date in yyyy-MM-dd format:\n", outStream.toString());
  }

  @Test
  public void testInputSellDate() {
    PortfolioView pv = new PortfolioBrokerageView(new PrintStream(System.out));
    pv.inputSellDate();
    assertEquals("Enter the sell date in yyyy-MM-dd format:\n", outStream.toString());
  }

}