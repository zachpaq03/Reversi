package cs3500.reversi.strategy;

import org.junit.Test;

import cs3500.reversi.model.BasicReversiGame;
import cs3500.reversi.model.Coord;
import cs3500.reversi.model.ReversiInterface;

import static org.junit.Assert.assertEquals;

/**
 * A class for testing the TryTwo strategy. Currently, it is pretty trivial because if the first
 * strategy fails, the second will also. For now, it only tests that TryTwo apply the first one
 * and not the second one, but in the future once more strategies are implemented it will test
 * that if the first one fails it will try the second one.
 */
public class TryTwoTests {

  @Test
  public void tryTwoPicksFirst() {
    ReversiInterface game = new BasicReversiGame();
    game.startGame(7, 5);
    ReversiStrategy noNextFirstAsManySec = new TryTwo(new NoNextToCorners(false),
            new AsManyCapturesAsPossible());
    Coord expected = new Coord(3, 0);
    assertEquals(expected, noNextFirstAsManySec.chooseMove(game, game.getTurn()));
  }
}
