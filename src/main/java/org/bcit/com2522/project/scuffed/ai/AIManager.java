package org.bcit.com2522.project.scuffed.ai;

import java.util.ArrayList;
import org.bcit.com2522.project.scuffed.client.Entity;
import org.bcit.com2522.project.scuffed.client.GameState;
import org.bcit.com2522.project.scuffed.client.Player;

/**
 * The type Ai manager.
 */
public class AIManager {

  /**
   * Start.
   *
   * @param gameState the game state
   */
  public static void start(GameState gameState) {
    // if this function is called on a non-AI player return
    if (!gameState.currentPlayer.isAI()) {
      return;
    }

    // generate allies arraylist
    ArrayList<Entity> allies = new ArrayList<Entity>();
    for (Entity[] row : gameState.getEntities()) {
      for (Entity entity : row) {
        if (entity != null) {
          if (entity.getOwnerID() == gameState.getCurrentPlayerID()) {
            allies.add(entity);
          }
        }
      }
    }

    System.out.format("%d allies \n", allies.size());

    // generate GameStates using GSGenerator
    ArrayList<GameState> gameStates = new ArrayList<GameState>();
    for (int i = 0; i < allies.size(); i++) {
      gameStates.add(GSGenerator.generateGameStateFromEntity(allies, gameState));

      if (allies.size() > 0) {
        allies.add(allies.get(0));
        allies.remove(0);
      }
    }

    // pick best turn
    GameState bestGameTurn = gameStates.get(0);
    for (GameState gameState1 : gameStates) {

      if (gameState1.compareTo(bestGameTurn) > 0) {
        bestGameTurn = gameState1;
      }
    }

    // set gameState to be the best turn.
    gameState.setEntities(bestGameTurn.getEntities());
    gameState.setMap(bestGameTurn.getMap());
    //gameState.currentPlayer = new Player(bestGameTurn.currentPlayer);

    for (Player value : gameState.players) {
      gameState.players.remove(value);
    }

    for (Player value : bestGameTurn.players) {
      gameState.players.add(new Player(value));

      if (bestGameTurn.currentPlayer.equals(value)) {
        gameState.currentPlayer = gameState.players.getLast();
      }
    }
    gameState.nextTurn();
  }
}
