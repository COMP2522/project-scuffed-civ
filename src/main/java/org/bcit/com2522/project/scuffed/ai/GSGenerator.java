package org.bcit.com2522.project.scuffed.ai;

import org.bcit.com2522.project.scuffed.client.*;

import java.util.ArrayList;
import java.util.Collection;

public class GSGenerator {
    public static GameState state;
    public static ArrayList<Entity> entities;

    //generate moves
    //generates every possible move an entity could make.
    private static ArrayList<GameState> generateMoves(Entity entity) {
        ArrayList<GameState> possibleMoves = new ArrayList<GameState>();

        if (entity instanceof Unit unit) {
            for (int i = -unit.getMaxMove(); i <= unit.getMaxMove(); i++) { //every possible move
                for (int j = -unit.getMaxMove(); j <= unit.getMaxMove(); j++) {
                    Unit thisUnit = unit;
                    Position position = thisUnit.getPosition(state.getEntities());
                    if ((thisUnit.withinMoveRange(new Position(position.getX() + i, position.getY() + j), state.getEntities()))) {
                        thisUnit.move(state.getEntities(), new Position(position.getX() + i, position.getY() + j));
                        possibleMoves.addAll(generateActions(thisUnit));
                    }
                }
            }
        } else {
            Building building = ((Building) entity);
            possibleMoves.addAll(generateActions(building));
        }
        return possibleMoves;
    }

    private static Collection<? extends GameState> generateActions(Entity entity) {
        ArrayList<GameState> actions = new ArrayList<GameState>();
        if (entity instanceof Soldier) {
            //similar logic to the move function
        } else if (entity instanceof Worker) {
            //make copy
        } else if (entity instanceof Building) {
            //make copy
        }
        return actions;
    }

    //determinebestmove
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

    private static void takeBestMove(Entity entity) {
        ArrayList<GameState> possibleMoves = generateMoves(entity);

        state = determineBestMove(possibleMoves);
    }



    public static GameState generateGameStateFromEntity(ArrayList<Entity> myEntities, GameState gameState) {
        entities = myEntities;
        state = gameState;

        //generate best move for each entity, starting with entity 0 owned by currentPlayer.
        // if that move involves making a new entity, add them to the arraylist.
        while (entities.size() > 0) {
            takeBestMove(entities.get(0));
            entities.remove(0);
        }



        return state;
    }
}
