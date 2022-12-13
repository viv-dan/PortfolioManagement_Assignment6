package brokerage.command;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import brokerage.API;
import brokerage.APIImpl;
import brokerage.CommandController;
import brokerage.PortfolioStrategyModel;
import brokerage.PortfolioView;

public class rebalancePortfolio implements CommandController {
  /**
   * it defines the command that needs to be executed depending upon the input provided by the
   * user.
   *
   * @param model represents the object of the model.
   * @param view  represents the object of the view.
   * @param scan  represents the input scanned from the user
   * @throws IOException throws input/output exception.
   */
  @Override
  public void goCommand(PortfolioStrategyModel model, PortfolioView view, Scanner scan) throws IOException {
    String portfolioName;
    LocalDate date = null;
    view.inputPortfolioName();
    portfolioName = scan.next();
    while (model.validateFlexPortfolioExist(portfolioName) == 0) {
      view.output("This portfolio doesn't exist. Provide a different name");
      portfolioName = scan.next();
    }
    // getting date info
    boolean isValid;
    int stockCount;
    Map<String,Double> stockWeightMap = new HashMap<>();
    double weight;
    String ticker;
    double weightDiff=0;
    do {
      try {
        view.inputDateToValidate();
        String userInput = scan.next();
        date = parseDate(userInput);
        isValid = true;
      } catch (DateTimeParseException exception) {
        isValid = false;
        view.output("Enter a valid date pattern:");
      }
    }
    while (!isValid);
    API api = new APIImpl();

    // get ticker
    String compostion = model.examinePortfolioByDate(portfolioName, date);
    String[] compositionSplit = compostion.split("\n");
    HashMap<String, Double> hm = new HashMap<>();
    for(int j=4; j<compositionSplit.length;j++){
      String[] tickerRow = compositionSplit[j].split("\t");
      hm.put(tickerRow[0], hm.getOrDefault(tickerRow[0],0.0));
    }
    for (String s:hm.keySet()) {
      if(s.startsWith("No")){
        view.output(s);
        view.output("Can't rebalance portfolio");
        return;
      }
    }
    stockCount=hm.size();
    for (int i = 0; i < stockCount; i++) {
      //view.inputTicker();
      view.output("Choose ticker from list");
      for (String s:hm.keySet()) {
        view.output(s);
      }
      ticker = scan.next();
      while (model.validateTickerExist(ticker) == 1) {
        view.output("Ticker doesn't exist. Provide a different name.");
        ticker = scan.next();
      }
      // get weight
      if (stockCount == 1) {
        view.output("Weight assigned = 100 because stock count is 1");
        weight = 100;
        stockWeightMap.put(ticker, weight);
      }
      if (stockCount > 1) {
        do {
          if(weightDiff>=100 || weightDiff<0){
            view.output("Input weight composition is incorrect");
            return;
          }
          view.inputWeight();
          view.output("Remaining weight: " + (100 - weightDiff));
          while (!scan.hasNextFloat()) {
            String input = scan.next();
            view.output(input + " is not a valid number.");
          }
          weight = scan.nextFloat();
          weightDiff = weightDiff + weight;
        }
        while (weight <= 0 || weight >= 100);
        stockWeightMap.put(ticker, weight);
      }
    }
    if(hm.keySet().equals(stockWeightMap.keySet())){
      try{
        view.output(model.reBalancePortfolio(portfolioName, date, stockWeightMap,api));
      }catch (Exception e){
        view.output(e.getMessage());
      }
    }
    else{
      view.output("Enter stocks that are present in the portfolio");
    }

  }

  /**
   * a helper method to parse the string in the specified date format.
   *
   * @param date the date that needs to be parsed in string.
   * @return returns the date in a desired format.
   */
  private LocalDate parseDate(String date) {
    try {
      return LocalDate.parse(date);
    } catch (Exception e) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm/dd/YYYY");
      return LocalDate.parse(date, formatter);
    }
  }
}
