package cs3500.reversi.provider;

/**
 * The Reversi interface represents a Reversi game board to manage a full game of Reversi.
 */
public interface Reversi extends ReadOnlyReversi {

  /**
   * Allows a player to take control of a tile. The tile must be unoccupied and on the board to
   * be occupied.
   *
   * @param player the team taking control of the tile
   * @param pos    the position of the tile
   * @throws IllegalArgumentException if the given team or position is null, or the position is
   *                                  not on the board.
   * @throws IllegalStateException    if the tile is already occupied, or the move is not allowed
   */
  void placePiece(Team player, Position pos)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Given a team and a position, determines if that move is legal in this game.
   *
   * @param playerTeam the player making the desired move
   * @param pos        the position to place a piece on
   * @return true if the move is legal, false otherwise
   * @throws IllegalArgumentException if the given team or position is null, or the position is
   *                                  not on the board.
   */
  boolean isMoveValid(Team playerTeam, Position pos) throws IllegalArgumentException;

  /**
   * If a turn is passed, the turn is moved to the other player. If both players pass in a row,
   * the game should end.
   */
  void pass();

  /**
   * Starts the Reversi game.
   */
  void startGame();

  /**
   * Adds a Features listener to the Reversi game.
   *
   * @param features The Features listener to be added.
   */
  void addFeatureListener(Features features);

  /**
   * Returns a copy of the given Reversi model.
   * @param model the model to make a copy of
   * @return the copy of the given model
   */
  Reversi getCopy(Reversi model);
}
