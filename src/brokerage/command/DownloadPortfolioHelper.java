package brokerage.command;

import java.io.IOException;
import java.util.Scanner;

import brokerage.CommandController;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioView;

/**
 * this class represent the command for downloading the portfolios in csv file.
 */
public class DownloadPortfolioHelper implements CommandController {

  @Override
  public void goCommand(PortfolioStrategyModel model, PortfolioView view, Scanner scan)
      throws IOException {
    view.output(model.downloadPortfolio());
  }
}
