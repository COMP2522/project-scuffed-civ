package org.bcit.com2522.project.scuffed.client;

import static processing.awt.ShimAWT.loadImage;

public class Soldier extends Unit{
    int speed;
    int damage;

    public Soldier(Window scene, Position position, Player player) {
        super(scene, position, player);
        texture = loadImage(scene, "sprites/mrClean.png");

    }


    public void move(){

    }
    public void attack(){

    }

    public void build(){

    }
}
