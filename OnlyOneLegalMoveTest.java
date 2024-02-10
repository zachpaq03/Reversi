package cs3500.reversi.strategy;

import org.junit.Test;

import cs3500.reversi.mocks.OnlyOneLegalMove;
import cs3500.reversi.model.Coord;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReversiInterface;

import static org.junit.Assert.assertEquals;

/**
 * Test that uses to only one legal move mock to ensure that the choose move will pick the
 * predetermined coordinate.
 */
public class OnlyOneLegalMoveTest {

  @Test
  public void onlyOneLegalMoveWorks() {
    ReversiInterface gameMock = new OnlyOneLegalMove();
    gameMock.startGame(11, 11);
    ReversiStrategy mostFlips = new AsManyCapturesAsPossible();
    Coord expected = new Coord(5, 7);
    assertEquals(expected.toString(), mostFlips.chooseMove(gameMock, Player.Player1).toString());
  }
}
