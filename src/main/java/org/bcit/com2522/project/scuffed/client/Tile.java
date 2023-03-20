package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.io.Serializable;

public class Tile implements Serializable {
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

    /**
     * Constructor for loading a map from a file. //TODO: maybe add an "occupied" boolean to this class
     *
     * @param position
     * @param type
     */
    public Tile(Position position, int type) {
        this.position = position;
        this.type = type;
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
    public static Tile fromJSONObject(JSONObject tiles) {
        Tile tile = new Tile(Position.fromJSONObject((JSONObject) tiles.get("position")), (int)(long) tiles.get("type"));
        return tile;
    }
}
