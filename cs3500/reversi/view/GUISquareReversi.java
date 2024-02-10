package cs3500.reversi.view;

import java.awt.Dimension;

import javax.swing.JFrame;

import cs3500.reversi.controller.Features;
import cs3500.reversi.model.ReadOnlyReversiInterface;

/**
 * A class for a GUI of a Reversi game on a square board.
 */
public class GUISquareReversi extends JFrame implements GUIView {
  private final SquareReversiPanel panel;

  /**
   * The constructor for a GUI of a Reversi game on a square board.
   *
   * @param model The model of the game being played.
   * @param player The name of the player to be displayed.
   */
  public GUISquareReversi(ReadOnlyReversiInterface model, String player) {
    super(player);

    int width = model.getGridLength() * 76;
    int height = model.getGridHeight() * 100;
    this.setPreferredSize(new Dimension(width, height));
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    this.panel = new SquareReversiPanel(model);
    this.add(panel);

    this.pack();
    this.setVisible();
  }

  @Override
  public void addClickListener() {
    this.panel.addMouseListener(new MouseAdapter(this.panel));
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void setVisible() {
    this.setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    this.panel.addFeature(features);
  }

  @Override
  public void illegalMovePopUp(String message) {
    this.panel.illegalMovePopUp(message);
  }
}
