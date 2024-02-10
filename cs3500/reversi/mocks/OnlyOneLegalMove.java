package cs3500.reversi.mocks;

import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReversiAbstract;
import cs3500.reversi.model.ReversiInterface;

/**
 * A mock class of the model where only moves at a specific point are legal.
 */
public class OnlyOneLegalMove extends ReversiAbstract implements ReversiInterface {
  public OnlyOneLegalMove() {
    super();
  }

  public OnlyOneLegalMove(boolean[][] grid, Player[][] playerGrid, Player[] players, Player turn,
                          int passCount) {
    super(grid, playerGrid, players, turn, passCount);
  }

  @Override
  public ReversiInterface getMutableCopy() {
    return new OnlyOneLegalMove(this.getGrid(), this.getPlayerGrid(), this.getPlayers(),
            this.getTurn(), this.getPassCount());
  }

  @Override
  public boolean legalMove(int x, int y) {
    return x == 5 && y == 7;
  }
}
