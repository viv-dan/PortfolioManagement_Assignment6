import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import brokerage.API;
import brokerage.APIImpl;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioStrategyModelImpl;

public class demo {

  public static void main(String[] args) {
    PortfolioStrategyModel pm = new PortfolioStrategyModelImpl();
    API api = new APIImpl();
    //System.out.println(api.stockCurrentValueFromAPI("GOOG", parseDate("2022-12-03")));
    //System.out.println(pm.displayFlexPortfolioValue("FANG", parseDate("2022-11-27"), api));
    //System.out.println(pm.displayWeightedPf("FANG"));
    //System.out.println(pm.examinePortfolioByDate("PF1", parseDate("2022-12-01")));
    System.out.println(pm.reBalancePortfolio("FANG", parseDate("2022-12-01"), api));
    //System.out.println(pm.validateStrategyPfExists("PF1"));
    //System.out.println(pm.displayWeightedPf("FANG"));
  }

  private static LocalDate parseDate(String date) {
    try {
      return LocalDate.parse(date);
    } catch (Exception e) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm/dd/YYYY");
      return LocalDate.parse(date, formatter);
    }
  }
}
