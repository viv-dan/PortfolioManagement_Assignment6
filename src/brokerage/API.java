package brokerage;

import java.time.LocalDate;

/**
 * This class represents the API as an object. An API object can make an api call to fetch the price
 * of the stock for any given date. This object has an operation (method) of getting the price of a
 * stock at any given date.
 */
public interface API {

  /**
   * to extract the price of a stock at any given date.
   *
   * @param ticker      the ticker of the stock.
   * @param valueOnDate the date on which one wants to know the price of a stock.
   * @return returns the price of the stock.
   */
  double stockCurrentValueFromAPI(String ticker, LocalDate valueOnDate);

}