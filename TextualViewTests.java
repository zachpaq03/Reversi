package cs3500.reversi.view;

import org.junit.Test;

import java.util.Arrays;

import cs3500.reversi.model.GameState;
import cs3500.reversi.model.ReversiInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the textual view of a Reversi game.
 */
public abstract class TextualViewTests {
  protected abstract ReversiInterface factory();

  @Test
  public void viewTestStartOfGameOddRow() {
    ReversiInterface game = factory();
    TextualView view = new BasicTextualView(game);
    game.startGame(5, 9);
    assertEquals("    -    \n" +
            "   - -    \n" +
            "  - - -  \n" +
            " - O X -  \n" +
            "- X - O -\n" +
            " - O X -  \n" +
            "  - - -  \n" +
            "   - -    \n" +
            "    -    ", view.toString());
  }

  @Test
  public void viewTestStartOfGameEvenRow() {
    ReversiInterface game = factory();
    TextualView view = new BasicTextualView(game);
    game.startGame(14, 7);
    assertEquals("    - - - - - - - - - - -  \n" +
            "   - - - - - - - - - - - -  \n" +
            "  - - - - - - O - - - - - -\n" +
            " - - - - - - X X - - - - - -\n" +
            "  - - - - - - O - - - - - -\n" +
            "   - - - - - - - - - - - -  \n" +
            "    - - - - - - - - - - -  ", view.toString());
  }


  @Test
  public void viewTestAfterSomeMoves() {
    ReversiInterface game = factory();
    TextualView view = new BasicTextualView(game);
    game.startGame(5, 9);
    game.placeDisc(2, 2);
    game.placeDisc(1, 1);
    game.placeDisc(2, 1);
    game.placeDisc(0, 3);
    game.placeDisc(0, 5);
    game.placeDisc(2, 6);
    game.placeDisc(3, 5);
    game.placeDisc(3, 3);
    game.placeDisc(2, 7);
    game.placeDisc(1, 7);
    assertFalse(game.isGameOver());
    game.pass();
    assertFalse(game.isGameOver());
    game.pass();
    assertTrue(game.isGameOver());
    assertEquals(game.endOfGameStatus(), GameState.PLAYER2WINS);
    assertEquals(game.getScore(), Arrays.asList(6, 10, 4));
    assertEquals("    -    \n" +
            "   O X    \n" +
            "  - X -  \n" +
            " O O O O  \n" +
            "- O - O -\n" +
            " X X O X  \n" +
            "  - O -  \n" +
            "   O X    \n" +
            "    -    ", view.toString());
  }

  @Test
  public void copyWorks() {
    ReversiInterface game = factory();
    TextualView view = new BasicTextualView(game);
    game.startGame(5, 9);
    game.placeDisc(2, 2);
    game.placeDisc(1, 1);
    game.placeDisc(2, 1);
    game.placeDisc(0, 3);
    game.placeDisc(0, 5);
    game.placeDisc(2, 6);
    game.placeDisc(3, 5);
    game.placeDisc(3, 3);
    game.placeDisc(2, 7);
    game.placeDisc(1, 7);
    ReversiInterface copyGame = game.getMutableCopy();
    TextualView copyView = new BasicTextualView(copyGame);
    assertEquals(view.toString(), copyView.toString());
  }
}
