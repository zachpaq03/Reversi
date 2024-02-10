package cs3500.reversi.strategy;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import cs3500.reversi.model.Color;
import cs3500.reversi.model.ReversiInterface;
import cs3500.reversi.model.SquareReversi;

import static org.junit.Assert.assertFalse;

/**
 * Tests for the AsManyCapturesAsPossible strategy on a square board.
 */
public class AsManyCapturesSquareReversiTests {

  @Test
  public void testStrategyCompletesGame() {
    ReversiInterface game = new SquareReversi();
    game.startGame(8, 8);
    AsManyCapturesAsPossible strat = new AsManyCapturesAsPossible();
    while (!game.isGameOver()) {
      try {
        strat.chooseMove(game, game.getTurn());
        game.placeDisc(strat.chooseMove(game, game.getTurn()).getX(), strat.chooseMove(game,
                game.getTurn()).getY());
      } catch (IllegalStateException ignore) {
        game.pass();
      }
    }
    Assert.assertTrue(game.isGameOver());

    ReversiInterface game2 = new SquareReversi();
    game2.startGame(20, 20);
    AsManyCapturesAsPossible strat2 = new AsManyCapturesAsPossible();
    while (!game2.isGameOver()) {
      try {
        strat2.chooseMove(game2, game2.getTurn());
        game2.placeDisc(strat2.chooseMove(game2, game2.getTurn()).getX(), strat2.chooseMove(game2,
                game2.getTurn()).getY());
      } catch (IllegalStateException ignore) {
        game2.pass();
      }
    }
    Assert.assertTrue(game2.isGameOver());

    ReversiInterface game3 = new SquareReversi();
    game3.startGame(4, 4);
    AsManyCapturesAsPossible strat3 = new AsManyCapturesAsPossible();
    while (!game3.isGameOver()) {
      try {
        strat3.chooseMove(game3, game3.getTurn());
        game3.placeDisc(strat3.chooseMove(game3, game3.getTurn()).getX(), strat3.chooseMove(game3,
                game3.getTurn()).getY());
      } catch (IllegalStateException ignore) {
        game3.pass();
      }
    }
    Assert.assertTrue(game3.isGameOver());
  }

  @Test
  public void testStrategyTieBreaker() {
    ReversiInterface game = new SquareReversi();
    game.startGame(8, 8);
    AsManyCapturesAsPossible strat = new AsManyCapturesAsPossible();
    game.placeDisc(strat.chooseMove(game, game.getTurn()).getX(), strat.chooseMove(game,
            game.getTurn()).getY());
    Assert.assertEquals(game.getDiscColor(4, 2), Color.BLACK);
    assertFalse(game.isDiscHere(2, 4));
  }

  @Test
  public void testGetsMostScore() {
    ReversiInterface game = new SquareReversi();
    game.startGame(8, 8);
    AsManyCapturesAsPossible strat = new AsManyCapturesAsPossible();
    game.placeDisc(4, 2);
    game.placeDisc(3, 2);
    game.placeDisc(strat.chooseMove(game, game.getTurn()).getX(), strat.chooseMove(game,
            game.getTurn()).getY());
    List<Integer> scores = game.getScore();
    Assert.assertEquals(6, scores.get(0).intValue());
  }
}
