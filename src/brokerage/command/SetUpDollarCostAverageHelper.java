package brokerage.command;

import brokerage.APIImpl;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import brokerage.API;
import brokerage.CommandController;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioView;

/**
 * this class represent the command for setting up dollar cost average.
 */
public class SetUpDollarCostAverageHelper implements CommandController {

  @Override
  public void goCommand(PortfolioStrategyModel model, PortfolioView view, Scanner scan)
      throws IOException {
    Map<String, Float> stockWeightMap = new HashMap<>();
    String portfolioName;
    int stockCount;
    String ticker;
    float weight = 0;
    float weightDiff = 0;
    LocalDate startDate = null;
    LocalDate endDate = null;
    float amount;
    double purchaseCom;
    int frequency;

    // get portfolio name
    view.inputPortfolioName();
    portfolioName = scan.next();
    while (model.validateStrategyPfExists(portfolioName) == 1) {
      view.output("This portfolio already exist. Provide a different name:");
      portfolioName = scan.next();
    }

    // get number of stock
    do {
      view.inputNoOfStocksToAdd();
      while (!scan.hasNextInt()) {
        String input = scan.next();
        view.output(input + " is not a valid number.");
      }
      stockCount = scan.nextInt();
    }
    while (stockCount <= 0);

    // get ticker
    view.output(model.displayListOfStocks());
    for (int i = 0; i < stockCount; i++) {
      view.inputTicker();
      ticker = scan.next();
      while (model.validateTickerExist(ticker) == 1) {
        view.output("Ticker doesn't exist. Provide a different name.");
        ticker = scan.next();
      }
      // get weight
      if (stockCount == 1) {
        view.output("Weight assigned = 100 because stock count is 1");
        weight = 100;
        stockWeightMap.put(ticker, weight);
      }
      if (stockCount > 1) {
        do {
          view.inputWeight();
          view.output("Remaining weight: " + (100 - weightDiff));
          while (!scan.hasNextFloat()) {
            String input = scan.next();
            view.output(input + " is not a valid number.");
          }
          weight = scan.nextFloat();
          weightDiff = weightDiff + weight;
        }
        while (weight <= 0 || weight >= 100);
        stockWeightMap.put(ticker, weight);
      }
    }

    // getting start date info
    boolean isValid;
    do {
      try {
        view.inputStartDate();
        String x = scan.next();
        startDate = parseDate(x);
        isValid = true;
      } catch (DateTimeParseException exception) {
        isValid = false;
        view.output("Enter a valid date pattern in yyyy-MM-dd:");
      }
    }
    while (!isValid);

    // getting end date info
    boolean isTrue;
    String answer;
    while (true) {
      view.output("Do you want to provide end date? Press Y or N: ");
      answer = scan.next().trim().toLowerCase();
      if (answer.equals("y")) {
        do {
          try {
            view.inputEndDate();
            String y = scan.next();
            endDate = parseDate(y);
            isTrue = true;
          } catch (DateTimeParseException exception) {
            isTrue = false;
            view.output("Enter a valid date pattern in yyyy-MM-dd:");
          }
        }
        while (!isTrue);
        break;
      }
      if (answer.equals("n")) {
        endDate = getTodayDate();
        break;
      }
    }

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

    // getting frequency

    do {
      view.inputFrequency();
      while (!scan.hasNextInt()) {
        String input = scan.next();
        view.output(input + " is not a valid number.");
      }
      frequency = scan.nextInt();
    }
    while (frequency <= 0);

    API api = new APIImpl();
    view.output(model.setUpDollarCostAverage(portfolioName, stockWeightMap, startDate,
        endDate, frequency, amount, purchaseCom, api));
  }

  /**
   * a helper method to get today's date in a specified format.
   *
   * @return returns today's date in specified format.
   */
  private LocalDate getTodayDate() {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String todayDate = dateFormat.format(LocalDate.now());
    return parseDate(todayDate);
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