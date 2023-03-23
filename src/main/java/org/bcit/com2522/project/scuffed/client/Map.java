package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import processing.core.PImage;

import java.awt.*;
import java.io.Serializable;

import static org.bcit.com2522.project.scuffed.client.Window.PImages;
import static processing.awt.ShimAWT.loadImage;

public class Map { //this is a tile manager
    int width;
    int height;
    Tile[][] tiles;
    private Color color;

    /**
     * Constructor used in loading a map from JSON.
     */
    public Map(){
        this.color = (Color.red);
    }

    public Map (int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = new Tile(new Position(i,j));
            }
        }

        this.color = (Color.red);
    }

    public void shift(int x, int y) {
        for (Tile[] row: tiles) {
            for (Tile element: row) {
                element.setPosition(new Position(element.getPosition().getX() + x,
                        element.getPosition().getY() + y));
            }
        }
    }

    public void draw(int zoomAmount, Window scene) {
        //this.scene.fill(color.getRed());
        //this.scene.circle(50, 50, 50);
        //this.scene.image(grass, 500,500);
        for (Tile[] row: tiles) {
            for (Tile element: row) {
                if(element.getType() == 0)
                    scene.image(PImages.get("grassTile"), element.getPosition().getX()*zoomAmount,element.getPosition().getY()*zoomAmount);
                else if(element.getType() == 1)
                    scene.image(PImages.get("rockTile"), element.getPosition().getX()*zoomAmount,element.getPosition().getY()*zoomAmount);
                else if(element.getType() == 2)
                    scene.image(PImages.get("waterTile"), element.getPosition().getX()*zoomAmount,element.getPosition().getY()*zoomAmount);
                else if(element.getType() == 3)
                    scene.image(PImages.get("sandTile"), element.getPosition().getX()*zoomAmount,element.getPosition().getY()*zoomAmount);
            }
        }
    }

    /**
     * Converts the map to a JSONObject that stores a 2d JSONArray of tiles.
     *
     * @return JSONObject map
     */
    public JSONObject toJSONObject() {
        JSONObject map = new JSONObject();
        JSONArray tilesArray = new JSONArray();
        for (Tile[] row: tiles) {
            JSONArray rowArray = new JSONArray();
            for (Tile element: row) {
                rowArray.add(element.toJSONObject());
            }
            tilesArray.add(rowArray);
        }
        map.put("tiles", tilesArray);
        map.put("width", width);
        map.put("height", height);
        return map;
    }

    /**
     * Creates a map from a JSONObject.
     *
     * @param mapObject JSONObject map
     * @param scene Window scene
     * @return Map map
     */
    public static Map fromJSONObject(JSONObject mapObject, Window scene) {
        Map map = new Map();
        map.width = ((Long) mapObject.get("width")).intValue();
        map.height = ((Long) mapObject.get("height")).intValue();
        map.tiles = new Tile[map.width][map.height];
        for (int i = 0; i < map.tiles.length; i++) {
            for (int j = 0; j < map.tiles[i].length; j++) {
                map.tiles[i][j] = Tile.fromJSONObject((JSONObject) ((JSONArray) ((JSONArray) mapObject.get("tiles")).get(i)).get(j));
            }
        }
        return map;
    }

    /**
     * Creates a map from a JSONObject.
     *
     * @param mapObject JSONObject map
     * @param scene Window scene
     * @return Map map
     */
    public static Map fromJSONObject(JSONObject mapObject) {
        Map map = new Map();
        map.width = (int)(long) mapObject.get("width");
        map.height = (int)(long) mapObject.get("height");
        map.tiles = new Tile[map.width][map.height];
        for (int i = 0; i < map.tiles.length; i++) {
            for (int j = 0; j < map.tiles[i].length; j++) {
                map.tiles[i][j] = Tile.fromJSONObject((JSONObject) ((JSONArray) ((JSONArray) mapObject.get("tiles")).get(i)).get(j));
            }
        }
        return map;
    }

    public Tile get(int x, int y) {
        return tiles[x][y];
    }

    public void regenResources() {
        for (Tile[] row: tiles) {
            for (Tile tile: row) {
                int regen = (int) (Math.random() * 10); // 0-9
                if (regen == 9 && tile.getType() < 3) {
                    tile.increaseType();
                }
            }
        }
    }
}
