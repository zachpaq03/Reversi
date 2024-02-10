package cs3500.reversi;

import java.util.Objects;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.BasicReversiGame;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReversiInterface;
import cs3500.reversi.model.SquareReversi;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.strategy.AsManyCapturesAsPossible;
import cs3500.reversi.strategy.MiniMax;
import cs3500.reversi.view.BasicTextViewSquareGame;
import cs3500.reversi.view.GUI;
import cs3500.reversi.view.GUISquareReversi;
import cs3500.reversi.view.GUIView;
import cs3500.reversi.view.TextualView;


/**
 * This class is for displaying the different levels of the extra credit.
 */
public class Main {
  /**
   * Run main with specific command line arguments to display the different levels of the extra
   * credit.
   */
  public static void main(String[] args) {
    if (Objects.equals(args[0], "0")) {
      ReversiInterface game = new BasicReversiGame();
      game.startGame(11, 11);
      GUIView view1 = new GUI(game, "Player1", true);
      GUIView view2 = new GUI(game, "Player2", true);
      Controller controller1 = new Controller(game, view1, new HumanPlayer(Player.Player1));
      Controller controller2 = new Controller(game, view2, new HumanPlayer(Player.Player2));
    }

    if (Objects.equals(args[0], "1")) {
      ReversiInterface game = new SquareReversi();
      game.startGame(10, 10);
      TextualView textualView = new BasicTextViewSquareGame(game);
      game.placeDisc(5, 3);
      game.placeDisc(6, 3);
      game.placeDisc(6, 4);
      game.placeDisc(6, 5);
      game.placeDisc(7, 6);
      game.placeDisc(6, 6);
      game.placeDisc(5, 6);
      System.out.println(textualView.toString());
    }

    if (Objects.equals(args[0], "2")) {
      ReversiInterface game = new SquareReversi();
      game.startGame(8, 8);
      GUIView view = new GUISquareReversi(game, "Player1");
      GUIView view2 = new GUISquareReversi(game, "Player2");
      new Controller(game, view, new HumanPlayer(Player.Player1));
      new Controller(game, view2, new HumanPlayer(Player.Player2));
    }


    if (Objects.equals(args[0], "4")) {
      ReversiInterface game2 = new SquareReversi();
      game2.startGame(8, 8);
      GUIView view3 = new GUISquareReversi(game2, "Player1");
      GUIView view4 = new GUISquareReversi(game2, "Player2");
      new Controller(game2, view3, new AIPlayer(game2, Player.Player1,
              new MiniMax(new AsManyCapturesAsPossible())));
      new Controller(game2, view4, new AIPlayer(game2, Player.Player2,
              new AsManyCapturesAsPossible()));
    }
  }
}
