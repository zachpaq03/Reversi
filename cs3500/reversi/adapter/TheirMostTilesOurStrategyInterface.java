package cs3500.reversi.adapter;

import cs3500.reversi.model.Coord;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReversiInterface;
import cs3500.reversi.provider.MostTiles;
import cs3500.reversi.provider.Position;
import cs3500.reversi.provider.Reversi;
import cs3500.reversi.provider.Team;
import cs3500.reversi.strategy.ReversiStrategy;

/**
 * A class the converts our provider's MostTiles strategy to our strategy interface so
 * that it can be used with our model.
 */
public class TheirMostTilesOurStrategyInterface extends MostTiles implements ReversiStrategy {
  private int size;
  private int gridLength;

  @Override
  public Coord chooseMove(ReversiInterface model, Player who) {
    Team team = Team.BLACK;
    if (who == Player.Player2) {
      team = Team.WHITE;
    }
    this.size = (model.getGridLength() + 1) / 2;
    this.gridLength = model.getGridLength();
    Position pos = super.choosePosition((Reversi) model, team);
    return convertPosToCoord(pos);
  }

  @Override
  public int assignPoints(int after, int before, int cols, int rows, ReversiInterface model) {
    // Not used
    return 0;
  }

  private Coord convertPosToCoord(Position pos) {
    Coord[][] diagonalBoard = getDiagonalArraysOfBoard();
    int index = pos.getX() + (size - 1);
    int y;
    if (index >= (size - 1)) {
      y = pos.getY() + (size - 1);
    } else {
      y = pos.getY() + index;
    }
    return diagonalBoard[index][y];
  }

  private Coord[][] getDiagonalArraysOfBoard() {
    Coord[][] result = new Coord[gridLength][gridLength];
    int count = 0;
    for (int x = -1 * (size - 1); x <= (size - 1); x++) {
      count++;
      int difference = Math.abs(x);
      int loopCap = gridLength - difference;
      boolean oddRow = count % 2 != 0;
      if (x >= 0) {
        oddRow = false;
      }
      Coord coord = getStarterCoord(x);
      for (int y = 0; y < loopCap; y++) {
        if (oddRow && y > 0) {
          coord = new Coord(coord.getX(), coord.getY() + 1);
        } else if (!oddRow && y > 0) {
          coord = new Coord(coord.getX() + 1, coord.getY() + 1);
        }
        result[x + (size - 1)][y] = coord;
        if (y > 0) {
          oddRow = !oddRow;
        }
      }
    }
    return result;
  }

  private Coord getStarterCoord(int xParam) {
    int index = xParam + size - 1;
    int x;
    int y;
    if (index >= (size - 1)) {
      x = Math.floorDiv((size - 1), 2) + xParam;
      y = 0;
    } else {
      x = Math.floorDiv(index, 2);
      y = -xParam;
    }
    return new Coord(x, y);
  }
}
