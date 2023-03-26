package org.bcit.com2522.project.scuffed.ai;

import org.bcit.com2522.project.scuffed.client.*;

import java.util.ArrayList;

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
        ArrayList<Entity> allies = new ArrayList<Entity>();
        ArrayList<Entity> enemies = new ArrayList<Entity>();

        for (Entity[] row : gameState.getEntities()) {
            for (Entity entity : row) {
                if (entity != null) {
                    if (entity.getOwnerID() == gameState.getCurrentPlayerID())
                        allies.add(entity);
                    else
                        enemies.add(entity);
                }
            }
        }

        for (Entity entity : allies) {
            if (entity instanceof Worker) {
                while (gameState.getMap().get(entity.getPosition().getX(), entity.getPosition().getY()).getType() < 1) {
                    Position free = entity.getFreePosition(gameState.getEntities());
                    if (free != null && ((Unit)entity).getRemainMove() > 0)
                        ((Unit) entity).move(gameState.getEntities(), new Position(free.getX(), free.getY()), 0, 0);
                    else
                        break;
                }
                if (numBuildings < numWorkers) {
                    ((Worker) entity).buildBuilding(gameState.getEntities());
                    numBuildings++;
                } else {
                    ((Worker) entity).collect(gameState.getMap().get(entity.getPosition().getX(), entity.getPosition().getY()));
                }
            } else if (entity instanceof Building) {
                if (numSoldiers < 2 * numWorkers) {
                    ((Building) entity).buildSoldier(gameState.getEntities());
                    numSoldiers++;
                } else {
                    ((Building) entity).buildWorker(gameState.getEntities());
                    numWorkers++;
                }
            } else if (entity instanceof Soldier) {
                for (Entity enemy : enemies) {
                    if (!((Soldier) entity).withinRange(enemy.getPosition())) {
                        ((Unit)entity).moveTowards(gameState.getEntities(), enemy.getPosition(),0,0);
                    }

                    System.out.println("trying to attack");
                    ((Soldier) entity).attack(gameState.getEntities(), enemy);
                }
            }
        }

        gameState.nextTurn();
        System.out.println("ai turn over");
    }

    public void moveTowards(Unit selected, Entity target) {

    }
}
