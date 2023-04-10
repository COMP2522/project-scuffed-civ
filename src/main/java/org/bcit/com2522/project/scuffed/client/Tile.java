package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

/**
 * The type Tile.
 */
public class Tile {
  private int type;

  /**
   * for creating a new tile.
   */
  public Tile() {
    type = (int) (Math.random() * 4); // 0-3
  }

  /**
   * for loading a tile from JSON.
   *
   * @param type the type
   */
  public Tile(int type) {
    this.type = type;
  }

  /**
   * From json object tile.
   *
   * @param tiles the tiles
   * @return the tile
   */
  public static Tile fromJSONObject(JSONObject tiles) {
    Tile tile = new Tile(((Number) tiles.get("type")).intValue());
    return tile;
  }

  /**
   * Gets type.
   *
   * @return the type
   */
  public int getType() {
    return type;
  }

  /**
   * Creates a JSON object from the tile.
   *
   * @return the json object
   */
  public JSONObject toJSONObject() {
    JSONObject obj = new JSONObject();
    //obj.put("position", position.toJSONObject());
    obj.put("type", type);
    return obj;
  }

  /**
   * Take resources int.
   *
   * @return the int
   */
  public int takeResources() {
    if (type >= 1) {
      type -= 1;
      return 1;
    }
    return 0;
  }

  /**
   * Increase type.
   */
  public void increaseType() {
    type++;
  }
}
