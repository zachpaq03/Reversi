package cs3500.reversi.controller;

import org.junit.Test;

import cs3500.reversi.mocks.LogsChangeInTurn;
import cs3500.reversi.model.BasicReversiGame;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReversiInterface;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.PlayerInterface;
import cs3500.reversi.view.GUI;
import cs3500.reversi.view.GUIView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * A test class for the controller.
 */
public class BasicControllerTests {
  @Test
  public void controllerMakesTurnChangeUsingMock() {
    StringBuilder log = new StringBuilder();
    ReversiInterface game = new LogsChangeInTurn(log);
    game.startGame(11, 11);
    PlayerInterface player1 = new HumanPlayer(Player.Player1);
    PlayerInterface player2 = new HumanPlayer(Player.Player2);
    GUIView view1 = new GUI(game, "player1", false);
    GUIView view2 = new GUI(game, "player2", false);
    Controller controller1 = new Controller(game, view1, player1);
    Controller controller2 = new Controller(game, view2, player2);
    controller1.makeMove(3, 4);
    controller2.passTurn();
    assertEquals(log.toString(), "Turn changed, Turn changed, ");
  }

  @Test
  public void illegalMoveDoesNotCrash() {
    ReversiInterface game = new BasicReversiGame();
    game.startGame(11, 11);
    PlayerInterface player1 = new HumanPlayer(Player.Player1);
    PlayerInterface player2 = new HumanPlayer(Player.Player2);
    GUIView view1 = new GUI(game, "player1", false);
    GUIView view2 = new GUI(game, "player2", false);
    Controller controller1 = new Controller(game, view1, player1);
    Controller controller2 = new Controller(game, view2, player2);
    controller1.makeMove(2, 4);
    assertSame(game.getTurn(), Player.Player1);
  }

  @Test
  public void controllerDoesNotAllowMoveNotYourTurn() {
    ReversiInterface game = new BasicReversiGame();
    game.startGame(11, 11);
    PlayerInterface player1 = new HumanPlayer(Player.Player1);
    PlayerInterface player2 = new HumanPlayer(Player.Player2);
    GUIView view1 = new GUI(game, "player1", false);
    GUIView view2 = new GUI(game, "player2", false);
    Controller controller1 = new Controller(game, view1, player1);
    Controller controller2 = new Controller(game, view2, player2);
    controller2.makeMove(3, 4);
    assertEquals(game.getTurn(), Player.Player1);
    assertEquals(game.getScore().get(0), game.getScore().get(1));
  }
}
