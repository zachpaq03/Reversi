package cs3500.reversi.model;

/**
 * A class for coordinates, the return type for strategies. It represents where on the board the
 * best place is to place a disc.
 */
public class Coord {
  private int col;
  private int row;

  /**
   * A constructor for a coordinate.
   *
   * @param col the x coordinate.
   * @param row the y coordinate.
   */
  public Coord(int col, int row) {
    this.col = col;
    this.row = row;
  }

  /**
   * A getter for the private column field, which represents the x-coordinate.
   *
   * @return the x coordinate.
   */
  public int getX() {
    return this.col;
  }

  /**
   * A getter for the private row field, which represents the y-coordinate.
   *
   * @return the y coordinate.
   */
  public int getY() {
    return this.row;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.col);
    sb.append(", ");
    sb.append(this.row);
    return sb.toString();
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || this.getClass() != other.getClass()) {
      return false;
    }
    Coord otherCoord = (Coord) other;
    return this.col == otherCoord.col && this.row == otherCoord.row;
  }

  @Override
  public int hashCode() {
    int result = 31;
    if (this.col != 0) {
      result *= this.col;
    }
    if (this.row != 0) {
      result *= this.row;
    }
    return result;
  }
}
