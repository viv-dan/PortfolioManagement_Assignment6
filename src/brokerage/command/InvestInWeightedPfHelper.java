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
 * this class represent the command for investing into a weighted portfolio.
 */
public class InvestInWeightedPfHelper implements CommandController {

  @Override
  public void goCommand(PortfolioStrategyModel model, PortfolioView view, Scanner scan)
      throws IOException {
    String portfolioName;
    LocalDate date = null;
    float amount;
    float purchaseCom;
    view.inputPortfolioName();
    portfolioName = scan.next();
    while (model.validateStrategyPfExists(portfolioName) == 0) {
      view.output("This portfolio doesn't exist. Provide a different name:");
      portfolioName = scan.next();
    }
    // getting date info
    boolean isValid;
    do {
      try {
        view.inputPurchaseDate();
        String userInput = scan.next();
        date = parseDate(userInput);
        isValid = true;
      } catch (DateTimeParseException exception) {
        isValid = false;
        view.output("Enter a valid date pattern in yyyy-MM-dd:");
      }
    }
    while (!isValid);

    // getting Purchase amount
    do {
      view.inputAmount();
      while (!scan.hasNextFloat()) {
        String input = scan.next();
        view.output(input + " is not a valid number.");
      }
      amount = scan.nextFloat();
    }
    while (amount <= 0);

    // getting Purchase Commission
    do {
      view.inputPurchaseCom();
      while (!scan.hasNextFloat()) {
        String input = scan.next();
        view.output(input + " is not a valid number.");
      }
      purchaseCom = scan.nextFloat();
    }
    while (purchaseCom <= 0);

    API api = new APIImpl();
    view.output(model.investInWeightedPf(portfolioName, date, amount, purchaseCom, api));
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
