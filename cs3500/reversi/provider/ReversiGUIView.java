package cs3500.reversi.provider;

import java.awt.Dimension;
import java.util.Objects;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 * Represents the graphical user interface view for a Reversi game.
 */
public class ReversiGUIView extends JFrame implements ReversiView {

  private final ReversiPanel canvas;

  private final ReadOnlyReversi model;

  /**
   * Constructs a ReversiGUIView with the specified model.
   *
   * @param model The ReadOnlyReversi model to be displayed.
   */
  public ReversiGUIView(ReadOnlyReversi model) {
    this.model = Objects.requireNonNull(model);
    this.setMinimumSize(new Dimension(500, 500));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    this.canvas = new ReversiPanel(model);
    this.add(canvas);

    this.pack();
    this.display(true);
  }

  @Override
  public void addFeatureListener(Features features) {
    this.canvas.addPanelListener(features);
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

  @Override
  public void update() {
    this.canvas.update(this.model);
  }

  @Override
  public void sendErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }
}