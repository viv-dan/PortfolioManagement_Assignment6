package brokerage;

import java.io.IOException;

/**
 * This interface represents an object (a control) that interacts with the view and the model. It
 * receives inputs from the view (user) and passes those inputs to the model for specific operation.
 * It translates the user's interactions with the view into actions that the model will perform. It
 * also displays the output of the various operations performed by the model to the view.
 */
public interface PortfolioController {

  /**
   * the go method that gives control to the controller.
   *
   * @param portfolioModel represents the model object.
   * @param portfolioView  represents the view object.
   * @throws IOException throws input/output exception.
   */
  void goController(PortfolioStrategyModel portfolioModel, PortfolioView portfolioView)
      throws IOException;

}