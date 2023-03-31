package org.bcit.com2522.project.scuffed.client;


import org.json.simple.JSONObject;

import java.awt.*;

public abstract class Unit extends Entity { //things that can move
    int maxMove;
    int remainMove;

    public Unit(int ownerId, int health, int cost, int speed) {
        super(ownerId, health, cost);
        this.maxMove = speed;
        this.remainMove = maxMove;
    }

    public void resetMove() {
        remainMove = maxMove;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject unitObject = super.toJSONObject();
        unitObject.put("maxMove", maxMove);
        unitObject.put("remainMove", remainMove);
        return unitObject;
    }

    public Boolean withinMoveRange(Position position, Entity[][] entities) {
        return Math.abs(position.getX() - this.getPosition(entities).getX()) + Math.abs(position.getY() - this.getPosition(entities).getY()) <= remainMove;
    }

    /**
     *
     * @param entities current list of entities on the map
     * @param position the position to travel to
     */
    public void move(Entity[][] entities, Position position) {
        Position oldPos = getPosition(entities);
        if (entities[position.getX()][position.getY()] != null) {
            System.out.println("that position is occupied");
        } else if (!withinMoveRange(position, entities)) {
            System.out.println("you can't move that far");
        } else {
            remainMove -= Math.abs(position.getX() - this.getPosition(entities).getX()) + Math.abs(position.getY() - this.getPosition(entities).getY());
            entities[oldPos.getX()][oldPos.getY()] = null;
            entities[position.getX()][position.getY()] = this;
        }
    }

    public int getRemainMove() {
        return remainMove;
    }
}
