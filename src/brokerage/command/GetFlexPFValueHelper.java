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
 * this class represent the command for getting the value of the flexible portfolio.
 */
public class GetFlexPFValueHelper implements CommandController {

  @Override
  public void goCommand(PortfolioStrategyModel model, PortfolioView view, Scanner scan)
      throws IOException {
    String portfolioName;
    LocalDate date = null;

    view.inputPortfolioName();
    portfolioName = scan.next();
    while (model.validateFlexPortfolioExist(portfolioName) == 0
        && model.validateStrategyPfExists(portfolioName) == 0) {
      view.output("This portfolio doesn't exist. Provide a different name:");
      portfolioName = scan.next();
    }
    boolean isValid;
    do {
      try {
        view.inputDateToValidate();
        String userInput = scan.next();
        date = parseDate(userInput);
        isValid = true;
      } catch (DateTimeParseException exception) {
        isValid = false;
        view.output("Enter a valid date pattern:");
      }
    }
    while (!isValid);
    API api = new APIImpl();
    view.output(model.displayFlexPortfolioValue(portfolioName, date, api));
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
