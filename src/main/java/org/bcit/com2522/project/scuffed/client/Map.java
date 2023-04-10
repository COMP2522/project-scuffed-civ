package org.bcit.com2522.project.scuffed.client;

import static org.bcit.com2522.project.scuffed.client.Window.GameImages;

import java.awt.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The type Map.
 */
public class Map { // this is a tile manager
  /**
   * The Width.
   */
  int width;
  /**
   * The Height.
   */
  int height;
  /**
   * The Tiles.
   */
  Tile[][] tiles;
  private Color color;

  /**
   * Constructor used in loading a map from JSON.
   */
  public Map() {
    this.color = (Color.red);
  }

  /**
   * Instantiates a new Map.
   *
   * @param width  the width
   * @param height the height
   */
  public Map(int width, int height) {
    this.width = width;
    this.height = height;
    tiles = new Tile[width][height];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        tiles[i][j] = new Tile();
      }
    }

    this.color = (Color.red);
  }

  /**
   * Instantiates a new Map.
   *
   * @param map the map
   */
  public Map(Map map) { // creates a deep copy of map
    this.width = map.width;
    this.height = map.height;
    this.tiles = new Tile[map.tiles.length][map.tiles[0].length];

    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[0].length; j++) {
        Tile tile = map.tiles[i][j];
        this.tiles[i][j] = new Tile(map.tiles[i][j].getType());
      }
    }
  }

  /**
   * Creates a map from a JSONObject.
   *
   * @param mapObject JSONObject map
   * @return Map map
   */
  public static Map fromJSONObject(JSONObject mapObject) {
    Map map = new Map();
    Object widthObject = mapObject.get("width");
    Object heightObject = mapObject.get("height");
    map.width = widthObject instanceof Long ? (int) (long) widthObject : (int) widthObject;
    map.height = heightObject instanceof Long ? (int) (long) heightObject : (int) heightObject;
    map.tiles = new Tile[map.width][map.height];
    for (int i = 0; i < map.tiles.length; i++) {
      for (int j = 0; j < map.tiles[i].length; j++) {
        map.tiles[i][j] = Tile
            .fromJSONObject(
                (JSONObject) ((JSONArray) ((JSONArray) mapObject.get("tiles")).get(i)).get(j));
      }
    }
    return map;
  }

  /**
   * Draw.
   *
   * @param zoomAmount the zoom amount
   * @param scene      the scene
   * @param xShift     the x shift
   * @param yShift     the y shift
   */
  public void draw(int zoomAmount, Window scene, int xShift, int yShift) {

    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[0].length; j++) {
        if (tiles[i][j].getType() == 0) {
          scene.image(GameImages.get("sandTile"), (i + xShift) * zoomAmount,
                  (j + yShift) * zoomAmount);
        } else if (tiles[i][j].getType() == 1) {
          scene.image(GameImages.get("waterTile"), (i + xShift) * zoomAmount,
                  (j + yShift) * zoomAmount);
        } else if (tiles[i][j].getType() == 2) {
          scene.image(GameImages.get("rockTile"), (i + xShift) * zoomAmount,
                  (j + yShift) * zoomAmount);
        } else if (tiles[i][j].getType() == 3) {
          scene.image(GameImages.get("grassTile"), (i + xShift) * zoomAmount,
                  (j + yShift) * zoomAmount);
        }
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
    for (Tile[] row : tiles) {
      JSONArray rowArray = new JSONArray();
      for (Tile element : row) {
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
   * Get tile.
   *
   * @param x the x
   * @param y the y
   * @return the tile
   */
  public Tile get(int x, int y) {
    return tiles[x][y];
  }

  /**
   * Get tile.
   *
   * @param position the position
   * @return the tile
   */
  public Tile get(Position position) {
    return tiles[position.getX()][position.getY()];
  }

  /**
   * Regen resources.
   */
  public void regenResources() {
    for (Tile[] row : tiles) {
      for (Tile tile : row) {
        int regen = (int) (Math.random() * 100); // 0-9
        if (regen == 99 && tile.getType() < 3) {
          tile.increaseType();
        }
      }
    }
  }
}
