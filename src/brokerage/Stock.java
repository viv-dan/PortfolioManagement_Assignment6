package brokerage;

import java.time.LocalDate;

/**
 * This class represents a stock. A stock has a ticker, quantity, purchase date, purchase price and
 * purchase commission attributes.
 */
public class Stock {

  private String ticker;
  private float quantity;
  private double purchasePrice;
  private LocalDate purchaseDate;
  private double purchaseCom;

  /**
   * sets the ticker of the stock.
   *
   * @param ticker takes ticker of the stock as argument.
   */
  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  /**
   * sets the quantity of the stock.
   *
   * @param quantity takes quantity of the stock as argument.
   */
  public void setQuantity(float quantity) {
    this.quantity = quantity;
  }

  /**
   * sets the purchase price of the stock.
   *
   * @param purchasePrice takes purchase price as argument.
   */
  public void setPurchasePrice(double purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  /**
   * sets the purchase date of the stock.
   *
   * @param purchaseDate takes purchase date as argument.
   */
  public void setPurchaseDate(LocalDate purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  /**
   * sets the purchase commission of the stock.
   *
   * @param purchaseCom takes the purchase commission as the argument.
   */
  public void setPurchaseCom(double purchaseCom) {
    this.purchaseCom = purchaseCom;
  }

  /**
   * to get the ticker of the stock.
   *
   * @return ticker of the stock
   */
  public String getTicker() {
    return this.ticker;
  }

  /**
   * to get the quantity of the stock.
   *
   * @return the quantity of the stock
   */
  public float getQuantity() {
    return this.quantity;
  }

  /**
   * to get the purchase price of the stock.
   *
   * @return the purchase price of the stock.
   */
  public double getPurchasePrice() {
    return this.purchasePrice;
  }

  /**
   * to get the purchase date of a stock.
   *
   * @return the purchase date of a stock.
   */
  public LocalDate getPurchaseDate() {
    return this.purchaseDate;
  }

  /**
   * to get the purchase commission of a stock.
   *
   * @return the purchase commission of a stock.
   */
  public double getPurchaseCom() {
    return this.purchaseCom;
  }

}
