package org.bcit.com2522.project.scuffed.client;

public class Unit extends Entity{

    public Unit(Window scene, Position position, Player player) {
        super(scene, position, player);
    }

    public void moveTo(Position position) {
        this.position = position;
    }
}
