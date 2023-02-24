package org.bcit.com2522.project.scuffed.client;

import processing.core.PVector;

import java.util.Random;
import java.util.random.*;
import java.awt.*;

import static processing.core.PApplet.*;
import static processing.core.PApplet.sin;

public class Map {
    Tile[][] tiles;
    Window scene;

    private Color color;

    public Map (Window scene) {

        this.scene = scene;

        this.color = (Color.red);
    }

    public void draw() {
        this.scene.fill(color.getRed());
        this.scene.circle(50, 50, 50);
    }
}
