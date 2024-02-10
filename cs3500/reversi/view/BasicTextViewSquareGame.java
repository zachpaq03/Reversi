package cs3500.reversi.view;

import java.util.Objects;

import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReadOnlyReversiInterface;

/**
 * A basic textual view for a game or Reversi on a square board.
 */
public class BasicTextViewSquareGame implements TextualView {
  private ReadOnlyReversiInterface game;

  /**
   * A constructor for this basic textual view.
   *
   * @param game the model of the game being played.
   */
  public BasicTextViewSquareGame(ReadOnlyReversiInterface game) {
    this.game = Objects.requireNonNull(game);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int y = 0; y < game.getGridHeight(); y++) {
      for (int x = 0; x < game.getGridLength(); x++) {
        sb.append(whatToPrint(x, y));
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  String whatToPrint(int x, int y) {
    if (!game.isDiscHere(x, y)) {
      return "_";
    } else if (game.getDiscColor(x, y) == Player.Player1.getColor()) {
      return "X";
    } else {
      return "O";
    }
  }

  @Override
  public String render() {
    return "";
  }
}
