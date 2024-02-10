package cs3500.reversi;

import cs3500.reversi.model.BasicReversiGame;
import cs3500.reversi.model.ReversiInterface;

/**
 * A subclass for the basic test with a basic Reversi game.
 */
public class BasicReversiBasicTests extends BasicTests {
  @Override
  protected ReversiInterface factory() {
    return new BasicReversiGame();
  }
}
