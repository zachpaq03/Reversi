package cs3500.reversi.provider;

/**
 * The ReversiView interface represents the view for the Reversi game.
 * It defines methods for adding feature listeners, displaying the view,
 * updating the view, and sending error messages.
 */
public interface ReversiView {

  /**
   * Adds a Features listener to the ReversiView.
   *
   * @param features The Features listener to be added.
   */
  void addFeatureListener(Features features);

  /**
   * Displays or hides the ReversiView based on the provided boolean.
   *
   * @param show If true, displays the ReversiView; if false, hides it.
   */
  void display(boolean show);

  /**
   * Updates the ReversiView to reflect the current state of the game.
   */
  void update();

  /**
   * Sends an error message to be displayed in the ReversiView.
   *
   * @param message The error message to be displayed.
   */
  void sendErrorMessage(String message);
}
