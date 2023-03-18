package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
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
}
