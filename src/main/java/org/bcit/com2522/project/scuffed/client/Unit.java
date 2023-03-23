package org.bcit.com2522.project.scuffed.client;


import org.json.simple.JSONObject;

public abstract class Unit extends Entity { //things that can move TODO: maybe make this abstract
    int maxMove;
    int remainMove;

    public Unit(Position position, Player owner) {
        super(position, owner);
        maxMove = 6;
        remainMove = maxMove;
    }

    public Unit(Position position, int ownerID) {
        super(position, ownerID);
        maxMove = 6;
        remainMove = maxMove;
    }

    public void resetMove() {
        remainMove = maxMove;
    }

    public Boolean moveTo(Position position) {
        //if the position is in range
        if(Math.abs(position.getX() - this.position.getX()) + Math.abs(position.getY() - this.position.getY()) <= remainMove) {
            remainMove -= Math.abs(position.getX() - this.position.getX()) + Math.abs(position.getY() - this.position.getY());
            this.position = position;
            return true;
        } else {
            System.out.println("that position is out of range");
            return false;
        }
    }
    @Override
    public JSONObject toJSONObject() {
        JSONObject unitObject = super.toJSONObject();
        unitObject.put("maxMove", maxMove);
        unitObject.put("remainMove", remainMove);
        return unitObject;
    }

    //TODO: combine this and moveTo
    public void move(Entity[][] entities, Position position, int x, int y, int xShift, int yShift) {
        Position oldPos = getPosition();
        if (moveTo(new Position(x - xShift, y - yShift))) {
            entities[oldPos.getX() + xShift][oldPos.getY() + yShift] = null;
            entities[x][y] = this;
            //selected = null;
        }
    }
}
