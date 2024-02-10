package cs3500.reversi.provider;

/**
 * \
 * This represents a game strategy where the player chooses the
 * tile which will capture the most opponent pieces. In the case of a tie, this will return the
 * uppermost-leftmost tile of the greatest value.
 */
public class MostTiles implements Strategy {

  @Override
  public Position choosePosition(Reversi model, Team player) {
    int count = 0;
    Position choice = null;
    int range = model.getSize() - 1;

    for (int q = -range; q <= range; q++) {
      int x1 = Math.max(-range, -q - range);
      int x2 = Math.min(range, -q + range);
      for (int x = x1; x <= x2; x++) {
        Position pos = new Position(x, q);
        if (model.isMoveValid(player, new Position(x, q))
                && model.getTileAt(new Position(x, q)).getTeam() == Team.UNCLAIMED) {
          int score = model.getScore(player);
          Reversi tempModel = model.getCopy(model);
          tempModel.placePiece(player, pos);
          int numTilesSwapped = tempModel.getScore(player) - score;
          if (numTilesSwapped > count) {
            count = numTilesSwapped;
            choice = pos;
          }
        }
      }
    }

    if (choice == null) {
      throw new IllegalStateException("No position available");
    }

    return choice;
  }
}
