package org.bcit.com2522.project.scuffed.ai;

import org.bcit.com2522.project.scuffed.client.*;

public class AI {
    private int numWorkers;
    private int numBuildings;
    private int numSoldiers;

    public AI() {
        numWorkers = 1;
        numBuildings = 0;
        numSoldiers = 0;
    }

    public void start (GameState gameState) {

        System.out.println("ai is in control");
        gameState.resetShift();

        for (Entity[] row : gameState.getEntities()) {
            for (Entity entity : row) {
                if (entity != null && entity.getOwnerID() == gameState.getCurrentPlayerID()) {
                    if (entity instanceof Unit) {
                        ((Unit) entity).move(gameState.getEntities(), new Position(entity.getPosition().getX() - 1, entity.getPosition().getY() - 1), 0, 0);
                    }
                    if (entity instanceof Worker) {
                        if (numBuildings < numWorkers) {
                            ((Worker) entity).buildBuilding(gameState.getEntities());
                            numBuildings++;
                        } else {
                            ((Worker) entity).collect(gameState.getMap().get(entity.getPosition().getX(), entity.getPosition().getY()));
                        }
                    } else if (entity instanceof Building) {
                        if (numSoldiers < 0.5 * numWorkers) {
                            ((Building) entity).buildSoldier(gameState.getEntities());
                        } else {
                            ((Building) entity).buildWorker(gameState.getEntities());
                        }
                    } else if (entity instanceof Soldier) {
                        for (Entity[] row2 : gameState.getEntities()) {
                            for (Entity entity2 : row2) {
                                if (entity2 != null && entity2.getOwnerID() != gameState.getCurrentPlayerID()) {
                                    ((Soldier) entity).attack(gameState.getEntities(), entity2);
                                    System.out.println("trying to attack");
                                }
                            }
                        }
                    }
                }
            }
        }

        gameState.nextTurn();
        System.out.println("ai turn over");
    }

    public void moveTowards(Unit selected, Entity target) {

    }
}
