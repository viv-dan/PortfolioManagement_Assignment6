package brokerage.gui;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import brokerage.API;
import brokerage.APIImpl;
import brokerage.PortfolioController;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioView;

/**
 * this class implements FeaturesGUI interface. It is the implementation of the GUI features that
 * this program has.
 */
public class PortfolioGUIController implements FeaturesGUI, PortfolioController {

  private PortfolioStrategyModel model;
  private PortfolioGUIVew view;

  API api = new APIImpl();
  Map<String, Float> stockWeightMap = new HashMap<>();
  Map<String, Double> stockMap = new HashMap<>();

  @Override
  public void goController(PortfolioStrategyModel portfolioModel, PortfolioView portfolioView) {
    model = portfolioModel;
    view = (PortfolioGUIVew) portfolioView;
    view.addFeaturesPortfolioBrokerage(this);
  }

  @Override
  public void exitFromProgram() {
    System.exit(0);
  }

  @Override
  public void backToMenu() {
    view.mainMenu();
    view.addFeaturesPortfolioBrokerage(this);
  }

  @Override
  public void compositionPortfolio() {
    view.compositionOfPortfolio();
    view.addFeaturesCompositionPortfolio(this);
  }

  @Override
  public void compositionPortfolioOfCertain(String portfolioName, String date) {
    if (!validateFieldIsEmpty(portfolioName)) {
      return;
    }
    if (model.validateFlexPortfolioExist(portfolioName) == 0
        && model.validateStrategyPfExists(portfolioName) == 0) {
      JOptionPane.showConfirmDialog(null, portfolioName
              + " portfolio doesn't exist. Enter a valid portfolio name.",
          "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
      return;
    }
    try {
      LocalDate queryDate = parseDate(date);
      view.outputForFeatures(model.examinePortfolioByDate(portfolioName, queryDate));
      view.addFeaturesOutputFrame(this);
    } catch (DateTimeException exception) {
      JOptionPane.showConfirmDialog(null, "Please enter a valid date "
          + "pattern in YYYY-MM-DD", "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
    }
  }

  @Override
  public void getCostBasis() {
    view.getCostBasisOfPortfolio();
    view.addFeaturesOfGetCostBasis(this);
  }

  @Override
  public void getCostBasisOutput(String portfolioName, String date) {
    if (!validateFieldIsEmpty(portfolioName)) {
      return;
    }
    if (model.validateFlexPortfolioExist(portfolioName) == 0
        && model.validateStrategyPfExists(portfolioName) == 0) {
      JOptionPane.showConfirmDialog(null, portfolioName
              + " portfolio doesn't exist. Enter a valid portfolio name.",
          "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
      return;
    }
    try {
      LocalDate queryDate = parseDate(date);
      view.outputForFeatures(model.getCostBasis(portfolioName, queryDate));
      view.addFeaturesOutputFrame(this);
    } catch (DateTimeException exception) {
      JOptionPane.showConfirmDialog(null, "Please enter a valid date "
          + "pattern in YYYY-MM-DD", "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
    }
  }

  @Override
  public void valueOfPortfolio() {
    view.getValueOFPortfolio();
    view.addFeaturesToGetPortfolioValue(this);
  }

  @Override
  public void getValueOfPortfolioFromModel(String portfolioName, String date) {
    if (!validateFieldIsEmpty(portfolioName)) {
      return;
    }
    if (model.validateFlexPortfolioExist(portfolioName) == 0
        && model.validateStrategyPfExists(portfolioName) == 0) {
      JOptionPane.showConfirmDialog(null, portfolioName
              + " portfolio doesn't exist. Enter a valid portfolio name.",
          "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
      return;
    }
    try {
      LocalDate queryDate = parseDate(date);
      view.outputForFeatures(model.displayFlexPortfolioValue(portfolioName, queryDate, api));
      view.addFeaturesOutputFrame(this);
    } catch (DateTimeException exception) {
      JOptionPane.showConfirmDialog(null, "Please enter a valid date "
          + "pattern in YYYY-MM-DD", "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
    }
  }

  @Override
  public void sellStockFromPortfolio() {
    view.sellStocksFromPortfolio();
    view.addFeaturesSellStocksFromPortfolio(this);
  }

  @Override
  public void sellingStocks(String portfolioName, String sellDate, String ticker, String quantity) {
    if (portfolioName.isBlank() || ticker.isBlank() || quantity.isBlank()
        || sellDate.isBlank()) {
      JOptionPane.showConfirmDialog(null, ticker
              + " fields cannot be blank", "PortfolioBrokerage",
          JOptionPane.CANCEL_OPTION);
      return;
    }
    if (model.validateTickerExist(ticker) == 1) {
      JOptionPane.showConfirmDialog(null, ticker
              + " ticker is not a valid ticker. Please input again!", "PortfolioBrokerage",
          JOptionPane.CANCEL_OPTION);
      return;
    }
    if (model.validateFlexPortfolioExist(portfolioName) == 0
        && model.validateStrategyPfExists(portfolioName) == 0) {
      JOptionPane.showConfirmDialog(null,
          "Portfolio with this name doesn't exist. Provide a different name",
          "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
      return;
    }

    if (validateInteger(quantity) == 1) {
      return;
    }
    try {
      int qty = Integer.parseInt(quantity);
      LocalDate sDate = parseDate(sellDate);

      if (model.validateSellableQuantity(portfolioName, sDate, ticker, qty, api)
          == 1) {
        JOptionPane.showConfirmDialog(null,
            "Not a valid sell",
            "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
      } else {
        view.outputForFeatures(model.sellStockFromFlexPF(portfolioName, ticker, qty, sDate, api));
        view.addFeaturesOutputFrame(this);
      }
    } catch (DateTimeException exception) {
      JOptionPane.showConfirmDialog(null, "Please enter a valid date "
          + "pattern in YYYY-MM-DD", "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
    }
  }

  @Override
  public void creatFlex() {
    view.createFlexiblePortfolioView();
    view.addFeaturesCreateFlexPortfolio(this);
  }

  @Override
  public void addStocksToPortfolio(String portfolioName, String ticker, String quantity,
      String purchaseDate, String purchaseCommission) {
    if (portfolioName.isBlank() || ticker.isBlank() || quantity.isBlank()
        || purchaseDate.isBlank()) {
      JOptionPane.showConfirmDialog(null, ticker
              + " fields cannot be blank", "PortfolioBrokerage",
          JOptionPane.CANCEL_OPTION);
      return;
    }
    if (model.validateTickerExist(ticker) == 1) {
      JOptionPane.showConfirmDialog(null, ticker
              + " ticker is not a valid ticker. Please input again!", "PortfolioBrokerage",
          JOptionPane.CANCEL_OPTION);
      return;
    }

    if (validateInteger(quantity) == 1) {
      return;
    }
    if (validateFloat(purchaseCommission) == 1) {
      return;
    }
    try {
      int qty = Integer.parseInt(quantity);
      LocalDate pDate = parseDate(purchaseDate);
      double pCom = Double.parseDouble(purchaseCommission);
      model.createListOfStockForFlex(ticker, qty, pDate, pCom, api);
      view.clearTextInTheCreatePortfolio();
    } catch (DateTimeException exception) {
      JOptionPane.showConfirmDialog(null, "Please enter a valid date "
          + "pattern in YYYY-MM-DD", "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
    }
  }

  @Override
  public void addStocksAndProceed(String portfolioName, String ticker, String quantity,
      String purchaseDate, String purchaseCommission) {
    if (portfolioName.isBlank() || ticker.isBlank() || quantity.isBlank() ||
        purchaseDate.isBlank() || purchaseCommission.isBlank()) {
      JOptionPane.showConfirmDialog(null, ticker
              + " fields cannot be blank", "PortfolioBrokerage",
          JOptionPane.CANCEL_OPTION);
      return;
    }

    if (model.validateTickerExist(ticker) == 1) {
      JOptionPane.showConfirmDialog(null, ticker
              + " ticker is not a valid ticker. Please input again!", "PortfolioBrokerage",
          JOptionPane.CANCEL_OPTION);
      return;
    }

    if (validateInteger(quantity) == 1) {
      return;
    }
    if (validateFloat(purchaseCommission) == 1) {
      return;
    }
    try {
      int qty = Integer.parseInt(quantity);
      LocalDate pDate = parseDate(purchaseDate);
      double pCom = Double.parseDouble(purchaseCommission);
      model.createListOfStockForFlex(ticker, qty, pDate, pCom, api);
      view.outputForFeatures(model.createPortfolio(portfolioName));
      view.addFeaturesOutputFrame(this);
    } catch (DateTimeException exception) {
      JOptionPane.showConfirmDialog(null, "Please enter a valid date "
          + "pattern in YYYY-MM-DD", "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
    }
  }

  @Override
  public void createWeightedPortfolioFeature() {
    view.createWeightedPortfolioFeatureImpl();
    view.addFeaturesCreateWeightedPortfolio(this);
  }

  @Override
  public void addStockToWeightedPF(String portfolioName, String ticker, String weight) {
    if (portfolioName.isBlank() || ticker.isBlank() || weight.isBlank()) {
      JOptionPane.showConfirmDialog(null, ticker
              + " fields cannot be blank", "PortfolioBrokerage",
          JOptionPane.CANCEL_OPTION);
      return;
    }
    if (model.validateTickerExist(ticker) == 1) {
      JOptionPane.showConfirmDialog(null, ticker
              + " ticker is not a valid ticker. Please input again!", "PortfolioBrokerage",
          JOptionPane.CANCEL_OPTION);
      return;
    }
    if (validateFloat(weight) == 1) {
      return;
    } else {
      float stockWeight = Float.parseFloat(weight);
      float totalWeight = 0;
      for (Map.Entry<String, Float> entry : stockWeightMap.entrySet()) {
        float x = entry.getValue();
        totalWeight = totalWeight + x;
      }
      if (totalWeight + stockWeight > 100) {
        JOptionPane.showMessageDialog(new JFrame(), "Total weight is greater than 100"
            + ". Cannot add this stock!!!");
        //view.setWeightedPFAddStockButtonNotClickable();
        return;
      }
      stockWeightMap.put(ticker, stockWeight);
      view.clearTextInTheCreateWeightedPF();
    }

  }

  @Override
  public void addStocksAndProceedForWeightedPF(String portfolioName, String ticker, String weight) {
    if (portfolioName.isBlank() || ticker.isBlank() || weight.isBlank()) {
      JOptionPane.showConfirmDialog(null, ticker
              + " fields cannot be blank", "PortfolioBrokerage",
          JOptionPane.CANCEL_OPTION);
      return;
    }
    if (model.validateTickerExist(ticker) == 1) {
      JOptionPane.showConfirmDialog(null, ticker
              + " ticker is not a valid ticker. Please input again!", "PortfolioBrokerage",
          JOptionPane.CANCEL_OPTION);
      return;
    }

    if (validateFloat(weight) == 1) {
      return;
    }
    float stockWeight = Float.parseFloat(weight);
    stockWeightMap.put(ticker, stockWeight);
    view.outputForFeatures(model.createWeightedPf(portfolioName, stockWeightMap));
    stockWeightMap.clear();
    view.addFeaturesOutputFrame(this);
  }

  @Override
  public void investWeightedPortfolioFeature() {
    view.investWeightedPortfolioFeatureImpl();
    view.addFeaturesInvestWeightedPortfolio(this);
  }

  @Override
  public void investForWeightedPF(String portfolioName, String amount, String purchaseCom,
      String purchaseDate) {
    if (portfolioName.isBlank() || amount.isBlank() || purchaseCom.isBlank()) {
      return;
    }
    if (model.validateStrategyPfExists(portfolioName) == 0) {
      JOptionPane.showConfirmDialog(null, portfolioName
              + " portfolio does not exist. Provide a valid portfolio name.",
          "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
      return;
    }
    try {
      if (validateFloat(amount) == 1) {
        return;
      }
      if (validateFloat(purchaseCom) == 1) {
        return;
      }
      float amt = Float.parseFloat(amount);
      float pCom = Float.parseFloat(purchaseCom);
      LocalDate pDate = parseDate(purchaseDate);
      view.outputForFeatures(model.investInWeightedPf(portfolioName, pDate, amt, pCom, api));
      view.addFeaturesOutputFrame(this);
    } catch (DateTimeException exception) {
      JOptionPane.showConfirmDialog(null, "Please enter a valid date "
          + "pattern in YYYY-MM-DD", "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
    }
  }

  @Override
  public void addStockToDCA(String portfolioName, String ticker, String weight, String startDate,
      String endDate, String frequency, String amount, String purchaseCom) {
    if (portfolioName.isBlank() || ticker.isBlank() || weight.isBlank() || startDate.isBlank()
        || endDate.isBlank() || frequency.isBlank() || amount.isBlank() || purchaseCom.isBlank()) {
      return;
    }
    if (model.validateTickerExist(ticker) == 1) {
      JOptionPane.showConfirmDialog(null, ticker
              + " ticker is not a valid ticker. Please input again!",
          "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
      return;
    }
    try {
      if (validateFloat(weight) == 1) {
        return;
      }
      if (validateFloat(amount) == 1) {
        return;
      }
      if (validateFloat(purchaseCom) == 1) {
        return;
      }
      if (validateInteger(frequency) == 1) {
        return;
      }
      float stockWeight = Float.parseFloat(weight);
      float totalWeight = 0;
      for (Map.Entry<String, Float> entry : stockWeightMap.entrySet()) {
        float x = entry.getValue();
        totalWeight = totalWeight + x;
      }
      if (totalWeight + stockWeight > 100) {
        JOptionPane.showMessageDialog(new JFrame(), "Total weight already reached 100"
            + ". Cannot add more stocks!!!");
        view.setDCAAddStockButtonNotClickable();
        return;
      }
      stockWeightMap.put(ticker, stockWeight);
      view.clearTextInTheDCA();
    } catch (DateTimeException exception) {
      JOptionPane.showConfirmDialog(null, "Please enter a valid date "
          + "pattern in YYYY-MM-DD", "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
    }
  }

  @Override
  public void addSaveAndProceedForDCA(String portfolioName, String ticker, String weight,
      String startDate, String endDate, String frequency, String amount, String purchaseCom) {
    if (portfolioName.isBlank() || ticker.isBlank() || weight.isBlank() || startDate.isBlank()
        || endDate.isBlank() || frequency.isBlank() || amount.isBlank() || purchaseCom.isBlank()) {
      return;
    }
    if (model.validateTickerExist(ticker) == 1) {
      JOptionPane.showConfirmDialog(null, ticker
              + " ticker is not a valid ticker. Please input again!",
          "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
      return;
    }
    try {
      LocalDate eDate = null;
      if (validateFloat(weight) == 1) {
        return;
      }
      if (validateFloat(amount) == 1) {
        return;
      }
      if (validateFloat(purchaseCom) == 1) {
        return;
      }
      if (validateInteger(frequency) == 1) {
        return;
      }
      float stockWeight = Float.parseFloat(weight);
      stockWeightMap.put(ticker, stockWeight);
      LocalDate sDate = parseDate(startDate);
      if (endDate.equals("")) {
        eDate = getTodayDate();
      } else {
        eDate = parseDate(endDate);
      }
      int freq = Integer.parseInt(frequency);
      float amt = Float.parseFloat(amount);
      float pCom = Float.parseFloat(purchaseCom);
      view.outputForFeatures(model.setUpDollarCostAverage(portfolioName, stockWeightMap,
          sDate, eDate, freq, amt, pCom, api));
      stockWeightMap.clear();
      view.addFeaturesOutputFrame(this);
    } catch (DateTimeException exception) {
      JOptionPane.showConfirmDialog(null, "Please enter a valid date "
          + "pattern in YYYY-MM-DD", "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
    }
  }

  @Override
  public void createDCAFeature() {
    view.createDCAFeatureImpl();
    view.addFeaturesCreateDCA(this);
  }

  @Override
  public void createRebalanceFeature() {
    view.rebalanceFeatureImpl();
    view.addFeaturesRebalance(this);
  }

  /**
   * This method re-balances the portfolio on a specific date.
   *
   * @param portfolioName the name of the portfolio to be re-balanced
   * @param date          the date on which the re-balancing has to be done
   */
  @Override
  public void startRebalancing(String portfolioName, String date,String ticker,String weight) {
    if (!validateFieldIsEmpty(portfolioName)) {
      return;
    }
    if (model.validateFlexPortfolioExist(portfolioName) == 0) {
      JOptionPane.showConfirmDialog(null, portfolioName
                      + " portfolio doesn't exist. Enter a valid portfolio name.",
              "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
      return;
    }
    try {
      if (validateFloat(weight) == 1) {
        return;
      }
      float stockWeight = Float.parseFloat(weight);
      double totalWeight = 0;
      for (Map.Entry<String, Double> entry : stockMap.entrySet()) {
        Double x = entry.getValue();
        totalWeight = totalWeight + x;
      }
      if (totalWeight + stockWeight > 100) {
        JOptionPane.showMessageDialog(new JFrame(), "Total weight already reached 100"
                + ". Cannot add more stocks!!!");
        view.setWeightedPFAddStockButtonNotClickable();
        return;
      }
      stockMap.put(ticker, Double.valueOf(stockWeight));
      LocalDate queryDate = parseDate(date);
      view.outputForFeatures(model.reBalancePortfolio(portfolioName, queryDate,stockMap, api));
      stockMap.clear();
      view.addFeaturesOutputFrame(this);
    } catch (DateTimeException exception) {
      JOptionPane.showConfirmDialog(null, "Please enter a valid date "
              + "pattern in YYYY-MM-DD", "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
    }
  }

  @Override
  public void addStockToRebalance(String portfolioName, String ticker, String weight, String date) {
    if(portfolioName.isBlank() || ticker.isBlank() || weight.isBlank() || date.isBlank()){
      return;
    }
    if (model.validateTickerExist(ticker) == 1) {
      JOptionPane.showConfirmDialog(null, ticker
                      + " ticker is not a valid ticker. Please input again!",
              "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
      return;
    }
    try {
      LocalDate eDate = null;
      if (validateFloat(weight) == 1) {
        return;
      }
      float stockWeight = Float.parseFloat(weight);
      double totalWeight = 0;
      for (Map.Entry<String, Double> entry : stockMap.entrySet()) {
        Double x = entry.getValue();
        totalWeight = totalWeight + x;
      }
      if (totalWeight + stockWeight > 100) {
        JOptionPane.showMessageDialog(new JFrame(), "Total weight already reached 100"
                + ". Cannot add more stocks!!!");
        view.setWeightedPFAddStockButtonNotClickable();
        return;
      }
      stockMap.put(ticker, Double.valueOf(stockWeight));
      LocalDate sDate = parseDate(date);
      view.clearTextInReBalance();
      view.addFeaturesOutputFrame(this);
    } catch (DateTimeException exception) {
      JOptionPane.showConfirmDialog(null, "Please enter a valid date "
              + "pattern in YYYY-MM-DD", "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
    }

  }

  /**
   * this is a helper method to validate if the field is empty.
   *
   * @param str string input from JText field
   * @return returns false is the field is empty else returns true.
   */
  private boolean validateFieldIsEmpty(String str) {
    if (str.isBlank()) {
      JOptionPane.showConfirmDialog(null, "Field cannot be blank",
          "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
      return false;
    }
    return true;
  }

  /**
   * this is a helper method to validate if the integer type variable is negative or zero.
   *
   * @param str string input from JText field
   * @return returns 1 or 0 depending upon whether the input is negative or zero.
   */
  private int validateInteger(String str) {
    try {
      int x = Integer.parseInt(str);
      if (x <= 0) {
        JOptionPane.showConfirmDialog(null,
            "Some text field(s) have value 0. Please correct it.",
            "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
        return 1;
      }
    } catch (NumberFormatException e) {
      JOptionPane.showConfirmDialog(null, str
              + " is not a number. Please correct it.", "PortfolioBrokerage",
          JOptionPane.CANCEL_OPTION);
    }
    return 0;
  }

  /**
   * this is a helper method to validate if the float type variable is negative or zero.
   *
   * @param str string input from JText field
   * @return returns 1 or 0 depending upon whether the input is negative or zero.
   */
  private int validateFloat(String str) {
    try {
      float x = Float.parseFloat(str);
      if (x <= 0) {
        JOptionPane.showConfirmDialog(null,
            "Some text field(s) have value 0. Please correct it.",
            "PortfolioBrokerage", JOptionPane.CANCEL_OPTION);
        return 1;
      }
    } catch (NumberFormatException e) {
      JOptionPane.showConfirmDialog(null, str
              + " is not a number. Please correct it.", "PortfolioBrokerage",
          JOptionPane.CANCEL_OPTION);
    }
    return 0;
  }


  /**
   * a helper method to parse the string in the specified date format.
   *
   * @param date the date that needs to be parsed in string.
   * @return returns the date in a desired format.
   */
  private LocalDate parseDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, formatter);
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

}
