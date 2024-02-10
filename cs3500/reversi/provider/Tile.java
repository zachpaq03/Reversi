package cs3500.reversi.provider;


/**
 * Represents a tile in a Reversi game board. Each tile has its own unique position and can be
 * controlled by a team at any given time, or be empty.
 */
public interface Tile {

  /**
   * Provides the team currently in control of this tile.
   *
   * @return the team in control of this tile
   */
  public Team getTeam();

  /**
   * Changes the team in control of this tile to the given team.
   *
   * @param team the team to take control
   * @throws IllegalArgumentException if the team is the same as the team already controlling this
   *                                  tile
   */
  public void changeTeam(Team team) throws IllegalArgumentException;

  /**
   * Returns the position of this tile.
   *
   * @return the unique position of this tile.
   */
  Position getPosition();

  @Override
  boolean equals(Object o);

  @Override
  int hashCode();
}
