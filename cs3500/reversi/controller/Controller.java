package cs3500.reversi.controller;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReversiInterface;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.PlayerInterface;
import cs3500.reversi.view.GUIView;

/**
 * A controller for a game of Reversi that works for both human players and AI players by setting
 * up listeners in the view (for humans) or in the player class itself (for AI), as well as in the
 * model.
 */
public class Controller implements Features, ModelFeatures {
  private ReversiInterface model;
  private GUIView view;
  private final Player player;
  private final PlayerInterface playerType;
  private boolean yourTurn;

  /**
   * A constructor for the controller.
   *
   * @param model The model of the Reversi game being played.
   * @param view The view for the player.
   * @param player The player, which is either human or AI.
   */
  public Controller(ReversiInterface model, GUIView view, PlayerInterface player) {
    this.player = Objects.requireNonNull(player).getWhichPlayer();
    this.playerType = player;
    // Calls helpers to set up listeners
    setView(Objects.requireNonNull(view));
    setModel(model);
  }

  // A helper that connects the view to the model. For humans, it adds a click listener and a key
  // listener (the features). For AI it adds a observer to the player class, to listen for AI made
  // moves.
  protected void setView(GUIView view) {
    this.view = Objects.requireNonNull(view);
    if (this.playerType instanceof HumanPlayer) {
      this.view.addFeatures(this);
      this.view.addClickListener();
    }
    if (this.playerType instanceof AIPlayer) {
      this.playerType.addFeatures(this);
    }
  }

  // Adds an observer to the model so it can update the view and keep track of whose turn it is.
  protected void setModel(ReversiInterface model) {
    this.model = Objects.requireNonNull(model);
    this.yourTurn();
    this.model.addObserver(this);
  }


  @Override
  public void makeMove(int x, int y) {
    // Checks boolean field determining if it is your turn.
    if (!this.yourTurn) {
      view.illegalMovePopUp("It is not your turn");
    }
    // Makes sure game is not over.
    if (!model.isGameOver() && this.yourTurn) {
      try {
        model.placeDisc(x, y);
      } catch (IllegalStateException iSE) {
        view.illegalMovePopUp(iSE.getMessage());
      }
    }
    view.refresh();
  }

  @Override
  public void passTurn() {
    if (!this.yourTurn) {
      view.illegalMovePopUp("It is not your turn");
    }
    if (!model.isGameOver() && this.yourTurn) {
      model.pass();
    }
    view.refresh();
  }

  @Override
  public boolean thisPlayersTurn() {
    return yourTurn;
  }

  @Override
  public void yourTurn() {
    this.yourTurn = model.getTurn() == this.player;
    handleAIPlayer();
  }

  // A helper method that will cause the AI to make a move if it is there turn.
  private void handleAIPlayer() {
    view.refresh();
    if (this.playerType instanceof AIPlayer) {
      if (yourTurn && !model.isGameOver()) {
        // Add a 2-second pause for visual effect, make it seem like the computer is thinking.
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
          @Override
          public void run() {
            Controller.this.playerType.makeMove();
            Controller.this.view.refresh();
          }
        }, 2000);
      }
    }
  }
}
