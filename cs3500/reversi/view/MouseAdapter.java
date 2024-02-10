package cs3500.reversi.view;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;


/**
 * A class that implements a MouseListener to handle click inputs.
 */
public class MouseAdapter implements MouseListener {
  Panel panel;

  public MouseAdapter(Panel panel) {
    this.panel = Objects.requireNonNull(panel);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Point pt = e.getPoint();
    int x = pt.x;
    int y = pt.y;

    this.panel.handleClick(x, y);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // No implementation for this part of the assignment.
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // No implementation for this part of the assignment.
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // No implementation for this part of the assignment.
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // No implementation for this part of the assignment.
  }
}
