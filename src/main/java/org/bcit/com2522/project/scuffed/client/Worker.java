package org.bcit.com2522.project.scuffed.client;

import static processing.awt.ShimAWT.loadImage;

public class Worker extends Unit{
    public Worker(Window scene, Position position, Player player) {
        super(scene, position, player);
        texture = loadImage(scene, "sprites/hammerDude.png");
    }
}
