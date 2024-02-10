package cs3500.reversi.player;

import cs3500.reversi.controller.Features;
import cs3500.reversi.model.Color;
import cs3500.reversi.model.Player;

/**
 * A class for a human player for the game of Reversi. Has multiple empty methods because the
 * listener exists in the view rather than the player class.
 */
public class HumanPlayer implements PlayerInterface {
  private Player player;

  public HumanPlayer(Player which) {
    this.player = which;
  }

  @Override
  public Player getWhichPlayer() {
    return this.player;
  }

  @Override
  public Color getColor() {
    return this.player.getColor();
  }

  @Override
  public int getScore() {
    // User can see score at all times on the view.
    return 0;
  }

  @Override
  public boolean isWinning() {
    // User can see score at all times on the view.
    return false;
  }

  @Override
  public void addFeatures(Features features) {
    // Not applicable for human players.
  }

  @Override
  public void makeMove() {
    // Not applicable for human players.
  }
}
