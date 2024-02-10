package cs3500.reversi.provider;

/**
 * The ReadOnlyReversi interface represents a basic Reversi game with a hexagonal game board.
 * Each game has two players, represented by black and white pieces. The game takes in a given
 * board size, where the size is the length of one side of the board. A game size must be at least
 * 3 for the game to be playable. This version is strictly observable and cannot be altered
 * using its methods.
 */
public interface ReadOnlyReversi {

  /**
   * Returns whether the game has ended. A game ends if there are no open tiles left on the board,
   * if both players pass in a row, or if there are no possible moves for either player.
   *
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver();

  /**
   * Returns the number of tiles controlled by the given player.
   *
   * @param player the team that controls tiles on the board
   * @return the number of tiles controlled by the given player
   */
  int getScore(Team player);

  /**
   * Returns the length of one side of the game board.
   *
   * @return the size of the game board, defined by the length of a side
   */
  int getSize();

  /**
   * Returns the number of tiles on this game board.
   *
   * @return the number of tiles existing on this game board
   */
  int getNumTiles();

  /**
   * Returns the tile at the given position.
   *
   * @param pos the desired position on the game board
   * @return the tile at the given position
   * @throws IllegalArgumentException if the given position is not on this board
   */
  Tile getTileAt(Position pos) throws IllegalArgumentException;

  /**
   * Returns the team whose current turn it is.
   *
   * @return the current turn's player
   */
  Team getPlayer();

  /**
   * Returns the current status of the game.
   *
   * @return the current status of the game (e.g., ongoing, draw, or victory)
   */
  Status gameStatus();

  /**
   * Returns the team that has won the game.
   *
   * @return the winning team, or null if the game is not over or ended in a draw
   */
  Team getWinner();
}
