package cs3500.reversi.model;

/**
 * An enum class for a player. Each player is assigned a specific
 * color that represents their disc color.
 */
public enum Player {
  Player1(Color.BLACK),
  Player2(Color.WHITE);
  private Color color;

  Player(Color color) {
    this.color = color;
  }

  /**
   * Returns the color associated with the specific player.
   * @return the color that represents the player.
   */
  public Color getColor() {
    return this.color;
  }
}
