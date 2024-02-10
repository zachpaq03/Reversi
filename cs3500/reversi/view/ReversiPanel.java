package cs3500.reversi.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cs3500.reversi.controller.Features;
import cs3500.reversi.model.ReadOnlyReversiInterface;

/**
 * The panel for a game of Reversi. This class draws the hexagons and discs for a game. If a
 * hexagon is clicked, this class will make the clicked hexagon highlighted and display both the
 * coordinates and the keyboard input to make a move.
 */
public class ReversiPanel extends JPanel implements Panel {
  private ReadOnlyReversiInterface model;
  // This field stores all of the current hexagons so that when a coordinate is clicked, it is
  // checked if any of these hexagons contain that point.
  private ArrayList<HexagonImage> hexagons = new ArrayList<>();
  private HexagonImage currentlyClickedHex;
  // Since every time the loop is called a new collection of hexagons is made, simply making a
  // hexagon the "clicked hexagon" does not work if they window was to be resized, or forced to
  // refresh in any way. However, the order in which the hexagons are created is always the same
  // and the amount of hexagons created never changes within a game. Therefore, by tracking the
  // index of the clicked hexagon, every hexagon made with that index will be highlighted, so even
  // when the window refreshes the clicked hexagon stays highlighted.
  private int clickedIndex;

  /**
   * A constructor for a panel that takes in a model so that it can see what coordinates it needs
   * to fit within the window.
   *
   * @param model A read-only version of a Reversi model.
   */
  public ReversiPanel(ReadOnlyReversiInterface model) {
    this.model = Objects.requireNonNull(model);
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Set coordinates to system humans are used to.
    g2d.translate(0, getHeight());
    g2d.scale(1, -1);

    // Call helper to actually add things to the panel.
    draw(g2d);
  }


  @Override
  public void draw(Graphics2D g2d) {
    // Since every time the window is refreshed new hexagons are made, the old list of hexagons no
    // longer exists, so the list needs to be cleared.
    this.hexagons.clear();
    g2d.setColor(Color.BLACK);
    // The size of the hexagons are dependent on the width of the board. This is designed so that
    // when the window is resized, the board still takes up the entire width of the screen.
    double radius = (getWidth() / (Math.sqrt(3) * model.getGridLength()));
    double centerXCord = radius / 2 * Math.sqrt(3);
    int index = 0;

    // Call a helper to draw the things that are always displayed whether a hexagon is clicked or
    // not: the scores and whose turn it is.
    drawScoresAndTurn(g2d);

    for (int row = 0; row < model.getGridHeight(); row++) {
      for (int col = 0; col < model.getGridLength(); col++) {
        // Checks if there is a hex here. (We know the loop starts at 0 and does not go greater than
        // the length or width of the board, so no need to worry about exceptions).
        if (model.isHexHere(col, row)) {
          HexagonImage newHex = new HexagonImage(centerXCord + 2 * centerXCord * col +
                  getOffset(row, centerXCord), getHeight() - (radius + 1.5 * radius * row),
                  radius, col, row);
          index++;
          g2d.setColor(Color.LIGHT_GRAY);
          if (this.currentlyClickedHex != null) {
            if (this.clickedIndex == index) {
              // Call helper to highlight a hexagon
              hexagonClicked(g2d, newHex);
            } else {
              nonClickedHexagon(g2d, newHex);
            }
          } else {
            // Call helper to make a non highlighted hexagon.
            nonClickedHexagon(g2d, newHex);
          }
          Ellipse2D.Double disc = new Ellipse2D.Double(centerXCord + 2 * centerXCord * col +
                  getOffset(row, centerXCord) - radius / 2, getHeight() -
                  (radius + 1.5 * radius * row) - radius / 2, radius, radius);
          // Call helper to add a disc
          makeDisc(col, row, g2d, disc);
          Ellipse2D.Double dot = new Ellipse2D.Double(centerXCord + 2 * centerXCord * col +
                  getOffset(row, centerXCord) - radius / 12, getHeight() -
                  (radius + 1.5 * radius * row) - radius / 12, radius / 6, radius / 6);
          // Call helper to add dots for legal moves (removes dot if hexagon gets clicked).
          makeLegalMoveDots(col, row, g2d, dot, this.currentlyClickedHex != null &&
                  this.clickedIndex == index);
        }
      }
    }
  }


  // A private helper method to help draw the board. The way our model works is by using an offset
  // coordinate system. Rows of odd length are offset ot the left and rows of even length are offset
  // to the right. Since rows always decrease by one in length, the offset is always switching back
  // and forth.
  private double getOffset(int row, double centerXCord) {
    int middleRow = (model.getGridHeight() - 1) / 2;
    if (model.getGridLength() % 2 == 0) {
      if (Math.abs(middleRow - row) % 2 == 0) {
        return 0;
      } else {
        return -centerXCord;
      }
    } else {
      if (Math.abs(middleRow - row) % 2 != 0) {
        return centerXCord;
      } else {
        return 0;
      }
    }
  }

  // A helper method to know what color disc to make based on the player who resides at a certain
  // location on the board.
  private Color getPlayerColor(cs3500.reversi.model.Color color) {
    if (color == cs3500.reversi.model.Color.BLACK) {
      return Color.BLACK;
    } else {
      return Color.WHITE;
    }
  }

  // For now, handle click resides in the panel class.

  @Override
  public void handleClick(int x, int y) {
    this.clickedIndex = 0;
    boolean offGrid = true;
    for (HexagonImage hexagon : this.hexagons) {
      this.clickedIndex++;
      // Need to rescale and translate the y coordinate.
      if (hexagon.contains(x, -y + getHeight())) {
        if (!model.isDiscHere(hexagon.col, hexagon.row)) {
          if (this.currentlyClickedHex != null && this.currentlyClickedHex.equals(hexagon)) {
            this.currentlyClickedHex = null;
            repaint();
            break;
          }
          this.currentlyClickedHex = hexagon;
          repaint();
          offGrid = false;
          break;
        }
      }
    }
    // This part of the code will make the highlighted hexagon go away if the user clicks on a
    // non-clickable part of the GUI.
    if (offGrid) {
      this.currentlyClickedHex = null;
      repaint();
    }
  }


  // A helper method that will make a clicked hexagon highlighted, and also display the coordinates
  // and the next actions.
  protected void hexagonClicked(Graphics2D g2d, HexagonImage newHex) {
    g2d.setColor(Color.BLACK);
    // Need to scale so text is not upside down. Changes back after writing the text.
    g2d.scale(1, -1);
    Font font = new Font("Serif", Font.BOLD, 20);
    String coordinates = "(" + String.valueOf(newHex.col) + ", " + String.valueOf(newHex.row) + ")";
    g2d.setFont(font);
    FontMetrics fontMetrics = g2d.getFontMetrics(font);
    int coordinatesMiddle = fontMetrics.stringWidth(coordinates);
    String message = "M = Move, P = Pass";
    int messageMiddle = fontMetrics.stringWidth(message);
    // The subtraction and division is done to center the text.
    g2d.drawString(coordinates, (getWidth() - coordinatesMiddle) / 2, -getHeight() / 10);
    if (!this.model.isGameOver()) {
      g2d.drawString(message, (getWidth() - messageMiddle) / 2, -getHeight() / 40 * 3);
    }
    g2d.scale(1, -1);
    g2d.setColor(Color.CYAN);
    nonClickedHexagon(g2d, newHex);
  }

  // A helper that makes grey hexagons.
  private void nonClickedHexagon(Graphics2D g2d, HexagonImage newHex) {
    g2d.fill(newHex);
    g2d.setColor(Color.BLACK);
    g2d.draw(newHex);
    this.hexagons.add(newHex);
  }



  // Helper that draws discs.
  private void makeDisc(int col, int row, Graphics2D g2d, Ellipse2D.Double disc) {
    // Checks if there is a disc here, (We know there is a a hex here so no need to worry about
    // exceptions being thrown.
    if (model.isDiscHere(col, row)) {
      g2d.setColor(getPlayerColor(model.getDiscColor(col, row)));
      g2d.fill(disc);
      g2d.setColor(getPlayerColor(model.getDiscColor(col, row)));
      g2d.draw(disc);
    }
  }

  // A helper method that draws small green dots wherever there are legal moces on the board at
  // the current moment.
  private void makeLegalMoveDots(int col, int row, Graphics2D g2d, Ellipse2D.Double dot,
                                 boolean clicked) {
    if (model.legalMove(col, row) && !clicked) {
      g2d.setColor(Color.GREEN);
      g2d.fill(dot);
      g2d.setColor(Color.GREEN);
      g2d.draw(dot);
    }
  }

  // A helper method that draws the current scores for both players, whose turn it is, and the state
  // of the game.
  private void drawScoresAndTurn(Graphics2D g2d) {
    g2d.scale(1, -1);
    Font font = new Font("Serif", Font.BOLD, 20);
    g2d.setFont(font);
    FontMetrics fontMetrics = g2d.getFontMetrics(font);
    if (!model.isGameOver()) {
      String turn = "Turn: " + model.getTurn().toString();
      int turnMiddle = fontMetrics.stringWidth(turn);
      g2d.drawString(turn, (getWidth() - turnMiddle) / 2, -getHeight() / 20);
    }
    String gameStatus = "Game State: " + getGameStatus(); // Call helper to get the current status.
    int gSMiddle = fontMetrics.stringWidth(gameStatus);
    g2d.drawString(gameStatus, (getWidth() - gSMiddle) / 2, -getHeight() / 40);
    String player1Score = "Player1 Score: " + model.getScore().get(0);
    int p1SMiddle = fontMetrics.stringWidth(player1Score);
    g2d.drawString(player1Score, (getWidth() - p1SMiddle) / 6, -getHeight() / 80 * 5);
    String player2Score = "Player2 Score: " + model.getScore().get(1);
    int p2SMiddle = fontMetrics.stringWidth(player2Score);
    g2d.drawString(player2Score, (getWidth() - p2SMiddle) / 6 * 5, -getHeight() / 80 * 5);
    g2d.scale(1, -1);
  }

  // Helper that returns a string representing the current stauts of the game.
  private String getGameStatus() {
    if (!model.isGameOver()) {
      return "In Progress";
    }
    switch (model.endOfGameStatus()) {
      case PLAYER1WINS:
        return "Player 1 Wins!";
      case PLAYER2WINS:
        return "Player 2 Wins!";
      case DRAW:
        return "Game Ends in Tie!";
      default:
        return "";
    }
  }

  @Override
  public void addFeature(Features features) {
    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        if (!ReversiPanel.this.model.isGameOver()) {
          switch (e.getKeyChar()) {
            case 'm':
              if (ReversiPanel.this.currentlyClickedHex != null) {
                features.makeMove(ReversiPanel.this.currentlyClickedHex.col,
                        ReversiPanel.this.currentlyClickedHex.row);
                ReversiPanel.this.currentlyClickedHex = null;
              }
              break;
            case 'p':
              features.passTurn();
              ReversiPanel.this.currentlyClickedHex = null;
              break;
            default:
          }
        }
      }

      @Override
      public void keyPressed(KeyEvent e) {
        // No key presses do anything as of now.
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // No key releases do anything as of now.
      }
    });
  }


  HexagonImage getCurrentlyClickedHex() {
    if (currentlyClickedHex != null) {
      return currentlyClickedHex;
    }
    throw new IllegalArgumentException("No currently clicked hexagon.");
  }

  ReadOnlyReversiInterface getModel() {
    return model;
  }

  public void illegalMovePopUp(String message) {
    JOptionPane.showMessageDialog(this, message, "Invalid Move", JOptionPane.ERROR_MESSAGE);
  }
}
