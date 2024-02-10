package cs3500.reversi.controller;

/**
 * An interface of features that a player can call.
 */
public interface Features {
  /**
   * A method that will make a move given valid coordinates, and if it is the player's turn who the
   * controller represents.
   *
   * @param x the x-coordinate for the move.
   * @param y the y-coordinate for the move.
   */
  void makeMove(int x, int y);

  /**
   * A method that will pass a player's turn given that it is that player's turn.
   */
  void passTurn();

  /**
   * Returns true if it is this player's turn.
   *
   * @return true if it is this player's turn.
   */
  boolean thisPlayersTurn();
}
