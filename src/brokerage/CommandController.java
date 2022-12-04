package brokerage;

import java.io.IOException;
import java.util.Scanner;

/**
 * the command interface for the command controller. It represents the command that needs to be
 * executed based on the input provided by the user.
 */
public interface CommandController {

  /**
   * it defines the command that needs to be executed depending upon the input provided by the
   * user.
   *
   * @param model represents the object of the model.
   * @param view  represents the object of the view.
   * @param scan  represents the input scanned from the user
   * @throws IOException throws input/output exception.
   */
  void goCommand(PortfolioStrategyModel model, PortfolioView view, Scanner scan) throws IOException;
}
