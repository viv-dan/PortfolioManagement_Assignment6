package brokerage.command;

import brokerage.APIImpl;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import brokerage.API;
import brokerage.CommandController;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioView;

/**
 * this class represent the command for selling the stock from flexible portfolio.
 */
public class SellStockFromFlexHelper implements CommandController {

  @Override
  public void goCommand(PortfolioStrategyModel model, PortfolioView view, Scanner scan)
      throws IOException {
    String portfolioName;
    String ticker;
    int quantity;
    LocalDate date = null;
    API api = new APIImpl();

    // getting portfolio name
    view.inputPortfolioName();
    portfolioName = scan.next();
    while (model.validateFlexPortfolioExist(portfolioName) == 0
        && model.validateStrategyPfExists(portfolioName) == 0) {
      view.output("Portfolio with this name doesn't exist. Provide a different name");
      portfolioName = scan.next();
    }

    do {
      // getting date info
      boolean isValid;
      do {
        try {
          view.inputSellDate();
          String userInput = scan.next();
          date = parseDate(userInput);
          isValid = true;
        } catch (DateTimeParseException exception) {
          isValid = false;
          view.output("Enter a valid date pattern:");
        }
      }
      while (!isValid);

      // Getting ticker info
      view.inputTicker();
      ticker = scan.next();
      while (model.validateTickerExist(ticker) == 1) {
        view.output("The app doesn't support this Ticker. Provide a different name.");
        ticker = scan.next();
      }

      // getting quantity info
      do {
        view.inputQuantity();
        while (!scan.hasNextInt()) {
          String input = scan.next();
          view.output(input + " is not a valid number.");
        }
        quantity = scan.nextInt();
      }
      while (quantity <= 0);

    }
    while (model.validateSellableQuantity(portfolioName, date, ticker, quantity, api)
        == 1);

    view.output(
        model.sellStockFromFlexPF(portfolioName, ticker, quantity, date, api));
  }

  /**
   * a helper method to parse the string in the specified date format.
   *
   * @param date the date that needs to be parsed in string.
   * @return returns the date in a desired format.
   */
  private LocalDate parseDate(String date) {
    try {
      return LocalDate.parse(date);
    } catch (Exception e) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm/dd/YYYY");
      return LocalDate.parse(date, formatter);
    }
  }
}
