import static org.junit.Assert.assertEquals;

import brokerage.API;
import brokerage.PortfolioBrokerageController;
import brokerage.PortfolioBrokerageView;
import brokerage.PortfolioController;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioView;
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
    public int createListOfStockForFlex(String ticker, int quantity, LocalDate purchaseDate,
        double purchaseCom,
        API api) {
      log.append("Testing");
      return 0;
    }

    @Override
    public String sellStockFromFlexPF(String portfolioName, String ticker, int quantity,
        LocalDate sellDate, API api) {
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
    String expectedOutput = "TestingInside createPortfolio Input: Portfolio";
    StringBuilder modelLog = new StringBuilder();
    PortfolioController controller = new PortfolioBrokerageController(in, System.out);
    controller.goController(new MockModel(modelLog), pv);
    assertEquals(expectedOutput, modelLog.toString());
  }

  @Test
  public void testGetValue() throws IOException {
    PortfolioView pv = new PortfolioBrokerageView(System.out);
    Reader in = new StringReader("6");
    StringBuilder modelLog = new StringBuilder();
    PortfolioController controller = new PortfolioBrokerageController(in, System.out);
    controller.goController(new MockModel(modelLog), pv);
    assertEquals("", modelLog.toString());
  }
}