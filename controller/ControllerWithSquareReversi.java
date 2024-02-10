package cs3500.reversi.controller;

import org.junit.Test;

import cs3500.reversi.mocks.SquareReversiLogChangesInTurn;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReversiInterface;
import cs3500.reversi.model.SquareReversi;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.PlayerInterface;
import cs3500.reversi.view.GUISquareReversi;
import cs3500.reversi.view.GUIView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * A test class for testing the controller works with a square Reversi game.
 */
public class ControllerWithSquareReversi {
  @Test
  public void controllerMakesTurnChangeUsingMock() {
    StringBuilder log = new StringBuilder();
    ReversiInterface game = new SquareReversiLogChangesInTurn(log);
    game.startGame(8, 8);
    PlayerInterface player1 = new HumanPlayer(Player.Player1);
    PlayerInterface player2 = new HumanPlayer(Player.Player2);
    GUIView view1 = new GUISquareReversi(game, "player1");
    GUIView view2 = new GUISquareReversi(game, "player2");
    Controller controller1 = new Controller(game, view1, player1);
    Controller controller2 = new Controller(game, view2, player2);
    controller1.makeMove(4, 2);
    controller2.passTurn();
    assertEquals(log.toString(), "Turn changed, Turn changed, ");
  }

  @Test
  public void illegalMoveDoesNotCrash() {
    ReversiInterface game = new SquareReversi();
    game.startGame(8, 8);
    PlayerInterface player1 = new HumanPlayer(Player.Player1);
    PlayerInterface player2 = new HumanPlayer(Player.Player2);
    GUIView view1 = new GUISquareReversi(game, "player1");
    GUIView view2 = new GUISquareReversi(game, "player2");
    Controller controller1 = new Controller(game, view1, player1);
    Controller controller2 = new Controller(game, view2, player2);
    controller1.makeMove(0, 0);
    assertSame(game.getTurn(), Player.Player1);
  }

  @Test
  public void controllerDoesNotAllowMoveNotYourTurn() {
    ReversiInterface game = new SquareReversi();
    game.startGame(8, 8);
    PlayerInterface player1 = new HumanPlayer(Player.Player1);
    PlayerInterface player2 = new HumanPlayer(Player.Player2);
    GUIView view1 = new GUISquareReversi(game, "player1");
    GUIView view2 = new GUISquareReversi(game, "player2");
    Controller controller1 = new Controller(game, view1, player1);
    Controller controller2 = new Controller(game, view2, player2);
    controller2.makeMove(3, 4);
    assertEquals(game.getTurn(), Player.Player1);
    assertEquals(game.getScore().get(0), game.getScore().get(1));
  }
}
