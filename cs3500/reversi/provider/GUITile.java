package cs3500.reversi.provider;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * Represents a graphical tile on the Reversi game board.
 */
public class GUITile {
  private final int x;
  private final int y;
  private Color color;
  private Team controlledBy;
  private final int side;
  private final Polygon hexagon;
  private final int coordY;
  private final int coordX;
  private boolean selected;

  /**
   * Constructs a GUITile with the specified parameters.
   *
   * @param color        The color of the tile.
   * @param x            The x-coordinate of the tile.
   * @param y            The y-coordinate of the tile.
   * @param sideLength   The side length of the hexagon representing the tile.
   * @param coordX       The x-coordinate on the model board.
   * @param coordY       The y-coordinate on the model board.
   * @param controlledBy The team that controls the tile.
   */
  public GUITile(Color color, int x, int y, int sideLength, int coordX, int coordY,
                 Team controlledBy) {
    this.color = Objects.requireNonNull(color);
    this.selected = false;
    this.side = sideLength;

    int[] xPoints = new int[6];
    int[] yPoints = new int[6];

    for (int i = 0; i < 6; i++) {
      yPoints[i] = y + (int) (sideLength * Math.cos(i * Math.PI / 3))
              - (int) (coordY * sideLength * .5 + sideLength) + sideLength;
      xPoints[i] = x + (int) (sideLength * Math.sin(i * Math.PI / 3))
              + (int) (coordY * sideLength / 2 * Math.tan(Math.toRadians(60)));
    }

    this.x = x;
    this.y = y;
    this.coordX = coordX;
    this.coordY = coordY;
    this.hexagon = new Polygon(xPoints, yPoints, 6);
    this.controlledBy = controlledBy;
  }

  /**
   * Updates the team that controls the tile.
   *
   * @param team The new controlling team.
   */
  public void update(Team team) {
    this.controlledBy = team;
  }

  /**
   * Gets the x-coordinate in the GUI of the tile.
   *
   * @return The x-coordinate.
   */
  protected int getX() {
    return this.x;
  }

  /**
   * Gets the y-coordinate in the GUI of the tile.
   *
   * @return The y-coordinate.
   */
  protected int getY() {
    return this.y;
  }

  /**
   * Gets the x-coordinate on the model board.
   *
   * @return The x-coordinate on the model board.
   */
  protected int getXCoord() {
    return this.coordX;
  }

  /**
   * Gets the y-coordinate on the model board.
   *
   * @return The y-coordinate on the model board.
   */
  protected int getYCoord() {
    return this.coordY;
  }

  /**
   * Sets the color of the tile.
   *
   * @param color The new color of the tile.
   */
  protected void setColor(Color color) {
    this.color = Objects.requireNonNull(color);
  }

  /**
   * Draws the tile on the specified graphics object.
   *
   * @param g The Graphics object to draw on.
   */
  protected void draw(Graphics g) {
    g.setColor(this.color);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.fillPolygon(this.hexagon);

    Stroke outlineStroke = new BasicStroke(4.0f);
    g2d.setStroke(outlineStroke);
    g2d.setColor(Color.BLACK);
    g2d.drawPolygon(this.hexagon);

    this.drawReversiPiece(g2d, this.hexagon);
  }

  /**
   * Draws the Reversi piece on the tile.
   *
   * @param g2d The Graphics2D object to draw on.
   * @param hex The hexagon representing the tile.
   */
  private void drawReversiPiece(Graphics2D g2d, Polygon hex) {
    if (controlledBy != Team.UNCLAIMED) {
      g2d.setColor(controlledBy.getColor());
      g2d.fillOval((int) (hex.xpoints[4] + (Math.sqrt(3) * side / 2)
              - (side / 2)), hex.ypoints[4], side, side);
    }
  }

  /**
   * Checks if a point is contained within the tile.
   *
   * @param point The point to check.
   * @return True if the point is inside the tile, false otherwise.
   */
  protected boolean containsPoint(Point2D point) {
    return this.hexagon.contains(point);
  }

  /**
   * Toggles the selection state of the tile.
   */
  protected void toggleSelection() {
    this.selected = !this.selected;
  }

  /**
   * Checks if the tile is selected.
   *
   * @return True if the tile is selected, false otherwise.
   */
  protected boolean isSelected() {
    return this.selected;
  }

  /**
   * Sets the team that controls the tile.
   *
   * @param team The new controlling team.
   */
  protected void setTeam(Team team) {
    this.controlledBy = Objects.requireNonNull(team);
  }
}