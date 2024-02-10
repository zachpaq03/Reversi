package cs3500.reversi.view;


import java.awt.Graphics2D;

import cs3500.reversi.controller.Features;

/**
 * An interface for a Panel. Panels are the components of a GUI that actually display components
 * of the game.
 */
public interface Panel {

  /**
   * A method to draw the components for the panel.
   *
   * @param g2d The Graphics2D object where the components are drawn.
   */
  public void draw(Graphics2D g2d);

  /**
   * Add features for when valid keys are pressed that will use call backs to update the model and
   * view.
   *
   * @param features The object that will handle the callbacks to make things happen.
   */
  public void addFeature(Features features);

  /**
   * A method for a pop-up to appear for the user indicating that a move that was made was illegal.
   *
   * @param message The message that will be printed on the popup.
   */
  public void illegalMovePopUp(String message);

  /**
   * Given coordinates, checks if there is a hexagon on the board that contains this coordinate.
   * Updates the currently clicked hexagon and clicked index fields.
   *
   * @param x the x coordinate that was clicked.
   * @param y the y coordinate that was clicked.
   */
  public void handleClick(int x, int y);
}
