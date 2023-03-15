package org.bcit.com2522.project.scuffed.client;

import static processing.awt.ShimAWT.loadImage;

public class Unit extends Entity { //things that can move
    int maxMove;
    int remainMove;

    public Unit(Window scene, Position position, Player player) {
        super(scene, position, player);
        maxMove = 6;
        remainMove = maxMove;
    }

    public void resetMove() {
        remainMove = maxMove;
    }

    public Boolean moveTo(Position position) {
        //if the position is in range
        if(Math.abs(position.getX() - this.position.getX()) + Math.abs(position.getY() - this.position.getY()) <= remainMove) {
            remainMove -= Math.abs(position.getX() - this.position.getX()) + Math.abs(position.getY() - this.position.getY());
            this.position = position;
            return true;
        } else {
            System.out.println("that position is out of range");
            return false;
        }
    }
}
