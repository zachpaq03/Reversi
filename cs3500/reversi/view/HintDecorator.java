package cs3500.reversi.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import cs3500.reversi.controller.Features;
import cs3500.reversi.model.Coord;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReadOnlyReversiInterface;
import cs3500.reversi.model.ReversiInterface;

/**
 * A class that extends the normal panel for a Reversi game and adds the functionality of having
 * hits available if the h key is pressed.
 */
public class HintDecorator extends ReversiPanel {
  private boolean hintsOn;
  private int currentHint;
  private boolean theirTurn;

  /**
   * A constructor for this extended panel.
   *
   * @param model The model that the game is being played on.
   */
  public HintDecorator(ReadOnlyReversiInterface model) {
    super(model);
  }


  @Override
  protected void hexagonClicked(Graphics2D g2d, HexagonImage newHex) {
    super.hexagonClicked(g2d, newHex);
    drawHint(g2d);
  }

  private void drawHint(Graphics2D g2d) {
    if (hintsOn) {
      g2d.setColor(Color.BLACK);
      g2d.scale(1, -1);
      Font font = new Font("Serif", Font.BOLD, 20);
      g2d.setFont(font);

      String hint = String.valueOf(currentHint);
      FontMetrics fontMetrics = g2d.getFontMetrics(font);
      int width = fontMetrics.stringWidth(hint);
      int height = fontMetrics.getHeight();

      int x = (int) getCurrentlyClickedHex().xCord - (width / 2);
      int y = (int) getCurrentlyClickedHex().yCord - (height / 4);

      g2d.drawString(hint, x, -y);

      g2d.scale(1, -1);
      g2d.setColor(Color.CYAN);
    }
  }


  @Override
  public void addFeature(Features features) {
    super.addFeature(features);
    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        if (!getModel().isGameOver() && e.getKeyChar() == 'h') {
          theirTurn = features.thisPlayersTurn();
          if (theirTurn) {
            hintsOn = !hintsOn;
          }
          repaint();
        }
        if (!getModel().isGameOver()) {
          if (e.getKeyChar() == 'm' || e.getKeyChar() == 'p') {
            notTheirTurn();
          }
        }
      }

      @Override
      public void keyPressed(KeyEvent e) {
        // Nothing to implement
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // Nothing to implement
      }
    });
  }

  private int getHint(ReadOnlyReversiInterface model, Coord coordinate) {
    ReversiInterface copyModel = model.getMutableCopy();
    Player who = copyModel.getTurn();
    int beforeScore;
    int afterScore;
    if (who == Player.Player1) {
      beforeScore = copyModel.getScore().get(0);
    } else {
      beforeScore = copyModel.getScore().get(1);
    }
    try {
      copyModel.placeDisc(coordinate.getX(), coordinate.getY());
      if (who == Player.Player1) {
        afterScore = copyModel.getScore().get(0);
      } else {
        afterScore = copyModel.getScore().get(1);
      }
    } catch (IllegalStateException illegalStateException) {
      return 0;
    }
    return afterScore - beforeScore - 1;
  }

  @Override
  public void handleClick(int x, int y) {
    super.handleClick(x, y);
    try {
      currentHint = getHint(getModel(), new Coord(getCurrentlyClickedHex().col,
              getCurrentlyClickedHex().row));
    } catch (IllegalArgumentException ignore) {
      // Do nothing
    }
    repaint();
  }

  public void notTheirTurn() {
    hintsOn = false;
    theirTurn = false;
  }
}
