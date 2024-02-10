package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import cs3500.reversi.model.GameState;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.SquareReversi;
import cs3500.reversi.view.BasicTextViewSquareGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Test class for a square Reversi model.
 */
public class SquareGameBasicTests {

  @Test
  public void makeAMove() {
    SquareReversi game = new SquareReversi();
    game.startGame(8, 8);
    game.placeDisc(2, 4);
    assertEquals(game.getScore(), Arrays.asList(4, 1, 3));
  }

  @Test
  public void endGame() {
    SquareReversi game = new SquareReversi();
    game.startGame(8, 8);
    game.placeDisc(2, 4);
    game.pass();
    game.pass();
    assertTrue(game.isGameOver());
    assertEquals(GameState.PLAYER1WINS, game.endOfGameStatus());
  }

  @Test
  public void cantCallMethodsWithoutStartingGame() {
    SquareReversi game = new SquareReversi();
    assertThrows(IllegalStateException.class, () -> game.placeDisc(0, 0));
    assertThrows(IllegalStateException.class, () -> game.pass());
    assertThrows(IllegalStateException.class, () -> game.getScore());
    assertThrows(IllegalStateException.class, () -> game.getTurn());
    assertThrows(IllegalStateException.class, () -> game.isDiscHere(0, 0));
    assertThrows(IllegalStateException.class, () -> game.getDiscColor(0, 0));
    assertThrows(IllegalStateException.class, () -> game.isGameOver());
    assertThrows(IllegalStateException.class, () -> game.isHexHere(0, 0));
    assertThrows(IllegalStateException.class, () -> game.getGridHeight());
    assertThrows(IllegalStateException.class, () -> game.getGridLength());
  }

  @Test
  public void startGameThrowsExceptions() {
    SquareReversi game = new SquareReversi();
    assertThrows(IllegalArgumentException.class, () -> game.startGame(4, 6));
    assertThrows(IllegalArgumentException.class, () -> game.startGame(7, 7));
    assertThrows(IllegalArgumentException.class, () -> game.startGame(0, 0));
    assertThrows(IllegalArgumentException.class, () -> game.startGame(22, 22));
    game.startGame(8, 8);
    assertThrows(IllegalStateException.class, () -> game.startGame(10, 10));
  }

  @Test
  public void throwsOnIllegalMove() {
    SquareReversi game = new SquareReversi();
    game.startGame(8, 8);
    assertThrows(IllegalStateException.class, () -> game.placeDisc(0, 0));
  }

  @Test
  public void throwsIAEPlaceDisc() {
    SquareReversi game = new SquareReversi();
    game.startGame(8, 8);
    assertThrows(IllegalArgumentException.class, () -> game.placeDisc(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> game.placeDisc(100, 200));
  }

  @Test
  public void correctWinner() {
    SquareReversi game = new SquareReversi();
    BasicTextViewSquareGame view = new BasicTextViewSquareGame(game);
    game.startGame(8, 8);
    game.placeDisc(4, 2);
    game.placeDisc(5, 2);
    game.placeDisc(5, 3);
    game.pass();
    game.pass();
    List<Integer> scores = game.getScore();
    Assert.assertTrue(scores.get(0) > scores.get(1));
    Assert.assertSame(game.endOfGameStatus(), GameState.PLAYER1WINS);
    // System.out.println(view.toString());
  }

  @Test
  public void cantPlaceDiscLegalMoveForOtherPlayer() {
    SquareReversi game = new SquareReversi();
    game.startGame(8, 8);
    assertThrows(IllegalStateException.class, () -> game.placeDisc(3, 2));
  }

  @Test
  public void placeDiscCanMakeMultipleLines() {
    SquareReversi game = new SquareReversi();
    BasicTextViewSquareGame view = new BasicTextViewSquareGame(game);
    game.startGame(8, 8);
    game.placeDisc(4, 2);
    game.placeDisc(5, 2);
    game.placeDisc(5, 3);
    game.placeDisc(5, 4);
    assertSame(game.getDiscColor(5, 3), Player.Player2.getColor());
    assertSame(game.getDiscColor(4, 4), Player.Player2.getColor());
    // System.out.println(view.toString());
  }

  @Test
  public void endInLineStopsChain() {
    // In a situation where you have _OXOX and you place an X on the _, it becomes XXXOX rather than
    // XXXXX
    SquareReversi game = new SquareReversi();
    BasicTextViewSquareGame view = new BasicTextViewSquareGame(game);
    game.startGame(8, 8);
    game.placeDisc(4, 2);
    game.placeDisc(5, 2);
    game.placeDisc(5, 3);
    game.pass();
    game.placeDisc(2, 5);
    game.placeDisc(5, 4);
    game.pass();
    game.placeDisc(2, 4);
    game.placeDisc(6, 1);
    assertEquals(Player.Player1.getColor(), game.getDiscColor(6, 1));
    assertEquals(Player.Player1.getColor(), game.getDiscColor(5, 2));
    assertEquals(Player.Player1.getColor(), game.getDiscColor(4, 3));
    assertEquals(Player.Player2.getColor(), game.getDiscColor(3, 4));
    assertEquals(Player.Player1.getColor(), game.getDiscColor(2, 5));
    // System.out.println(view.toString());
  }

  @Test
  public void playerTurnChanges() {
    SquareReversi game = new SquareReversi();
    game.startGame(8, 8);
    assertEquals(Player.Player1, game.getTurn());
    game.placeDisc(4, 2);
    assertEquals(Player.Player2, game.getTurn());
    game.pass();
    assertEquals(Player.Player1, game.getTurn());
  }

  @Test
  public void isDiscHereThrows() {
    SquareReversi game = new SquareReversi();
    game.startGame(8, 8);
    assertThrows(IllegalArgumentException.class, () -> game.isDiscHere(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> game.isDiscHere(0, -1));
    assertThrows(IllegalArgumentException.class, () -> game.isDiscHere(-1, -1));
    assertThrows(IllegalArgumentException.class, () -> game.isDiscHere(0, 10));
  }

  @Test
  public void isDiscHereWorks() {
    SquareReversi game = new SquareReversi();
    game.startGame(8, 8);
    assertTrue(game.isDiscHere(4, 3));
    game.placeDisc(4, 2);
    assertTrue(game.isDiscHere(4, 2));
  }

  @Test
  public void getDiscColorThrows() {
    SquareReversi game = new SquareReversi();
    game.startGame(8, 8);
    assertThrows(IllegalArgumentException.class, () -> game.getDiscColor(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> game.getDiscColor(0, -1));
    assertThrows(IllegalArgumentException.class, () -> game.getDiscColor(-1, -1));
    assertThrows(IllegalArgumentException.class, () -> game.getDiscColor(0, 10));
    assertThrows(IllegalStateException.class, () -> game.getDiscColor(0, 0));
    assertThrows(IllegalStateException.class, () -> game.getDiscColor(2, 4));
  }

  @Test
  public void getDiscColorWorks() {
    SquareReversi game = new SquareReversi();
    game.startGame(8, 8);
    assertEquals(Player.Player1.getColor(), game.getDiscColor(3, 3));
  }

  @Test
  public void getDiscColorChangesAfterDiscColorChanges() {
    SquareReversi game = new SquareReversi();
    game.startGame(8, 8);
    assertSame(Player.Player2.getColor(), game.getDiscColor(4, 3));
    game.placeDisc(4, 2);
    assertSame(Player.Player1.getColor(), game.getDiscColor(4, 3));
  }

  @Test
  public void isHexHereIsAlwaysTrue() {
    SquareReversi game = new SquareReversi();
    game.startGame(8, 8);
    for (int i = 0; i < game.getGridLength(); i++) {
      for (int j = 0; j < game.getGridHeight(); j++) {
        assertTrue(game.isHexHere(i, j));
      }
    }
  }

  @Test
  public void isHexHereThrows() {
    SquareReversi game = new SquareReversi();
    game.startGame(8, 8);
    assertThrows(IllegalArgumentException.class, () -> game.isHexHere(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> game.isHexHere(0, -1));
    assertThrows(IllegalArgumentException.class, () -> game.isHexHere(-1, -1));
    assertThrows(IllegalArgumentException.class, () -> game.isHexHere(0, 10));
  }

  @Test
  public void endGameStatusThrows() {
    SquareReversi game = new SquareReversi();
    assertThrows(IllegalStateException.class, () -> game.endOfGameStatus());
    game.startGame(8, 8);
    assertThrows(IllegalStateException.class, () -> game.endOfGameStatus());
    game.pass();
    assertThrows(IllegalStateException.class, () -> game.endOfGameStatus());
  }

  @Test
  public void noLegalMovesLeft() {
    SquareReversi game = new SquareReversi();
    game.startGame(2, 2);
    assertFalse(game.anyLegalMoves());
  }

  @Test
  public void legalMovesLeft() {
    SquareReversi game = new SquareReversi();
    game.startGame(8, 8);
    assertTrue(game.anyLegalMoves());
    game.placeDisc(4, 2);
    game.pass();
    assertTrue(game.anyLegalMoves());
  }

  @Test
  public void legalMoveWorks() {
    SquareReversi game = new SquareReversi();
    game.startGame(8, 8);
    BasicTextViewSquareGame view = new BasicTextViewSquareGame(game);
    assertTrue(game.legalMove(4, 2));
    assertTrue(game.legalMove(3, 5));
    assertFalse(game.legalMove(5, 2));
    game.placeDisc(4, 2);
    assertTrue(game.legalMove(5, 2));
    // System.out.println(view.toString());
  }
}
