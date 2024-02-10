package cs3500.reversi.strategy;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.BasicReversiGame;
import cs3500.reversi.model.Color;
import cs3500.reversi.model.ReversiInterface;


/**
 * Basic tests for the extra credit strategies.
 */
public class ExtraCreditStrategyTests {

  @Test
  public void prioritizeCornersChoosesCorner() {
    ReversiInterface game = new BasicReversiGame();
    game.startGame(6, 7);
    ReversiStrategy strat = new PrioritizeCorners(false);
    game.placeDisc(2,5);
    game.placeDisc(4,4);
    game.placeDisc(3,1);
    game.placeDisc(2,4);
    game.placeDisc(1,5);
    game.placeDisc(strat.chooseMove(game, game.getTurn()).getX(), strat.chooseMove(game,
            game.getTurn()).getY());
    Assert.assertEquals(game.getDiscColor(2,6), Color.WHITE);
  }

  @Test
  public void NoNextToCornersWorks() {
    ReversiInterface game = new BasicReversiGame();
    game.startGame(8, 9);
    ReversiStrategy strat = new NoNextToCorners(false);
    game.placeDisc(3,2);
    game.placeDisc(3,3);
    game.placeDisc(strat.chooseMove(game, game.getTurn()).getX(), strat.chooseMove(game,
            game.getTurn()).getY());
    Assert.assertEquals(game.getDiscColor(2,4), Color.BLACK);

  }

  @Test
  public void MiniMaxCorrectly() {
    ReversiInterface game = new BasicReversiGame();
    game.startGame(8, 9);
    ReversiStrategy oppStrat = new AsManyCapturesAsPossible();
    ReversiStrategy strat = new MiniMax(oppStrat);
    game.placeDisc(3,2);
    game.placeDisc(3,3);
    game.placeDisc(2,3);
    game.placeDisc(2,2);
    game.placeDisc(strat.chooseMove(game, game.getTurn()).getX(), strat.chooseMove(game,
            game.getTurn()).getY());
    // Checks that minimax chose the one move to give White only 1 capture, other moves gave
    // white 2.
    Assert.assertEquals(game.getDiscColor(2,4),Color.BLACK);

  }
}
