OVERVIEW

The code in this file is a model for a game of Reversi, with tests to ensure the model works as intended.
There is now a controller and a graphical user interface to support the game. A full game of Reversi
can be player with either humans, AI, or a mix of both. 

QUICK START (Updated 12/4/23, during Part4 of assignment)

The following entered into the terminal would start a game of Reversi:
jave -jar/.../Reversi.jar 11 11 human human default provider

There first two parameters are optional, they set the size of the board by specifying the width and
height of the board (NOTE: the provider view can only be used if the width is equal to the height).
The next parameters are the player types, with human representing human players. For AI players, enter
strategy1 (Our AsManyCapturesAsPossible), strategy2 (Our NoNextToCorners), strategy3 (Our PrioritizeCorners), 
strategy4 (Our MiniMax), providerstrategy1 (Provider's MostTiles), providerstrategy2 (Provider's AvoidNextToCorner), 
or providerstrategy3 (Provider's GetCorners). For strategy4, providerstrategy2, and providerstrategy3, they must be
followed by another strategy that will represent either the predicted opponent strategy for strategy 4, or the 
backup strategy for providerstrategy2 and providerstrategy3. This keeps going on until one of strategy1, strategy2,
strategy3, or providerstrategy1 is entered. Next and finally is the parameters for the view. These are optional, and
if nothing is entered then both views will be our view implementation. If provider is entered and the row length is 
equal to the height, then the corresponding view will be the providers view. The first view parameter is for player1, 
and the second is for player2. 

KEY COMPONENTS AND SUBCOMPONENTS
The main component is the ReversiAbstract class. This is an abstract class that implements the ReversiInterface.
Each method from the interface is defined in this class. Currently there is one subclass, the BasicReversiGame
class which inherits all of the methods from the super class, meaning it does not override any methods. Future
iterations of the game may want this flexibility. Some of the subcomponents of this class would be the various
enum classes that exist. These make the identity of certain objects much easier, and protect against things like
Illegal Argument Exceptions. Another subcomponent is the disc interface and class. Since discs are a more complicated
object with their own fields, it was necessary to make an interface and class so that they are easier to represent.

The second key component is the basic textual view class. Currently this can only represent a textual view, but
when a GUI is implemented, this component will be even more valuable. The main subcomponents would be some specific
methods from the model. Those methods would be the various getters so that the view can know the dimensions of the
board, and where on the board to display certain things like discs and hexagons.

SOURCE ORGANIZATION
The cs3500.reversi package contains multiple packages and classes of its own. It contains the model package,
which holds classes tied to the model of the Reversi game. These classes include enum classes, interfaces,
the abstract class that represents the model, and subclasses of that abstract class. The Next package is
the player package which contains the player interface, which at this point is mostly just a place holder.
The final package is the view package, which contains both the interface and a class that implements the
interface to a textual view. Finally, the only things that exist in the cs3500.reversi package that are not
in a sub package are the main class, which is currently empty, and this README file.

In the test package, there is a cs3500.reversi package, where once again there lives multiple packages
and classes. First there is a model package, which tests package-private methods from the model. Then
there is a view package which includes a class for tests for the textual view. And finally there are
abstract test classes and subclasses that test the public signatures of the model.

CHANGES FOR PART 2

UPDATES AND CHANGES TO MODEL

* Made a read-only model interface and had the mutable model extend that interface.
* Moved all observer methods to the read-only interface.
* Added a few new observer methods; get a read only copy of the board, see if a given move is legal
and see if there are any legal moves.
* Also added a new method to the mutable model interface, a get mutable copy method. This is necessary
to try out moves to see what is the best for the strategy objects.
* Added a second constructor for a model, one that takes in a grid, the players, who's turn, and the pass count.
This constructor is for getting copies of a game that is in progress.
* In order to make the second constructor work, a number of protected getter methods were added. A method
for getting the boolean grid, the player grid, the array of players, and the pass count were all added (A method
to figure out who's turn it is already exists).
* Made a change to the start game method and the constructor for a Reversi game. Previously, you had to 
specify the players in a player array as an argument to start game. But since it is always just player 1 
and player 2, the constructor now sets the players field to Player1 and Player2 and there is one less
argument in the start game method. 

KEYBOARD INPUT

* When a user clicks a hexagon, they are prompted to give a keyboard input to do a certain action. M was chosen
for place disc and p was chosen for pass. M because move starts with m, and p because pass starts with p.

NEW CLASSES

Two new interfaces were added to the view package. These are GUIView and Panel. GUI implements GUIView and holds
the content of a panel. Reversi panel implements panel and draws the components of the game. Some classes that are
subcomponents to the panel class are the hexagon image class, which draws hexagons for the board, and a mouse
adapter class which helps handle mouse input. There is a new package, strategy, that holds strategies for the game
of Reversi. There is an interface for strategies that has an abstract class and many subclasses implement it.
One of those strategies is AsManyCapturesAsPossible, which returns the coordinates for the move that
will give the given player the most captures. Throws an illegal state exception if there is no move, rather than 
returning null. The details of the other classes can be found in the extra credit section. There is also a new 
class in the cs3500.reversi package called Reversi that is a temporary way to actually view the GUI. 

CHANGES BASED ON FEEDBACK FROM PART 1

* Made a coordinate class to help with encapsulation.
* Made all Enum class members be all caps
* Made textual view ensure that the model it takes in is not null, and if no out is given than set out
to System.out.
* Changed return values for hash code where it was overridden. 

EXTRA CREDIT

The extra credit strategies are located in the strategy package in the cs3500.reversi package. The classes
that represent the extra credit strategies are NoNextToCorners, PrioritizeCorners, and MiniMax, and they all
share code with the StrategyAbstract class. 

CHANGES FOR PART 3

* Added classes that implement the PlayerInterface interface, AIPlayer and HumanPlayer
* Rather than having a place disc method and a pass method, there is now just a make move method
that determines the players move. 
* New observers field added to the model, so that controllers can be notified whenever the turn
changes.
* New methods added in the view that will call back to the controller to handle key inputs. 

PART 4

* We were able to get their view to work with our model and controller, as well as all of the strategies.
However, it only works for symmetrical boards (each side of the board is the same amount of hexagons) because
in our providers model they only support games of this format, and their view reflects that fact. However, 
our model is able to support boards of any size, but many of those boards will not work with their board. You
can play an entire game or verse an AI using either our own view or our providers view. 

* At the time of this submission, we have not had to make any changes to our code for our customers.