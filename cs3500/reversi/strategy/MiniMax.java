package cs3500.reversi.strategy;

import java.util.Objects;

import cs3500.reversi.model.Coord;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReversiInterface;

/**
 * A strategy that given the opponents' strategy, finds the move that will leave the opponent with
 * the smallest possible move.
 */
public class MiniMax extends StrategyAbstract implements ReversiStrategy {
  // The max length of a board is 20. That means there could be 19 rows above and below it. 19 * 2
  // + the middle row is 39, which is the longest possible move. The more points an opponent can get
  // on their next turn, the more is taken away from this value. That way, the highest value still
  // represents the best move to minimize the opponent.
  private static final int MAXPOINTS = 39;
  private ReversiStrategy opponentStrategy;

  /**
   * A contructor for the minimax strategy. It requires the opponents potential strategy as a
   * parameter.
   *
   * @param opponentStrategy The opponents potential strategy.
   */
  public MiniMax(ReversiStrategy opponentStrategy) {
    super();
    this.opponentStrategy = Objects.requireNonNull(opponentStrategy);
  }

  @Override
  public int assignPoints(int after, int before, int cols, int rows, ReversiInterface model) {
    // Make a copy because we are going to be mutating it
    ReversiInterface copyModel = model.getMutableCopy();
    copyModel.placeDisc(cols, rows);
    Coord opponentPotentialMove;
    try {
      opponentPotentialMove = this.opponentStrategy.chooseMove(copyModel, copyModel.getTurn());
      // The best potential move is making the opponent pass
    } catch (IllegalStateException pass) {
      return MAXPOINTS;
    }
    Player turn = copyModel.getTurn();
    int oppBefore;
    if (turn == Player.Player1) {
      oppBefore = copyModel.getScore().get(0);
    } else {
      oppBefore = copyModel.getScore().get(1);
    }
    copyModel.placeDisc(opponentPotentialMove.getX(), opponentPotentialMove.getY());
    int oppAfter;
    if (turn == Player.Player1) {
      oppAfter = copyModel.getScore().get(0);
    } else {
      oppAfter = copyModel.getScore().get(1);
    }
    return MAXPOINTS - (oppAfter - oppBefore);
  }
}
