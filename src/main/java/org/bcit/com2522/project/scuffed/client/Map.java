package org.bcit.com2522.project.scuffed.client;

import processing.core.PImage;
import processing.core.PVector;

import java.util.Random;
import java.util.random.*;
import java.awt.*;

import static processing.awt.ShimAWT.loadImage;
import static processing.core.PApplet.*;
import static processing.core.PApplet.sin;

public class Map { //this is a tile manager
    PImage grass;
    PImage rocks;
    PImage sand;
    PImage water;
    Tile[][] tiles;
    Window scene;

    Player player;

    int zoomamount;

    private Color color;

    public Map (Window scene, int width, int height) {
        grass = loadImage(scene, "sprites/Menu/tile_grass.png");
        rocks = loadImage(scene, "sprites/Menu/tile_rocks.png");
        sand = loadImage(scene, "sprites/Menu/tile_sand.png");
        water = loadImage(scene, "sprites/Menu/tile_water.png");

        tiles = new Tile[width][height];

        zoomamount = 32;


        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = new Tile(new Position(i,j));
            }
        }

        this.scene = scene;

        this.color = (Color.red);
    }

    public void move(int x, int y, int scale) {
        for (Tile[] row: tiles) {
            for (Tile element: row) {
                element.setPosition(new Position(element.getPosition().getX() + (x * scale),
                        element.getPosition().getY() + (y * scale)));
            }
        }
    }

    public void zoom(float amount) {

    }

    public void setZoomamount(int zoomamount) {
        this.zoomamount = zoomamount;

        for (Tile[] row: tiles) {
            for (Tile element: row) {
                grass.resize(zoomamount, 0);
                rocks.resize(zoomamount, 0);
                sand.resize(zoomamount, 0);
                water.resize(zoomamount, 0);

                //scale += amount;
            }
        }
    }

    public int getZoomamount() {
        return zoomamount;
    }



    public void draw() {
        //this.scene.fill(color.getRed());
        //this.scene.circle(50, 50, 50);
        //this.scene.image(grass, 500,500);
        for (Tile[] row: tiles) {
            for (Tile element: row) {
                if(element.getType() == 0)
                    this.scene.image(grass, element.getPosition().getX()*zoomamount,element.getPosition().getY()*zoomamount);
                else if(element.getType() == 1)
                    this.scene.image(rocks, element.getPosition().getX()*zoomamount,element.getPosition().getY()*zoomamount);
                else if(element.getType() == 2)
                    this.scene.image(sand, element.getPosition().getX()*zoomamount,element.getPosition().getY()*zoomamount);
                else if(element.getType() == 3)
                    this.scene.image(water, element.getPosition().getX()*zoomamount,element.getPosition().getY()*zoomamount);

            }
        }
    }
}
