package cs3500.reversi.strategy;

import cs3500.reversi.model.ReversiInterface;

/**
 * A class for a strategy that picks the spot that will get the most points possible.
 */
public class AsManyCapturesAsPossible extends StrategyAbstract implements ReversiStrategy {

  // Most points = the move that gets the most flips
  public int assignPoints(int after, int before, int cols, int rows, ReversiInterface model) {
    return after - before;
  }

}
