package brokerage.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import brokerage.CommandController;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioView;

/**
 * this class represents the command for creating a weighted portfolio.
 */
public class CreateWeightedPfHelper implements CommandController {

  @Override
  public void goCommand(PortfolioStrategyModel model, PortfolioView view, Scanner scan) {

    Map<String, Float> stockWeightMap = new HashMap<>();
    String portfolioName;
    int stockCount;
    String ticker;
    float weight;
    float weightDiff = 0;

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
    view.output(model.createWeightedPf(portfolioName, stockWeightMap));

  }
}
