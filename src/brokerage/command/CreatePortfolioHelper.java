package brokerage.command;

import java.time.LocalDate;
import java.util.Scanner;
import brokerage.CommandController;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioView;

/**
 * this class represents the command for creating the inflexible portfolio.
 */
public class CreatePortfolioHelper implements CommandController {

  @Override
  public void goCommand(PortfolioStrategyModel model, PortfolioView view, Scanner scan) {
    String portfolioName;
    String ticker;
    int stockCount;
    int quantity;
    view.inputPortfolioName();
    portfolioName = scan.next();
    while (model.validatePortfolioExist(portfolioName) == 1) {
      view.output("Cannot edit existing portfolio. Provide a different name.");
      portfolioName = scan.next();
    }

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
      view.inputTicker();
      ticker = scan.next();
      while (model.validateTickerExist(ticker) == 1) {
        view.output("Ticker doesn't exist. Provide a different name.");
        ticker = scan.next();
      }

      do {
        view.inputQuantity();
        while (!scan.hasNextInt()) {
          String input = scan.next();
          view.output(input + " is not a valid number.");
        }
        quantity = scan.nextInt();
      }
      while (quantity <= 0);

      model.createListOfStock(ticker, quantity, LocalDate.now());
    }
    view.output(model.createPortfolio(portfolioName));
  }
}
