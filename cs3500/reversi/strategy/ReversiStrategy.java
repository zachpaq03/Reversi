package cs3500.reversi.strategy;

import cs3500.reversi.model.Coord;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReversiInterface;

/**
 * An interface for strategies of playing the game Reversi.
 */
public interface ReversiStrategy {
  /**
   * Returns the coordinates that should be played to use whichever strategy they represent.
   *
   * @param model The model of the game where the strategy will be used. The method creates a deep
   *              copy so that the model can be mutated without affecting the actual board.
   * @param who   The player whose turn it is.
   * @return The coordinates to the move that fulfills the intended strategy.
   * @throws IllegalStateException if there are no moves available.
   */
  public Coord chooseMove(ReversiInterface model, Player who);

  /**
   * A given move will be assigned points, the higher the points the better and the assignment
   * of the points differs depending on the strategy being used.
   *
   * @param after  The amount of points for the player who makes the move after making the move.
   * @param before The amount of points for the player who makes the move before making the move.
   * @param cols   The column of the potential move.
   * @param rows   The row of the potential move.
   * @param model  The model that this move is being made on.
   * @return The amount of points for this move.
   */
  public int assignPoints(int after, int before, int cols, int rows, ReversiInterface model);
}
