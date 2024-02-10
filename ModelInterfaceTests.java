package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import cs3500.reversi.model.GameState;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReversiInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Testing that the specifications from the interface for a Reversi game are upheld in the model.
 */
public abstract class ModelInterfaceTests {
  protected abstract ReversiInterface factory();

  @Test
  public void cantCallMethodsWithoutStartingGame() {
    ReversiInterface game = factory();
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
    ReversiInterface game = factory();
    assertThrows(IllegalArgumentException.class, () -> game.startGame(1, 3));
    assertThrows(IllegalArgumentException.class, () -> game.startGame(5, 1));
    assertThrows(IllegalArgumentException.class, () -> game.startGame(21, 11));
    assertThrows(IllegalArgumentException.class, () -> game.startGame(2, 5));
    assertThrows(IllegalArgumentException.class, () -> game.startGame(6, 4));
    assertThrows(IllegalArgumentException.class, () -> game.startGame(-1, -5));
    //Test starting a game when one is already going on
    game.startGame(2, 3);
    assertThrows(IllegalStateException.class, () -> game.startGame(2, 3));
  }


  @Test
  public void testPlaceDiscLegalOddRowGame() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    //   game.placeDisc(0, 3);
    //   game.placeDisc(3, 3);
    //   game.placeDisc(2, 6);
    game.placeDisc(2, 2);
    //   game.placeDisc(3, 5);
    //   game.placeDisc(0, 5);
    assertEquals(Player.Player1.getColor(), game.getDiscColor(1, 3));
    game.placeDisc(1, 1);
    assertEquals(Player.Player2.getColor(), game.getDiscColor(2, 2));
    assertEquals(Player.Player2.getColor(), game.getDiscColor(2, 3));
  }

  @Test
  public void testPlaceDiscLegalEvenRowGame() {
    ReversiInterface game = factory();
    game.startGame(4, 7);
    game.placeDisc(1, 1);
    game.placeDisc(3, 2);
    assertEquals(game.getScore().get(0), game.getScore().get(1));
  }

  @Test
  public void throwsForIllegalMoveOddRowGame() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    assertThrows(IllegalStateException.class, () -> game.placeDisc(2, 0));
    assertThrows(IllegalStateException.class, () -> game.placeDisc(2, 4));
  }

  @Test
  public void throwsIAEPlaceDisc() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    assertThrows(IllegalArgumentException.class, () -> game.placeDisc(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> game.placeDisc(0, 0));
    assertThrows(IllegalArgumentException.class, () -> game.placeDisc(100, 200));
  }

  @Test
  public void gameEndAfterPasses() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    game.placeDisc(2, 2);
    game.placeDisc(1, 1);
    game.pass();
    game.placeDisc(2, 6);
    game.pass();
    assertFalse(game.isGameOver());
    game.pass();
    Assert.assertTrue(game.isGameOver());
  }

  @Test
  public void testingCorrectWinner() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    game.placeDisc(2, 2);
    game.placeDisc(1, 1);
    game.placeDisc(2, 1);
    game.placeDisc(0, 3);
    game.placeDisc(0, 5);
    game.placeDisc(2, 6);
    game.placeDisc(3, 5);
    game.pass();
    game.pass();
    List<Integer> scores = game.getScore();
    Assert.assertTrue(scores.get(0) > scores.get(1));
    Assert.assertSame(game.endOfGameStatus(), GameState.PLAYER1WINS);
  }

  @Test(expected = IllegalStateException.class)
  public void testWrongPlayerPlace() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    game.placeDisc(2, 2);
    game.placeDisc(0, 5);
  }

  @Test(expected = IllegalStateException.class)
  public void testNoGapInLine() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    game.placeDisc(2, 2);
    game.placeDisc(1, 7);
  }

  @Test(expected = IllegalStateException.class)
  public void testCantMakePair() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    game.placeDisc(0, 4);
  }

  @Test
  public void testMoveMakesDoubleLine() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    game.placeDisc(2,2);
    game.placeDisc(1,1);
    game.placeDisc(3,3);
    assertEquals(Player.Player1.getColor(), game.getDiscColor(2, 3));
    assertEquals(Player.Player1.getColor(), game.getDiscColor(3, 4));
  }

  @Test
  public void PlaceDiscExceptions() {
    ReversiInterface game = factory();
    assertThrows(IllegalStateException.class, () -> game.placeDisc(2, 2));
    game.startGame(5, 9);
    assertThrows(IllegalArgumentException.class, () -> game.placeDisc(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> game.placeDisc(8, 0));
    assertThrows(IllegalArgumentException.class, () -> game.placeDisc(10, 24));
    assertThrows(IllegalArgumentException.class, () -> game.placeDisc(1, 0));
    assertThrows(IllegalStateException.class, () -> game.placeDisc(3, 4));
    assertThrows(IllegalStateException.class, () -> game.placeDisc(4, 4));
  }

  @Test
  public void testMixedLine() {
    ReversiInterface game = factory();
    game.startGame(9, 9);
    game.placeDisc(4,2);
    game.placeDisc(2,3);
    game.placeDisc(5,3);
    game.placeDisc(5,5);
    game.pass();
    game.placeDisc(3,1);
    game.placeDisc(1,3);
    assertEquals(Player.Player1.getColor(), game.getDiscColor(1, 3));
    assertEquals(Player.Player1.getColor(), game.getDiscColor(2, 3));
    assertEquals(Player.Player1.getColor(), game.getDiscColor(3, 3));
    assertEquals(Player.Player2.getColor(), game.getDiscColor(4, 3));
    assertEquals(Player.Player1.getColor(), game.getDiscColor(5, 3));
  }

  @Test
  public void getScoreUsesAbsoluteValue() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    game.pass();
    game.placeDisc(0, 3);
    assertEquals(game.getScore(), Arrays.asList(2, 5, 3));
  }

  @Test
  public void getScoreEven() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    assertEquals(game.getScore(), Arrays.asList(3, 3, 0));
  }

  @Test
  public void playerTurnChanges() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    assertEquals(Player.Player1, game.getTurn());
    game.placeDisc(2, 2);
    assertEquals(Player.Player2, game.getTurn());
    game.pass();
    assertEquals(Player.Player1, game.getTurn());
  }

  @Test
  public void isDiscHereThrows() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    assertThrows(IllegalArgumentException.class, () -> game.isDiscHere(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> game.isDiscHere(0, -1));
    assertThrows(IllegalArgumentException.class, () -> game.isDiscHere(-1, -1));
    assertThrows(IllegalArgumentException.class, () -> game.isDiscHere(6, 0));
    assertThrows(IllegalArgumentException.class, () -> game.isDiscHere(0, 10));
    assertThrows(IllegalArgumentException.class, () -> game.isDiscHere(0, 0));
  }

  @Test
  public void isDiscHereWorks() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    assertTrue(game.isDiscHere(1, 4));
    game.placeDisc(3, 3);
    assertTrue(game.isDiscHere(3, 3));
  }

  @Test
  public void getDiscColorThrows() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    assertThrows(IllegalArgumentException.class, () -> game.getDiscColor(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> game.getDiscColor(0, -1));
    assertThrows(IllegalArgumentException.class, () -> game.getDiscColor(-1, -1));
    assertThrows(IllegalArgumentException.class, () -> game.getDiscColor(6, 0));
    assertThrows(IllegalArgumentException.class, () -> game.getDiscColor(0, 10));
    assertThrows(IllegalArgumentException.class, () -> game.getDiscColor(0, 0));
    assertThrows(IllegalStateException.class, () -> game.getDiscColor(2, 4));
  }

  @Test
  public void getDiscColorWorks() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    assertEquals(Player.Player1.getColor(), game.getDiscColor(1, 4));
  }

  @Test
  public void getDiscColorWorksAfterDiscColorChanges() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    assertEquals(Player.Player1.getColor(), game.getDiscColor(1, 4));
    game.pass();
    game.placeDisc(0, 5);
    assertEquals(Player.Player2.getColor(), game.getDiscColor(1, 4));
  }

  @Test
  public void isHexHereThrows() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    assertThrows(IllegalArgumentException.class, () -> game.isHexHere(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> game.isHexHere(0, -1));
    assertThrows(IllegalArgumentException.class, () -> game.isHexHere(-1, -1));
    assertThrows(IllegalArgumentException.class, () -> game.isHexHere(6, 0));
    assertThrows(IllegalArgumentException.class, () -> game.isHexHere(0, 10));
  }

  @Test
  public void isHexHereWorksDiscAndNoDisc() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    assertTrue(game.isHexHere(1, 4));
    assertTrue(game.isHexHere(2, 4));
    assertFalse(game.isHexHere(0, 0));
  }

  @Test
  public void endGameStatusThrows() {
    ReversiInterface game = factory();
    assertThrows(IllegalStateException.class, () -> game.endOfGameStatus());
    game.startGame(5, 9);
    assertThrows(IllegalStateException.class, () -> game.endOfGameStatus());
    game.pass();
    assertThrows(IllegalStateException.class, () -> game.endOfGameStatus());
  }

  @Test
  public void noLegalMovesLeft() {
    ReversiInterface game = factory();
    game.startGame(2, 3);
    assertFalse(game.anyLegalMoves());
  }

  @Test
  public void legalMovesLeft() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    assertTrue(game.anyLegalMoves());
    game.placeDisc(3, 3);
    game.pass();
    assertTrue(game.anyLegalMoves());
  }

  @Test
  public void legalMoveWorks() {
    ReversiInterface game = factory();
    game.startGame(5, 9);
    assertFalse(game.legalMove(0, 2));
    assertFalse(game.legalMove(1, 4));
    assertTrue(game.legalMove(0, 3));
  }

}
