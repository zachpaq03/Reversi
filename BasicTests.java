package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import cs3500.reversi.model.GameState;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReversiInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * An abstract class of very basic test cases for an understanding of how the game works.
 */
public abstract class BasicTests {
  protected abstract ReversiInterface factory();

  @Test
  public void makeAMove() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    game.placeDisc(3, 3);
    assertEquals(game.getScore(), Arrays.asList(5, 2, 3));
  }

  @Test
  public void endGame() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    game.placeDisc(3, 3);
    game.pass();
    game.pass();
    assertTrue(game.isGameOver());
    assertEquals(GameState.PLAYER1WINS, game.endOfGameStatus());
  }

  @Test
  public void setUpOddLengthBoard() {
    ReversiInterface game = factory();
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
  }

  @Test
  public void setUpBoardEvenLength() {
    ReversiInterface game = factory();
    game.startGame(4, 7);
    Assert.assertEquals(game.getGridLength(), 4);
    Assert.assertEquals(game.getGridHeight(), 7);
    assertFalse(game.isHexHere(0, 0));
    assertTrue(game.isHexHere(1, 3));
    assertTrue(game.isHexHere(2, 3));
    assertTrue(game.isHexHere(2, 0));
    assertTrue(game.isHexHere(1, 1));
  }
}
