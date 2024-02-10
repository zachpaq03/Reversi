package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.ModelFeatures;

/**
 * A class for a basic game of Reversi. The basic game has a few specifications on the structure
 * of the board. The longest row is the middle row, which can be no longer than 20 hexagons.
 * Every row above the middle row decreases by 1, and the same structure takes place for below
 * the middle row. The board must be symmetric, meaning there is an equal amount of rows above
 * the middle row and below it. This means that the height of the board must be an odd number.
 * Also, a row must have at least 1 hexagon, so the height cannot be greater than twice the
 * longest row minus one.
 */

// This class was made abstract so that future variations of the game will be easier to implement,
// thought for this part of the assignment it serves no real benefit.
public abstract class ReversiAbstract implements ReversiInterface {
  // Some useful global variables: the maximum length of a row for a basic game, which is 20.
  // Also, offsets that correspond with one another based on the index of the array. For example, if
  // you apply the 0th elements for evenRowOffset and evenColOffset on an even row, then apply
  // the oddRowOffset and oddColOffset to the result of the previous offset, you will get a straight
  // line. This can be applied over and over again, so long as the line stays within the boundaries
  // of the game. Very useful for the logic behind finding legal moves.
  public static final int MAX_ROW_SIZE = 20;
  public static final int[] EVEN_ROW_OFFSET = {-1, -1, 0, 1, 1, 0};
  public static final int[] EVEN_COL_OFFSET = {0, 1, 1, 1, 0, -1};
  public static final int[] ODD_ROW_OFFSET = {-1, -1, 0, 1, 1, 0};
  public static final int[] ODD_COL_OFFSET = {-1, 0, 1, 0, -1, -1};
  // A field for modeling where in the 2D Array there are hexagons. True implies there
  // is a hexagon at this location, false implies there is not.
  private boolean[][] grid;
  // A field for modeling where player discs are on the map. The 2D array will either return
  // the player whose disc is at this position, or null if there is no disc here or it isn't a
  // hexagon.
  private Player[][] playerGrid;
  // INVARIANT: The height of the grid is always non-negative.
  // The height of the grid, specified by the players.
  private int gridHeight;
  // The length of the grid, specified by the players.
  private int gridLength;
  // An array of the players involved in the game.
  private Player[] players;
  // A field to determine whose turn it is in the game.
  private Player playerWhoIsUp;
  // The state of the game. Useful for knowing if a game can be started, or if methods
  // that imply that game has already been started can be called.
  private GameState gameState;
  // How many times has a moved been passed, consecutively. Useful for determining if the
  // game is over or not.
  private int passCount;
  private List<ModelFeatures> observers;

  /**
   * A constructor for a basic game of Reversi that sets all of the fields to their defualt values.
   */
  public ReversiAbstract() {
    // Setting all the fields to default values.
    this.grid = new boolean[0][0];
    this.playerGrid = new Player[0][0];
    // INVARIANT: Default value of gridHeight is 0, which is non negative.
    this.gridHeight = 0;
    this.gridLength = 0;
    Player[] players = new Player[2];
    players[0] = Player.Player1;
    players[1] = Player.Player2;
    this.players = players;
    this.playerWhoIsUp = Player.Player1;
    this.gameState = GameState.NOTSTARTED;
    this.passCount = 0;
    this.observers = new ArrayList<>();
  }

  /**
   * A second constructor for making games that are already in progress. This is mostly used for
   * making copies of a game.
   *
   * @param grid The boolean grid representing if there are hexagons or not.
   * @param playerGrid A player grid representing if there is a disc for either player there.
   * @param players The array of players.
   * @param playerWhoIsUp The player whose turn it is.
   * @param passCount The pass count.
   */
  public ReversiAbstract(boolean[][] grid, Player[][] playerGrid, Player[] players,
                         Player playerWhoIsUp, int passCount) {
    this.grid = grid;
    this.playerGrid = playerGrid;
    this.gridHeight = grid.length;
    this.gridLength = grid[0].length;
    this.players = players;
    this.playerWhoIsUp = playerWhoIsUp;
    this.gameState = GameState.INPROGRESS;
    this.passCount = passCount;
    this.observers = new ArrayList<>();
  }

  @Override
  public void startGame(int rowLength, int height) {
    checkForExceptionsStartGame(rowLength, height, players);
    this.gameState = GameState.INPROGRESS;
    this.grid = new boolean[height][rowLength];
    this.playerGrid = new Player[height][rowLength];
    // INVARIANT: The checkForExpceptionsStartGame helper ensures the height is at least
    // 3, which will always be non-negative.
    this.gridHeight = height;
    this.gridLength = rowLength;
    this.playerWhoIsUp = players[0];
    setGrid(height, rowLength);
  }

  // A helper method to check for any exceptions when starting a game. Requires things like
  // the game hasn't already been started, row length is at least 2 and also less than or equal to
  // the max, height is at least 3, height is valid based on the row length, height is odd, and the
  // players array is valid.
  void checkForExceptionsStartGame(int rowLength, int height, Player[] players) {
    if (this.gameState != GameState.NOTSTARTED) {
      throw new IllegalStateException("Game has already been started.");
    }
    // Limiting how large a board can be. Row length of 20 is more than enough.
    if (rowLength > MAX_ROW_SIZE) {
      throw new IllegalArgumentException(String.format("The width of the board cannot exceed %s.",
              MAX_ROW_SIZE));
    }
    // The player array is limited to two players.
    if (players == null || players.length != 2) {
      throw new IllegalArgumentException("There must be exactly two players in order to play.");
    }
  }

  // A package private method that sets up the board for the beginning of the game.
  void setGrid(int height, int rowLength) {
    for (int i = 0; i < height; i++) {
      // Figures out how many non hexagon spots are taken up in the array. Does this by finding
      // differences in the row index, since each row decrease by 1 as it gets farther away from
      // the middle row.
      int amountOfNonHex = Math.abs(((height - 1) / 2) - i);
      int start = 0;
      int end = rowLength;
      boolean addToStart = true;
      // Finds out if the row will have an odd or even amount of hexagons.
      if (getHexAmount(height, rowLength, i) == OddOrEven.EVEN) {
        addToStart = false;
      }
      // In this model, rows with an odd amount of hexagons are offset to the left, and ones with
      // an even amount are offset to the right. Thus, odd rows start by adding blank spaces to the
      // left (increase the starting point) and then add blank spaces to the right (decrease the
      // ending point), and even rows do the same process in the opposite order.
      while (amountOfNonHex > 0) {
        if (addToStart) {
          start++;
        } else {
          end--;
        }
        amountOfNonHex--;
        addToStart = !addToStart;
      }
      // Loops through and makes the array return true for where hexagons are on the grid.
      for (int j = start; j < end; j++) {
        this.grid[i][j] = true;
      }
    }
    // Calls helper method to set the discs at the beginning of the game.
    setInitialDiscs(height, rowLength);
  }

  // A helper method that sets up where the starter discs are placed at the very beginning of the
  // game. Setup is slightly different based on if the middle row has an odd or even amount of
  // hexagons. If the middle row has an even amount of hexagons, then 4 discs are placed in the
  // middle of the board in a diamond shape with no blank hexagon in the middle. If the middle row
  // has an odd amount of hexagons there are 6 discs placed in the middle in a hexagon shape, with
  // one empty hexagon in the middle. The different setup is done to preserve the symmetry of the
  // starting board.
  void setInitialDiscs(int height, int rowLength) {
    List<Integer> points = new ArrayList<>();
    int middleRow = (height - 1) / 2;
    if (rowLength % 2 == 0) {
      points.add(rowLength / 2);
      points.add((rowLength / 2) - 1);
      this.playerGrid[middleRow][points.get(0)] = Player.Player1;
      this.playerGrid[middleRow][points.get(1)] = Player.Player1;
      this.playerGrid[middleRow + 1][points.get(0)] = Player.Player2;
      this.playerGrid[middleRow - 1][points.get(0)] = Player.Player2;
    } else {
      points.add(((rowLength - 1) / 2) - 1);
      points.add(((rowLength - 1) / 2) + 1);
      points.add((rowLength - 1) / 2);
      this.playerGrid[middleRow][points.get(0)] = Player.Player1;
      this.playerGrid[middleRow][points.get(1)] = Player.Player2;
      this.playerGrid[middleRow - 1][points.get(0)] = Player.Player2;
      this.playerGrid[middleRow + 1][points.get(0)] = Player.Player2;
      this.playerGrid[middleRow - 1][points.get(2)] = Player.Player1;
      this.playerGrid[middleRow + 1][points.get(2)] = Player.Player1;
    }
  }

  // A helper method that returns if the amount of hexagons in the row is even or odd.
  OddOrEven getHexAmount(int height, int length, int i) {
    int number = length + Math.abs(((height - 1) / 2) - i);
    if (number % 2 == 0) {
      return OddOrEven.EVEN;
    } else {
      return OddOrEven.ODD;
    }
  }

  @Override
  public void placeDisc(int x, int y) {
    // Check that there is at least one legal move left.
    if (!anyLegalMoves()) {
      throw new IllegalStateException("There are no legal moves available.");
    }
    // Calls helper to check for exceptions
    checkForExceptionsDisc(x, y);
    if (!legalMove(x, y)) {
      throw new IllegalStateException("This is not a legal move.");
    }
    // Calls helper to update the board.
    updateBoard(x, y);
    // Calls helper to update whose turn it is on the model.
    changeTurn();
    // Reset pass count to zero since the player didn't pass and the pass count tracks
    // consecutive passes.
    this.passCount = 0;
  }

  @Override
  public void pass() {
    gameNotStarted();
    // Increase the pass count.
    this.passCount++;
    // Calls helper to update whose turn it is on the model.
    changeTurn();
  }

  // A helper method to update the model on whose turn it is.
  void changeTurn() {
    Player oldTurn = this.playerWhoIsUp;
    if (oldTurn == Player.Player1) {
      this.playerWhoIsUp = Player.Player2;
    } else {
      this.playerWhoIsUp = Player.Player1;
    }
    if (!this.observers.isEmpty()) {
      alertObserver();
    }
  }

  // A helper to check for the exceptions when calling disc related methods like move disc. Ensures
  // that the given coordinates are not negative or greater than the border of the board, and makes
  // sure the given coordinate is on a hexagon.
  void checkForExceptionsDisc(int x, int y) {
    gameNotStarted();
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("One or more of the given coordinates is negative.");
    }
    if (y > this.gridHeight) {
      throw new IllegalArgumentException("The given y-coordinate is larger than the height of the" +
              " board");
    }
    if (x > this.gridLength) {
      throw new IllegalArgumentException("The given x-coordinate is larger than the length of the" +
              " board");
    }
    if (!isHexHere(x, y)) {
      throw new IllegalArgumentException("There is no hexagon here.");
    }
  }

  @Override
  public boolean legalMove(int x, int y) {
    gameNotStarted();
    if (!isHexHere(x, y)) {
      return false;
    }
    // Iterates through all the different offsets to see if there is a legal move in any direction.
    // Calls a helper that returns a boolean if there is a path in the given direction.
    for (int i = 0; i < EVEN_ROW_OFFSET.length; i++) {
      List<Integer> path = new ArrayList<>();
      if (traverseBoard(x, y, EVEN_ROW_OFFSET[i], EVEN_COL_OFFSET[i], ODD_ROW_OFFSET[i],
              ODD_COL_OFFSET[i], path, false)) {
        return true;
      }
    }
    return false;
  }

  // Called when a move is considered valid, this helper updates the board after a move.
  void updateBoard(int x, int y) {
    boolean sameTurn = false;
    // Iterates through each offset to find the paths created by the moves, does not stop after
    // finding one because there could be multiple baths and all need to be updated.
    for (int i = 0; i < EVEN_ROW_OFFSET.length; i++) {
      List<Integer> path = new ArrayList<>();
      if (traverseBoard(x, y, EVEN_ROW_OFFSET[i], EVEN_COL_OFFSET[i], ODD_ROW_OFFSET[i],
              ODD_COL_OFFSET[i], path, sameTurn)) {
        sameTurn = true;
        for (int j = 0; j < path.size(); j += 2) {
          // Updating the board.
          this.playerGrid[path.get(j)][path.get(j + 1)] = this.playerWhoIsUp;
        }
      }
    }
  }

  // A helper method to look for paths that exist if a disc is placed on a certain spot.
  boolean traverseBoard(int x, int y, int evenRowOffset, int evenColOffset, int oddRowOffset,
                        int oddColOffset, List<Integer> path, boolean sameTurn) {
    int row = y;
    int col = x;

    // isHexHere and isDiscHere methods can throw Illegal Argument Exceptions and Illegal State
    // Exceptions, so the if statement is surrounded by a try-catch statement.
    try {
      if (isHexHere(col, row) && (!isDiscHere(col, row) || sameTurn)) {
        // Add the hexagon where the disc is being place to the path.
        path.add(row);
        path.add(col);
        // Update the row and col with the offsets.
        col = updateCol(row, col, evenColOffset, oddColOffset);
        row = updateRow(row, evenRowOffset, oddRowOffset);
        // Add the new hexagon after the offset
        path.add(row);
        path.add(col);
        // While continuing down this path, if a hexagon is the same color then it is a valid path,
        // so the method returns true.
        while (isDiscHere(col, row) && notSameColor(row, col)) {
          col = updateCol(row, col, evenColOffset, oddColOffset);
          row = updateRow(row, evenRowOffset, oddRowOffset);
          path.add(row);
          path.add(col);

          if (isDiscHere(col, row) && getDiscColor(col, row) == this.playerWhoIsUp.getColor()) {
            return true;
          }
        }
      }
    } catch (IllegalArgumentException | IllegalStateException e) {
      return false;
    }
    return false;
  }

  // A helper method to update the row based on the offset.
  int updateRow(int row, int evenRowOffset, int oddRowOffset) {
    int result = row;
    if (getRowLength(row) == OddOrEven.EVEN) {
      result += evenRowOffset;
    } else {
      result += oddRowOffset;
    }
    return result;
  }

  // A helper method to update the column based on the offset.
  int updateCol(int row, int col, int evenColOffset, int oddColOffset) {
    int result = col;
    if (getRowLength(row) == OddOrEven.EVEN) {
      result += evenColOffset;
    } else {
      result += oddColOffset;
    }
    return result;
  }

  // A helper method to check if two discs are the same color.
  boolean notSameColor(int y, int x) {
    return this.playerGrid[y][x].getColor() != this.playerWhoIsUp.getColor();
  }

  // A helper method to find if a row that is already initialized is odd or even.
  OddOrEven getRowLength(int y) {
    if (y > this.gridHeight) {
      throw new IllegalArgumentException(String.format("There is no row %s on this grid", y));
    }
    int count = 0;
    for (int i = 0; i < this.gridLength; i++) {
      if (isHexHere(i, y)) {
        count++;
      }
    }
    if (count % 2 == 0) {
      return OddOrEven.EVEN;
    } else {
      return OddOrEven.ODD;
    }
  }

  @Override
  public List<Integer> getScore() {
    gameNotStarted();
    int player1Score = 0;
    int player2Score = 0;
    for (Player[] array : this.playerGrid) {
      for (Player player : array) {
        if (player == Player.Player1) {
          player1Score++;
        } else if (player == Player.Player2) {
          player2Score++;
        }
      }
    }
    List<Integer> result = new ArrayList<>();
    result.add(player1Score);
    result.add(player2Score);
    result.add(Math.abs(player1Score - player2Score));
    return result;
  }

  @Override
  public Player getTurn() {
    gameNotStarted();
    return this.playerWhoIsUp;
  }

  @Override
  public boolean isDiscHere(int x, int y) {
    checkForExceptionsDisc(x, y);
    return this.playerGrid[y][x] != null;
  }

  @Override
  public Color getDiscColor(int x, int y) {
    checkForExceptionsDisc(x, y);
    if (isDiscHere(x, y)) {
      return this.playerGrid[y][x].getColor();
    }
    throw new IllegalStateException("There is no disc here.");
  }

  @Override
  public boolean isGameOver() {
    gameNotStarted();
    if (this.passCount == 2) {
      // Figure out who won and update the game status field.
      List<Integer> finalScores = getScore();
      if (finalScores.get(0) > finalScores.get(1)) {
        this.gameState = GameState.PLAYER1WINS;
      } else if (finalScores.get(1) > finalScores.get(0)) {
        this.gameState = GameState.PLAYER2WINS;
      } else {
        this.gameState = GameState.DRAW;
      }
      return true;
    }
    return false;
  }

  @Override
  public boolean isHexHere(int x, int y) {
    gameNotStarted();
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("One or more of the given coordinates is negative.");
    }
    if (y >= this.gridHeight) {
      throw new IllegalArgumentException("The given y-coordinate is larger than the height of the" +
              " board");
    }
    if (x >= this.gridLength) {
      throw new IllegalArgumentException("The given x-coordinate is larger than the length of the" +
              " board");
    }
    return this.grid[y][x];
  }

  @Override
  public int getGridHeight() {
    gameNotStarted();
    return this.gridHeight;
  }

  @Override
  public int getGridLength() {
    gameNotStarted();
    return this.gridLength;
  }

  @Override
  public boolean anyLegalMoves() {
    gameNotStarted();
    // Goes through every hexagon and checks every direction for a path.
    for (int rowNum = 0; rowNum < this.gridHeight; rowNum++) {
      for (int hex = 0; hex < this.gridLength; hex++) {
        if (isHexHere(hex, rowNum)) {
          for (int index = 0; index < EVEN_ROW_OFFSET.length; index++) {
            if (legalMove(hex, rowNum)) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  @Override
  public GameState endOfGameStatus() {
    isGameOver();
    if (this.gameState == GameState.INPROGRESS
            || this.gameState == GameState.NOTSTARTED) {
      throw new IllegalStateException("The game is not over yet.");
    }
    return this.gameState;
  }

  @Override
  public ReadOnlyReversiInterface getCopyOfBoard() {
    return this;
  }

  // A helper method to check if the game has started yet.
  void gameNotStarted() {
    if (this.gameState == GameState.NOTSTARTED) {
      throw new IllegalStateException("The game has not started yet.");
    }
  }

  // Protected methods that get the private fields needed to call the new constructor from a
  // subclass.
  protected boolean[][] getGrid() {
    boolean[][] copyGrid = new boolean[this.getGridHeight()][this.getGridLength()];
    for (int row = 0; row < this.getGridHeight(); row++) {
      for (int col = 0; col < this.getGridLength(); col++) {
        copyGrid[row][col] = this.grid[row][col];
      }
    }
    return copyGrid;
  }

  protected Player[][] getPlayerGrid() {
    Player[][] copyGrid = new Player[this.getGridHeight()][this.getGridLength()];
    for (int row = 0; row < this.getGridHeight(); row++) {
      for (int col = 0; col < this.getGridLength(); col++) {
        copyGrid[row][col] = this.playerGrid[row][col];
      }
    }
    return copyGrid;
  }

  protected Player[] getPlayers() {
    Player[] copyPlayers = new Player[2];
    for (int i = 0; i < 2; i++) {
      copyPlayers[i] = this.players[i];
    }
    return copyPlayers;
  }

  protected int getPassCount() {
    return this.passCount;
  }

  @Override
  public void addObserver(ModelFeatures modelFeatures) {
    this.observers.add(modelFeatures);
  }

  private void alertObserver() {
    for (ModelFeatures observer : this.observers) {
      observer.yourTurn();
    }
  }

  protected boolean[][] getGridField() {
    return this.grid;
  }

  protected Player[][] getPlayerGridField() {
    return this.playerGrid;
  }
}
