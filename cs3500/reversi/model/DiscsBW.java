package cs3500.reversi.model;

/**
 * A class of discs which extend the discs interface specific to
 * just black and white pieces, which would be used in a standard
 * game.
 */
public class DiscsBW implements Discs {
  private Color color;

  /**
   * Constructor for one of these discs which are either black or white.
   */
  public DiscsBW(Color color) {
    this.color = color;
  }

  @Override
  public String toString() {
    if (this.color == Color.WHITE) {
      return "O";
    } else if (this.color == Color.BLACK) {
      return "X";
    } else {
      throw new IllegalStateException();
    }
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || this.getClass() != other.getClass()) {
      return false;
    }
    DiscsBW otherDisc = (DiscsBW) other;
    return this.color == otherDisc.color;
  }

  @Override
  public int hashCode() {
    return this.color.hashCode();
  }
}
