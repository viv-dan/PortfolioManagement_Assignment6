package brokerage.command;

import brokerage.APIImpl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import brokerage.API;
import brokerage.CommandController;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioView;

/**
 * this class represent the command for creating the flexible portfolio.
 */
public class CreateFlexPortfolioHelper implements CommandController {

  @Override
  public void goCommand(PortfolioStrategyModel model, PortfolioView view, Scanner scan) {

    String portfolioName;
    String ticker;
    int quantity;
    int stockCount;
    Double purchaseCom;
    LocalDate date = null;
    view.inputPortfolioName();
    portfolioName = scan.next();
    API api = new APIImpl();

    do {
      view.inputNoOfStocksToAdd();
      while (!scan.hasNextInt()) {
        String input = scan.next();
        view.output(input + " is not a valid number.");
      }
      stockCount = scan.nextInt();
    }
    while (stockCount <= 0);

    view.output(model.displayListOfStocks());
    for (int i = 0; i < stockCount; i++) {
      // getting ticker
      view.inputTicker();
      ticker = scan.next();
      while (model.validateTickerExist(ticker) == 1) {
        view.output("Ticker doesn't exist. Provide a different name.");
        ticker = scan.next();
      }

      // getting quantity
      do {
        view.inputQuantity();
        while (!scan.hasNextInt()) {
          String input = scan.next();
          view.output(input + " is not a valid number.");
        }
        quantity = scan.nextInt();
      }
      while (quantity <= 0);

      // getting date info
      boolean isValid;
      do {
        try {
          view.inputPurchaseDate();
          String userInput = scan.next();
          date = parseDate(userInput);
          if (api.stockCurrentValueFromAPI(ticker, date) == 0) {
            view.output("Cannot purchase on weekend or public holiday. Please input another date");
            isValid = false;
          } else {
            isValid = true;
          }
        } catch (DateTimeParseException exception) {
          isValid = false;
        }
      }
      while (!isValid);

      // getting Purchase Commission
      do {
        view.inputPurchaseCom();
        while (!scan.hasNextDouble()) {
          String input = scan.next();
          view.output(input + " is not a valid number.");
        }
        purchaseCom = scan.nextDouble();
      }
      while (purchaseCom <= 0);

      // creating list of stock
      model.createListOfStockForFlex(ticker, quantity, date, purchaseCom, api);
    }
    view.output(model.createPortfolio(portfolioName));
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
