package brokerage;

/**
 * This interface represents an object (a view) that interacts with the client. It requests user to
 * provide inputs, It also displays the output of the various operations that the user performs.
 */
public interface PortfolioView {

  /**
   * to get the choice of task that the user wants to perform on the application.
   */
  void inputChoice();

  /**
   * to get the number of stocks that the user wants to add to the portfolio.
   */
  void inputNoOfStocksToAdd();

  /**
   * to get the name of the portfolio.
   */
  void inputPortfolioName();

  /**
   * to get the ticker of a stock that the user wants to add to the portfolio.
   */
  void inputTicker();

  /**
   * to get the quantity of a particular stock.
   */
  void inputQuantity();

  /**
   * to get the file path that the user want to upload in order to create the portfolio.
   */
  void inputFilePath();

  /**
   * asks users to input date for determining the portfolio value.
   */
  void inputDateToValidate();

  /**
   * asks user to input the date of purchase of a particular stock.
   */
  void inputPurchaseDate();

  /**
   * asks user to input the sell date of a particular stock.
   */

  void inputSellDate();

  /**
   * asks user to input the purchase commission.
   */
  void inputPurchaseCom();


  /**
   * asks user to input the weight for each stock.
   */
  void inputWeight();

  /**
   * asks user to input the amount that he/she wants to invest in a weighted portfolio.
   */
  void inputAmount();

  /**
   * asks user to input the start date for dollar cost average.
   */
  void inputStartDate();

  /**
   * asks user to input the end date for dollar cost average.
   */
  void inputEndDate();

  /**
   * asks user to input the frequency for the dollar cost averaging.
   */
  void inputFrequency();

  /**
   * method to display output to the console.
   *
   * @param s takes string as parameter
   */
  void output(String s);

}

