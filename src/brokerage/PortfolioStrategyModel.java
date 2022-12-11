package brokerage;

import java.time.LocalDate;
import java.util.Map;

/**
 * This is the interface for the portfolio's strategies. It represents various strategies a user can
 * have in a portfolio. It defines signature for two strategies: User can create a weighted
 * portfolio with specified weights for the stocks. User can also set up a dollar cost averaging
 * where a specified amount is invested between two dates.
 */

public interface PortfolioStrategyModel extends PortfolioFlexModel {


  /**
   * creates a portfolio with specified weights for each stock.
   *
   * @param portfolioName  name of the portfolio.
   * @param stockWeightMap stock and weight map.
   * @return returns a text output whether the portfolio was successfully created or not.
   */
  String createWeightedPf(String portfolioName, Map<String, Float> stockWeightMap);

  /**
   * displays a weighted portfolio. The list of stocks with their specified weights.
   *
   * @param portfolioName name of the portfolio that one wants to display
   * @return returns the portfolio and its composition - list of stocks with their weights.
   */
  String displayWeightedPf(String portfolioName);

  /**
   * method to invest into a weighted portfolio with specified amount, commission, and date.
   *
   * @param portfolioName name of the portfolio.
   * @param investDate    the date of investment/purchase.
   * @param amount        the amount that the user wants to invest into the portfolio.
   * @param purchaseCom   the purchase commission.
   * @param api           the object of the api class to make the api call.
   * @return returns the message whether the purchase was successfully made or not.
   */
  String investInWeightedPf(String portfolioName, LocalDate investDate,
      float amount, float purchaseCom, API api);

  /**
   * a method to set up a dollar cost averaging for a weighted portfolio.
   *
   * @param portfolioName  name of the weighted portfolio.
   * @param stockWeightMap the stocks and weights mapping.
   * @param startDate      the start date of the investment.
   * @param endDate        the end date of the investment. user may not specify the end date.
   * @param frequency      the frequency of investment in days.
   * @param amount         the amount of investment.
   * @param purchaseCom    the purchase commission.
   * @param api            the object of the api class to make the api call.
   * @return returns the message whether dollar cost averaging was successfully set up or not.
   */
  String setUpDollarCostAverage(String portfolioName, Map<String, Float> stockWeightMap,
      LocalDate startDate, LocalDate endDate, int frequency,
      float amount, double purchaseCom, API api);

  /**
   * displays the dollar cost averaging currently active/set up.
   *
   * @param portfolioName name of the portfolio.
   * @return the dollar cost averaging currently active/set up.
   */
  String displayDollarCostAverage(String portfolioName);

  /**
   * a method to validate whether a portfolio with a strategy exits or not.
   *
   * @param portfolioName name of the portfolio.
   * @return returns 1 or 0 depending upon whether the portfolio with a strategy exits or not.
   */
  int validateStrategyPfExists(String portfolioName);

  /**
   * a method which re-balances the portfolio according to their weights.
   *
   * @param portfolioName the name of the portfolio
   * @param date the date on which the portfolio has to be re-balanced
   * @return returns a successful message or a failure message according to the input data.
   */
  String reBalancePortfolio(String portfolioName, LocalDate date, Map<String, Double> weights, API api);

}




