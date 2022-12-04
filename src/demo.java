import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioStrategyModelImpl;

public class demo {

  public static void main(String[] args) {
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    System.out.println(pm.displayWeightedPf("FANG"));

  }
}
