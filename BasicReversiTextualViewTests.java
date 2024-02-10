package cs3500.reversi.view;

import cs3500.reversi.model.BasicReversiGame;
import cs3500.reversi.model.ReversiInterface;

/**
 * A subclass for the textual view abstract class that is specific to a basic Reversi game.
 */
public class BasicReversiTextualViewTests extends TextualViewTests {
  @Override
  protected ReversiInterface factory() {
    return new BasicReversiGame();
  }
}
