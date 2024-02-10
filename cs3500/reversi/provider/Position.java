package cs3500.reversi.provider;

import java.util.Objects;

/**
 * Represents a vector position on a hexagonal grid. Positions each have an x and y coordinate,
 * the x is horizontal, and the y is an angled axis.
 */
public class Position {

  private final int x;
  private final int y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Position position = (Position) o;
    return this.x == position.getX() && this.y == position.getY();
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "(" + Integer.toString(x) + ", " + Integer.toString(y) + ")";
  }
}
