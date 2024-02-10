package cs3500.reversi.adapter;

import cs3500.reversi.model.Coord;
import cs3500.reversi.model.GameState;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReversiAbstract;
import cs3500.reversi.model.ReversiInterface;
import cs3500.reversi.provider.Features;
import cs3500.reversi.provider.Position;
import cs3500.reversi.provider.Reversi;
import cs3500.reversi.provider.Status;
import cs3500.reversi.provider.Team;
import cs3500.reversi.provider.Tile;

/**
 * A class for converting our model to implement our provider's model interface so that we
 * can use their view implementation with our model.
 */
public class OurModelTheirModelInterface extends ReversiAbstract implements Reversi {
  private final int size;

  /**
   * Constructor for a model, only makes symmetrical boards meaning each side is of the same length.
   *
   * @param size The length of each side of the board.
   */
  public OurModelTheirModelInterface(int size) {
    super();
    this.size = size;
  }

  /**
   * A constructor to make a mutable copy of the model, can make a model that is in the middle of a
   * game.
   *
   * @param grid The boolean grid representing if a hexagon is at that location.
   * @param playerGrid A grid of Player types showing if there is a disc at the location.
   * @param players The array of players in the game.
   * @param playerWhoIsUp The player whose turn it currently is.
   * @param passCount The current pass count (2 passes in a row means the game ends).
   */
  public OurModelTheirModelInterface(boolean[][] grid, Player[][] playerGrid, Player[] players,
                                     Player playerWhoIsUp, int passCount) {
    super(grid, playerGrid, players, playerWhoIsUp, passCount);
    this.size = (super.getGridLength() + 1) / 2;
  }

  @Override
  public ReversiInterface getMutableCopy() {
    return new OurModelTheirModelInterface(super.getGrid(), super.getPlayerGrid(),
            super.getPlayers(), super.getTurn(), super.getPassCount());
  }

  @Override
  public int getScore(Team player) {
    // Our getScores are a little different, so we adjust for that.
    if (player == Team.BLACK) {
      return super.getScore().get(0);
    }
    if (player == Team.WHITE) {
      return super.getScore().get(1);
    }
    return 0;
  }

  @Override
  public int getSize() {
    // We don't have anything like size in our model, so we just define it as a field in this class.
    // For symmetric models, which is the only kind of model our provider's code supports, the size
    // is equivalent to the row length + 1, divided by 2.
    return size;
  }

  @Override
  public int getNumTiles() {
    // Not used in their view
    return 0;
  }

  @Override
  public Tile getTileAt(Position pos) throws IllegalArgumentException {
    // A big difference in our model and our providers model is the coordinate system. We need to
    // call a helper to convert from their system to ours and also change the type to a Coord
    // (our implementation) from a position (their implementation).
    Coord coord = convertPosToCoord(pos);
    Team team = Team.UNCLAIMED;
    if (!super.isHexHere(coord.getX(), coord.getY())) {
      throw new IllegalArgumentException("No Tile here");
    }
    if (super.isDiscHere(coord.getX(), coord.getY())) {
      if (super.getDiscColor(coord.getX(), coord.getY()) == Player.Player1.getColor()) {
        team = Team.BLACK;
      } else {
        team = Team.WHITE;
      }
    }
    return new OurTile(team);
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
    Coord[][] result = new Coord[super.getGridLength()][super.getGridLength()];
    int count = 0;
    for (int x = -1 * (size - 1); x <= (size - 1); x++) {
      count++;
      int difference = Math.abs(x);
      int loopCap = super.getGridLength() - difference;
      boolean oddRow = count % 2 != 0;
      if (x >= 0 && size % 2 == 0) {
        oddRow = false;
      } else if (x >= 0 && size % 2 != 0) {
        oddRow = true;
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
  public Team getPlayer() {
    if (super.getTurn() == Player.Player1) {
      return Team.BLACK;
    }
    return Team.WHITE;
  }

  @Override
  public Status gameStatus() {
    if (!super.isGameOver()) {
      return Status.Playing;
    } else if (super.endOfGameStatus() == GameState.PLAYER1WINS || super.endOfGameStatus() ==
            GameState.PLAYER2WINS) {
      return Status.Won;
    }
    return Status.Tied;
  }

  @Override
  public Team getWinner() {
    // Not an exact method we have, but we have a number of methods and fields that when combined
    // provide the same functionality.
    if (!super.isGameOver() || gameStatus() == Status.Tied) {
      return null;
    }
    if (super.isGameOver()) {
      if (getScore(Team.BLACK) > getScore(Team.WHITE)) {
        return Team.BLACK;
      }
      if (getScore(Team.WHITE) > getScore(Team.BLACK)) {
        return Team.WHITE;
      }
    }
    return null;
  }

  @Override
  public void placePiece(Team player, Position pos) throws IllegalArgumentException,
          IllegalStateException {
    // Once again convert, then our placeDisc method is essentially the same as their placePiece.
    Coord coord = convertPosToCoord(pos);
    super.placeDisc(coord.getX(), coord.getY());
  }

  @Override
  public boolean isMoveValid(Team playerTeam, Position pos) throws IllegalArgumentException {
    // Once again convert, then our legalMove method is essentially the same as their isMoveValid.
    Coord coord = convertPosToCoord(pos);
    return super.legalMove(coord.getX(), coord.getY());
  }

  @Override
  public void startGame() {
    // Our constructor takes in the length and height of the board so need to get those values from
    // the size.
    super.startGame(size * 2 - 1, size * 2 - 1);
  }

  @Override
  public void addFeatureListener(Features features) {
    // Our addObserver is essentially the same as their addFeatureListener
    super.addObserver((OurControllerTheirFeatures) features);
  }

  @Override
  public Reversi getCopy(Reversi model) {
    // Our getMutableCopy is essentially the same as their getCopy.
    return (Reversi) getMutableCopy();
  }
}