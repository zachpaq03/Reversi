package cs3500.reversi.strategy;

import org.junit.Test;

import cs3500.reversi.model.Coord;
import cs3500.reversi.model.ReversiInterface;
import cs3500.reversi.model.SquareReversi;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the try-two strategy on a square board.
 */
public class TryTwoSquareReversiTest {
  @Test
  public void tryTwoPicksFirst() {
    ReversiInterface game = new SquareReversi();
    game.startGame(4, 4);
    game.placeDisc(2, 0);
    ReversiStrategy noNextFirstAsManySec = new TryTwo(new NoNextToCorners(true),
            new PrioritizeCorners(true));
    Coord expected = new Coord(1, 0);
    // If it used prioritize corners first, it would pick (3, 0).
    assertEquals(expected, noNextFirstAsManySec.chooseMove(game, game.getTurn()));
  }
}
