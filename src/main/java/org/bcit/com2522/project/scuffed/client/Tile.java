package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

public class Tile {
    private Position position;

    private int type;
    //0: grass
    //1: rocks
    //2: sand
    //3: water

    public Tile(Position position) {
        type = (int) (Math.random() * 4); // 1-4
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public int getType() {
        return type;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("position", position.toJSONObject());
        obj.put("type", type);
        return obj;
    }
}
