package brokerage.gui;

import brokerage.PortfolioView;

/**
 * this interface contains the methods which performs the action on the graphical user interface.
 */
public interface PortfolioBrokerageGUIView extends PortfolioView {

  /**
   * the method used to perform the action in the portfolio brokerage panel.
   *
   * @param features passing the object of featuresGUI Interface.
   */
  void addFeaturesPortfolioBrokerage(FeaturesGUI features);

  /**
   * the method used to perform the action in the flex portfolio panel.
   *
   * @param feature passing the object of featuresGUI Interface.
   */
  void addFeaturesCreateFlexPortfolio(FeaturesGUI feature);

  /**
   * the method is used to display the main panel to the user.
   */
  void mainMenu();

  /**
   * composition portfolio is used to display the composition of portfolio to the user.
   */
  void compositionOfPortfolio();

  /**
   * the method used to perform the action in the invest weighted portfoliopanel.
   *
   * @param feature passing the object of featuresGUI Interface.
   */
  void addFeaturesInvestWeightedPortfolio(FeaturesGUI feature);

  /**
   * this method displays the fields of create dollar cost average panel to user.
   */
  void createDCAFeatureImpl();

  /**
   * the method used to perform the action in the create dollar cost average panel.
   *
   * @param feature passing the object of featuresGUI Interface.
   */
  void addFeaturesCreateDCA(FeaturesGUI feature);

  /**
   * the method used to perform the action in the weighted portfolio panel.
   *
   * @param feature passing the object of featuresGUI Interface.
   */
  void addFeaturesCreateWeightedPortfolio(FeaturesGUI feature);

  /**
   * this method is used to clear the text fields in the weighted portfolio.
   */
  void clearTextInTheCreateWeightedPF();

  /**
   * method is used to disable the AddStock button in the weighted portfolio panel.
   */
  void setWeightedPFAddStockButtonNotClickable();

  /**
   * this makes the add stock button greyed out once the valid stocks are added.
   */
  void setDCAAddStockButtonNotClickable();

  /**
   * the method used to perform the action in the composition of portfolio panel.
   *
   * @param feature passing the object of featuresGUI Interface.
   */
  void addFeaturesCompositionPortfolio(FeaturesGUI feature);

  /**
   * this method displays the fields which are required to create a portfolio.
   */
  void createFlexiblePortfolioView();

  /**
   * this method is used to display the fields which are required to get the composition.
   */
  void getCostBasisOfPortfolio();

  /**
   * the method used to perform the action in the cost basis of portfolio panel.
   *
   * @param feature passing the object of featuresGUI Interface.
   */
  void addFeaturesOfGetCostBasis(FeaturesGUI feature);

  /**
   * this method is used to display the output of the every feature in the GUI.
   *
   * @param output represents the output need to be displayed to the user.
   */
  void outputForFeatures(String output);

  /**
   * the method used to perform the action in the output panel.
   *
   * @param feature passing the object of featuresGUI Interface.
   */
  void addFeaturesOutputFrame(FeaturesGUI feature);

  /**
   * this method displays the fields which are required to get the value of portfolio.
   */
  void getValueOFPortfolio();

  /**
   * the method used to perform the action in the get portfolio panel.
   *
   * @param feature passing the object of featuresGUI Interface.
   */
  void addFeaturesToGetPortfolioValue(FeaturesGUI feature);

  /**
   * this method displays the fields which are required to sell the stocks from the portfolio.
   */
  void sellStocksFromPortfolio();

  /**
   * the method used to perform the action in the sell stock panel.
   *
   * @param feature passing the object of featuresGUI Interface.
   */
  void addFeaturesSellStocksFromPortfolio(FeaturesGUI feature);

  /**
   * this method is used to clear the textfields in the create portfolio panel.
   */
  void clearTextInTheCreatePortfolio();

  /**
   * this method display all the fields which are required to create a weighted portfolio.
   */
  void createWeightedPortfolioFeatureImpl();

  /**
   * this method displays all the fields which are required to invest in a weighted portfolio.
   */
  void investWeightedPortfolioFeatureImpl();

  /**
   * this method clear all the text-fields in the dollar cost average panel.
   */
  void clearTextInTheDCA();

  /**
   * The method will display fields which are required to rebalance a portfolio.
   */
  void rebalanceFeatureImpl();

  /**
   * the method is used to add the rebalance features in the view.
   *
   * @param feature the feature object which will act as event listener.
   */
  void addFeaturesRebalance(FeaturesGUI feature);

  /**
   * The method clears the text field in the rebalancing of portfolio panel.
   */
  void clearTextInReBalance();

}
