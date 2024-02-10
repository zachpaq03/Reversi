package cs3500.reversi.adapter;

import cs3500.reversi.provider.Position;
import cs3500.reversi.provider.Team;
import cs3500.reversi.provider.Tile;

/**
 * A very simple implementation of our provider's tile interface.
 */
public class OurTile implements Tile {
  private Team team;

  /**
   * Constructor for a tile that is equivalent to a hexagon on our board.
   *
   * @param team the team that owns this tile currently.
   */
  public OurTile(Team team) {
    this.team = team;
  }

  @Override
  public Team getTeam() {
    return team;
  }

  @Override
  public void changeTeam(Team team) throws IllegalArgumentException {
    // Never used;
  }

  @Override
  public Position getPosition() {
    // Never used;
    return null;
  }
}
