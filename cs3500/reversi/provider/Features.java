package cs3500.reversi.provider;

/**
 * The Features interface defines the methods that represent various actions or features
 * in a game or application by the view or model.
 */
public interface Features {

  /**
   * Invoked when a tile is clicked at the specified coordinates.
   *
   * @param xCoord The x-coordinate of the clicked tile.
   * @param yCoord The y-coordinate of the clicked tile.
   */
  void tileClicked(int xCoord, int yCoord);

  /**
   * Attempts to perform a move based on the current state of the game.
   */
  void attemptMove();

  /**
   * Attempts to pass the turn to the next player or phase.
   */
  void attemptPass();

  /**
   * Changes the turns, switching to the next player or phase in the game.
   */
  void changeTurns();

  /**
   * Notifies the system about the current turn or player.
   */
  void notifyTurn();
}