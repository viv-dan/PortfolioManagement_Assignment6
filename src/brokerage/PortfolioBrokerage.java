package brokerage;

import brokerage.gui.PortfolioBrokerageGUIView;
import brokerage.gui.PortfolioGUIController;
import brokerage.gui.PortfolioGUIVew;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * This class have the 'main' method. It is from where the program needs to be executed.
 */
public class PortfolioBrokerage {

  /**
   * the main method of the application from where the program starts.
   *
   * @param args takes single argument of type string array.
   */
  public static void main(String[] args) {
    try {
      Scanner sc = new Scanner(System.in);
      System.out.println("1 : Text based view");
      System.out.println("2 : GUI view");
      int a = sc.nextInt();
      if (a == 1) {
        new PortfolioBrokerageController(new InputStreamReader(System.in),
            new PrintStream(System.out)).goController(new PortfolioStrategyModelImpl(),
            new PortfolioBrokerageView(System.out));
      } else if (a == 2) {
        PortfolioStrategyModel model = new PortfolioStrategyModelImpl();
        PortfolioBrokerageGUIView view = new PortfolioGUIVew("PortfolioBrokerage");
        PortfolioController control = new PortfolioGUIController();
        control.goController(model, view);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}