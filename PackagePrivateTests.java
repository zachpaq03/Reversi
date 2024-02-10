package cs3500.reversi.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for package private methods for a basic Reversi game.
 */
public class PackagePrivateTests {

  @Test
  public void setUpOddLengthBoard() {
    BasicReversiGame game = new BasicReversiGame();
    game.startGame(5, 9);
    assertFalse(game.isHexHere(0, 0));
    assertTrue(game.isHexHere(2, 0));
    assertFalse(game.isHexHere(0, 2));
    assertTrue(game.isHexHere(1, 1));
    assertTrue(game.isHexHere(2, 8));
    assertTrue(game.isDiscHere(1, 4));
    assertTrue(game.isDiscHere(3, 4));
    assertTrue(game.isDiscHere(1, 3));
    assertTrue(game.isDiscHere(2, 3));
    assertTrue(game.isDiscHere(1, 5));
    assertTrue(game.isDiscHere(2, 5));
    assertEquals(Player.Player1.getColor(), game.getDiscColor(1, 4));
    assertEquals(Player.Player2.getColor(), game.getDiscColor(3, 4));
    assertEquals(Player.Player1.getColor(), game.getDiscColor(2, 3));
    assertEquals(Player.Player2.getColor(), game.getDiscColor(1, 3));
    assertEquals(Player.Player1.getColor(), game.getDiscColor(2, 5));
    assertEquals(Player.Player2.getColor(), game.getDiscColor(1, 5));
  }

  @Test
  public void setUpBoardEvenLength() {
    BasicReversiGame game = new BasicReversiGame();
    game.startGame(4, 7);
    assertFalse(game.isHexHere(0, 0));
    assertTrue(game.isHexHere(1, 3));
    assertTrue(game.isHexHere(2, 3));
    assertTrue(game.isHexHere(2, 0));
    assertTrue(game.isHexHere(1, 1));
    assertTrue(game.isDiscHere(1, 3));
    assertTrue(game.isDiscHere(2, 3));
    assertTrue(game.isDiscHere(2, 2));
    assertTrue(game.isDiscHere(2, 4));
    assertEquals(Player.Player1.getColor(), game.getDiscColor(1, 3));
    assertEquals(Player.Player1.getColor(), game.getDiscColor(2, 3));
    assertEquals(Player.Player2.getColor(), game.getDiscColor(2, 2));
    assertEquals(Player.Player2.getColor(), game.getDiscColor(2, 4));
  }

  @Test
  public void testGridSmallBoard() {
    BasicReversiGame game = new BasicReversiGame();
    game.startGame(2, 3);
    assertTrue(game.isHexHere(1, 0));
    assertTrue(game.isHexHere(0, 1));
    assertTrue(game.isHexHere(1, 1));
    assertTrue(game.isHexHere(1, 2));
    assertTrue(game.isDiscHere(1, 0));
    assertTrue(game.isDiscHere(0, 1));
    assertTrue(game.isDiscHere(1, 1));
    assertTrue(game.isDiscHere(1, 2));
  }

  @Test
  public void getHexAmountWorks() {
    BasicReversiGame game = new BasicReversiGame();
    game.startGame(5, 9);
    for (int i = 0; i < 9; i++) {
      if (i % 2 == 0) {
        assertEquals(OddOrEven.ODD, game.getHexAmount(game.getGridHeight(), game.getGridLength(),
                i));
      } else {
        assertEquals(OddOrEven.EVEN, game.getHexAmount(game.getGridHeight(), game.getGridLength(),
                i));
      }
    }
  }

  @Test
  public void updateRowAndColWork() {
    BasicReversiGame game = new BasicReversiGame();
    game.startGame(6, 9);
    assertEquals(game.updateRow(4, -1, -1), 3);
    assertEquals(game.updateRow(5, -1, 0), 5);
    assertEquals(game.updateCol(4, 4, -1, -1), 3);
    assertEquals(game.updateCol(5, 4, -1, 0), 4);
  }

  @Test
  public void notSameColorWorks() {
    BasicReversiGame game = new BasicReversiGame();
    game.startGame(2, 3);
    assertTrue(game.notSameColor(0, 1));
  }
}
