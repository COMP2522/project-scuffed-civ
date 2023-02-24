package org.bcit.com2522.project.scuffed.client;

public class Tile {
    private Position position;
    private Entity entity;

    private int type;
    //0: grass
    //1: rocks
    //2: sand
    //3: water

    public Tile(Position position) {
        type = (int) (Math.random() * 4); // 1-4
        this.entity = null;
        this.position = position;
    }

    public void draw(){

    }

    public Position getPosition() {
        return position;
    }

    public int getType() {
        return type;
    }
}
