package cs3500.reversi.provider;

/**
 * The GamePlayer interface represents a player in the game of Reversi.
 * Each player has the ability to place a game piece on the board or pass their turn.
 * A valid move must capture at least one piece of the opponent's.
 */
public interface GamePlayer {

  /**
   * Retrieves the position where the player wants to place their game piece.
   *
   * @param model The Reversi model representing the current state of the game.
   * @return The desired position for placing the game piece.
   */
  Position getPos(Reversi model);

  /**
   * Gets the team (color) associated with the player.
   *
   * @return The team to which the player belongs.
   */
  Team getTeam();
}