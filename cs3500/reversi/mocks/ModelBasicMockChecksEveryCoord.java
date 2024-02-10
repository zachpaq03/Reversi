package cs3500.reversi.mocks;

import java.util.Objects;

import cs3500.reversi.model.BasicReversiGame;
import cs3500.reversi.model.ReversiAbstract;
import cs3500.reversi.model.ReversiInterface;

/**
 * A mock that logs every time a coordinate is checked, used for the strategy classes.
 */
public class ModelBasicMockChecksEveryCoord extends ReversiAbstract implements ReversiInterface {
  final StringBuilder log;

  public ModelBasicMockChecksEveryCoord(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public boolean isHexHere(int x, int y) {
    log.append("(");
    log.append(x);
    log.append(", ");
    log.append(y);
    log.append(") ");
    return super.isHexHere(x, y);
  }

  @Override
  public ReversiInterface getMutableCopy() {
    return new BasicReversiGame(this.getGrid(), this.getPlayerGrid(), this.getPlayers(),
            this.getTurn(), this.getPassCount());
  }

}
