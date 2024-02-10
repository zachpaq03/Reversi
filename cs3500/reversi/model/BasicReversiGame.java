package cs3500.reversi.model;

/**
 * A subclass of the Reversi abstract class that does not override any of the methods. This is the
 * most standard version of the game.
 */
public class BasicReversiGame extends ReversiAbstract {
  public BasicReversiGame() {
    super();
  }

  /**
   * A constructor for a mutable copy of the model which can be made mid-game.
   *
   * @param grid The boolean grid representing if a hexagon is at that location.
   * @param playerGrid A grid of Player types showing if there is a disc at the location.
   * @param players The array of players in the game.
   * @param playerWhoIsUp The player whose turn it currently is.
   * @param passCount The current pass count (2 passes in a row means the game ends).
   */
  public BasicReversiGame(boolean[][] grid, Player[][] playerGrid, Player[] players,
                          Player playerWhoIsUp, int passCount) {
    super(grid, playerGrid, players, playerWhoIsUp, passCount);
  }

  @Override
  public ReversiInterface getMutableCopy() {
    return new BasicReversiGame(super.getGrid(), super.getPlayerGrid(), super.getPlayers(),
            super.getTurn(), super.getPassCount());
  }

  @Override
  void checkForExceptionsStartGame(int rowLength, int height, Player[] players) {
    // If the row length is 1, and each row decrease by one above and below the middle row, than
    // this board will just be a single hexagon, meaning an equal amount of discs for each player
    // cannot be placed. Thus, a row length of at least 2 is required (Although a row length of
    // exactly 2 will end in an immediate draw, but it is still allowed).
    if (rowLength < 2) {
      throw new IllegalArgumentException("The width of the game must be greater than 1.");
    }
    // Height must be at least 3. The board is symmetrical in the version of the game, and a one
    // row game would not be very fun.
    if (height < 3) {
      throw new IllegalArgumentException("The height must be at least 3.");
    }
    // If the rows of the board are decreasing by one for every row above or below the middle row,
    // then there comes a point where the amount of hexagons in a row is 1. The board should not be
    // able to go lower than that. This requirement makes the largest the height can be the
    // situation where the row decrease all the way to a size of one, but no greater.
    if (height > (rowLength * 2 - 1)) {
      throw new IllegalArgumentException(String.format("The height of the board cannot be greater" +
              " than two times the row length minus one. For the given row length, the height" +
              " cannot exceed %s.", (rowLength * 2 - 1)));
    }
    // To ensure that the board is symmetrical, it is necessary for the height to be odd. In a
    // symmetrical board every row has a partner of equal length, besides the middle row, making
    // the row total odd.
    if (height % 2 == 0) {
      throw new IllegalArgumentException("The height of the board must be odd.");
    }
    super.checkForExceptionsStartGame(rowLength, height, players);
  }
}
