LIST OF CHANGES MADE TO THE DESIGN FROM THE PREVIOUS DESIGN IN ASSIGNMENT4:


1) Implemented the command design pattern: Our program now implements command design pattern for the command based interface. Based on the user choice it calls different command 

object. In order to implement this patter we created an interface called CommandController. This interface defines the signature of command method called goCommand() that takes 

model, view and scan object. This interface is implemented by different commands (classes). The goCommand() method is implemented differently in different classes.  If a 

particular command needs to be executed, then the object of that command is created and with the help of that object the method within that command (class) is called. This 

method defines the action that needs to be done for that particular command. 



JUSTIFICATION FOR THE DESIGN: Creating a command design pattern makes the go method within the controller less cluttered, if in case in future we want to add more commands to 

the controller. This pattern also allows scalability of operations the user can perform in future. If in future a new command needs to be written then we just have to create a 

new implementation for the CommandController interface. In this new implementation the goCommand() method can be implemented differently. 

________________________________________________________________________________________________________________________________________________________________________________

2) Created a new Interface for the model named PortfolioStrategyModel: This new interface has the method signatures in order to implement the new strategies and also extends the 

previous model interface.


JUSTIFICATION FOR THE DESIGN: This interface is created to support the various investment strategies. This interface defines the method signature for implementing investment 

strategies such as creating a weighted portfolio, making a fixed amount of investment into the weighted portfolio. It also has method signature for creating dollar cost 

averaging investment strategy. This new interface will allow future scalability if in case user wants to implement various strategies. This interface can be implemented 

differently to support different strategies in future. 

________________________________________________________________________________________________________________________________________________________________________________

3) Created a new class called PortfolioStrategyModelImpl: This new class implements the new PortfolioStrategyModel interface. It implements the methods which are 

declared in the PortfolioStrategyModel and also  extends the old model PortfolioFlexBrokerageModel implementation class so that existing methods can be reused.


JUSTIFICATION FOR THE DESIGN: According to solid principles the program should be closed for modification but open for extension. The new 

class is used to implement the features of the methods which are declared in the new interface. We wanted to keep the strategy features in a separate interface class instead of 

modifying the existing one, And also it would extends the old model class to reuse the private methods.

________________________________________________________________________________________________________________________________________________________________________________


4) Created a package called GUI: This package is created to keep all the interface and classes which are related to Graphical User Interface separately.

________________________________________________________________________________________________________________________________________________________________________________

5)Created a new feature interface called FeatureGUI: This interface contains the method signatures of the GUI features and also extends the previous controller.


JUSTIFICATION FOR THE DESIGN: This new interface is used to keep all the features of GUI separately, Instead of adding the new features to the existing controller

and also to differentiate the difference between the features of text based and GUI based features.

________________________________________________________________________________________________________________________________________________________________________________

6)Created a new controller called PortfolioGUIController:

JUSTIFICATION FOR THE DESIGN: This class implements the methods that are declared in the FeatureGUI interface, And the main purpose of this class is used to 

validate the basic inputs and communicate to the model and gets back the output from the model and passes it to the view to display it in the GUI, The class is used

to implement the features of the GUI separately and also extends the old controller.

________________________________________________________________________________________________________________________________________________________________________________

7)Created a new feature interface called PortfolioBrokerageGUIView:

JUSTIFICATION FOR THE DESIGN: This interface is used to contain all the methods related to the View and also extends the old view PortfolioView, To keep all 

the methods separately that are related to the GUI view and to differentiate the text based view and GUI View.

________________________________________________________________________________________________________________________________________________________________________________

8)Created a new class called PortfolioGUIView:

JUSTIFICATION FOR THE DESIGN:This class is used to implement the methods that are created in the PortfolioBrokerageGUIView and also it extends the JFrame to implement

use for the GUI Design.























