package cs3500.reversi.player;

import java.util.Objects;

import cs3500.reversi.controller.Features;
import cs3500.reversi.model.Color;
import cs3500.reversi.model.Coord;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReversiInterface;
import cs3500.reversi.strategy.ReversiStrategy;

/**
 * A class for an AI player that has an observer, which calls the controller whenever the AI makes
 * a move.
 */
public class AIPlayer implements PlayerInterface {
  private ReversiInterface model;
  private Player whichPlayer;
  private ReversiStrategy strategy;
  private Features featureObserver;


  /**
   * A constructor for an AI player, which takes in the model so that it can create a deep copy, the
   * player they represent, and a strategy so that they can pick their moves.
   *
   * @param model The model of the Reversi game.
   * @param whichPlayer Which player they represent in the game, 1 or 2.
   * @param strategy The strategy the AI is using.
   */
  public AIPlayer(ReversiInterface model, Player whichPlayer, ReversiStrategy strategy) {
    this.model = Objects.requireNonNull(model);
    this.whichPlayer = whichPlayer;
    this.strategy = Objects.requireNonNull(strategy);
  }

  @Override
  public Player getWhichPlayer() {
    return this.whichPlayer;
  }

  @Override
  public Color getColor() {
    return this.whichPlayer.getColor();
  }

  @Override
  public int getScore() {
    if (this.whichPlayer == Player.Player1) {
      return this.model.getScore().get(0);
    } else {
      return this.model.getScore().get(1);
    }
  }

  @Override
  public boolean isWinning() {
    if (this.whichPlayer == Player.Player1) {
      return this.model.getScore().get(0) > this.model.getScore().get(1);
    } else {
      return this.model.getScore().get(1) > this.model.getScore().get(0);
    }
  }

  @Override
  public void addFeatures(Features features) {
    this.featureObserver = features;
  }

  @Override
  public void makeMove() {
    Coord move;
    try {
      move = this.strategy.chooseMove(model, model.getTurn());
      alertMakeMove(move.getX(), move.getY());
    } catch (IllegalStateException iSE) {
      alertPass();
    }
  }

  private void alertMakeMove(int x, int y) {
    this.featureObserver.makeMove(x, y);
  }

  private void alertPass() {
    this.featureObserver.passTurn();
  }
}
