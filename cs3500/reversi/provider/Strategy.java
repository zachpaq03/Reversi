package cs3500.reversi.provider;

/**
 * A Strategy interface for choosing where to play next for the given player.
 */
public interface Strategy {
  /**
   * Returns the position for the best move using the designed Strategy.
   * If there are no available moves, an exception will be thrown.
   * In case of ties, the leftmost, uppermost position will be returned.
   *
   * @param model  the game being played to determine the next move
   * @param player the player whose move is being found
   * @return the position of the best next move
   * @throws IllegalStateException if there are no possible moves to play according to the strategy
   */
  Position choosePosition(Reversi model, Team player) throws IllegalStateException;
}

