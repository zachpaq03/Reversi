package cs3500.reversi.provider;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * A JPanel for visualizing a Reversi game board and handling user input.
 */
public class ReversiPanel extends JPanel implements MouseListener, KeyListener {
  private final List<GUITile> tileList;
  private final List<Features> listeners;
  private final ReadOnlyReversi model;
  private GUITile selected;
  private final JLabel screenNotice = new JLabel();


  /**
   * Visualizes a game board in the current state of the provided ReadOnlyReversi. A list of GUI
   * tiles are created, each of which is associated to a coordinate on the model board. The Panel
   * is a listener for mouse and key input, allowing users to select and deselect tiles by clicking
   * them, and to make a move or pass by pressing 'm' or 'p'.
   *
   * @param model the model which is being projected
   */
  public ReversiPanel(ReadOnlyReversi model) {
    this.model = Objects.requireNonNull(model);
    setBackground(Color.pink);
    addMouseListener(this);
    add(screenNotice, BorderLayout.NORTH);

    this.listeners = new ArrayList<>();
    this.tileList = new ArrayList<>();

    int sideLength = 1000 / (4 * model.getSize());

    int range = model.getSize() - 1;

    for (int x = -range; x <= range; x++) {
      int y1 = Math.max(-range, -x - range);
      int y2 = Math.min(range, -x + range);
      for (int y = y1; y <= y2; y++) {
        GUITile tile = new GUITile(
                Team.UNCLAIMED.getColor(),
                (int) (x * sideLength * Math.sqrt(3)),
                y * 2 * sideLength, sideLength,
                x, y, model.getTileAt(new Position(x, y)).getTeam());
        this.tileList.add(tile);
      }
    }
    addKeyListener(this);
    setFocusable(true);
  }

  private void refreshTiles() {
    for (GUITile t : this.tileList) {
      Position p = new Position(t.getXCoord(), t.getYCoord());
      Tile pos = this.model.getTileAt(p);
      t.update(pos.getTeam());
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g.create();

    g2d.transform(transformLogicalToPhysical());

    for (GUITile tile : this.tileList) {
      tile.draw(g2d);
    }

    if (this.model.isGameOver() && this.model.gameStatus() == Status.Won) {
      this.screenNotice.setText(model.getWinner().toString() + " team wins!");
    } else if (this.model.isGameOver() && this.model.gameStatus() == Status.Tied) {
      this.screenNotice.setText("Game is a draw!");
    } else {
      this.screenNotice.setText(this.model.getPlayer().toString() + "'s turn");
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Point clicked = e.getPoint();

    Point2D logicalP = transformPhysicalToLogical().transform(clicked, null);

    boolean anyselected = false;
    if (!this.model.isGameOver()) {

      System.out.println(e.getPoint());
      for (GUITile tile : tileList) {
        if (tile.isSelected()) {
          tile.toggleSelection();
        } else if (tile.containsPoint(logicalP)) {
          tile.toggleSelection();
          this.selected = tile;
        }

        if (tile.isSelected()) {
          tile.setColor(Color.green);
          this.selected = tile;
          fireTileClicked(tile.getXCoord(), tile.getYCoord());
          anyselected = true;
        } else {
          tile.setColor(Team.UNCLAIMED.getColor());
        }
      }
      if (!anyselected) {
        this.selected = null;
      }
      this.repaint();
    }
  }

  /**
   * Adds a listener to the panel.
   *
   * @param listener The listener to add.
   */
  protected void addPanelListener(Features listener) {
    this.listeners.add(Objects.requireNonNull(listener));
  }

  /**
   * Notifies listeners that a tile has been clicked.
   *
   * @param xCoord The x-coordinate of the clicked tile.
   * @param yCoord The y-coordinate of the clicked tile.
   */
  protected void fireTileClicked(int xCoord, int yCoord) {
    for (Features listener : listeners) {
      listener.tileClicked(xCoord, yCoord);
    }
  }


  @Override
  public void mousePressed(MouseEvent e) {
    // Not implemented
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // Not implemented
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // Not implemented
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // Not implemented
  }

  /**
   * Computes the transformation that converts board coordinates
   * (with (width/2,height/2) in center, width and height our logical size)
   * into screen coordinates (with (0,0) in upper-left,
   * width and height in pixels). This is the inverse of {transformPhysicalToLogical()}.
   *
   * @return The necessary transformation
   */
  private AffineTransform transformLogicalToPhysical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = new Dimension(1000, 1000);
    ret.translate(getWidth() / 2., getHeight() / 2.);
    ret.scale(getWidth() / preferred.getWidth(), getHeight() / preferred.getHeight());

    return ret;
  }

  /**
   * Computes the transformation that converts screen coordinates
   * (with (0,0) in upper-left, width and height in pixels)
   * into board coordinates (with (width/2,height/2) in center, width and height
   * our logical size). This is the inverse of {transformLogicalToPhysical()}.
   *
   * @return The necessary transformation
   */
  private AffineTransform transformPhysicalToLogical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = new Dimension(1000, 1000);
    ret.scale(preferred.getWidth() / getWidth(), preferred.getHeight() / getHeight());
    ret.translate(-getWidth() / 2., -getHeight() / 2.);
    return ret;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // Not implemented
  }

  @Override
  public void keyPressed(KeyEvent e) {
    char keyChar = e.getKeyChar();
    if (keyChar == 'p' || keyChar == 'P') {
      System.out.println("Player attempted to pass turn");
      for (Features listener : listeners) {
        listener.attemptPass();
        this.repaint();
      }
    } else if (keyChar == 'm' || keyChar == 'M') {
      if (this.selected == null) {
        System.out.println("Select a tile if you wish to attempt a move");
      } else {
        System.out.println("Player attempted move at " + this.selected.getXCoord() + ", "
                + this.selected.getYCoord());
        for (Features listener : listeners) {
          listener.attemptMove();
          this.refreshTiles();
        }
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // Not implemented
  }

  /**
   * Updates the view based on the given ReadOnlyReversi model.
   *
   * @param model The ReadOnlyReversi model to update the view with.
   */
  protected void update(ReadOnlyReversi model) {
    for (GUITile tile : this.tileList) {
      Tile modelTile = model.getTileAt(new Position(tile.getXCoord(), tile.getYCoord()));
      tile.setTeam(modelTile.getTeam());
    }
    this.repaint();
  }
}