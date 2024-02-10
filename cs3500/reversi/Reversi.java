package cs3500.reversi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.reversi.adapter.OurControllerTheirFeatures;
import cs3500.reversi.adapter.OurModelTheirModelInterface;
import cs3500.reversi.adapter.TheirAvoidNextToCornersOurStrategyInterface;
import cs3500.reversi.adapter.TheirGetCornersOurStrategyInterface;
import cs3500.reversi.adapter.TheirMostTilesOurStrategyInterface;
import cs3500.reversi.adapter.TheirViewOurViewInterface;
import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.BasicReversiGame;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReadOnlyReversiInterface;
import cs3500.reversi.model.ReversiInterface;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.PlayerInterface;
import cs3500.reversi.provider.Features;
import cs3500.reversi.provider.Strategy;
import cs3500.reversi.strategy.AsManyCapturesAsPossible;
import cs3500.reversi.strategy.MiniMax;
import cs3500.reversi.strategy.NoNextToCorners;
import cs3500.reversi.strategy.PrioritizeCorners;
import cs3500.reversi.strategy.ReversiStrategy;
import cs3500.reversi.view.GUI;
import cs3500.reversi.view.GUIView;

/**
 * The main class that can start a Reversi game given command line arguments.
 */
public final class Reversi {
  private static int index;
  private static int rowLength;
  private static int height;
  private static PlayerInterface[] players;
  private static boolean custom;

  /**
   * The main method, which will launch the game. There are 2 optional arguments at the beginning
   * that will set the length and width of the board. If no integers are given, then the game will
   * use the default 11 x 11 board. Then the user must enter 2 strategies. For human players,
   * they simply enter "human". For the ai, there are four strategies. Strategy1 is as many captures
   * as possible. Strategy 2 is avoiding edges. Strategy3 is prioritizing corners. Strategy4 is
   * minimax, which requires that the next argument is the strategy the ai thinks the opponent is
   * using. Notice how in each strategy, there is no space between strategy and the number.
   *
   * @param args The Command Line Arguments.
   */
  public static void main(String[] args) {
    index = 0;
    rowLength = 11;
    height = 11;
    players = new PlayerInterface[2];
    custom = false;
    // This helper will try to get the given row and length if they exist, and if they do it will
    // update the custom boolean field to be true.
    getLengthAndHeight(args);

    cs3500.reversi.provider.Reversi adapterGame = null;
    ReversiInterface game = null;

    boolean provView = doesArgsWantProviderView(args);
    if (rowLength == height && provView) {
      adapterGame = new OurModelTheirModelInterface((rowLength + 1) / 2);
      adapterGame.startGame();
    } else if (!provView) {
      game = new BasicReversiGame();
      // Given length and height may not be valid, so try-catch needed.
      try {
        game.startGame(rowLength, height);
      } catch (IllegalStateException | IllegalArgumentException ex) {
        throw new IllegalArgumentException(ex.getMessage());
      }
    } else if (rowLength != height && provView) {
      throw new IllegalArgumentException("If you want to use the provider's view, you must have" +
              " a perfectly symmetrical board, meaning the row length must be equal to the height");
    }


    // Set up the players
    getPlayers(args, game, adapterGame);


    List<GUIView> views = getViews(args, game, adapterGame);
    // Set up views
    GUIView view1 = views.get(0);
    GUIView view2 = views.get(1);

    // Make the controllers
    if (game != null) {
      Controller controller1 = new Controller(game, view1, players[0]);
      Controller controller2 = new Controller(game, view2, players[1]);
    } else {
      if (view1 instanceof TheirViewOurViewInterface) {
        Features controller1 = new OurControllerTheirFeatures((ReversiInterface) adapterGame, view1,
                players[0]);
      } else {
        Controller controller1 = new Controller((ReversiInterface) adapterGame, view1, players[0]);
      }
      if (view2 instanceof TheirViewOurViewInterface) {
        Features controller2 = new OurControllerTheirFeatures((ReversiInterface) adapterGame, view2,
                players[1]);
      } else {
        Controller controller2 = new Controller((ReversiInterface) adapterGame, view2, players[1]);
      }
    }
  }


  // Helper method for when the length and height of the board are custom.
  private static void getLengthAndHeight(String[] args) {
    try {
      rowLength = Integer.parseInt(args[0]);
      index++;
    } catch (NumberFormatException ignore) {
      return;
    }
    try {
      height = Integer.parseInt(args[1]);
      index++;
      // If both length and height are successfully set, then it is custom settings.
      custom = true;
    } catch (NumberFormatException ignore) {
      throw new IllegalArgumentException("If you enter a custom length, you must enter a custom " +
              "height. ");
    }
  }

  // Helper method to set up the players for the game, whether they are human or AI.
  private static void getPlayers(String[] args, ReversiInterface game,
                                 cs3500.reversi.provider.Reversi adapterGame) {
    if (game != null) {
      players[0] = getPlayerType(args, Player.Player1, game, null);
      players[1] = getPlayerType(args, Player.Player2, game, null);
    } else {
      players[0] = getPlayerType(args, Player.Player1, null, adapterGame);
      players[1] = getPlayerType(args, Player.Player2, null, adapterGame);
    }
  }

  // A helper method to get the type of player, human or AI.
  private static PlayerInterface getPlayerType(String[] args, Player player1or2,
                                               ReversiInterface game,
                                               cs3500.reversi.provider.Reversi adapterGame) {
    if (game != null) {
      errorNotEnoughArgs(args);
      switch (args[index].toLowerCase()) {
        case "human":
          index++;
          return new HumanPlayer(player1or2);
        case "strategy1":
          index++;
          return new AIPlayer(game, player1or2, new AsManyCapturesAsPossible());
        case "strategy2":
          index++;
          return new AIPlayer(game, player1or2, new NoNextToCorners(false));
        case "strategy3":
          index++;
          return new AIPlayer(game, player1or2, new PrioritizeCorners(false));
        case "strategy4":
          index++;
          ReversiStrategy oppStrat;
          oppStrat = getStrat(args);
          return new AIPlayer(game, player1or2, new MiniMax(oppStrat));
        case "providerstrategy1":
          index++;
          return new AIPlayer(game, player1or2, new TheirMostTilesOurStrategyInterface());
        case "providerstrategy2":
          index++;
          ReversiStrategy oppStrat2;
          oppStrat2 = getStrat(args);
          return new AIPlayer(game, player1or2,
                  new TheirAvoidNextToCornersOurStrategyInterface((Strategy) oppStrat2));
        case "providerstrategy3":
          index++;
          ReversiStrategy oppStrat3;
          oppStrat3 = getStrat(args);
          return new AIPlayer(game, player1or2,
                  new TheirGetCornersOurStrategyInterface((Strategy) oppStrat3));
        default:
          throw new IllegalArgumentException("Not a valid input");
      }
    } else {
      errorNotEnoughArgs(args);
      switch (args[index].toLowerCase()) {
        case "human":
          index++;
          return new HumanPlayer(player1or2);
        case "strategy1":
          index++;
          return new AIPlayer((ReversiInterface) adapterGame, player1or2,
                  new AsManyCapturesAsPossible());
        case "strategy2":
          index++;
          return new AIPlayer((ReversiInterface) adapterGame, player1or2,
                  new NoNextToCorners(false));
        case "strategy3":
          index++;
          return new AIPlayer((ReversiInterface) adapterGame, player1or2,
                  new PrioritizeCorners(false));
        case "strategy4":
          index++;
          ReversiStrategy oppStrat;
          oppStrat = getStrat(args);
          return new AIPlayer((ReversiInterface) adapterGame, player1or2, new MiniMax(oppStrat));
        case "providerstrategy1":
          index++;
          return new AIPlayer((ReversiInterface) adapterGame, player1or2,
                  new TheirMostTilesOurStrategyInterface());
        case "providerstrategy2":
          index++;
          ReversiStrategy oppStrat2;
          oppStrat2 = getStrat(args);
          return new AIPlayer((ReversiInterface) adapterGame, player1or2,
                  new TheirAvoidNextToCornersOurStrategyInterface((Strategy) oppStrat2));
        case "providerstrategy3":
          index++;
          ReversiStrategy oppStrat3;
          oppStrat3 = getStrat(args);
          return new AIPlayer((ReversiInterface) adapterGame, player1or2,
                  new TheirGetCornersOurStrategyInterface((Strategy) oppStrat3));
        default:
          throw new IllegalArgumentException("Not a valid input");
      }
    }
  }

  // A helper method to get a strategy, needed for finding what strategy minimax is guessing the
  // opponent uses.
  private static ReversiStrategy getStrat(String[] args) {
    errorNotEnoughArgs(args);
    switch (args[index].toLowerCase()) {
      case "strategy1":
        index++;
        return new AsManyCapturesAsPossible();
      case "strategy2":
        index++;
        return new NoNextToCorners(false);
      case "strategy3":
        index++;
        return new PrioritizeCorners(false);
      case "strategy4":
        index++;
        // Recursive so minimax can work.
        ReversiStrategy oppStrat = getStrat(args);
        return new MiniMax(oppStrat);
      case "providerstrategy1":
        index++;
        return new TheirMostTilesOurStrategyInterface();
      case "providerstrategy2":
        index++;
        ReversiStrategy oppStrat2 = getStrat(args);
        return new TheirAvoidNextToCornersOurStrategyInterface((Strategy) oppStrat2);
      case "providerstrategy3":
        index++;
        ReversiStrategy oppStrat3 = getStrat(args);
        return new TheirGetCornersOurStrategyInterface((Strategy) oppStrat3);
      default:
        throw new IllegalArgumentException("Not a valid input");
    }
  }

  private static List<GUIView> getViews(String[] args, ReversiInterface game,
                                        cs3500.reversi.provider.Reversi adapterGame) {
    // Default (no argument) equals two views from our code
    if (args.length - 1 < index) {
      return Arrays.asList(new GUI(game, "player1", false),
              new GUI(game, "player2", false));
    }
    List<GUIView> result = new ArrayList<>();
    if (adapterGame != null) {
      int playerNum = 1;
      while (args.length - 1 >= index && result.size() < 2) {
        errorNotEnoughArgs(args);
        switch (args[index].toLowerCase()) {
          case "default":
            result.add(new GUI((ReadOnlyReversiInterface) adapterGame, "player" + playerNum,
                    false));
            playerNum++;
            index++;
            break;
          case "provider":
            if (rowLength != height) {
              throw new IllegalArgumentException("Provider view will only work with perfectly " +
                      "symmetric boards, meaning the length and height of the board must be " +
                      "equal.");
            }
            result.add(new TheirViewOurViewInterface(adapterGame));
            playerNum++;
            index++;
            break;
          default:
            throw new IllegalArgumentException("Not a view type. Views are either default or" +
                    " provider");
        }
      }
    } else {
      result.add(new GUI(game, "player1", false));
      result.add(new GUI(game, "player2", false));
    }
    if (result.size() != 2) {
      throw new IllegalArgumentException("Not enough inputs for the views. Either enter none for" +
              "default settings, or 2 views.");
    }
    return result;
  }

  private static boolean doesArgsWantProviderView(String[] args) {
    for (String string : args) {
      if (string.toLowerCase().equals("provider")) {
        return true;
      }
    }
    return false;
  }

  // A helper method that throws an illegal argument exception if there are not enough arguments.
  private static void errorNotEnoughArgs(String[] args) {
    if (index >= args.length) {
      throw new IllegalArgumentException("Not enough inputs, try checking if you have any " +
              "strategies that need a second argument.");
    }
  }
}
