package cs3500.reversi.provider;

import java.util.Objects;


/**
 * \
 * This represents a game strategy where the player chooses the
 * tile which will capture the most opponent pieces and avoid any tile
 * next to a corner.
 */
public class AvoidNextToCorners implements Strategy {

  private final Strategy backup;

  public AvoidNextToCorners(Strategy backup) {
    this.backup = Objects.requireNonNull(backup);
  }

  @Override
  public Position choosePosition(Reversi model, Team player) {
    int[] posToAvoid = {model.getSize() - 1, -model.getSize() + 1};

    Position pos = backup.choosePosition(model, player);
    for (int val : posToAvoid) {
      if (!(pos.getX() == val && (pos.getY() == 1 || pos.getY() == -1))) {
        if (!(pos.getY() == val && (pos.getX() == 1 || pos.getX() == -1))) {
          return pos;
        }
      }
    }

    return backup.choosePosition(model, player);
  }
}
