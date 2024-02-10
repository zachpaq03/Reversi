package cs3500.reversi.view;

import java.awt.geom.Path2D;

/**
 * A class for an image of a hexagon. It is a Path2D object that draws a hexagon with the given
 * coordinates and side length.
 */
public class HexagonImage extends Path2D.Double {
  double xCord;
  double yCord;
  int col;
  int row;

  /**
   * The constructor for an image of a hexagon.
   *
   * @param centerX The x-coordinate of the center of the hexagon.
   * @param centerY The y-coordinate of the center of the hexagon.
   * @param sideLength The side length of the hexagon.
   * @param col The column the hexagon is in on the board.
   * @param row The row the hexagon is in on the board.
   */
  public HexagonImage(double centerX, double centerY, double sideLength, int col, int row) {
    double angle = Math.PI / 3;
    double radius = sideLength;
    double x;
    double y;
    this.xCord = centerX;
    this.yCord = centerY;
    this.col = col;
    this.row = row;

    moveTo(centerX, centerY + radius);

    for (int i = 1; i < 6; i++) {
      x = centerX + radius * Math.cos((i * angle) + (Math.PI / 2));
      y = centerY + radius * Math.sin((i * angle) + (Math.PI / 2));
      lineTo(x, y);
    }

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
    HexagonImage otherHex = (HexagonImage) other;
    return (this.xCord == otherHex.xCord && this.yCord == otherHex.yCord && this.col == otherHex.col
            && this.row == otherHex.row);
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
