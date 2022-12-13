import static org.junit.Assert.assertEquals;

import brokerage.API;
import brokerage.PortfolioBrokerageController;
import brokerage.PortfolioBrokerageView;
import brokerage.PortfolioController;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioStrategyModelImpl;
import brokerage.PortfolioView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.Map;
import org.junit.Test;

/**
 * testing the PortfolioBrokerageController.
 */
public class PortfolioBrokerageControllerTest {
  private final ByteArrayOutputStream temp = new ByteArrayOutputStream();
  private PrintStream output = new PrintStream(temp);

  class MockModel implements PortfolioStrategyModel {

    private final StringBuilder log;
    private PrintStream out;

    public MockModel(StringBuilder log) {
      this.log = log;
    }


    @Override
    public int createListOfStock(String ticker, int quantity, LocalDate purchaseDate) {
      return 0;
    }

    @Override
    public String createPortfolio(String portfolioName) {
      log.append("Inside createPortfolio " + "Input: " + portfolioName);
      return "";
    }

    @Override
    public String createPortfolioThroughFile(String portfolioName, String filePath) {
      log.append("Inside createPortfolioThroughFile " + "Input1: " + portfolioName + " Input2: "
          + filePath);
      return "";
    }

    @Override
    public String examineAllPortfolios(String portfolioName) {
      return null;
    }


    @Override
    public String displayPortfolioValue(String portfolioName, LocalDate valueOnDate) {
      log.append(" Inside displayPortfolioValue " + "Input: " + portfolioName);
      return "";
    }

    @Override
    public String downloadPortfolio() throws IOException {
      return null;
    }

    @Override
    public int validatePortfolioExist(String portfolioName) {
      log.append("Inside validatePortfolioExist " + "Input: " + portfolioName);
      return 0;
    }

    @Override
    public int validateTickerExist(String portfolioName) {
      return 0;
    }

    @Override
    public String displayListOfStocks() {
      return null;
    }



    @Override
    public int createListOfStockForFlex(String ticker, double quantity, LocalDate purchaseDate, double purchaseCom, API api) {
      return 0;
    }

    @Override
    public String sellStockFromFlexPF(String portfolioName, String ticker, double quantity, LocalDate sellDate, API api) {
      return null;
    }

    @Override
    public int validateSellableQuantity(String portfolioName, LocalDate sellDate, String ticker,
        int quantity, API api) {
      return 0;
    }

    @Override
    public String examinePortfolioByDate(String portfolioName, LocalDate date) {
      return null;
    }

    @Override
    public String getCostBasis(String portfolioName, LocalDate queryDate) {
      return null;
    }

    @Override
    public String displayFlexPortfolioValue(String portfolioName, LocalDate valueOnDate, API api) {
      return null;
    }

    @Override
    public String plotPerformanceOfPortfolio(String portfolioName, LocalDate startDate,
        LocalDate endDate, API api) {
      return null;
    }

    @Override
    public int validateFlexPortfolioExist(String portfolioName) {
      return 0;
    }


    @Override
    public String createWeightedPf(String portfolioName, Map<String, Float> stockWeightMap) {
      return null;
    }

    @Override
    public String displayWeightedPf(String portfolioName) {
      return null;
    }

    @Override
    public String investInWeightedPf(String portfolioName, LocalDate investDate, float amount,
        float purchaseCom, API api) {
      return null;
    }

    @Override
    public String setUpDollarCostAverage(String portfolioName,
        Map<String, Float> stockWeightMap, LocalDate startDate, LocalDate endDate,
        int frequency, float amount, double purchaseCom, API api) {
      return null;
    }

    @Override
    public String displayDollarCostAverage(String portfolioName) {
      return null;
    }

    @Override
    public int validateStrategyPfExists(String portfolioName) {
      return 0;
    }

    @Override
    public String reBalancePortfolio(String portfolioName, LocalDate date, Map<String, Double> weights, API api) {
      return null;
    }

  }

  @Test
  public void testCreatePortfolio() throws Exception {
    PrintStream out;
    PortfolioView pv = new PortfolioBrokerageView(System.out);
    Reader in = new StringReader("1\nPF1\n1\nAAPL\n10\n6");
    String expectedOutput = "Inside validatePortfolioExist Input: "
        + "PF1Inside createPortfolio Input: PF1";
    StringBuilder modelLog = new StringBuilder();
    PortfolioController controller = new PortfolioBrokerageController(in, System.out);
    controller.goController(new MockModel(modelLog), pv);
    assertEquals(expectedOutput, modelLog.toString());
  }


  @Test
  public void testCreatePortfolioThroughFile() throws Exception {
    PortfolioView pv = new PortfolioBrokerageView(System.out);
    Reader in = new StringReader("2\nPF2\nuploadfile1.csv\n6");
    String expectedOutput = "Inside validatePortfolioExist Input: PF2"
        + "Inside createPortfolioThroughFile "
        + "Input1: PF2 Input2: uploadfile1.csv";
    StringBuilder modelLog = new StringBuilder();
    PortfolioController controller = new PortfolioBrokerageController(in, System.out);
    controller.goController(new MockModel(modelLog), pv);
    assertEquals(expectedOutput, modelLog.toString());
  }

  @Test
  public void testDisplayPortfolioValue() throws Exception {
    PortfolioView pv = new PortfolioBrokerageView(System.out);
    Reader in = new StringReader("2\nPF1\nuploadfile1.csv\n6");
    String expectedOutput = "Inside validatePortfolioExist Input: PF1"
        + "Inside createPortfolioThroughFile Input1: PF1 Input2: uploadfile1.csv";
    StringBuilder modelLog = new StringBuilder();
    PortfolioController controller = new PortfolioBrokerageController(in, System.out);
    controller.goController(new MockModel(modelLog), pv);
    assertEquals(expectedOutput, modelLog.toString());
  }

  @Test
  public void testDownloadPortfolio() throws Exception {
    PortfolioView pv = new PortfolioBrokerageView(System.out);
    Reader in = new StringReader("5\n6");
    StringBuilder modelLog = new StringBuilder();
    PortfolioController controller = new PortfolioBrokerageController(in, System.out);
    controller.goController(new MockModel(modelLog), pv);
    assertEquals("", modelLog.toString());
  }

  @Test
  public void testValidatePortfolioExist() throws IOException {
    PortfolioView pv = new PortfolioBrokerageView(System.out);
    Reader in = new StringReader("1\nPF1\n1\nAAPL\n10\n6");
    String expectedOutput = "Inside validatePortfolioExist Input: "
        + "PF1Inside createPortfolio Input: PF1";
    StringBuilder modelLog = new StringBuilder();
    PortfolioController controller = new PortfolioBrokerageController(in, System.out);
    controller.goController(new MockModel(modelLog), pv);
    assertEquals(expectedOutput, modelLog.toString());
  }

  @Test
  public void testValidateTickerExist() throws IOException {
    PortfolioView pv = new PortfolioBrokerageView(System.out);
    Reader in = new StringReader("1\nPF1\n1\nAAPL\n10\n6");
    String expectedOutput = "Inside validatePortfolioExist Input: "
        + "PF1Inside createPortfolio Input: PF1";
    StringBuilder modelLog = new StringBuilder();
    PortfolioController controller = new PortfolioBrokerageController(in, System.out);
    controller.goController(new MockModel(modelLog), pv);
    assertEquals(expectedOutput, modelLog.toString());
  }

  @Test
  public void testCreateFlexPortfolio() throws IOException {
    PortfolioView pv = new PortfolioBrokerageView(System.out);
    Reader in = new StringReader("7\nPortfolio\n1\nAAPL\n10\n2022-01-05\n10.05\n6");
    String expectedOutput = "Inside createPortfolio Input: Portfolio";
    StringBuilder modelLog = new StringBuilder();
    PortfolioController controller = new PortfolioBrokerageController(in, System.out);
    controller.goController(new MockModel(modelLog), pv);
    assertEquals(expectedOutput, modelLog.toString());
  }

  @Test
  public void testGetValue()  {
    PortfolioView pv = new PortfolioBrokerageView(System.out);
    Reader in = new StringReader("6");
    StringBuilder modelLog = new StringBuilder();
    PortfolioController controller = new PortfolioBrokerageController(in, System.out);
    try {
      controller.goController(new MockModel(modelLog), pv);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals("", modelLog.toString());
  }
  @Test
  public void testRebalancePortfolio()  {
    PortfolioView pv = new PortfolioBrokerageView(System.out);
    Reader in = new StringReader("19\nPF2\n2022-11-11\nBABA\n20\nINTC\n"+
            "20\nPBR\n20\nAAPL\n40\n6");
    ByteArrayOutputStream by= new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);
    StringBuilder modelLog = new StringBuilder();
    PortfolioController controller = new PortfolioBrokerageController(in, out);
    try {
      controller.goController(new PortfolioStrategyModelImpl(), pv);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.setupMenu();
    output.println("Enter the name of the portfolio:");
    output.println("Enter the date in yyyy-MM-dd format:");
    this.setStocklist();
    output.println("Remaining weight: 100.0");
    this.setStocklist();
    output.println("Remaining weight: 80.0");
    this.setStocklist();
    output.println("Remaining weight: 60.0");
    this.setStocklist();
    output.println("Remaining weight: 40.0");
    output.println("Re-balancing done successfully");
    this.setupMenu();
    assertEquals(temp.toString(), by.toString());
  }

  private void setStocklist() {
    output.println("Choose ticker from list");
    output.println("BABA");
    output.println("PBR");
    output.println("AAPL");
    output.println("INTC");
    output.println("Enter the weight for the stock between 1 and 100:");
  }

  private void setupMenu() {
    output.println("\n=================== INFLEXIBLE PORTFOLIO ===============================");
    output.println("Press 1 to create a new Inflexible Portfolio manually.");
    output.println("Press 2 to create a new Inflexible Portfolio through a file upload.");
    output.println("Press 3 to examine the composition of the Inflexible Portfolio.");
    output.println("Press 4 to get the value of a Inflexible Portfolio at a certain date.");
    output.println("Press 5 to download your portfolios into a file.");
    output.println("Press 6 to exit from the menu.");

    output.println("\n=================== FLEXIBLE PORTFOLIO ===============================");
    output.println("Press 7 to create and/or add stocks to a Flexible Portfolio.");
    output.println("Press 8 to sell stocks from an existing Flexible Portfolio.");
    output.println("Press 9 to know the cost basis of a Flexible Portfolio.");
    output.println("Press 10 to plot the performance of a Flexible Portfolio.");
    output.println("Press 11 to examine the composition of the Flexible Portfolio.");
    output.println("Press 12 to get the value of a Flexible portfolio at a certain date.");
    output.println(
            "Press 13 to examine the composition of Flexible portfolio at a certain date.");
    output.println("Press 14 to create a weighted portfolio.");
    output.println("Press 15 to display a weighted portfolio.");
    output.println("Press 16 to make an investment in a weighted portfolio.");
    output.println("Press 17 to set up dollar cost average for a portfolio.");
    output.println("Press 18 to display dollar cost average set up.");
    output.println("Press 19 to re-balance a flexible portfolio on a specific date");
    output.println("\nEnter your selection from above available options:");
  }
  @Test
  public void testRebalancePortfolioOnHolidayDate()  {
    PortfolioView pv = new PortfolioBrokerageView(System.out);
    Reader in = new StringReader("19\nPF2\n2022-11-20\nBABA\n20\nINTC\n"+
            "20\nPBR\n20\nAAPL\n40\n6");
    ByteArrayOutputStream by= new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);
    StringBuilder modelLog = new StringBuilder();
    PortfolioController controller = new PortfolioBrokerageController(in, out);
    try {
      controller.goController(new PortfolioStrategyModelImpl(), pv);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.setupMenu();
    output.println("Enter the name of the portfolio:");
    output.println("Enter the date in yyyy-MM-dd format:");
    this.setStocklist();
    output.println("Remaining weight: 100.0");
    this.setStocklist();
    output.println("Remaining weight: 80.0");
    this.setStocklist();
    output.println("Remaining weight: 60.0");
    this.setStocklist();
    output.println("Remaining weight: 40.0");
    output.println("Cannot re-balance portfolio on holiday");
    this.setupMenu();
    assertEquals(temp.toString(), by.toString());
  }
  @Test
  public void testRebalancePortfolioOnDifferentPortfolio()  {
    PortfolioView pv = new PortfolioBrokerageView(System.out);
    Reader in = new StringReader("19\nASD\nPF2\n2022-11-21\nBABA\n20\nINTC\n"+
            "20\nPBR\n20\nAAPL\n40\n6");
    ByteArrayOutputStream by= new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);
    StringBuilder modelLog = new StringBuilder();
    PortfolioController controller = new PortfolioBrokerageController(in, out);
    try {
      controller.goController(new PortfolioStrategyModelImpl(), pv);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.setupMenu();
    output.println("Enter the name of the portfolio:");
    output.println("This portfolio doesn't exist. Provide a different name");
    output.println("Enter the date in yyyy-MM-dd format:");
    this.setStocklist();
    output.println("Remaining weight: 100.0");
    this.setStocklist();
    output.println("Remaining weight: 80.0");
    this.setStocklist();
    output.println("Remaining weight: 60.0");
    this.setStocklist();
    output.println("Remaining weight: 40.0");
    output.println("Re-balancing done successfully");
    this.setupMenu();
    assertEquals(temp.toString(), by.toString());
  }
}