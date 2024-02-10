package cs3500.reversi.adapter;

import cs3500.reversi.controller.Features;
import cs3500.reversi.provider.ReadOnlyReversi;
import cs3500.reversi.provider.ReversiGUIView;
import cs3500.reversi.view.GUIView;

/**
 * A class that takes our providers view and makes it implement our GUI view interface.
 */
public class TheirViewOurViewInterface extends ReversiGUIView implements GUIView {
  /**
   * Constructs a ReversiGUIView with the specified model.
   *
   * @param model The ReadOnlyReversi model to be displayed.
   */
  public TheirViewOurViewInterface(ReadOnlyReversi model) {
    super(model);
  }

  @Override
  public void addClickListener() {
    // No implementation needed.
  }

  @Override
  public void refresh() {
    super.update();
  }

  @Override
  public void setVisible() {
    // No implementation needed.
  }

  @Override
  public void addFeatures(Features features) {
    super.addFeatureListener((OurControllerTheirFeatures) features);
  }

  @Override
  public void illegalMovePopUp(String message) {
    super.sendErrorMessage(message);
  }
}
