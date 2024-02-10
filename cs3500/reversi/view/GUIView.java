package cs3500.reversi.view;

import cs3500.reversi.controller.Features;

/**
 * An interface to a GUI, a view that is graphical.
 */
public interface GUIView {

  /**
   * A method that will add a click listener, so that actions will happen when a certain location
   * is clicked.
   */
  public void addClickListener();

  /**
   * Refresh the view to update the view after there are changes to the model or part of the screen
   * which is clickable was clicked.
   */
  public void refresh();

  /**
   * Make the view visible to the user.
   */
  public void setVisible();

  /**
   * Add a features type for call back when certain keys are pressed. This method will pass on the
   * call to the method with the same name in the panel field of the GUI.
   *
   * @param features The object that is there for callbacks when a key is pressed.
   */
  public void addFeatures(Features features);

  /**
   * Show a popup when the user tries to make a move they cannot legally make based on the rules of
   * the game. This method will pass on the call to the method with the same name in the panel
   * field of the GUI.
   *
   * @param message The message that will be shown in the pop up.
   */
  public void illegalMovePopUp(String message);
}
