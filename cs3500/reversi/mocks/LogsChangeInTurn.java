package cs3500.reversi.mocks;

import java.util.Objects;

import cs3500.reversi.model.BasicReversiGame;
import cs3500.reversi.model.ReversiAbstract;
import cs3500.reversi.model.ReversiInterface;

/**
 * A class for a mock model that logs whenever a turn is changed.
 */
public class LogsChangeInTurn extends ReversiAbstract implements ReversiInterface {
  final StringBuilder log;

  /**
   * Constructor for this mock model that adds a log.
   *
   * @param log the log for the mock model that updates when the turn is changed.
   */
  public LogsChangeInTurn(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public void placeDisc(int x, int y) {
    log.append("Turn changed, ");
    super.placeDisc(x, y);
  }

  @Override
  public void pass() {
    log.append("Turn changed, ");
    super.pass();
  }

  @Override
  public ReversiInterface getMutableCopy() {
    return new BasicReversiGame(this.getGrid(), this.getPlayerGrid(), this.getPlayers(),
            this.getTurn(), this.getPassCount());
  }
}
