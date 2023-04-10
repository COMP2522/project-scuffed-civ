package org.bcit.com2522.project.scuffed.ai;

import java.util.ArrayList;
import org.bcit.com2522.project.scuffed.client.*;

/**
 * The type Gs generator.
 */
public class GSGenerator {
  /**
   * The constant state.
   */
  public static GameState state;
  /**
   * The Entities.
   */
  public static ArrayList<Entity> entities;

  /**
   * Generate game state from entity game state.
   *
   * @param myEntities my entities
   * @param gameState  the game state
   * @return the game state
   */
  public static GameState generateGameStateFromEntity(ArrayList<Entity> myEntities, GameState gameState) {
    entities = myEntities;
    state = gameState;

    for (Entity entity : entities) {
      takeBestMove(entity);
    }

    return state;
  }


  /**
   * takes the best move for a specific entity
   * @param entity the entity to take the move
   */
  private static void takeBestMove(Entity entity) {
    ArrayList<GameState> possibleMoves = generateMoves(entity);

    if (possibleMoves.size() > 0) {
      state = determineBestMove(possibleMoves);
    }
  }

  /**
   * takes a list of moves and chooses the best one
   * @param possibleMoves list of moves
   * @return best move
   */
  private static GameState determineBestMove(ArrayList<GameState> possibleMoves) {
    GameState bestGameState = possibleMoves.get(0);
    for (GameState gameState : possibleMoves) {
      if (gameState.compareTo(bestGameState) > 0) {
        bestGameState = gameState;
      }
    }

    return bestGameState;
  }

  /**
   * generates all possible moves for a specific entity
   * @param entity entity
   * @return all moves
   */
  private static ArrayList<GameState> generateMoves(Entity entity) {
    ArrayList<GameState> possibleMoves = new ArrayList<>();
    Position position = entity.getPosition(state.getEntities());

    if (entity instanceof Unit unit) {
      for (int i = -unit.getMaxMove(); i <= unit.getMaxMove(); i++) { //every possible move
        for (int j = -unit.getMaxMove(); j <= unit.getMaxMove(); j++) {
          if (position != null && (unit.withinMoveRange(new Position(position.getX() + i, position.getY() + j), state.getEntities()))) {
            GameState gs = new GameState(state); //creates a deep copy of state
            Unit unit2 = ((Unit) gs.getEntities()[position.getX()][position.getY()]);


            unit2.move(gs.getEntities(), new Position(position.getX() + i, position.getY() + j));
            possibleMoves.add(gs);
            generateActions(unit2, possibleMoves);
          }
        }
      }
    } else {
      if (position != null) {
        GameState gs = new GameState(state);
        Building building = ((Building) gs.getEntities()[position.getX()][position.getY()]);

        possibleMoves.add(gs);
        generateActions(building, possibleMoves);
      }
    }
    return possibleMoves;
  }

  /**
   * generates all possible actions for a specific entity
   * @param entity entity
   * @param gs list of moves
   */
  private static void generateActions(Entity entity, ArrayList<GameState> gs) {
    Position position = entity.getPosition(gs.get(gs.size() - 1).getEntities());
    if (entity instanceof Soldier soldier) {
      for (int i = -soldier.getRange(); i <= soldier.getRange(); i++) { //every possible position to shoot
        for (int j = -soldier.getRange(); j <= soldier.getRange(); j++) {
          if (position != null && (soldier.withinRange(new Position(position.getX() + i, position.getY() + j), gs.get(gs.size() - 1).getEntities()))) {
            GameState gs1 = new GameState(gs.get(gs.size() - 1));
            soldier = (Soldier) gs1.getEntities()[position.getX()][position.getY()];
            soldier.attack(gs1.getEntities(), gs1.getEntities()[position.getX() + i][position.getY() + j]);
            gs.add(gs1);
          }
        }
      }
    } else if (entity instanceof Worker) {
      GameState gs0 = gs.get(gs.size() - 1);
      GameState gs1 = new GameState(gs0);
      GameState gs2 = new GameState(gs0);
      if (entity.getPosition(gs0.getEntities()) != null) {
        ((Worker) gs1.getEntities()[position.getX()][position.getY()]).buildBuilding(gs1.getEntities());
        ((Worker) gs2.getEntities()[position.getX()][position.getY()]).collect(gs2.getMap().get(entity.getPosition(gs0.getEntities())));
      }
      gs.add(gs1);
      gs.add(gs2);
    }
    if (entity instanceof Building) {
      GameState gs0 = gs.get(gs.size() - 1);
      GameState gs1 = new GameState(gs0);
      GameState gs2 = new GameState(gs0);
      if (entity.getPosition(gs0.getEntities()) != null) {
        ((Building) gs1.getEntities()[position.getX()][position.getY()]).buildSoldier(gs1.getEntities());
        ((Building) gs2.getEntities()[position.getX()][position.getY()]).buildWorker(gs2.getEntities());
      }
      gs.add(gs1);
      gs.add(gs2);
    }
  }


}
