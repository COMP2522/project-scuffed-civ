package org.bcit.com2522.project.scuffed.ai;

import org.bcit.com2522.project.scuffed.client.Entity;
import org.bcit.com2522.project.scuffed.client.GameState;

import java.util.ArrayList;

public class AIManager {

    //for each entity on the board, create a gamestate that starts with that entity

    public static void start (GameState gameState) {
        //if this function is called on a non-AI player return
        if (!gameState.currentPlayer.isAI()) {
            return;
        }

        //generate allies arraylist
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


        //generate GameStates using GSGenerator
        ArrayList<GameState> gameStates = new ArrayList<GameState>();
        for (int i = 0; i < allies.size(); i++) {
            gameStates.add(GSGenerator.generateGameStateFromEntity(allies, gameState));

            if (allies.size() > 0) {
                allies.add(allies.get(0));
                allies.remove(0);
            }
        }


        System.out.format("%d possible turns \n", gameStates.size());

        //pick best turn
        GameState bestGameTurn = gameStates.get(0);
        for (GameState gameState1 : gameStates) {

            if (gameState1.compareTo(bestGameTurn) > 0) {
                bestGameTurn = gameState1;
            }
        }
        System.out.println("bestGameTurn:");
        bestGameTurn.printEntities();

        //set gameState to be the best turn.
        //gameState = bestGameTurn;
        gameState.setEntities(bestGameTurn.getEntities());
        gameState.setMap(bestGameTurn.getMap());

        System.out.println("final gameState:");
        gameState.printEntities();


        //set the current turn to next turn.
        gameState.nextTurn();
        System.out.println("ai turn over");
    }
}