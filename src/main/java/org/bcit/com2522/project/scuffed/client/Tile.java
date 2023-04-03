package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.io.Serializable;

import static processing.awt.ShimAWT.loadImage;

public class Tile{
    private int type;

    /**
     *  for creating a new tile.
     */
    public Tile() {
        type = (int) (Math.random() * 4); // 0-3
    }

    /**
     *  for loading a tile from JSON.
     *
     *  @param type
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
