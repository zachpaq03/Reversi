package cs3500.reversi.view;

import java.util.Objects;

import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReadOnlyReversiInterface;
import cs3500.reversi.model.ReversiInterface;

/**
 * A class that renders a textual view of the Reversi game.
 */
public class BasicTextualView implements TextualView {
  private ReadOnlyReversiInterface game;
  private Appendable out;


  public BasicTextualView(ReadOnlyReversiInterface game) {
    this.game = Objects.requireNonNull(game);
    this.out = System.out;
  }

  public BasicTextualView(ReversiInterface game, Appendable out) {
    this.game = Objects.requireNonNull(game);
    this.out = out;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < game.getGridHeight(); i++) {
      if (getRowOffset(game.getGridLength(), game.getGridHeight(), i)) {
        sb.append(" ");
      }
      for (int j = 0; j < game.getGridLength(); j++) {
        sb.append(whatToPrint(j, i));
      }
      int finalIndex = sb.length() - 1;
      sb.deleteCharAt(finalIndex);
      if (i < game.getGridHeight() - 1) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }

  // The setup of the model makes it so that rows with odd amounts of hexagons are offset to the
  // left, and rows with an even amount are offset to the right. This method figures out which way
  // the row should be offset.
  boolean getRowOffset(int gridLength, int gridHeight, int i) {
    int offset = Math.abs(((gridHeight - 1) / 2) - i);
    int oddOrEven = Math.abs(gridLength - offset);
    return oddOrEven % 2 == 0;
  }

  // This method figures out what should be printed, if the given coordinate is off the hexagon
  // board it prints nothing, if it is on the board and there is no disc is prints -, and if there
  // is a disc it prints which disc it is, which in the textual view is either X or O.
  String whatToPrint(int x, int y) {
    if (!game.isHexHere(x, y)) {
      return "  ";
    }
    if (!game.isDiscHere(x, y)) {
      return "- ";
    }
    if (game.getDiscColor(x, y) == Player.Player1.getColor()) {
      return "X ";
    } else {
      return "O ";
    }
  }

  // Not needed for this part of the assignment.
  @Override
  public String render() {
    return null;
  }
}
