package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

/**
 * Represents the position on the map, this should only be used to reference location relative to the map, not relative to the screen >:(
 */
public class Position {
  private int x;
  private int y;

  /**
   * Instantiates a new Position.
   *
   * @param x the x
   * @param y the y
   */
  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * From json object position.
   *
   * @param positionObject the position object
   * @return the position
   */
  public static Position fromJSONObject(JSONObject positionObject) {
    int x = ((Number) positionObject.get("x")).intValue();
    int y = ((Number) positionObject.get("y")).intValue();
    return new Position(x, y);
  }

  /**
   * Equals boolean.
   *
   * @param other the other
   * @return the boolean
   */
  public boolean equals(Position other) {
    return x == other.x && y == other.y;
  }

  /**
   * Gets x.
   *
   * @return the x
   */
  public int getX() {
    return x;
  }

  /**
   * Sets x.
   *
   * @param x the x
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Gets y.
   *
   * @return the y
   */
  public int getY() {
    return y;
  }

  /**
   * Sets y.
   *
   * @param y the y
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * To json object json object.
   *
   * @return the json object
   */
  public JSONObject toJSONObject() {
    JSONObject obj = new JSONObject();
    obj.put("x", x);
    obj.put("y", y);
    return obj;
  }

  @Override
  public String toString() {
    return "Position{" +
            "x=" + x +
            ", y=" + y +
            '}';
  }
}
