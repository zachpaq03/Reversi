package cs3500.reversi.model;

/**
 * An interface for a game of Reversi. Has mutator methods place disc and pass, and
 * a collection of observer methods.
 */
public interface ReversiInterface extends ReadOnlyReversiInterface {

  /**
   * A method that starts the game. Sets up the model with the given specifics
   * on the width and height of the board.
   *
   * @param rowLength The length of the longest row, which will be the row in the
   *                  middle of the board.
   * @param height    The height of the board, from top to bottom. Must be an odd number
   *                  that is less than or equal to the (length - 1) times 2, so that
   *                  the rows decrease by one when going up or down from the center, and
   *                  have at least one shape.
   * @throws IllegalStateException    if the game has already started.
   * @throws IllegalArgumentException if the given height and width are invalid for the
   *                                  rules of the specific game, or if any of the inputs
   *                                  are null.
   */
  public void startGame(int rowLength, int height);

  /**
   * Place a disk at a specific place on the grid.
   *
   * @param x the X-Coordinate of where the player wants to place their disc.
   * @param y the Y-Coordinate of where the player wants to place their disc.
   * @throws IllegalStateException    if the game has not started yet.
   * @throws IllegalArgumentException if either given coordinate is negative, larger than
   *                                  the max width of the board, or on a coordinate that
   *                                  does not exist.
   * @throws IllegalStateException    if the spot the player is attempting to place their disc
   *                                  at is taken, or it is not a legal move.
   */
  public void placeDisc(int x, int y);

  /**
   * Called when a player passes their turn. If this is called two times in a row, the
   * game is over.
   *
   * @throws IllegalStateException if the game has not started yet.
   */
  public void pass();
}