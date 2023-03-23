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
    public void move(Entity[][] entities, Position position, int xShift, int yShift) {
        Position oldPos = getPosition();
        if (entities[position.getX() - xShift][position.getY() - yShift] != null) {
            System.out.println("cannot move there");
            return;
        } else if (moveTo(new Position(position.getX() - xShift, position.getY() - yShift))) {
            entities[oldPos.getX() + xShift][oldPos.getY() + yShift] = null;
            entities[position.getX()][position.getY()] = this;
            //selected = null;
        }
    }

    public void moveTowards(Entity[][] entities, Position position, int xShift, int yShift) {
        Position tempPos = getPosition();
        while (remainMove > 0 && !tempPos.equals(position)) {
            if (Math.abs(position.getX() - getPosition().getX()) >= Math.abs(position.getY() - getPosition().getY())) {
                if (position.getX() > tempPos.getX())
                    tempPos.setX(tempPos.getX() + 1);
                else if (position.getX() < tempPos.getX())
                    tempPos.setX(tempPos.getX() - 1);
            } else {
                if (position.getY() > tempPos.getY())
                    tempPos.setY(tempPos.getY() + 1);
                else if (position.getY() < tempPos.getY())
                    tempPos.setY(tempPos.getY() - 1);
            }

            move(entities, tempPos, xShift, yShift);
            if (remainMove < 1)
                break;
        }
    }

    public int getRemainMove() {
        return remainMove;
    }
}
