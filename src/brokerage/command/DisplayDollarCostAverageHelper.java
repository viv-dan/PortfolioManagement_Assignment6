package brokerage.command;

import java.util.Scanner;
import brokerage.CommandController;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioView;

/**
 * this class represent the command for displaying the dollar cost average set up.
 */
public class DisplayDollarCostAverageHelper implements CommandController {

  @Override
  public void goCommand(PortfolioStrategyModel model, PortfolioView view, Scanner scan) {
    String portfolioName;
    view.inputPortfolioName();
    portfolioName = scan.next();
    while (model.validateStrategyPfExists(portfolioName) == 0) {
      view.output("This portfolio doesn't exist. Provide a different name:");
      portfolioName = scan.next();
    }
    view.output(model.displayDollarCostAverage(portfolioName));
  }
}
