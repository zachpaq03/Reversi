This program creates a game of Reversi to be played by two players on a hexagonal game board.
On a player's turn they can play a piece on the board or pass their turn. In order to play a piece,
the tile must not be controlled by another piece and must capture an opponent piece
during that play.

Capturing a piece means that in one linear direction from the piece played,
the player has another piece along with at least one opponent piece in between.
When a player takes their turn, any opposing pieces between will be
swapped to the current player's.

The game ends when both players pass their turn or there are no valid moves left.

Map of Source:

Controller package contains:
    - Features interface, defining behavior for an asynchronous controller
    - GamePlayer interface, defining behavior for player actions
    - A ReversiController class and HumanPlayer and MachinePlayer classes, allowing for computer
    players and live human players

Model package contains:
    - Reversi and ReadOnlyReversi interfaces, interfaces which define all behavior for Reversi
        -ReadOnlyReversi does not contain any methods which mutate the model
    - Basic Reversi, the class for a standard game
    - Tile interface, for defining the game tile behavior
    - ReversiTile and Position, both used for establishing the game board
    - Team enum, to define different teams
    - A Status enum, to define states of the game

Strategy package contains:
    - Strategy interface, defining the choosePosition method
    - AvoidNextToCorners, a strategy to make sure pieces are not played next to corners
    - GetCorners, a strategy that prioritizes getting corners
    - MostTiles, looks for the most tiles that can be swapped in a turn
    - PromptUser, asks the user for their move and makes that move

View package contains:
    - View and ReversiView interfaces, for defining graphic views and GUI behavior
    - GUITile, ReversiPanel and ReversiGUIView, to control GUI behavior
    - ReversiTextView, creates a textual view of the game in the console

Main- to run the game



To easily run this program, arguments can be set in the main configuration. There must be 3
arguments, the first an integer, defining the size of board, the second and third defining player
types. They can currently be 'human', or 'mosttiles', our working strategy for a computer player



** STRATEGIES **
We have added a Strategies interface with a method choosePosition() which is given a team and
a model and returns the position based on the strategy. Our MostTiles strategy looks for the
position which will flip the most tiles. If there is no move to make, it throws an
IllegalStateException.

We also created preliminary classes for Avoiding Next To Corners and Getting Corners. However,these
are not completed because they require more interaction with other strategies.


The model controls the gameplay, monitoring whose turn it is and where pieces have been played.
The view simply displays the state of the game, pulling information from the model to
accurately show users the state of the game.

Our model is made up of a Reversi board, or game, along with Tiles, which can hold a player and
each have a unique coordinate position. Coordinate positions are vectors with one direction
following horizontal, or traditional x-axis, and the other along a slanted axis. Coordinates
increase left and down and the origin begins at the center of the game board.

The GUI has full functionality, allowing for a full game to be played by one person with a computer
or two people. A player must click a tile and type m to move. If the move is invalid, a message
will be displayed informing them. If it is not their turn, a message will inform them to wait.
When the game has ended, the GUI will no longer accept players to click tiles or make moves.


Changes for part 2:

- Added a Reversi Mock to test Strategy use on a game

BasicReversi:

method getTileAt() returned a reference to the tile queried. This has been changed to
return a copy of said tile.

Now, a new private method, getTileAtInternal() is used in the model
implementation.

isValidMove() was made public and added to interface so Strategies could access the method.



Team: New Enum "UNCLAIMED" added. Rather than having the unclaimed tiles have null team, there is
now an unclaimed team with its own color, which corresponds ot the boards color. Additionally,
colors corresponding to team name were assigned to each team enum.


Changes for Part 3:

Our controller package has been developed, allowing for model functionality to now be controlled
by the GUI view. Both human and computer players have the capability of playing.

Within our model, we have added a Status enum to define if the game is still being played or someone
has won or tied.

In addition, We have created a startGame(), addFeatureListener(), gameStatus(), notifyListeners()
and updateWinner() methods to fully incorporate controller features and help communicate with the
view and controller.

Our GUI panel now includes an update tiles method to update teams as the game is played.
We have also created addFeatureListeners, update  and sendErrorMessage methods to help fill
out the game functionality and user interactions.