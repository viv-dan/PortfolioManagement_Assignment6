package brokerage;

import java.io.PrintStream;

/**
 * this class implements the interface for the view of the application. It defines the signature of
 * the methods to be used for I/O.
 */
public class PortfolioBrokerageView implements PortfolioView {

  private PrintStream out;

  /**
   * constructor of the view interface.
   */

  public PortfolioBrokerageView(PrintStream out) {
    this.out = out;
  }

  @Override
  public void inputChoice() {
    this.out.println("\n=================== INFLEXIBLE PORTFOLIO ===============================");
    this.out.println("Press 1 to create a new Inflexible Portfolio manually.");
    this.out.println("Press 2 to create a new Inflexible Portfolio through a file upload.");
    this.out.println("Press 3 to examine the composition of the Inflexible Portfolio.");
    this.out.println("Press 4 to get the value of a Inflexible Portfolio at a certain date.");
    this.out.println("Press 5 to download your portfolios into a file.");
    this.out.println("Press 6 to exit from the menu.");

    this.out.println("\n=================== FLEXIBLE PORTFOLIO ===============================");
    this.out.println("Press 7 to create and/or add stocks to a Flexible Portfolio.");
    this.out.println("Press 8 to sell stocks from an existing Flexible Portfolio.");
    this.out.println("Press 9 to know the cost basis of a Flexible Portfolio.");
    this.out.println("Press 10 to plot the performance of a Flexible Portfolio.");
    this.out.println("Press 11 to examine the composition of the Flexible Portfolio.");
    this.out.println("Press 12 to get the value of a Flexible portfolio at a certain date.");
    this.out.println(
        "Press 13 to examine the composition of Flexible portfolio at a certain date.");
    this.out.println("Press 14 to create a weighted portfolio.");
    this.out.println("Press 15 to display a weighted portfolio.");
    this.out.println("Press 16 to make an investment in a weighted portfolio.");
    this.out.println("Press 17 to set up dollar cost average for a portfolio.");
    this.out.println("Press 18 to display dollar cost average set up.");
    this.out.println("Press 19 to re-balance a flexible portfolio on a specific date");
    this.out.println("\nEnter your selection from above available options:");
  }

  @Override
  public void inputNoOfStocksToAdd() {
    this.out.println("Enter (non-negative integer greater than 0) for number of stocks:");
  }

  @Override
  public void inputPortfolioName() {
    this.out.println("Enter the name of the portfolio:");
  }

  @Override
  public void inputTicker() {
    this.out.println("Enter the ticker of the stock:");
  }

  @Override
  public void inputQuantity() {
    this.out.println("Enter the quantity of the stock:");
  }

  @Override
  public void inputFilePath() {
    this.out.println("Enter the file path:");
  }

  @Override
  public void inputDateToValidate() {
    this.out.println("Enter the date in yyyy-MM-dd format:");
  }

  @Override
  public void inputPurchaseDate() {
    this.out.println("Enter the purchase date in yyyy-MM-dd format:");
  }

  @Override
  public void inputSellDate() {
    this.out.println("Enter the sell date in yyyy-MM-dd format:");
  }

  @Override
  public void inputPurchaseCom() {
    this.out.println("Enter the purchase commission:");
  }

  @Override
  public void inputWeight() {
    this.out.println("Enter the weight for the stock between 1 and 100:");
  }

  @Override
  public void inputAmount() {
    this.out.println("Enter the investment amount for weighted portfolio: ");
  }

  @Override
  public void inputStartDate() {
    this.out.println("Enter the start date for dollar cost average: ");
  }

  @Override
  public void inputEndDate() {
    this.out.println("Enter the end date for dollar cost average: ");
  }

  @Override
  public void inputFrequency() {
    this.out.println("Enter the frequency/interval in days: ");
  }

  @Override
  public void output(String s) {
    this.out.println(s);
  }

}
