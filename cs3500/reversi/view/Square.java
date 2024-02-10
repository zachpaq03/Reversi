package cs3500.reversi.view;

import java.awt.geom.Path2D;

/**
 * A class that represents a square on the board of a Reversi game.
 */
public class Square extends Path2D.Double {
  int col;
  int row;
  double xCord;
  double yCord;

  /**
   * A constructor for the square.
   *
   * @param centerX    The x-coordinate where the center of the square should be.
   * @param centerY    The y-coordinate where the center of the square should be.
   * @param sideLength The length of s side of the square.
   * @param col        Which column in the model this square is in.
   * @param row        Which row in the model this square is in.
   */
  public Square(double centerX, double centerY, double sideLength, int col, int row) {
    this.col = col;
    this.row = row;
    this.xCord = centerX;
    this.yCord = centerY;

    double radius = sideLength / 2.0;

    moveTo(centerX - radius, centerY - radius);
    lineTo(centerX + radius, centerY - radius);
    lineTo(centerX + radius, centerY + radius);
    lineTo(centerX - radius, centerY + radius);
    closePath();
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other == null || this.getClass() != other.getClass()) {
      return false;
    }
    Square otherSquare = (Square) other;
    return (this.xCord == otherSquare.xCord && this.yCord == otherSquare.yCord
            && this.col == otherSquare.col && this.row == otherSquare.row);
  }

  @Override
  public int hashCode() {
    int result = 31;
    if (this.row != 0) {
      result *= this.row;
    }
    if (this.col != 0) {
      result *= this.col;
    }
    if (this.xCord != 0) {
      result *= this.xCord;
    }
    if (this.yCord != 0) {
      result *= this.yCord;
    }
    return result;
  }
}

