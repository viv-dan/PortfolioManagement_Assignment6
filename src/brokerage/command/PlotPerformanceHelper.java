package brokerage.command;

import brokerage.APIImpl;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import brokerage.API;
import brokerage.CommandController;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioView;

/**
 * this class represent the command for plotting the performance of the portfolio.
 */
public class PlotPerformanceHelper implements CommandController {

  @Override
  public void goCommand(PortfolioStrategyModel model, PortfolioView view, Scanner scan)
      throws IOException {
    String portfolioName;
    LocalDate startDate;
    LocalDate endDate;
    API api = new APIImpl();

    view.inputPortfolioName();
    portfolioName = scan.next();
    while (model.validateFlexPortfolioExist(portfolioName) == 0) {
      view.output("This portfolio doesn't exist. Provide a different name:");
      portfolioName = scan.next();
    }
    view.output("Enter the start date for plotting the performance and date "
        + "should be in MM/dd/YYYY format:");
    startDate = LocalDate.parse(scan.next(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    view.output("Enter the End date for plotting the performance and date "
        + "should be in MM/dd/yyyy format:");
    endDate = LocalDate.parse(scan.next(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    view.output(
        model.plotPerformanceOfPortfolio(portfolioName, startDate, endDate, api));
  }
}
