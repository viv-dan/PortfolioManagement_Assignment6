package brokerage;

import java.time.LocalDate;

/**
 * this is the interface for the model. This model represents operations for the flexible portfolio.
 * A flexible portfolio will have these features. Add Stock to the portfolio. Sell stock from the
 * portfolio. Determine the cost basis by a specific date. Determine the value of a portfolio on a
 * specific date. plot the performance of the portfolio in the form of bar chart.
 */
public interface PortfolioFlexModel extends PortfolioModel {

  /**
   * creates list of stock object for the flexible portfolio.
   *
   * @param ticker       ticker symbol of the stock.
   * @param quantity     quantity of the shares of a stock.
   * @param purchaseDate purchase date of the stock.
   * @param purchaseCom  purchase commission.
   * @param api          API call object to obtain the price of the stock at a particular date.
   * @return an integer 1 or 0 that indicates whether the portfolio was created or not
   */
  int createListOfStockForFlex(String ticker, double quantity, LocalDate purchaseDate,
      double purchaseCom, API api);

  /**
   * method that allows to perform sell operation on the portfolio. One can sell x number of shares,
   * of a stock from the portfolio.
   *
   * @param portfolioName name of the portfolio.
   * @param ticker        ticker symbol of the stock that a user wants to sell.
   * @param quantity      quantity of the shares one wants to sell.
   * @param sellDate      date when the sell occurred.
   * @return returns the status indicating whether the sell was successfully made or not.
   */
  String sellStockFromFlexPF(String portfolioName, String ticker, double quantity,
      LocalDate sellDate, API api);

  /**
   * a method to validate whether a stock can be sold from a portfolio on a particular date or not.
   *
   * @param portfolioName name of the portfolio.
   * @param sellDate      date when the sell occurred.
   * @param ticker        ticker symbol of the stock that a user wants to sell.
   * @param quantity      quantity of the shares one wants to sell.
   * @return an integer 1 or 0 that indicates whether the sell operation can be performed or not.
   */
  int validateSellableQuantity(String portfolioName, LocalDate sellDate, String ticker,
      int quantity, API api);

  /**
   * a method to examine the composition of a portfolio by a particular date.
   *
   * @param portfolioName name of the portfolio.
   * @param date          the date when a user wants to know the composition of a portfolio.
   * @return returns the composition of the portfolio for a particular date.
   */
  String examinePortfolioByDate(String portfolioName, LocalDate date);

  /**
   * method to get the cost basis of the portfolio on a particular date.
   *
   * @param portfolioName name of the portfolio.
   * @param queryDate     the date when a user wants to know the cost basis of a portfolio.
   * @return the cost basis of the portfolio on a particular date.
   */
  String getCostBasis(String portfolioName, LocalDate queryDate);

  /**
   * method to get the value of the portfolio on a particular date.
   *
   * @param portfolioName name of the portfolio.
   * @param valueOnDate   date on which the user wants to know the value of the portfolio.
   * @param api           API call object to obtain the price of the stock at a particular date.
   * @return returns the value of the portfolio on a particular date.
   */
  String displayFlexPortfolioValue(String portfolioName, LocalDate valueOnDate, API api);

  /**
   * method to plot the performance of the portfolio in the form of bar char. It plots the bar chart
   * between the two given dates.
   *
   * @param portfolioName name of the portfolio.
   * @param startDate     start date.
   * @param endDate       end date.
   * @param api           API call object to obtain the price of the stock at a particular date.
   * @return returns a bar chart with a scale that indicates the performance of the portfolio.
   */
  String plotPerformanceOfPortfolio(String portfolioName, LocalDate startDate, LocalDate endDate,
      API api);

  /**
   * to validate whether a portfolio exists or not.
   *
   * @param portfolioName name of the portfolio.
   * @return 1 or 0 depending upon whether portfolio already exists or not.
   */
  public int validateFlexPortfolioExist(String portfolioName);
}
