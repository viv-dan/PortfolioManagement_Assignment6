package brokerage.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

/**
 * This class implements the GUI view of this program.
 * It implements methods that takes inputs from the user and displays the output.
 */
public class PortfolioGUIVew extends JFrame implements PortfolioBrokerageGUIView {

  private JButton createFlexiblePortfolio;
  private JButton addStockToPortfolio;
  private JButton sellStocksFromPortfolio;
  private JButton getCostBasisOfPortfolio;
  private JButton getValueOfPortfolio;
  private JButton compositionPortfolio;
  private JButton createWeightedPortfolio;
  private JButton investIntoWeightedPortfolio;
  private JButton setUpDollarCostAverage;
  private JButton setUpRebalancePortfolio;
  private JButton exit;
  private JButton backToMenu;
  private JButton getComposition;
  private JButton getCostBasisOutput;
  private JButton sellStocksPortfolio;
  private JButton addStock;
  private JButton saveAndProceed;
  private JButton addStockForWeightedPF;
  private JButton saveAndProceedForWeightedPF;
  private JButton investWeightedPF;
  private JButton addStockForDCA;
  private JButton rebalancePortfolio;
  private JButton saveAndProceedForDCA;

  private JTextField pn;
  private JTextField pn1;
  private JTextField date;
  private JTextField getPortfolioName;
  private JTextField getTicker;
  private JTextField getQuantity;
  private JTextField getWeight;
  private JTextField getPurchaseDate;
  private JTextField getPurchaseCommission;
  private JTextField getAmount;

  private JTextField getStartDate;
  private JTextField getEndDate;
  private JTextField getFrequency;

  private JTextField ticker;
  private JTextField quantity;
  private JTextField getDate;

  /**
   * this the constructor of the class. It constructs the GUI view object with menu options that a
   * user can choose from.
   *
   * @param str takes string as input parameter.
   */
  public PortfolioGUIVew(String str) {
    super(str);
    JPanel panel = new JPanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel.setLayout(new FlowLayout());
    setSize(800, 600);
    setLocation(50, 50);
    panel.setLayout(new GridLayout(10, 0));

    // creating a new portfolio
    createFlexiblePortfolio = new JButton("Create a flexible portfolio and add/buy stocks");
    createFlexiblePortfolio.setPreferredSize(new Dimension(800, 100));
    createFlexiblePortfolio.setActionCommand("CreateFlexiblePortfolioButton");
    panel.add(createFlexiblePortfolio);

    // selling shares
    sellStocksFromPortfolio = new JButton("Sell shares of a stock from a portfolio");
    sellStocksFromPortfolio.setActionCommand("sellStocksFromPortfolioButton");
    panel.add(sellStocksFromPortfolio);

    //getting cost basis
    getCostBasisOfPortfolio = new JButton("Get cost basis of a portfolio");
    getCostBasisOfPortfolio.setActionCommand("getCostBasisOfPortfolioButton");
    panel.add(getCostBasisOfPortfolio);

    // getting value
    getValueOfPortfolio = new JButton("Get value of a portfolio");
    getValueOfPortfolio.setActionCommand("getValueOfPortfolioButton");
    panel.add(getValueOfPortfolio);

    //The ability to save and retrieve flexible portfolios from files
    compositionPortfolio = new JButton("Get composition of a portfolio");
    compositionPortfolio.setActionCommand("compositionPortfolioButton");
    panel.add(compositionPortfolio);

    //create a weighted portfolio
    createWeightedPortfolio = new JButton("Create weighted portfolio");
    createWeightedPortfolio.setActionCommand("createWeightedPortfolioButton");
    panel.add(createWeightedPortfolio);

    //invest into a weighted portfolio
    investIntoWeightedPortfolio = new JButton("Invest into a weighted portfolio");
    investIntoWeightedPortfolio.setActionCommand("investIntoWeightedPortfolioButton");
    panel.add(investIntoWeightedPortfolio);

    // set up a dollar cost average
    setUpDollarCostAverage = new JButton("Create a Dollar Cost Average portfolio");
    setUpDollarCostAverage.setActionCommand("setUpDollarCostAverageButton");
    panel.add(setUpDollarCostAverage);

    // rebalance a portfolio
    setUpRebalancePortfolio = new JButton("Re-balance an existing portfolio");
    setUpRebalancePortfolio.setActionCommand("setUpRebalancePortfolioButton");
    panel.add(setUpRebalancePortfolio);

    //exit
    exit = new JButton("exitFromProgram");
    exit.setActionCommand("exit Button");
    panel.add(exit);

    this.getContentPane().add(panel);
    //pack();
    setVisible(true);
  }

  // action listener for main panel
  @Override
  public void addFeaturesPortfolioBrokerage(FeaturesGUI feature) {
    createFlexiblePortfolio.addActionListener(e -> feature.creatFlex());
    getCostBasisOfPortfolio.addActionListener(e -> feature.getCostBasis());
    compositionPortfolio.addActionListener(e -> feature.compositionPortfolio());
    getValueOfPortfolio.addActionListener(e -> feature.valueOfPortfolio());
    sellStocksFromPortfolio.addActionListener(e -> feature.sellStockFromPortfolio());
    createWeightedPortfolio.addActionListener(e -> feature.createWeightedPortfolioFeature());
    investIntoWeightedPortfolio.addActionListener(e -> feature.investWeightedPortfolioFeature());
    setUpDollarCostAverage.addActionListener(e -> feature.createDCAFeature());
    setUpRebalancePortfolio.addActionListener(e -> feature.createRebalanceFeature());
    exit.addActionListener(e -> feature.exitFromProgram());

  }

  @Override
  public void getCostBasisOfPortfolio() {
    this.getContentPane().removeAll();
    JPanel panel = new JPanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel.setLayout(new GridLayout(5, 0));

    // get portfolioname
    JLabel lbName = new JLabel("Enter the portfolio name: ");
    panel.add(lbName);
    pn1 = new JTextField(10);
    panel.add(pn1);
    //get date label and text
    JLabel lbDate = new JLabel("Enter the Date: ");
    panel.add(lbDate);
    date = new JTextField(10);
    panel.add(date);
    //get cost basis button
    getCostBasisOutput = new JButton("Get Cost Basis");
    getCostBasisOutput.setActionCommand("getCostBasisButton");
    panel.add(getCostBasisOutput);
    backToMenu = new JButton("Main Menu");
    backToMenu.setActionCommand("Main Menu");
    panel.add(backToMenu);
    exit = new JButton("exitFromProgram");
    exit.setActionCommand("exit Button");
    panel.add(exit);
    this.getContentPane().add(panel);
    pack();
    setResizable(true);
    setVisible(true);
  }

  @Override
  public void addFeaturesOfGetCostBasis(FeaturesGUI feature) {
    exit.addActionListener(e -> feature.exitFromProgram());
    backToMenu.addActionListener(e -> feature.backToMenu());
    getCostBasisOutput.addActionListener(
        e -> feature.getCostBasisOutput(pn1.getText(), date.getText()));

  }

  @Override
  public void createFlexiblePortfolioView() {
    this.getContentPane().removeAll();
    JPanel panel = new JPanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel.setLayout(new GridLayout(7, 0));
    JLabel portfolioName = new JLabel("Enter PortfolioName: ");
    panel.add(portfolioName);
    getPortfolioName = new JTextField(10);
    panel.add(getPortfolioName);
    JLabel ticker = new JLabel("Enter Ticker: ");
    panel.add(ticker);
    getTicker = new JTextField(10);
    panel.add(getTicker);
    JLabel quantity = new JLabel("Enter Quantity: ");
    panel.add(quantity);
    getQuantity = new JTextField(10);
    panel.add(getQuantity);
    JLabel purchaseDate = new JLabel("Enter Purchase Date: ");
    panel.add(purchaseDate);
    getPurchaseDate = new JTextField(10);
    panel.add(getPurchaseDate);
    JLabel purchaseComission = new JLabel("Enter Purchase comission: ");
    panel.add(purchaseComission);
    getPurchaseCommission = new JTextField(10);
    panel.add(getPurchaseCommission);
    addStock = new JButton("Add another Stock");
    addStock.setActionCommand("add stock button");
    panel.add(addStock);
    saveAndProceed = new JButton("Save and Proceed");
    saveAndProceed.setActionCommand("save and proceedbutton");
    panel.add(saveAndProceed);
    exit = new JButton("exitFromProgram");
    exit.setActionCommand("exit Button");
    backToMenu = new JButton("Main Menu");
    backToMenu.setActionCommand("Main Menu");
    panel.add(backToMenu);
    panel.add(exit);
    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }

  @Override
  public void addFeaturesCreateFlexPortfolio(FeaturesGUI feature) {
    backToMenu.addActionListener(e -> feature.backToMenu());
    addStock.addActionListener(e -> feature.addStocksToPortfolio(getPortfolioName.getText(),
        getTicker.getText(), getQuantity.getText(), getPurchaseDate.getText(),
        getPurchaseCommission.getText()));
    saveAndProceed.addActionListener(e -> feature.addStocksAndProceed(getPortfolioName.getText(),
        getTicker.getText(), getQuantity.getText(), getPurchaseDate.getText(),
        getPurchaseCommission.getText()));
  }

  @Override
  public void outputForFeatures(String output) {
    this.getContentPane().removeAll();
    JPanel panel = new JPanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel.setLayout(new FlowLayout());
    JTextArea lb = new JTextArea(output);
    panel.add(lb);

    backToMenu = new JButton("Main Menu");
    backToMenu.setActionCommand("Main Menu");
    panel.add(backToMenu);

    exit = new JButton("exitFromProgram");
    exit.setActionCommand("exit Button");
    panel.add(exit);
    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }

  @Override
  public void addFeaturesOutputFrame(FeaturesGUI feature) {
    backToMenu.addActionListener(e -> feature.backToMenu());
    exit.addActionListener(e -> feature.exitFromProgram());
  }

  @Override
  public void getValueOFPortfolio() {
    this.getContentPane().removeAll();
    JPanel panel = new JPanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel.setLayout(new GridLayout(5, 0));
    // get portfolioname
    JLabel lbName = new JLabel("Enter the portfolio name: ");
    panel.add(lbName);
    pn1 = new JTextField(10);
    panel.add(pn1);
    //get date label and text
    JLabel lbDate = new JLabel("Enter the Date: ");
    panel.add(lbDate);
    date = new JTextField(10);
    panel.add(date);
    //get cost basis button
    getValueOfPortfolio = new JButton("Get value of Portfolio");
    getValueOfPortfolio.setActionCommand("getvalueofportfoliobutton");
    panel.add(getValueOfPortfolio);
    backToMenu = new JButton("Main Menu");
    backToMenu.setActionCommand("Main Menu");
    panel.add(backToMenu);
    exit = new JButton("exitFromProgram");
    exit.setActionCommand("exit Button");
    panel.add(exit);
    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }

  @Override
  public void addFeaturesToGetPortfolioValue(FeaturesGUI feature) {
    getValueOfPortfolio.addActionListener(
        e -> feature.getValueOfPortfolioFromModel(pn1.getText(), date.getText()));
    exit.addActionListener(e -> feature.exitFromProgram());
    backToMenu.addActionListener(e -> feature.backToMenu());
  }

  @Override
  public void sellStocksFromPortfolio() {
    this.getContentPane().removeAll();
    JPanel panel = new JPanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel.setLayout(new GridLayout(7, 0));
    // get portfolioname
    JLabel lbName = new JLabel("Enter the portfolio name: ");
    panel.add(lbName);
    pn1 = new JTextField(10);
    panel.add(pn1);
    //get date label and text
    JLabel lbDate = new JLabel("Enter the sell Date: ");
    panel.add(lbDate);
    date = new JTextField(10);
    panel.add(date);
    JLabel tickerlb = new JLabel("Enter the ticker of the stock: ");
    panel.add(tickerlb);
    ticker = new JTextField(10);
    panel.add(ticker);
    JLabel quantitylb = new JLabel("Enter the quantity of the stock: ");
    panel.add(quantitylb);
    quantity = new JTextField(10);
    panel.add(quantity);
    //panel.add("\n");
    sellStocksPortfolio = new JButton("Sell the stocks from portfolio");
    sellStocksPortfolio.setActionCommand("sellStocksPortfoliobutton");
    panel.add(sellStocksPortfolio);
    backToMenu = new JButton("Main Menu");
    backToMenu.setActionCommand("Main Menu");
    panel.add(backToMenu);
    exit = new JButton("exitFromProgram");
    exit.setActionCommand("exit Button");
    panel.add(exit);
    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }

  @Override
  public void addFeaturesSellStocksFromPortfolio(FeaturesGUI feature) {
    sellStocksPortfolio.addActionListener(
        e -> feature.sellingStocks(pn1.getText(), date.getText(), ticker.getText(),
            quantity.getText()));
    exit.addActionListener(e -> feature.exitFromProgram());
    backToMenu.addActionListener(e -> feature.backToMenu());
  }

  @Override
  public void clearTextInTheCreatePortfolio() {
    getPortfolioName.setEditable(false);
    setBackgroundColorToGray(getPortfolioName);
    getTicker.setText("");
    getQuantity.setText("");
    getPurchaseDate.setText("");
    getPurchaseCommission.setText("");
  }

  @Override
  public void clearTextInTheDCA() {
    getTicker.setText("");
    getWeight.setText("");
    getStartDate.setEditable(false);
    setBackgroundColorToGray(getStartDate);
    getEndDate.setEditable(false);
    setBackgroundColorToGray(getEndDate);
    getFrequency.setEditable(false);
    setBackgroundColorToGray(getFrequency);
    getPortfolioName.setEditable(false);
    setBackgroundColorToGray(getPortfolioName);
    getAmount.setEditable(false);
    setBackgroundColorToGray(getAmount);
    getPurchaseCommission.setEditable(false);
    setBackgroundColorToGray(getPurchaseCommission);
  }

  @Override
  public void createWeightedPortfolioFeatureImpl() {
    this.getContentPane().removeAll();
    JPanel panel = new JPanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel.setLayout(new GridLayout(7, 0));

    JLabel portfolioName = new JLabel("Enter PortfolioName: ");
    panel.add(portfolioName);
    getPortfolioName = new JTextField(10);
    panel.add(getPortfolioName);

    JLabel ticker = new JLabel("Enter Ticker: ");
    panel.add(ticker);
    getTicker = new JTextField(10);
    panel.add(getTicker);

    JLabel weight = new JLabel("Enter Weight: ");
    panel.add(weight);
    getWeight = new JTextField(10);
    panel.add(getWeight);

    addStockForWeightedPF = new JButton("Add another Stock");
    addStockForWeightedPF.setActionCommand("add stock button");
    panel.add(addStockForWeightedPF);

    saveAndProceedForWeightedPF = new JButton("Save and Proceed");
    saveAndProceedForWeightedPF.setActionCommand("save and proceed button");
    panel.add(saveAndProceedForWeightedPF);

    exit = new JButton("exitFromProgram");
    exit.setActionCommand("exit Button");
    backToMenu = new JButton("Main Menu");
    backToMenu.setActionCommand("Main Menu");
    panel.add(backToMenu);
    panel.add(exit);
    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }

  @Override
  public void investWeightedPortfolioFeatureImpl() {
    this.getContentPane().removeAll();
    JPanel panel = new JPanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel.setLayout(new GridLayout(7, 0));

    JLabel portfolioName = new JLabel("Enter PortfolioName: ");
    panel.add(portfolioName);
    getPortfolioName = new JTextField(10);
    panel.add(getPortfolioName);

    JLabel amount = new JLabel("Enter Amount in USD: ");
    panel.add(amount);
    getAmount = new JTextField(10);
    panel.add(getAmount);

    JLabel purchaseCom = new JLabel("Enter Purchase Commission in USD: ");
    panel.add(purchaseCom);
    getPurchaseCommission = new JTextField(10);
    panel.add(getPurchaseCommission);

    JLabel purchaseDate = new JLabel("Enter Purchase Date in yyyy-MM-DD format: ");
    panel.add(purchaseDate);
    getPurchaseDate = new JTextField(10);
    panel.add(getPurchaseDate);

    investWeightedPF = new JButton("Invest");
    investWeightedPF.setActionCommand("invest button");
    panel.add(investWeightedPF);

    exit = new JButton("exitFromProgram");
    exit.setActionCommand("exit Button");
    backToMenu = new JButton("Main Menu");
    backToMenu.setActionCommand("Main Menu");
    panel.add(backToMenu);
    panel.add(exit);
    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }

  @Override
  public void addFeaturesInvestWeightedPortfolio(FeaturesGUI feature) {
    exit.addActionListener(e -> feature.exitFromProgram());
    backToMenu.addActionListener(e -> feature.backToMenu());
    investWeightedPF.addActionListener(e -> feature.investForWeightedPF(getPortfolioName.getText(),
        getAmount.getText(), getPurchaseCommission.getText(), getPurchaseDate.getText()));
  }

  @Override
  public void createDCAFeatureImpl() {
    this.getContentPane().removeAll();
    JPanel panel = new JPanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel.setLayout(new GridLayout(10, 0));

    JLabel portfolioName = new JLabel("Enter PortfolioName: ");
    panel.add(portfolioName);
    getPortfolioName = new JTextField(10);
    panel.add(getPortfolioName);

    JLabel ticker = new JLabel("Enter ticker symbol: ");
    panel.add(ticker);
    getTicker = new JTextField(10);
    panel.add(getTicker);

    JLabel weight = new JLabel("Enter weight: ");
    panel.add(weight);
    getWeight = new JTextField(10);
    panel.add(getWeight);

    JLabel startDate = new JLabel("Enter start date in yyyy-mm-dd format: ");
    panel.add(startDate);
    getStartDate = new JTextField(10);
    panel.add(getStartDate);

    JLabel endDate = new JLabel("Enter end date in yyyy-mm-dd format: ");
    panel.add(endDate);
    getEndDate = new JTextField(10);
    panel.add(getEndDate);

    JLabel frequency = new JLabel("Enter frequency in days: ");
    panel.add(frequency);
    getFrequency = new JTextField(10);
    panel.add(getFrequency);

    JLabel amount = new JLabel("Enter Amount in USD: ");
    panel.add(amount);
    getAmount = new JTextField(10);
    panel.add(getAmount);

    JLabel purchaseCom = new JLabel("Enter Purchase Commission in USD: ");
    panel.add(purchaseCom);
    getPurchaseCommission = new JTextField(10);
    panel.add(getPurchaseCommission);

    addStockForDCA = new JButton("Add more stock");
    addStockForDCA.setActionCommand("Add more stock button");
    panel.add(addStockForDCA);

    saveAndProceedForDCA = new JButton("Save and Proceed");
    saveAndProceedForDCA.setActionCommand("DCA button");
    panel.add(saveAndProceedForDCA);

    exit = new JButton("exitFromProgram");
    exit.setActionCommand("exit Button");

    backToMenu = new JButton("Main Menu");
    backToMenu.setActionCommand("Main Menu");
    panel.add(backToMenu);
    panel.add(exit);
    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }

  @Override
  public void rebalanceFeatureImpl() {
    this.getContentPane().removeAll();
    JPanel panel = new JPanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel.setLayout(new GridLayout(6, 0));

    // get portfolioname
    JLabel lbName = new JLabel("Portfolio name ");
    panel.add(lbName);
    pn1 = new JTextField(6);
    panel.add(pn1);


    //get date label and text
    JLabel lbDate = new JLabel("Enter the Date: ");
    panel.add(lbDate);
    date = new JTextField(6);
    panel.add(date);
    //weight and ticker button
    JLabel ticker = new JLabel("Enter Ticker: ");
    panel.add(ticker);
    getTicker = new JTextField(6);
    panel.add(getTicker);
    JLabel weight = new JLabel("Enter Weight: ");
    panel.add(weight);
    getWeight = new JTextField(6);
    panel.add(getWeight);
    //Add stocks
    addStockForWeightedPF = new JButton("Add another Stock");
    addStockForWeightedPF.setActionCommand("add stock button");
    panel.add(addStockForWeightedPF);
    //re-balance portfolio
    rebalancePortfolio = new JButton("Rebalance Portfolio");
    rebalancePortfolio.setActionCommand("rebalancePortfolio");
    panel.add(rebalancePortfolio);
    //
    backToMenu = new JButton("Main Menu");
    backToMenu.setActionCommand("Main Menu");
    panel.add(backToMenu);
    exit = new JButton("exitFromProgram");
    exit.setActionCommand("exit Button");
    panel.add(exit);
    this.getContentPane().add(panel);
    pack();
    setResizable(true);
    setVisible(true);
  }

  @Override
  public void addFeaturesRebalance(FeaturesGUI feature) {
    exit.addActionListener(e -> feature.exitFromProgram());
    backToMenu.addActionListener(e -> feature.backToMenu());
    addStockForWeightedPF.addActionListener(e -> feature.addStockToRebalance(pn1.getText(),
            getTicker.getText(),getWeight.getText(),date.getText()));
    rebalancePortfolio.addActionListener(e -> feature.startRebalancing(pn1.getText(), date.getText(),
            getTicker.getText(),getWeight.getText()));
  }

  @Override
  public void clearTextInReBalance() {
    getTicker.setText("");
    getWeight.setText("");
    pn1.setEditable(false);
    setBackgroundColorToGray(pn1);
  }

  @Override
  public void addFeaturesCreateDCA(FeaturesGUI feature) {
    exit.addActionListener(e -> feature.exitFromProgram());
    backToMenu.addActionListener(e -> feature.backToMenu());

    addStockForDCA.addActionListener(e -> feature.addStockToDCA(getPortfolioName.getText(),
        getTicker.getText(), getWeight.getText(), getStartDate.getText(), getEndDate.getText(),
        getFrequency.getText(), getAmount.getText(), getPurchaseCommission.getText()));

    saveAndProceedForDCA.addActionListener(
        e -> feature.addSaveAndProceedForDCA(getPortfolioName.getText(),
            getTicker.getText(), getWeight.getText(), getStartDate.getText(), getEndDate.getText(),
            getFrequency.getText(), getAmount.getText(), getPurchaseCommission.getText()));
  }




  @Override
  public void addFeaturesCreateWeightedPortfolio(FeaturesGUI feature) {
    exit.addActionListener(e -> feature.exitFromProgram());
    backToMenu.addActionListener(e -> feature.backToMenu());
    addStockForWeightedPF.addActionListener(e -> feature.addStockToWeightedPF(
        getPortfolioName.getText(), getTicker.getText(), getWeight.getText()));
    saveAndProceedForWeightedPF.addActionListener(
        e -> feature.addStocksAndProceedForWeightedPF(getPortfolioName.getText(),
            getTicker.getText(), getWeight.getText()));
  }

  @Override
  public void clearTextInTheCreateWeightedPF() {
    getTicker.setText("");
    getWeight.setText("");
    getPortfolioName.setEditable(false);
    setBackgroundColorToGray(getPortfolioName);
  }

  @Override
  public void setWeightedPFAddStockButtonNotClickable() {
    addStockForWeightedPF.setEnabled(false);
  }

  @Override
  public void setDCAAddStockButtonNotClickable() {
    addStockForDCA.setEnabled(false);
  }

  @Override
  public void addFeaturesCompositionPortfolio(FeaturesGUI feature) {
    exit.addActionListener(e -> feature.exitFromProgram());
    backToMenu.addActionListener(e -> feature.backToMenu());
    getComposition.addActionListener(
        e -> feature.compositionPortfolioOfCertain(pn.getText(), getDate.getText()));
  }

  @Override
  public void mainMenu() {
    this.getContentPane().removeAll();
    JPanel panel = new JPanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel.setLayout(new FlowLayout());
    setSize(800, 600);
    setLocation(50, 50);
    panel.setLayout(new GridLayout(10, 0));

    // creating a new portfolio
    createFlexiblePortfolio = new JButton("Create a flexible portfolio and add/buy stocks");
    createFlexiblePortfolio.setPreferredSize(new Dimension(800, 100));
    createFlexiblePortfolio.setActionCommand("CreateFlexiblePortfolioButton");
    panel.add(createFlexiblePortfolio);

    // selling shares
    sellStocksFromPortfolio = new JButton("Sell shares of a stock from a portfolio");
    sellStocksFromPortfolio.setActionCommand("sellStocksFromPortfolioButton");
    panel.add(sellStocksFromPortfolio);

    //getting cost basis
    getCostBasisOfPortfolio = new JButton("Get cost basis of a portfolio");
    getCostBasisOfPortfolio.setActionCommand("getCostBasisOfPortfolioButton");
    panel.add(getCostBasisOfPortfolio);

    // getting value
    getValueOfPortfolio = new JButton("Get value of a portfolio");
    getValueOfPortfolio.setActionCommand("getValueOfPortfolioButton");
    panel.add(getValueOfPortfolio);

    //The ability to save and retrieve flexible portfolios from files
    compositionPortfolio = new JButton("Get composition of a portfolio");
    compositionPortfolio.setActionCommand("compositionPortfolioButton");
    panel.add(compositionPortfolio);

    //create a weighted portfolio
    createWeightedPortfolio = new JButton("Create weighted portfolio");
    createWeightedPortfolio.setActionCommand("createWeightedPortfolioButton");
    panel.add(createWeightedPortfolio);

    //invest into a weighted portfolio
    investIntoWeightedPortfolio = new JButton("Invest into a weighted portfolio");
    investIntoWeightedPortfolio.setActionCommand("investIntoWeightedPortfolioButton");
    panel.add(investIntoWeightedPortfolio);

    // set up a dollar cost average
    setUpDollarCostAverage = new JButton("Create a dollar cost average portfolio");
    setUpDollarCostAverage.setActionCommand("setUpDollarCostAverageButton");
    panel.add(setUpDollarCostAverage);

    // rebalance a portfolio
    setUpRebalancePortfolio = new JButton("Re-balance an existing portfolio");
    setUpRebalancePortfolio.setActionCommand("setUpRebalancePortfolioButton");
    panel.add(setUpRebalancePortfolio);

    //exit
    exit = new JButton("exitFromProgram");
    exit.setActionCommand("exit Button");
    panel.add(exit);

    this.getContentPane().add(panel);
    //pack();
    setVisible(true);
  }

  @Override
  public void compositionOfPortfolio() {
    this.getContentPane().removeAll();
    JPanel panel = new JPanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel.setLayout(new GridLayout(4, 0));
    JLabel portfolioName = new JLabel("Enter the portfolio name: ");
    panel.add(portfolioName);
    pn = new JTextField(10);
    panel.add(pn);

    JLabel dateLabel = new JLabel("Enter the date: ");
    panel.add(dateLabel);
    getDate = new JTextField(10);
    panel.add(getDate);

    backToMenu = new JButton("Main Menu");
    backToMenu.setActionCommand("Main Menu");
    panel.add(backToMenu);
    getComposition = new JButton("Get composition");
    getComposition.setActionCommand("GetCompositionOfPortfolio");
    panel.add(getComposition);
    exit = new JButton("exitFromProgram");
    exit.setActionCommand("exit Button");
    panel.add(exit);
    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }

  @Override
  public void inputChoice() {
    return;
  }

  @Override
  public void inputNoOfStocksToAdd() {
    return;
  }

  @Override
  public void inputPortfolioName() {
    return;
  }

  @Override
  public void inputTicker() {
    return;
  }

  @Override
  public void inputQuantity() {
    return;
  }

  @Override
  public void inputFilePath() {
    return;
  }

  @Override
  public void inputDateToValidate() {
    return;
  }

  @Override
  public void inputPurchaseDate() {
    return;
  }

  @Override
  public void inputSellDate() {
    return;
  }

  @Override
  public void inputPurchaseCom() {
    return;
  }

  @Override
  public void inputWeight() {
    return;
  }

  @Override
  public void inputAmount() {
    return;
  }

  @Override
  public void inputStartDate() {
    return;
  }

  @Override
  public void inputEndDate() {
    return;
  }

  @Override
  public void inputFrequency() {
    return;
  }

  @Override
  public void output(String s) {
    return;
  }

  /**
   * sets the JText field background color to gray.
   *
   * @param field takes JText field as input
   */
  private void setBackgroundColorToGray(JTextField field) {
    field.setBackground(UIManager.getColor("Color.LIGHT_GRAY"));
  }


}
