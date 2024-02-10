package cs3500.reversi.view;

/**
 * An interface for a textual view of the game.
 */
public interface TextualView {
  /**
   * A method that renders that state of the game as a string.
   *
   * @return The state of the game as a string.
   * @throws IllegalStateException if it cannot render the game.
   */
  public String render();
}
