package cs3500.reversi.strategy;

import cs3500.reversi.model.OddOrEven;
import cs3500.reversi.model.ReadOnlyReversiInterface;
import cs3500.reversi.model.ReversiInterface;

/**
 * A strategy that prioritizes corners because once they are captured, they cannot be recaptured.
 */
public class PrioritizeCorners extends StrategyAbstract implements ReversiStrategy {
  private boolean squareGame;

  public PrioritizeCorners(boolean squareGame) {
    this.squareGame = squareGame;
  }

  @Override
  public int assignPoints(int after, int before, int cols, int rows, ReversiInterface model) {
    int value = after - before;
    // Call helper to see if it is a corner spot. If it is, multiply it by the length of the longest
    // row so that it's value will be greater than a move that doesn't fill a corner.
    if (isCornerSpot(cols, rows, model)) {
      value *= model.getGridLength();
    }
    return value;
  }

  // A helper to check if a hexagon is a corner spot.
  private boolean isCornerSpot(int cols, int rows, ReadOnlyReversiInterface model) {
    if (!squareGame) {
      int middleRow = (model.getGridHeight() - 1) / 2;
      if (rows == middleRow && (cols == 0 || cols == model.getGridLength() - 1)) {
        return true;
      }
      // Corners only exits on the top, bottom, and middle row. (Middle row already checked above)
      if (rows == 0 || rows == model.getGridHeight() - 1) {
        int left = 0;
        int right = model.getGridLength() - 1;
        // The amount of coordinates that don't contain hexagons is equal to the difference from the
        // middle row. (Because each row decrease by length 1 that farther it is from the middle)
        int difference = getDifference(rows, model);
        boolean startFromLeft = true;
        if (getRowOddOrEven(rows, model) != OddOrEven.ODD) {
          startFromLeft = false;
        }
        while (difference > 0) {
          if (startFromLeft) {
            left++;
          } else {
            right--;
          }
          difference--;
          startFromLeft = !startFromLeft;
        }
        if (cols == left || cols == right) {
          return true;
        }
      }
    } else {
      return (cols == 0 && rows == 0) || (cols == 0 && rows == model.getGridHeight() - 1) ||
              (cols == model.getGridLength() - 1 && rows == model.getGridHeight() - 1) ||
              (cols == model.getGridLength() - 1 && rows == 0);
    }
    return false;
  }
}
