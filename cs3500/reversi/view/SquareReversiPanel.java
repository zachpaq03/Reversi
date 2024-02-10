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
 * A class for a panel on a GUI that represents the board of a Reversi game with a square board.
 */
public class SquareReversiPanel extends JPanel implements Panel {
  private ReadOnlyReversiInterface model;
  private ArrayList<Square> squareList = new ArrayList<>();
  private Square currentlyClickedSquare;
  private int clickedIndex;

  /**
   * A constructor for the panel.
   *
   * @param model The model that the game is being played on.
   */
  public SquareReversiPanel(ReadOnlyReversiInterface model) {
    this.model = Objects.requireNonNull(model);
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    g2d.translate(0, getHeight());
    g2d.scale(1, -1);

    draw(g2d);
  }

  @Override
  public void draw(Graphics2D g2d) {
    this.squareList.clear();

    int radius = getWidth() / (model.getGridLength() * 2);
    int diameter = radius * 2;
    int index = 0;

    drawScoresAndTurn(g2d);

    for (int row = 0; row < model.getGridHeight(); row++) {
      for (int col = 0; col < model.getGridLength(); col++) {
        if (model.isHexHere(col, row)) {
          Square newSquare = new Square((radius + col * diameter),
                  (getHeight() - (radius + row * diameter)), diameter, col, row);
          index++;
          g2d.setColor(Color.LIGHT_GRAY);
          if (this.currentlyClickedSquare != null) {
            if (this.clickedIndex == index) {
              squareClicked(g2d, newSquare);
            } else {
              nonClickedSquare(g2d, newSquare);
            }
          } else {
            nonClickedSquare(g2d, newSquare);
          }
          if (model.isDiscHere(col, row)) {
            Ellipse2D.Double disc = new Ellipse2D.Double((radius + col * diameter) - radius / 2.0,
                    getHeight() - (radius + row * diameter) - radius / 2.0, radius, radius);
            makeDisc(g2d, disc, col, row);
          }
          Ellipse2D.Double dot = new Ellipse2D.Double((radius + col * diameter) - radius / 8.0,
                  getHeight() - (radius + row * diameter) - radius / 8.0, radius / 4.0,
                  radius / 4.0);
          if (model.legalMove(col, row)) {
            makeLegalMoveDots(col, row, g2d, dot, this.currentlyClickedSquare != null
                    && this.clickedIndex == index);
          }
        }
      }
    }
  }


  private void nonClickedSquare(Graphics2D g2d, Square square) {
    g2d.fill(square);
    g2d.setColor(Color.BLACK);
    g2d.draw(square);
    this.squareList.add(square);
  }

  private void makeDisc(Graphics2D g2d, Ellipse2D.Double disc, int col, int row) {
    g2d.setColor(getPlayerColor(model.getDiscColor(col, row)));
    g2d.fill(disc);
    g2d.setColor(getPlayerColor(model.getDiscColor(col, row)));
    g2d.draw(disc);
  }

  private Color getPlayerColor(cs3500.reversi.model.Color color) {
    if (color == cs3500.reversi.model.Color.BLACK) {
      return Color.BLACK;
    } else {
      return Color.WHITE;
    }
  }

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
    g2d.drawString(player1Score, (getWidth() - p1SMiddle) / 8, -getHeight() / 80 * 5);
    String player2Score = "Player2 Score: " + model.getScore().get(1);
    int p2SMiddle = fontMetrics.stringWidth(player2Score);
    g2d.drawString(player2Score, (getWidth() - p2SMiddle) / 8 * 7, -getHeight() / 80 * 5);
    g2d.scale(1, -1);
  }

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
  public void handleClick(int x, int y) {
    this.clickedIndex = 0;
    boolean offGrid = true;
    for (Square square : this.squareList) {
      this.clickedIndex++;
      // Need to rescale and translate the y coordinate.
      if (square.contains(x, -y + getHeight())) {
        if (!model.isDiscHere(square.col, square.row)) {
          if (this.currentlyClickedSquare != null && this.currentlyClickedSquare.equals(square)) {
            this.currentlyClickedSquare = null;
            repaint();
            break;
          }
          this.currentlyClickedSquare = square;
          repaint();
          offGrid = false;
          break;
        }
      }
    }
    // This part of the code will make the highlighted hexagon go away if the user clicks on a
    // non-clickable part of the GUI.
    if (offGrid) {
      this.currentlyClickedSquare = null;
      repaint();
    }
  }


  // A helper method that will make a clicked hexagon highlighted, and also display the coordinates
  // and the next actions.
  protected void squareClicked(Graphics2D g2d, Square newSquare) {
    g2d.setColor(Color.BLACK);
    // Need to scale so text is not upside down. Changes back after writing the text.
    g2d.scale(1, -1);
    Font font = new Font("Serif", Font.BOLD, 20);
    String coordinates = "(" + String.valueOf(newSquare.col) + ", " + String.valueOf(newSquare.row)
            + ")";
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
    nonClickedSquare(g2d, newSquare);
  }

  private void makeLegalMoveDots(int col, int row, Graphics2D g2d, Ellipse2D.Double dot,
                                 boolean clicked) {
    if (!clicked) {
      g2d.setColor(Color.GREEN);
      g2d.fill(dot);
      g2d.setColor(Color.GREEN);
      g2d.draw(dot);
    }
  }

  @Override
  public void addFeature(Features features) {
    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        if (!SquareReversiPanel.this.model.isGameOver()) {
          switch (e.getKeyChar()) {
            case 'm':
              if (SquareReversiPanel.this.currentlyClickedSquare != null) {
                features.makeMove(SquareReversiPanel.this.currentlyClickedSquare.col,
                        SquareReversiPanel.this.currentlyClickedSquare.row);
                System.out.println(String.format("Attempted move at (%s, &s)",
                        SquareReversiPanel.this.currentlyClickedSquare.col,
                        SquareReversiPanel.this.currentlyClickedSquare.row));
                SquareReversiPanel.this.currentlyClickedSquare = null;
              }
              break;
            case 'p':
              features.passTurn();
              System.out.println("Attempted to pass");
              SquareReversiPanel.this.currentlyClickedSquare = null;
              break;
            default:
          }
        }
      }

      @Override
      public void keyPressed(KeyEvent e) {
        // Do nothing
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // Do nothing
      }
    });
  }

  @Override
  public void illegalMovePopUp(String message) {
    JOptionPane.showMessageDialog(this, message, "Invalid Move",
            JOptionPane.ERROR_MESSAGE);
  }
}
