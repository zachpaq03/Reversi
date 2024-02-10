package cs3500.reversi.strategy;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import cs3500.reversi.model.BasicReversiGame;
import cs3500.reversi.model.Color;
import cs3500.reversi.model.ReversiInterface;

/**
 * Tests for the strategy that picks the move that will get the most discs to flip.
 */
public class AsManyCapturesTests {

  @Test
  public void testStrategyCompletesGame() {
    ReversiInterface game = new BasicReversiGame();
    game.startGame(11, 11);
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
  }

  @Test
  public void testStrategyTieBreaker() {
    ReversiInterface game = new BasicReversiGame();
    game.startGame(11, 11);
    AsManyCapturesAsPossible strat = new AsManyCapturesAsPossible();
    game.placeDisc(strat.chooseMove(game, game.getTurn()).getX(), strat.chooseMove(game,
            game.getTurn()).getY());
    Assert.assertEquals(game.getDiscColor(3, 4), Color.BLACK);
  }

  @Test
  public void testGetsMostScore() {
    ReversiInterface game = new BasicReversiGame();
    game.startGame(11, 11);
    AsManyCapturesAsPossible strat = new AsManyCapturesAsPossible();
    game.placeDisc(3, 4);
    game.placeDisc(strat.chooseMove(game, game.getTurn()).getX(), strat.chooseMove(game,
            game.getTurn()).getY());
    List<Integer> scores = game.getScore();
    Assert.assertEquals(5, scores.get(1).intValue());
  }

  @Test
  public void testEvenLengthBoard() {
    ReversiInterface game = new BasicReversiGame();
    game.startGame(8, 9);
    AsManyCapturesAsPossible strat = new AsManyCapturesAsPossible();
    game.placeDisc(strat.chooseMove(game, game.getTurn()).getX(), strat.chooseMove(game,
            game.getTurn()).getY());
    //test tiebreaker
    Assert.assertEquals(game.getDiscColor(3, 2), Color.BLACK);
    while (!game.isGameOver()) {
      try {
        strat.chooseMove(game, game.getTurn());
        game.placeDisc(strat.chooseMove(game, game.getTurn()).getX(), strat.chooseMove(game,
                game.getTurn()).getY());
      } catch (IllegalStateException ignore) {
        game.pass();
      }
    }
    //test game ends
    Assert.assertTrue(game.isGameOver());


  }

  @Test
  public void testBoardwithOneCorner() {
    ReversiInterface game = new BasicReversiGame();
    game.startGame(8, 15);
    AsManyCapturesAsPossible strat = new AsManyCapturesAsPossible();
    game.placeDisc(strat.chooseMove(game, game.getTurn()).getX(), strat.chooseMove(game,
            game.getTurn()).getY());
    //test tiebreaker
    Assert.assertEquals(game.getDiscColor(3, 5), Color.BLACK);
    while (!game.isGameOver()) {
      try {
        strat.chooseMove(game, game.getTurn());
        game.placeDisc(strat.chooseMove(game, game.getTurn()).getX(), strat.chooseMove(game,
                game.getTurn()).getY());
      } catch (IllegalStateException ignore) {
        game.pass();
      }
    }
    //test game ends
    Assert.assertTrue(game.isGameOver());

  }
}
