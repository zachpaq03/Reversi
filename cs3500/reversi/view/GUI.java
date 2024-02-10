package cs3500.reversi.view;

import java.awt.Dimension;

import javax.swing.JFrame;

import cs3500.reversi.controller.Features;
import cs3500.reversi.model.ReadOnlyReversiInterface;

/**
 * A class for the GUI of the Reversi Game. Uses a panel to draw the elements of the game.
 */
public class GUI extends JFrame implements GUIView {
  private final ReversiPanel panel;

  /**
   * A constructor for the GUI of the Reversi game. Takes in a model, which it passes on to the
   * panel when it constructs a panel. Also uses the length of the model to determine how big the
   * window should be.
   *
   * @param model A read-only version of a Reversi model.
   */
  public GUI(ReadOnlyReversiInterface model, String player, Boolean hints) {
    super(player);

    // The width of the board is dependent on the length of the board.
    int width = model.getGridLength() * 75;
    double radius = (width / (Math.sqrt(3) * model.getGridLength()));
    // The height is made so that there is some space underneath the board, so that when hexagons
    // are clicked the messages have room to be displayed.
    int height = (int) ((radius * 2 * model.getGridHeight()));
    this.setPreferredSize(new Dimension(width, height));
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    if (hints) {
      this.panel = new HintDecorator(model);
    } else {
      this.panel = new ReversiPanel(model);
    }
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
