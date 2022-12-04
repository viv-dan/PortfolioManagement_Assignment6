package brokerage.command;

import java.util.Scanner;
import brokerage.CommandController;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioView;

/**
 * this class represent the command for examining portfolio composition.
 */
public class ExaminePortfolioHelper implements CommandController {

  @Override
  public void goCommand(PortfolioStrategyModel model, PortfolioView view, Scanner scan) {
    String portfolioName;

    view.inputPortfolioName();
    portfolioName = scan.next();
    while (model.validatePortfolioExist(portfolioName) == 0) {
      view.output("This portfolio doesn't exist. Provide a different name:");
      portfolioName = scan.next();
    }
    view.output(model.examineAllPortfolios(portfolioName));
  }
}
