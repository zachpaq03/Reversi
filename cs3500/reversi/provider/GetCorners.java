package cs3500.reversi.provider;

import java.util.Objects;


/**
 * \
 * This represents a game strategy where the player chooses the
 * tile which will capture the most opponent pieces and prioritize playing
 * in corners.
 */
public class GetCorners implements Strategy {

  private final Strategy backup;

  public GetCorners(Strategy backup) {
    this.backup = Objects.requireNonNull(backup);
  }

  @Override
  public Position choosePosition(Reversi model, Team player) {
    int cornerMost = model.getSize() - 1;
    int[] cornerX = {0, cornerMost, cornerMost, 0, -cornerMost, -cornerMost};
    int[] cornerY = {-cornerMost, -cornerMost, 0, cornerMost, cornerMost, 0};

    for (int i = 0; i < cornerX.length; i++) {
      Position pos = new Position(cornerX[i], cornerY[i]);
      if (model.isMoveValid(player, pos)
              && model.getTileAt(pos).getTeam() == Team.UNCLAIMED) {
        return pos;
      }
    }
    return backup.choosePosition(model, player);
  }
}
