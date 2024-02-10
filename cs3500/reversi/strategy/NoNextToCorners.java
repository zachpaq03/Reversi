package cs3500.reversi.strategy;

import cs3500.reversi.model.OddOrEven;
import cs3500.reversi.model.ReadOnlyReversiInterface;
import cs3500.reversi.model.ReversiInterface;

// NOTE: There was some misinterpreting of a corner and this strategy more accurately represents
// avoiding edges.

/**
 * A class for a strategy that tries to avoid moves that are next to corners, since the opponent can
 * potentially capture that corner on the next turn and there will be no way for the player to get
 * it back.
 */
public class NoNextToCorners extends StrategyAbstract implements ReversiStrategy {
  private boolean squareGame;

  public NoNextToCorners(boolean squareGame) {
    this.squareGame = squareGame;
  }

  @Override
  public int assignPoints(int after, int before, int cols, int rows, ReversiInterface model) {
    // If a move is next to an edge, return 0 points, else return the amount of flips
    if (isNextToCorner(cols, rows, model)) {
      return 0;
    } else {
      return after - before;
    }
  }

  // A helper to determine if a move is next to an edge.
  private boolean isNextToCorner(int cols, int rows, ReadOnlyReversiInterface model) {
    if (!squareGame) {
      // The first and last rows do not contain any hexagons that are next to an edge.
      if (rows == 0 || rows == model.getGridHeight() - 1) {
        return false;
      }
      int leftPoint = 0;
      int rightPoint = model.getGridLength() - 1;
      int difference = getDifference(rows, model);
      boolean startFromLeft = true;
      if (getRowOddOrEven(rows, model) != OddOrEven.ODD) {
        startFromLeft = false;
      }
      while (difference > 0) {
        if (startFromLeft) {
          leftPoint++;
        } else {
          rightPoint--;
        }
        difference--;
        startFromLeft = !startFromLeft;
      }
      leftPoint++;
      rightPoint--;
      // Every hexagon besides the edge point of the 2nd and 2nd to last row will be next to an
      // edge.
      if (rows == 1 || rows == model.getGridHeight() - 2) {
        if (cols >= leftPoint && cols <= rightPoint) {
          return true;
        }
        // if it is not the 2nd or 2nd to last row, it will have to be the 2nd hex in the row or
        // 2nd to last.
      } else {
        if (cols == leftPoint || cols == rightPoint) {
          return true;
        }
      }
    } else {
      if (rows == 0 || rows == model.getGridHeight() - 1 || cols == 0 ||
              cols == model.getGridLength() - 1) {
        return false;
      }
      if (rows == 1 || rows == model.getGridHeight() - 2) {
        return cols >= 1 && cols <= model.getGridLength() - 2;
      } else {
        return cols == 1 || cols == model.getGridLength() - 2;
      }
    }
    return false;
  }
}
