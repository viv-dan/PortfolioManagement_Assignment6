package brokerage;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

import brokerage.command.CreateFlexPortfolioHelper;
import brokerage.command.CreatePortfolioHelper;
import brokerage.command.CreatePortfolioThroughFileUploadHelper;
import brokerage.command.CreateWeightedPfHelper;
import brokerage.command.DisplayDollarCostAverageHelper;
import brokerage.command.DisplayWeightedPfHelper;
import brokerage.command.DownloadPortfolioHelper;
import brokerage.command.ExamineCompositionByDateHelper;
import brokerage.command.ExamineCompositionHelper;
import brokerage.command.ExaminePortfolioHelper;
import brokerage.command.GetCostBasisHelper;
import brokerage.command.GetFlexPFValueHelper;
import brokerage.command.GetPortfolioValueHelper;
import brokerage.command.InvestInWeightedPfHelper;
import brokerage.command.PlotPerformanceHelper;
import brokerage.command.SellStockFromFlexHelper;
import brokerage.command.SetUpDollarCostAverageHelper;
import brokerage.command.rebalancePortfolio;


/**
 * this class implements the PortfolioController interface.
 */
public class PortfolioBrokerageController implements PortfolioController {

  final Readable in;
  private PrintStream out;

  /**
   * constructs the controller to take input stream and output stream as arguments.
   *
   * @param in  the input stream that it receives from the view.
   * @param out the output stream that needs to be displayed to the view.
   */
  public PortfolioBrokerageController(Readable in, PrintStream out) {
    this.in = in;
    this.out = out;
  }

  @Override
  public void goController(PortfolioStrategyModel portfolioModel, PortfolioView portfolioView)
      throws IOException {
    Objects.requireNonNull(portfolioModel);
    CommandController command = null;
    int menuChoice;
    portfolioView = new PortfolioBrokerageView(out);
    Scanner scan = new Scanner(this.in);
    while (true) {
      do {
        portfolioView.inputChoice();
        while (!scan.hasNextInt()) {
          portfolioView.output(
              "Only integer is allowed. Please provide an integer between 1 and 19!");
          scan.next();
        }
        menuChoice = scan.nextInt();
      }
      while (!(menuChoice < 20 && menuChoice > 0));
      switch (menuChoice) {

        // Create an Inflexible Portfolio manually.
        case 1:
          command = new CreatePortfolioHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        // Create an Inflexible Portfolio through a file upload.
        case 2:
          command = new CreatePortfolioThroughFileUploadHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        // Examine the composition of the portfolio.
        case 3:
          command = new ExaminePortfolioHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        // Get the value of a portfolio at a certain date.
        case 4:
          command = new GetPortfolioValueHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        // Download your portfolios into a file.
        case 5:
          command = new DownloadPortfolioHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        // Exit from the menu.
        case 6:
          System.exit(0);
          break;

        //*************************** Menu Options for Flexible Portfolio ********************

        // Create a Flexible Portfolio manually.
        case 7:
          command = new CreateFlexPortfolioHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        // Sell stocks from an existing Flexible Portfolio.
        case 8:
          command = new SellStockFromFlexHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        // Examine the cost basis of a Flexible Portfolio.
        case 9:
          command = new GetCostBasisHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        // Plot the performance of a portfolio
        case 10:
          command = new PlotPerformanceHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        // examine the composition of the portfolio.
        case 11:
          command = new ExamineCompositionHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        //to get the value of a Flexible portfolio at a certain date.
        case 12:
          command = new GetFlexPFValueHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        // examine the composition of Flexible portfolio at a certain date.
        case 13:
          command = new ExamineCompositionByDateHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        // create a weighted portfolio.
        case 14:
          command = new CreateWeightedPfHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        // display a weighted portfolio.
        case 15:
          command = new DisplayWeightedPfHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        // invest into weighted portfolio.
        case 16:
          command = new InvestInWeightedPfHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        // set up dollar cost average.
        case 17:
          command = new SetUpDollarCostAverageHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;

        // display dollar cost average.
        case 18:
          command = new DisplayDollarCostAverageHelper();
          command.goCommand(portfolioModel, portfolioView, scan);
          break;
        // re-balance a portfolio with weights.
        case 19:
          command = new rebalancePortfolio();
          command.goCommand(portfolioModel, portfolioView, scan);

        default:
          System.out.println(String.format("Unknown command %s", in));
          command = null;
          break;
      }
    }
  }
}