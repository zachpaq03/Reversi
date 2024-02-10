package cs3500.reversi.mocks;

import java.util.Objects;

import cs3500.reversi.model.ReversiInterface;
import cs3500.reversi.model.SquareReversi;

/**
 * A mock model for making sure that the controller is changing the player's turn when it is
 * supposed to.
 */
public class SquareReversiLogChangesInTurn extends SquareReversi implements ReversiInterface {
  final StringBuilder log;

  /**
   * Constructor for this mock model that adds a log.
   *
   * @param log the log for the mock model that updates when the turn is changed.
   */
  public SquareReversiLogChangesInTurn(StringBuilder log) {
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
}
