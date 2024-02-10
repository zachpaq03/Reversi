package cs3500.reversi.strategy;

import java.util.Objects;

import cs3500.reversi.model.Coord;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReversiInterface;

/**
 * A class that allows for two strategies to be attempted.
 */
public class TryTwo implements ReversiStrategy {
  ReversiStrategy first;
  ReversiStrategy second;

  /**
   * A constructor that takes in two strategies to try out.
   *
   * @param first  The first strategy.
   * @param second The second strategy.
   */
  public TryTwo(ReversiStrategy first, ReversiStrategy second) {
    this.first = Objects.requireNonNull(first);
    this.second = Objects.requireNonNull(second);
  }

  @Override
  public Coord chooseMove(ReversiInterface model, Player who) {
    try {
      Coord ans = this.first.chooseMove(model, who);
      return ans;
    } catch (IllegalStateException ignore) {
      try {
        return this.second.chooseMove(model, who);
      } catch (IllegalStateException bothFail) {
        throw new IllegalStateException("Both strategies failed to find a move");
      }
    }
  }

  @Override
  public int assignPoints(int after, int before, int cols, int rows, ReversiInterface model) {
    return 0;
  }
}
