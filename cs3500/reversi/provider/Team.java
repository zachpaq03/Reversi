package cs3500.reversi.provider;

import java.awt.Color;

/**
 * Represents a team in the game of Reversi.
 */
public enum Team {
  BLACK(Color.BLACK),
  WHITE(Color.WHITE),
  UNCLAIMED(Color.LIGHT_GRAY);

  private Color color;

  Team(Color color) {
    this.color = color;
  }

  public Color getColor() {
    return this.color;
  }
}
