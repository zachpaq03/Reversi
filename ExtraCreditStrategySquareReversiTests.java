package cs3500.reversi.strategy;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.Color;
import cs3500.reversi.model.ReversiInterface;
import cs3500.reversi.model.SquareReversi;

/**
 * Tests for the extra credit strategies on a Square Reversi board.
 */
public class ExtraCreditStrategySquareReversiTests {

  @Test
  public void prioritizeCornersChoosesCorner() {
    ReversiInterface game = new SquareReversi();
    game.startGame(4, 4);
    ReversiStrategy strat = new PrioritizeCorners(true);
    game.placeDisc(2,0);
    game.placeDisc(strat.chooseMove(game, game.getTurn()).getX(), strat.chooseMove(game,
            game.getTurn()).getY());
    Assert.assertEquals(game.getDiscColor(3,0), Color.WHITE);
  }

  @Test
  public void NoNextToCornersWorks() {
    ReversiInterface game = new SquareReversi();
    game.startGame(6, 6);
    ReversiStrategy strat = new NoNextToCorners(true);
    game.placeDisc(3,1);
    game.placeDisc(2,1);
    game.placeDisc(strat.chooseMove(game, game.getTurn()).getX(), strat.chooseMove(game,
            game.getTurn()).getY());
    Assert.assertEquals(game.getDiscColor(1,0), Color.BLACK);

  }

  @Test
  public void MiniMaxCorrectly() {
    ReversiInterface game = new SquareReversi();
    game.startGame(8, 8);
    ReversiStrategy oppStrat = new AsManyCapturesAsPossible();
    ReversiStrategy strat = new MiniMax(oppStrat);
    game.placeDisc(4,2);
    game.placeDisc(strat.chooseMove(game, game.getTurn()).getX(), strat.chooseMove(game,
            game.getTurn()).getY());
    Assert.assertEquals(game.getDiscColor(5,2),Color.WHITE);

  }

}
