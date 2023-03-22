package org.bcit.com2522.project.scuffed.ai;

import org.bcit.com2522.project.scuffed.client.Entity;
import org.bcit.com2522.project.scuffed.client.GameState;
import org.bcit.com2522.project.scuffed.client.Player;

public class AI {

    public AI() {

    }

    public void start (GameState gameState) {

        System.out.println("ai is in control");
        gameState.shift(1,1);

        for (Entity[] row : gameState.getEntities()) {
            for (Entity entity : row) {
                if (entity != null && entity.getOwnerID() == gameState.getCurrentPlayerID()) {

                }
            }
        }

        gameState.nextTurn();
        System.out.println("ai turn over");
    }
}
