package cs3500.reversi.adapter;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.Coord;
import cs3500.reversi.model.ReversiInterface;
import cs3500.reversi.player.PlayerInterface;
import cs3500.reversi.provider.Features;
import cs3500.reversi.provider.Position;
import cs3500.reversi.view.GUIView;

/**
 * A class that used the adapter pattern to convert our controller to implement our providers
 * features interface so that our code would work with their view implementation.
 */
public class OurControllerTheirFeatures extends Controller implements Features {
  private Coord clickedTile;
  private final int size;
  private final int gridLength;

  /**
   * A constructor for the controller.
   *
   * @param model  The model of the Reversi game being played.
   * @param view   The view for the player.
   * @param player The player, which is either human or AI.
   */
  public OurControllerTheirFeatures(ReversiInterface model, GUIView view, PlayerInterface player) {
    super(model, view, player);
    this.size = (model.getGridLength() + 1) / 2;
    this.gridLength = model.getGridLength();
  }

  @Override
  public void tileClicked(int xCoord, int yCoord) {
    // A big difference in our model and our providers model is the coordinate system. We need to
    // call a helper to convert from their system to ours and also change the type to a Coord
    // (our implementation) from a position (their implementation).
    Coord coord = convertPosToCoord(new Position(xCoord, yCoord));
    this.clickedTile = coord;
  }

  // Helper to convert coordinate systems.
  private Coord convertPosToCoord(Position pos) {
    // Our providers used axial coordinates which makes each diagonal row a new row on the board,
    // so we create an array of each diagonal on the board in this helper.
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

  // Helper to get an array of all the diagonals on the board.
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

  // This helper gets the first coord for a given diagonal row, and then it can build off from their
  // to get what coord comes next.
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


  @Override
  public void attemptMove() {
    // Our make move is essentially the same as their attempt move.
    super.makeMove(this.clickedTile.getX(), this.clickedTile.getY());
  }

  @Override
  public void attemptPass() {
    // Our pass is essentially the same as their attemptPass.
    super.passTurn();
  }


  // Our yourTurn is essentially the same as their changeTurns and notifyTurn combined.
  @Override
  public void changeTurns() {
    super.yourTurn();
  }

  @Override
  public void notifyTurn() {
    super.yourTurn();
  }
}
