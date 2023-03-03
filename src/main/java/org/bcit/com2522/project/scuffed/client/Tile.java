package org.bcit.com2522.project.scuffed.client;

public class Tile {
    private Position position;

    private int type;
    //0: grass
    //1: rocks
    //2: sand
    //3: water

    public Tile(Position position) {
        type = (int) (Math.random() * 4); // 1-4
        this.position = position;
    }

    public Entity getEntity() {
        //return entity;
        return null;
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
