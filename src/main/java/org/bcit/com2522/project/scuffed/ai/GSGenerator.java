package org.bcit.com2522.project.scuffed.ai;

import org.bcit.com2522.project.scuffed.client.*;

import java.util.ArrayList;

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
   * @param myEntities the my entities
   * @param gameState  the game state
   * @return the game state
   */
  public static GameState generateGameStateFromEntity(ArrayList<Entity> myEntities, GameState gameState) {
    entities = myEntities;
    state = gameState;

    int i = 0;
    while (i < entities.size()) {
      takeBestMove(entities.get(i));
      i++;
    }

    state.printEntities();

    return state;
  }

  private static void takeBestMove(Entity entity) {
    ArrayList<GameState> possibleMoves = generateMoves(entity);
    System.out.format("%d possible moves\n", possibleMoves.size());

    if (possibleMoves.size() > 0) {
      state = determineBestMove(possibleMoves);
    }
  }

  //finds the best move of the list and sets the gamestate to the result of that move.
  private static GameState determineBestMove(ArrayList<GameState> possibleMoves) {
    GameState bestGameState = possibleMoves.get(0);
    for (GameState gameState : possibleMoves) {
      if (gameState.compareTo(bestGameState) > 0) {
        bestGameState = gameState;
      }
    }

    return bestGameState;
  }

  //generate moves
  //generates every possible move an entity could make.
  private static ArrayList<GameState> generateMoves(Entity entity) {
    ArrayList<GameState> possibleMoves = new ArrayList<>();

    if (entity instanceof Unit unit) {
      for (int i = -unit.getMaxMove(); i <= unit.getMaxMove(); i++) { //every possible move
        for (int j = -unit.getMaxMove(); j <= unit.getMaxMove(); j++) {
          Position position = unit.getPosition(state.getEntities());
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
      Building building = ((Building) entity);
      GameState gs = new GameState(state);
      possibleMoves.add(gs);
      generateActions(building, possibleMoves);
    }
    return possibleMoves;
  }

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
    } else if (entity instanceof Building) {
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
