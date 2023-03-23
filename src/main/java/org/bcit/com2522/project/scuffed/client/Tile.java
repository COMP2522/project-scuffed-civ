package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.io.Serializable;

import static processing.awt.ShimAWT.loadImage;

public class Tile{
    private Position position;
    private int type;

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

    public int takeResources() {
        if (type >= 1) {
            type -= 1;
            return 1;
        }
        return 0;
    }
}
