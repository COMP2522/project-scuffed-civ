package org.bcit.com2522.project.scuffed.client;

import processing.core.PImage;

import java.awt.*;

import static processing.awt.ShimAWT.loadImage;

public class Map { //this is a tile manager
    PImage grass;
    PImage rocks;
    PImage sand;
    PImage water;
    Tile[][] tiles;
    Window scene;

    Player player;

    private Color color;

    public Map (Window scene, int width, int height) {
        grass = loadImage(scene, "sprites/Menu/tile_grass.png");
        rocks = loadImage(scene, "sprites/Menu/tile_rocks.png");
        sand = loadImage(scene, "sprites/Menu/tile_sand.png");
        water = loadImage(scene, "sprites/Menu/tile_water.png");

        tiles = new Tile[width][height];

        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = new Tile(new Position(i,j));
            }
        }

        this.scene = scene;

        this.color = (Color.red);
    }

    public void shift(int x, int y) {
        for (Tile[] row: tiles) {
            for (Tile element: row) {
                element.setPosition(new Position(element.getPosition().getX() + x,
                        element.getPosition().getY() + y));
            }
        }
    }

    public void resize(int zoomAmount) {
        for (Tile[] row: tiles) {
            for (Tile element: row) {
                grass.resize(zoomAmount, 0);
                rocks.resize(zoomAmount, 0);
                sand.resize(zoomAmount, 0);
                water.resize(zoomAmount, 0);

                //scale += amount;
            }
        }
    }



    public void draw(int zoomAmount) {
        //this.scene.fill(color.getRed());
        //this.scene.circle(50, 50, 50);
        //this.scene.image(grass, 500,500);
        for (Tile[] row: tiles) {
            for (Tile element: row) {
                if(element.getType() == 0)
                    this.scene.image(grass, element.getPosition().getX()*zoomAmount,element.getPosition().getY()*zoomAmount);
                else if(element.getType() == 1)
                    this.scene.image(rocks, element.getPosition().getX()*zoomAmount,element.getPosition().getY()*zoomAmount);
                else if(element.getType() == 2)
                    this.scene.image(sand, element.getPosition().getX()*zoomAmount,element.getPosition().getY()*zoomAmount);
                else if(element.getType() == 3)
                    this.scene.image(water, element.getPosition().getX()*zoomAmount,element.getPosition().getY()*zoomAmount);

            }
        }
    }
}
