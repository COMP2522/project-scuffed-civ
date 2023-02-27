package org.bcit.com2522.project.scuffed.client;

import processing.core.PVector;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static processing.core.PConstants.*;

public class GameState { //everything manager this is the player manager
    Map map;
    Player currentTurn;
    ArrayList<Player> players;// could be a circular linked list instead might make logic easier

    Entity[][] entities;
    Entity selected;

    Window scene;

    int scale;

    public GameState(Window scene, int numplayers, int mapwidth, int mapheight) {
        players = new ArrayList<Player>();
        this.scene = scene;

        entities = new Entity[mapwidth][mapheight];
        map = new Map(scene, mapwidth, mapheight);

        scale = 1;

    }

    public void init() {

        //this is just for now more logic will have to go into making players later
        players.add(new Player(scene));

        currentTurn = players.get(0);
    }

    public void clicked(PVector mousePos) {
        Position position = new Position((int) (mousePos.x / 32), (int) (mousePos.y / 32));

        //if (the mouse is over the map)

        if(entities[position.getX()][position.getY()] == null && selected == null) { //make new entity
            //players.get(currentTurn.getPlayerNum()).addEntity(position);
            entities[position.getX()][position.getY()] = new Entity(scene, position, currentTurn);
            System.out.println("making an entity");
        } else if (entities[position.getX()][position.getY()] != null) { //select existing entity
            //.getOwner().equals(currentTurn)
            selected = entities[position.getX()][position.getY()];
            System.out.println("selected");
        } else if (entities[position.getX()][position.getY()] == null && selected != null) { //move selected entity
            entities[selected.getPosition().getX()][selected.getPosition().getY()] = null;
            selected.moveTo(position);
            entities[position.getX()][position.getY()] = selected;
            System.out.println("trying to move");
            selected = null;
        }
        else {
            System.out.println("else");
        }

        //else (the mouse is over a different button)
    }

    public void keyPressed(char key) {
        if(key == 'w') {
            move(0, 1);
        }
        else if(key == 'a') {
            move(1, 0);
        }
        else if(key == 's') {
            move(0, -1);
        }
        else if(key == 'd') {
            move(-1, 0);
        }
        if (key == CODED) {
            if (scene.keyCode == UP) {
                zoom(2);
            } else if (scene.keyCode == DOWN) {
                zoom(0.5f);
            }
        }
    }

    //amount is the
    public void zoom(float amount) {
        if (map.getZoomamount() <= 32 && amount < 1) {

        } else {
            map.setZoomamount((int) (map.getZoomamount() * amount));
            //scale /= amount;
        }
    }

    public void move(int x, int y) {
        //int scale = 1;

        map.move(x, y, scale);

        for (Entity[] row: entities) {
            for (Entity element: row) {
                if(element != null) {
                    element.moveTo(new Position(element.getPosition().getX() + (x * scale),
                            element.getPosition().getY() + (y * scale)));
                }
            }
        }
    }

    public static void nextTurn(){

    }

    public void draw() {
        map.draw();
        for (Player player: players)
            player.draw(); //player overlay maybe? idk what this would be its doing nothing rn

        for (Entity[] row: entities) {
            for (Entity entity: row) {
                if(entity != null) {
                    entity.draw();
                }
            }
        }
    }

    public static void save(){

    }
}
