package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.io.Serializable;

import static processing.awt.ShimAWT.loadImage;

public class Tile{
    private int type;

    public Tile() {
        type = (int) (Math.random() * 4); // 0-3
    }

    /**
     * Constructor for loading a map from a file. //TODO: maybe add an "occupied" boolean to this class
     *
     * @param type
     */
    public Tile(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        //obj.put("position", position.toJSONObject());
        obj.put("type", type);
        return obj;
    }
    public static Tile fromJSONObject(JSONObject tiles) {
        Tile tile = new Tile((int)(long) tiles.get("type"));
        return tile;
    }

    public int takeResources() {
        if (type >= 1) {
            type -= 1;
            return 1;
        }
        return 0;
    }

    public void increaseType() {
        type++;
    }
}
