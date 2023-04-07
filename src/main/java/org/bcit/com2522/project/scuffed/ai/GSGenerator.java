package org.bcit.com2522.project.scuffed.ai;

import org.bcit.com2522.project.scuffed.client.*;

import java.util.ArrayList;

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
                    Position position = unit.getPosition(state.getEntities());
                    if (position != null && (unit.withinMoveRange(new Position(position.getX() + i, position.getY() + j), state.getEntities()))) {
                        System.out.println("adding a move");
                        GameState gs = new GameState(state); //creates a deep copy of state
                        Unit unit2 = ((Unit)gs.getEntities()[unit.getPosition(state.getEntities()).getX()][unit.getPosition(state.getEntities()).getY()]);
                        unit2.move(gs.getEntities(), new Position(position.getX() + i, position.getY() + j));

                        possibleMoves.add(gs);
                        //possibleMoves.addAll(generateActions(unit));
                        generateActions(unit2, possibleMoves);
                    }
                }
            }
        } else {
            Building building = ((Building) entity);
            GameState gs = state;
            possibleMoves.add(gs);
            generateActions(building, possibleMoves);
        }
        return possibleMoves;
    }

    private static void generateActions(Entity entity, ArrayList<GameState> gs) {
        if (entity instanceof Soldier soldier) {
            for (int i = -soldier.getRange(); i <= soldier.getRange(); i++) { //every possible move
                for (int j = -soldier.getRange(); j <= soldier.getRange(); j++) {
                    Position position = entity.getPosition(gs.get(gs.size() - 1).getEntities());
                    if (position != null && (soldier.withinRange(new Position(position.getX() + i, position.getY() + j), gs.get(gs.size() - 1).getEntities()))) {
                        GameState gs1 = new GameState(gs.get(gs.size() - 1));
                        soldier.attack(gs1.getEntities(), gs1.getEntities()[position.getX() + i][position.getY() + j]);
                        gs.add(gs1);
                    }
                }
            }
        } else if (entity instanceof Worker) {
            GameState gs0 = gs.get(gs.size() - 1);
            GameState gs1 = new GameState(gs0);
            GameState gs2 = new GameState(gs0);
            ((Worker)gs1.getEntities()[entity.getPosition(gs0.getEntities()).getX()][entity.getPosition(gs0.getEntities()).getY()]).buildBuilding(gs1.getEntities());
            ((Worker)gs2.getEntities()[entity.getPosition(gs0.getEntities()).getX()][entity.getPosition(gs0.getEntities()).getY()]).collect(gs2.getMap().get(entity.getPosition(gs0.getEntities())));
            gs.add(gs1);
            gs.add(gs2);
        } else if (entity instanceof Building) {
            GameState gs0 = gs.get(gs.size() - 1);
            GameState gs1 = new GameState(gs0);
            GameState gs2 = new GameState(gs0);
            ((Building)gs1.getEntities()[entity.getPosition(gs0.getEntities()).getX()][entity.getPosition(gs0.getEntities()).getY()]).buildSoldier(gs1.getEntities(), 1, 1, 1, 1);
            ((Building)gs2.getEntities()[entity.getPosition(gs0.getEntities()).getX()][entity.getPosition(gs0.getEntities()).getY()]).buildWorker(gs2.getEntities());
            gs.add(gs1);
            gs.add(gs2);
        }
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
        System.out.format("%d possible moves\n", possibleMoves.size());

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
