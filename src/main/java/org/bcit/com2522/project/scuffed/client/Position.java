package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.io.Serializable;

/**
 * Represents the position on the map
 */
public class Position{
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Position other) {
        return x == other.x && y == other.y;
    }

    public static Position fromJSONObject(JSONObject positionObject) {
        int x = ((Number) positionObject.get("x")).intValue();
        int y = ((Number) positionObject.get("y")).intValue();
        return new Position(x, y);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

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
