package cs3500.reversi.player;

import cs3500.reversi.controller.Features;
import cs3500.reversi.model.Color;
import cs3500.reversi.model.Player;

// This is the interface for a player.

/**
 * An Interface for a player that is open for both human players and AI.
 */
public interface PlayerInterface {
  /**
   * A method to find out if the player is Player1 or Player 2.
   *
   * @return which Player this player is in the game.
   */
  public Player getWhichPlayer();

  /**
   * A method to find out which discs belong to the player.
   *
   * @return the disc color that are this players' disc.
   */
  public Color getColor();

  /**
   * A method for a player to get their current score.
   *
   * @return the player's score.
   */
  public int getScore();

  /**
   * A method that tells the player if they are winning or not.
   *
   * @return true if this player is winning, false if they are losing or tying.
   */
  public boolean isWinning();

  public void addFeatures(Features features);

  public void makeMove();
}

