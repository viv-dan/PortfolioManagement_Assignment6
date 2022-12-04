package brokerage;

import java.io.IOException;
import java.time.LocalDate;

/**
 * this is the interface for the inflexible portfolio. It represents a portfolio that can contain
 * one or more stocks.
 */
public interface PortfolioModel {

  /**
   * creates list of stock.
   *
   * @param ticker       ticker symbol of the stock
   * @param quantity     quantity of the stock.
   * @param purchaseDate purchase date of the stock.
   * @return returns an integer that denotes whether the list of stock was created or not.
   */
  int createListOfStock(String ticker, int quantity, LocalDate purchaseDate);

  /**
   * method to create the portfolio. A portfolio may have one or more stocks of different
   * quantities.
   *
   * @param portfolioName name of the portfolio.
   * @return returns a string displaying the contents of the portfolio created.
   */
  String createPortfolio(String portfolioName);

  /**
   * creates a portfolio through a file upload. A portfolio has a list of stocks. A stock has ticker
   * and quantity as attributes.
   *
   * @param filePath the path of the file to be uploaded.
   */
  String createPortfolioThroughFile(String portfolioName, String filePath);

  /**
   * displays all the portfolios with the list of stocks in it.
   */
  String examineAllPortfolios(String portfolioName);

  /**
   * displays the total value of a particular portfolio.
   *
   * @param portfolioName the name of the portfolio
   * @return the value of the portfolio in string.
   */
  String displayPortfolioValue(String portfolioName, LocalDate valueOnDate);

  /**
   * method to download the portfolios in a file.
   */
  String downloadPortfolio() throws IOException;

  /**
   * method to validate if the portfolio exists. this method is used while creating the portfolio.
   *
   * @param portfolioName name of the portfolio.
   * @return returns an integer 1 or 0.
   */
  int validatePortfolioExist(String portfolioName);


  /**
   * validates if the ticker is a valid ticker.
   *
   * @param portfolioName takes ticker as argument
   * @return an integer 1 or 0 depending on the condition.
   */
  int validateTickerExist(String portfolioName);

  /**
   * displays list of stocks available for creation of a portfolio.
   *
   * @return list of stocks available for creation of a portfolio
   */
  String displayListOfStocks();

}
