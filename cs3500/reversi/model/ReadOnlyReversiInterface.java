package cs3500.reversi.model;

import java.util.List;

import cs3500.reversi.controller.ModelFeatures;

/**
 * An interface for the observation methods of a Reversi game.
 */
public interface ReadOnlyReversiInterface {
  /**
   * Returns the score of the current game as a list. Player 1's score is the first element
   * of the list, player 2's is the second, and the third is the score difference.
   *
   * @return A list of the scores of the game.
   * @throws IllegalStateException if the game has not started yet.
   */
  public List<Integer> getScore();

  /**
   * Returns the player whose turn it currently is.
   *
   * @return the player whose turn it is.
   * @throws IllegalStateException if the game has not started yet.
   */
  public Player getTurn();

  /**
   * Checks if there is a disc at the given coordinate.
   *
   * @param x the X-Coordinate to check if there is a disc.
   * @param y the Y-Coordinate to check if there is a disc.
   * @return true if there is a disc at the given coordinate, false otherwise.
   * @throws IllegalStateException    if the game has not started yet.
   * @throws IllegalArgumentException if the coordinates are negative, or greater
   *                                  than the width of height of the board, or there
   *                                  is no hexagon at the given coordinate.
   */
  public boolean isDiscHere(int x, int y);

  /**
   * Returns the color of the disc at a given coordinate.
   *
   * @param x the X-Coordinate to check if there is a disc.
   * @param y the Y-Coordinate to check if there is a disc.
   * @return the color of the disc at the location, or null if there is no disc.
   * @throws IllegalStateException    if the game has not started yet.
   * @throws IllegalArgumentException if either given coordinate is negative, larger than
   *                                  the max width of the board, or on a coordinate that
   *                                  does not exist.
   * @throws IllegalStateException    if there is no disc at the given coordinate.
   */
  public Color getDiscColor(int x, int y);

  /**
   * Checks whether the game is over, which is true when the two players
   * have passed their moves consecutively.
   *
   * @return true if the game is over, false if the game is still ongoing.
   * @throws IllegalStateException if the game has not started yet.
   */
  public boolean isGameOver();

  /**
   * Checks whether the given coordinate has a hexagon.
   *
   * @param x the X-Coordinate to check.
   * @param y the Y-Coordinate to check.
   * @return true if there is a hexagon at the given coordinate.
   * @throws IllegalStateException    if the game has not started yet.
   * @throws IllegalArgumentException if the coordinates are negative,
   *                                  or the coordinates are greater than
   *                                  the width or height of the board.
   */
  public boolean isHexHere(int x, int y);

  /**
   * Returns the height of the grid for the game, which is the amount of rows.
   *
   * @return the height of the grid.
   * @throws IllegalStateException if the game has not started yet.
   */
  public int getGridHeight();

  /**
   * Returns the length of the grid for the game, which is the amount of columns.
   *
   * @return the length of the grid.
   * @throws IllegalStateException if the game has not started yet.
   */
  public int getGridLength();

  /**
   * A method that returns the status at the end of the game, which is a win for one
   * of the players, or a draw.
   *
   * @return the state of the game when it is over.
   * @throws IllegalStateException if the game is not over.
   */
  public GameState endOfGameStatus();

  /**
   * Returns a copy of the board that is read-only.
   *
   * @return a read-only copy of the board.
   */
  public ReadOnlyReversiInterface getCopyOfBoard();

  /**
   * Checks if placing a disc at the given coordinate is a legal move for the player who is up.
   *
   * @param x the x-coordinate for the desired move.
   * @param y the y-coordinate for the desired move.
   * @return true if the move is legal, false if not.
   * @throws IllegalStateException    if the game has not started yet.
   * @throws IllegalArgumentException if the coordinates are negative, or greater
   *                                  than the width of height of the board, or there
   *                                  is no hexagon at the given coordinate.
   */
  public boolean legalMove(int x, int y);

  /**
   * Checks if there are any legal moves left in the game for the player who is up.
   *
   * @return true if there are legal moves left, false if not.
   * @throws IllegalStateException if the game has not started yet.
   */
  public boolean anyLegalMoves();

  /**
   * Add an observer to be notified when the turn changed in the model.
   *
   * @param modelFeatures The class that implements the modelFeatures interface that will be
   *                      notified when the turn changes.
   */
  public void addObserver(ModelFeatures modelFeatures);

  /**
   * Returns a new instance of the board that is mutable. Useful for testing out moves to
   * determine the optimal strategy.
   *
   * @return a new ReversiInterface with the current state of the game.
   * @throws IllegalStateException if the game has not started yet.
   */
  public ReversiInterface getMutableCopy();
}

