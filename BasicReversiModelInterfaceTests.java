package cs3500.reversi;

import cs3500.reversi.model.BasicReversiGame;
import cs3500.reversi.model.ReversiInterface;

/**
 * A subclass for the tests that test the public signature in the model, specifically for a
 * basic Reversi game.
 */
public class BasicReversiModelInterfaceTests extends ModelInterfaceTests {
  protected ReversiInterface factory() {
    return new BasicReversiGame();
  }

}
