package cs3500.reversi.strategy;

import cs3500.reversi.model.Coord;
import cs3500.reversi.model.OddOrEven;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReadOnlyReversiInterface;
import cs3500.reversi.model.ReversiInterface;

/**
 * An abstract class for the strategy classes. The choose move method is the same for each strategy,
 * because it just picks the move with the highest amount of points. The assign points method
 * differs from strategy to strategy. THey also share the methods that determine how far a row is
 * from the middle row of a board and if a row has an even or odd amount of hexagons.
 */
public abstract class StrategyAbstract implements ReversiStrategy {

  @Override
  public Coord chooseMove(ReversiInterface model, Player who) {
    // Make a copy to avoid unwanted mutation. Keep an original version because each move tested
    // needs to be tested on the original model, not the mutated one.
    ReversiInterface copyModelOrig = model.getMutableCopy();
    ReversiInterface copyModel = copyModelOrig.getMutableCopy();
    int points = 0;
    Coord result = null;
    for (int rows = 0; rows < copyModel.getGridHeight(); rows++) {
      for (int cols = 0; cols < copyModel.getGridLength(); cols++) {
        if (model.isHexHere(cols, rows) && !copyModel.isDiscHere(cols, rows)
                && copyModel.legalMove(cols, rows)) {
          int before = 0;
          int after = 0;
          if (who == Player.Player1) {
            before = copyModel.getScore().get(0);
            copyModel.placeDisc(cols, rows);
            after = copyModel.getScore().get(0);
          } else {
            before = copyModel.getScore().get(1);
            copyModel.placeDisc(cols, rows);
            after = copyModel.getScore().get(1);
          }
          // Call assign points to see if this new move gets more points than the current highest
          if (assignPoints(after, before, cols, rows, model) >= points) {
            if (result == null || (assignPoints(after, before, cols, rows, model)) > points) {
              points = (assignPoints(after, before, cols, rows, model));
              result = new Coord(cols, rows);
              // If they get the same amount of points, pick the one closest to the top left
              // (the lowest sum of the coordinates since the origin is the top left)
            } else if ((assignPoints(after, before, cols, rows, model)) == points && (rows + cols)
                    < (result.getX() + result.getY())) {
              points = (assignPoints(after, before, cols, rows, model));
              result = new Coord(cols, rows);
            }
          }
          // Reset the model
          copyModel = copyModelOrig.getMutableCopy();
        }
      }
    }
    if (result == null) {
      throw new IllegalStateException("No move available.");
    }
    return result;
  }

  // A helper method that gets how far a row is from the middle row.
  protected int getDifference(int rows, ReadOnlyReversiInterface model) {
    int middleRow = (model.getGridHeight() - 1) / 2;
    return Math.abs(rows - middleRow);
  }

  // A helper method that gets if a row is odd or even, which is important for knowing how the row
  // is offset.
  protected OddOrEven getRowOddOrEven(int rows, ReadOnlyReversiInterface model) {
    if (model.getGridLength() % 2 == 0) {
      if (getDifference(rows, model) % 2 == 0) {
        return OddOrEven.EVEN;
      } else {
        return OddOrEven.ODD;
      }
    } else {
      if (getDifference(rows, model) % 2 == 0) {
        return OddOrEven.ODD;
      } else {
        return OddOrEven.EVEN;
      }
    }
  }

  public abstract int assignPoints(int after, int before, int cols, int rows,
                                   ReversiInterface model);
}
