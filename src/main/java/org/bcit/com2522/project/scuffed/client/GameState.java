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

    public GameState(Window scene, int numplayers, int mapwidth, int mapheight) {
        players = new ArrayList<Player>();
        this.scene = scene;

        entities = new Entity[mapwidth][mapheight];
        map = new Map(scene, mapwidth, mapheight);

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
            map.moveUp();
        }
        else if(key == 'a') {
            map.moveLeft();
        }
        else if(key == 's') {
            map.moveDown();
        }
        else if(key == 'd') {
            map.moveRight();
        }
        if (key == CODED) {
            if (scene.keyCode == UP) {
                map.zoom(2f);
                System.out.println("up pressed");
            } else if (scene.keyCode == DOWN) {
                map.zoom(0.5f);
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
