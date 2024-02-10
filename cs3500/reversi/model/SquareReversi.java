package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for a game of Reversi on a square board. Rules are the same as a normal game of
 * Reversi but on a board with squares instead, meaning there are more directions for a plauer
 * to make a move in.
 */
public class SquareReversi extends ReversiAbstract implements ReversiInterface {
  public final static int[] DIRECTIONS_X = {0, -1, -1, -1, 0, 1, 1, 1};
  public final static int[] DIRECTIONS_Y = {1, 1, 0, -1, -1, -1, 0, 1};

  public SquareReversi() {
    super();
  }

  public SquareReversi(boolean[][] grid, Player[][] playerGrid, Player[] players,
                       Player playerWhoIsUp, int passCount) {
    super(grid, playerGrid, players, playerWhoIsUp, passCount);
  }

  @Override
  public ReversiInterface getMutableCopy() {
    return new SquareReversi(super.getGrid(), super.getPlayerGrid(), super.getPlayers(),
            super.getTurn(), super.getPassCount());
  }


  @Override
  void checkForExceptionsStartGame(int rowLength, int height, Player[] players) {
    if (rowLength % 2 != 0 || rowLength != height) {
      throw new IllegalArgumentException("Row length must be equal to height and that value must" +
              "also be even.");
    }
    if (rowLength < 2) {
      throw new IllegalArgumentException("Board must be at least 2 x 2");
    }
    super.checkForExceptionsStartGame(rowLength, height, players);
  }

  @Override
  void setGrid(int height, int rowLength) {
    for (int x = 0; x < height; x++) {
      for (int y = 0; y < rowLength; y++) {
        super.getGridField()[x][y] = true;
      }
    }
    setInitialDiscs(height, rowLength);
  }

  @Override
  void setInitialDiscs(int height, int rowLength) {
    int midMinus1 = height / 2 - 1;
    int mid = height / 2;
    super.getPlayerGridField()[midMinus1][midMinus1] = Player.Player1;
    super.getPlayerGridField()[mid][mid] = Player.Player1;
    super.getPlayerGridField()[midMinus1][mid] = Player.Player2;
    super.getPlayerGridField()[mid][midMinus1] = Player.Player2;
  }

  @Override
  public boolean legalMove(int x, int y) {
    super.gameNotStarted();
    for (int i = 0; i < DIRECTIONS_X.length; i++) {
      List<Integer> path = new ArrayList<>();
      if (traverseBoard(x, y, DIRECTIONS_Y[i], DIRECTIONS_X[i], DIRECTIONS_Y[i], DIRECTIONS_X[i],
              path, false)) {
        return true;
      }
    }
    return false;
  }

  @Override
  void updateBoard(int x, int y) {
    boolean sameTurn = false;
    for (int i = 0; i < DIRECTIONS_X.length; i++) {
      List<Integer> path = new ArrayList<>();
      if (traverseBoard(x, y, DIRECTIONS_Y[i], DIRECTIONS_X[i], DIRECTIONS_Y[i], DIRECTIONS_X[i],
              path, sameTurn)) {
        sameTurn = true;
        for (int j = 0; j < path.size(); j += 2) {
          super.getPlayerGridField()[path.get(j)][path.get(j + 1)] = super.getTurn();
        }
      }
    }

  }

  @Override
  boolean traverseBoard(int x, int y, int evenRowOffset, int evenColOffset, int oddRowOffset,
                        int oddColOffset, List<Integer> path, boolean sameTurn) {
    int row = y;
    int col = x;

    try {
      if (!isDiscHere(col, row) || sameTurn) {
        path.add(row);
        path.add(col);
        row += evenRowOffset;
        col += evenColOffset;
        path.add(row);
        path.add(col);

        while (isDiscHere(col, row) && super.notSameColor(row, col)) {
          col += evenColOffset;
          row += evenRowOffset;
          path.add(row);
          path.add(col);

          if (isDiscHere(col, row) && getDiscColor(col, row) == super.getTurn().getColor()) {
            return true;
          }
        }
      }
    } catch (IllegalArgumentException | IllegalStateException e) {
      return false;
    }
    return false;
  }
}
