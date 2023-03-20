package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.io.Serializable;

import static processing.awt.ShimAWT.loadImage;


public class Unit extends Entity implements Serializable { //things that can move TODO: maybe make this abstract
    int maxMove;
    int remainMove;
    String unitType; //worker, soldier

    public Unit(Window scene, Position position, Player player) {
        super(scene, position, player);
        entityType = "unit";
        maxMove = 6;
        remainMove = maxMove;
    }

    public Unit(Window scene){
        super(scene);
        entityType = "unit";
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

    public JSONObject toJSONObject() {
        switch(unitType) {
            case "soldier":
                Soldier soldier = (Soldier) this;
                return soldier.toJSONObject();
            case "worker":
                Worker worker = (Worker) this;
                return worker.toJSONObject();
            default:
                return null;
        }
    }

    public static Unit fromJSONObject(JSONObject unitObject, Window scene) {
        if(unitObject == null) {
            return null;
        }
        switch((String) unitObject.get("unitType")) {
            case "soldier":
                return Soldier.fromJSONObject(unitObject, scene);
            case "worker":
                return Worker.fromJSONObject(unitObject, scene);
            default:
                return null;
        }
    }


}
