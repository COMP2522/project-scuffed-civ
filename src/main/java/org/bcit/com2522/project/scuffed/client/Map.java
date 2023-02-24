package org.bcit.com2522.project.scuffed.client;

import processing.core.PImage;
import processing.core.PVector;

import java.util.Random;
import java.util.random.*;
import java.awt.*;

import static processing.awt.ShimAWT.loadImage;
import static processing.core.PApplet.*;
import static processing.core.PApplet.sin;

public class Map {
    PImage grass;
    PImage rocks;
    PImage sand;
    PImage water;
    Tile[][] tiles;
    Window scene;

    private Color color;

    public Map (Window scene, int width, int height) {
        grass = loadImage(scene, "sprites/Menu/tile_grass.png");
//        rocks = loadImage("\\sprites\\tile_grass.png");
//        sand = loadImage("\\sprites\\tile_grass.png");
//        water = loadImage("\\sprites\\tile_grass.png");

        this.scene = scene;

        this.color = (Color.red);
    }

    public void draw() {
        this.scene.fill(color.getRed());
        this.scene.circle(50, 50, 50);
        this.scene.image(grass, 500,500);
    }
}
