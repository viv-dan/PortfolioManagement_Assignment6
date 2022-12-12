package brokerage.gui;

/**
 * the featuresGUI interface is used to represent the features which are handled in the GUI.
 */
public interface FeaturesGUI {

  /**
   * the Exit method is used to exit from the window.
   */
  void exitFromProgram();

  /**
   * creatFlex method is used to display the createPortfolio panel to the user to get the inputs.
   */
  void creatFlex();

  /**
   * this method is used to navigate back to the menu panel from any other panel.
   */
  void backToMenu();

  /**
   * composition portfolio method is used display all the required things to user to fetch the
   * related portfolio composition.
   */
  void compositionPortfolio();

  /**
   * this method takes portfolioName and date and fetch the output from the csv file to the user.
   *
   * @param portfolioName represents the portfolioName.
   * @param date          represents the date.
   */

  void compositionPortfolioOfCertain(String portfolioName, String date);

  /**
   * the method used to display all the required fields to user to fetch thhe cost of portoflio.
   */
  void getCostBasis();

  /**
   * this method displays the cost basis of a portfolio.
   *
   * @param portfolioName represents portfolio name.
   * @param date          represents the date on which cost basis is need.
   */
  void getCostBasisOutput(String portfolioName, String date);

  /**
   * this method displays all the required fields to fetch the value of portfolio to user.
   */
  void valueOfPortfolio();

  /**
   * this method would display the value of portfolio to the user.
   *
   * @param portfolioName represents the portfolio name.
   * @param date          represents the date on which value of portfolio needed.
   */
  void getValueOfPortfolioFromModel(String portfolioName, String date);

  /**
   * this method would display all the required field to sell the stocks from a portfolio, to user.
   */
  void sellStockFromPortfolio();

  /**
   * this method displays confirmation after selling the stocks from the portfolio.
   *
   * @param portfolioName represents the portfolio name.
   * @param date          represents on what date the stocks need to be sold.
   * @param ticker        represents the ticker of the stock.
   * @param quantity      represent quantity of the stock to be sold.
   */

  void sellingStocks(String portfolioName, String date, String ticker, String quantity);

  /**
   * this method is used to add multiple stocks to the portfolio.
   *
   * @param portfolioName      represents portfolio name.
   * @param ticker             represents ticker of the stock.
   * @param quantity           represents the quantity of the stock.
   * @param purchaseDate       represents the purchaseDate on which date the stock got purchased.
   * @param purchaseCommission represents the commission add to add this stock to portfolio.
   */
  void addStocksToPortfolio(String portfolioName, String ticker, String quantity,
      String purchaseDate,
      String purchaseCommission);

  /**
   * this method is used to add the last/OnlyOne stock to the portfolio.
   *
   * @param portfolioName      represents the portfolio name.
   * @param ticker             represents the ticker of stock.
   * @param quantity           represents the quantity of the stock.
   * @param purchaseDate       represents the date on which we purchased the stock.
   * @param purchaseCommission represents the commission added for buying the stock.
   */
  void addStocksAndProceed(String portfolioName, String ticker, String quantity,
      String purchaseDate,
      String purchaseCommission);

  /**
   * this method is used to display all the fields which are required for creating the weighted
   * portfolio.
   */
  void createWeightedPortfolioFeature();

  /**
   * this method add multiple stocks to the weighted portfolio.
   *
   * @param portfolioName represents the portfolio name.
   * @param ticker        represents the ticker of the stock.
   * @param weight        represents the weight of stock need to be added to the portfolio.
   */
  void addStockToWeightedPF(String portfolioName, String ticker, String weight);

  /**
   * this method is used to add one/Last stock to the portfolio.
   *
   * @param portfolioName represents the portfolio name.
   * @param ticker        represents the ticker of the stock.
   * @param weight        represents the weight of the stock needed to add to a particular
   *                      portfolio.
   */
  void addStocksAndProceedForWeightedPF(String portfolioName, String ticker, String weight);

  /**
   * this method is used to display the fields to user to collect required details to invest in
   * portfolio.
   */
  void investWeightedPortfolioFeature();

  /**
   * this method invests the amount to a particular portfolio.
   *
   * @param portfolioName represents portfolio name.
   * @param amount        represents the amount invested.
   * @param purchaseCom   represents the purchase commission.
   * @param purchaseDate  represents the purchase date on which user is investing.
   */
  void investForWeightedPF(String portfolioName, String amount, String purchaseCom,
      String purchaseDate);

  /**
   * this method is used to add multiple stocks to the dollar cost average portfolio.
   *
   * @param portfolioName represents the portfolio name.
   * @param ticker        represents the ticker of stock.
   * @param weight        represents the weight of stock need to be added to portfolio.
   * @param startDate     represents the starting date.
   * @param endDate       represents the end date.
   * @param frequency     represents how frequency we need to add stock to portfolio.
   * @param amount        represents the amount.
   * @param purchaseCom   represents the purchase commission need to be added.
   */
  void addStockToDCA(String portfolioName, String ticker, String weight, String startDate,
      String endDate, String frequency, String amount, String purchaseCom);

  /**
   * this method is used to add only one/last stock to the dollar cost average.
   *
   * @param portfolioName represents the portfolio name.
   * @param ticker        represents the ticker of the stocks.
   * @param weight        represents  the weight of stock need to be added.
   * @param startDate     represents the start date.
   * @param endDate       represents the end date.
   * @param frequency     represents how frequency we need to add stock to portfolio.
   * @param amount        represents the amount.
   * @param purchaseCom   represents the purchase commission need to be added.
   */
  void addSaveAndProceedForDCA(String portfolioName, String ticker, String weight,
      String startDate, String endDate, String frequency, String amount, String purchaseCom);

  /**
   * this method would display all the required field need to create dollar cost average, to user.
   */
  void createDCAFeature();

  /**
   * This method would display all the required fields need to re-balance the portfolio, to user.
   */
  void createRebalanceFeature();

  /**
   * This method re-balances the portfolio on a specific date.
   *
   * @param portfolioName the name of the portfolio to be re-balanced
   * @param date the date on which the re-balancing has to be done
   */
  void startRebalancing(String portfolioName, String date,String ticker,String weight);

  void addStockToRebalance(String portfolioName, String ticker, String weight, String date);
}
