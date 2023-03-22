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
        JSONObject unitObject;
        if(this instanceof Worker) {
            unitObject = ((Worker)this).toJSONObject();
        } else if(this instanceof Soldier) {
            unitObject = ((Soldier)this).toJSONObject();
        } else {
           throw new IllegalArgumentException("this is not a valid unit");
        }
        unitObject.put("entityType", "unit");
        unitObject.put("maxMove", maxMove);
        unitObject.put("remainMove", remainMove);
        return unitObject;
    }

    public static Unit fromJSONObject(JSONObject unitObject, Window scene) {
        if(unitObject == null) {
            return null;
        }
        switch((String) unitObject.get("entityType")) {
            case "soldier":
                return Soldier.fromJSONObject(unitObject, scene);
            case "worker":
                return Worker.fromJSONObject(unitObject, scene);
            default:
                return null;
        }
    }
}
