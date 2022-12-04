package brokerage.command;

import java.util.Scanner;
import brokerage.CommandController;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioView;

/**
 * this class represent the command for creating the portfolio by uploading the file.
 */
public class CreatePortfolioThroughFileUploadHelper implements CommandController {

  @Override
  public void goCommand(PortfolioStrategyModel model, PortfolioView view, Scanner scan) {
    String portfolioName;
    String filePath;

    view.inputPortfolioName();
    portfolioName = scan.next();
    while (model.validatePortfolioExist(portfolioName) == 1) {
      view.output("Cannot edit existing portfolio. Provide a different name:");
      portfolioName = scan.next();
    }
    view.inputFilePath();
    filePath = scan.next();
    view.output(model.createPortfolioThroughFile(portfolioName, filePath));
  }
}
