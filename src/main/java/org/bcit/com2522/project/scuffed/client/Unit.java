package org.bcit.com2522.project.scuffed.client;


import org.json.simple.JSONObject;

import java.awt.*;

public abstract class Unit extends Entity { //things that can move TODO: maybe make this abstract
    int maxMove;
    int remainMove;

    public Unit(Position position, int ownerId, Color pColor, int health, int cost, int speed) {
        super(position, ownerId, pColor, health, cost);
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

    public Boolean withinMoveRange(Position position) {
        return Math.abs(position.getX() - this.position.getX()) + Math.abs(position.getY() - this.position.getY()) <= remainMove;
    }

    //TODO: combine this and moveTo
    public void move(Entity[][] entities, Position position, int xShift, int yShift) {
        Position oldPos = getPosition();
        if (entities[position.getX() - xShift][position.getY() - yShift] != null) {
            System.out.println("that position is occupied");
        } else if (!withinMoveRange(position)) {
            System.out.println("you can't move that far");
        } else {
            remainMove -= Math.abs(position.getX() - this.position.getX()) + Math.abs(position.getY() - this.position.getY());
            this.position = new Position(position.getX()-xShift, position.getY()-yShift);
            entities[oldPos.getX() + xShift][oldPos.getY() + yShift] = null;
            entities[position.getX()][position.getY()] = this;
        }
    }

    /**
     *
     * @param entities 2d array of entities on the map
     * @param position the position to move towards
     * @param xShift the xShift of the map
     * @param yShift the yShift of the map
     */
    public void moveTowards(Entity[][] entities, Position position, int xShift, int yShift) { //TODO this is fucking things up atm, will be removed
        Position tempPos = getPosition();
        while (remainMove > 0 && !tempPos.equals(position)) {
            if (Math.abs(position.getX() - getPosition().getX()) >= Math.abs(position.getY() - getPosition().getY())) {
                if (position.getX() > tempPos.getX() && entities[tempPos.getX() + 1][tempPos.getY()] == null)
                    tempPos.setX(tempPos.getX() + 1);
                else if (position.getX() < tempPos.getX() && entities[tempPos.getX() - 1][tempPos.getY()] == null)
                    tempPos.setX(tempPos.getX() - 1);
            } else {
                if (position.getY() > tempPos.getY() && entities[tempPos.getX()][tempPos.getY() + 1] == null)
                    tempPos.setY(tempPos.getY() + 1);
                else if (position.getY() < tempPos.getY() && entities[tempPos.getX()][tempPos.getY() - 1] == null)
                    tempPos.setY(tempPos.getY() - 1);
            }

            move(entities, tempPos, xShift, yShift);
            if (remainMove < 1 || tempPos.equals(getPosition()))
                break;
        }

        entities[getPosition().getX()][getPosition().getY()] = this;
    }

    public int getRemainMove() {
        return remainMove;
    }
}
