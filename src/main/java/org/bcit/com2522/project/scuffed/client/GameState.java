package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PConstants.*;

public class GameState { //everything manager this is the player manager
    int gameId;
    Map map;
    Player currentPlayer;
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

        for(int i = 0; i < numplayers; i++) {
            players.add(new Player(scene, i));
        }

    }

    public void init() {

        //this is just for now more logic will have to go into making players later

        currentPlayer = players.get(0);
    }

    public void clicked(PVector mousePos) {
        Position position = new Position((int) (mousePos.x / 32), (int) (mousePos.y / 32));

        if (mousePos.x >= 700 && mousePos.y >= 500) { //position of nextTurn button
            nextTurn();
        }

        //TODO everything below this should be mathed out and shortened
        else if(entities[position.getX()][position.getY()] == null && selected == null) { //make new entity
            //players.get(currentTurn.getPlayerNum()).addEntity(position);
            entities[position.getX()][position.getY()] = new Entity(scene, position, currentPlayer);
            System.out.println("making an entity");
        } else if (entities[position.getX()][position.getY()] != null &&
                entities[position.getX()][position.getY()].getOwner() == currentPlayer) { //select existing entity
            //.getOwner().equals(currentTurn)
            selected = entities[position.getX()][position.getY()];
            System.out.println("selected");
        } else if (entities[position.getX()][position.getY()] == null && selected != null) { //move selected entity
            entities[selected.getPosition().getX()][selected.getPosition().getY()] = null;
            selected.moveTo(position);
            entities[position.getX()][position.getY()] = selected;
            System.out.println("trying to move");
            selected = null;
        } else if (selected != null && entities[position.getX()][position.getY()] != null
                && entities[position.getX()][position.getY()].getOwner() != currentPlayer) { //attack enemy entity

        }
        else {
            System.out.println("you can't select that token");
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
        //TODO entities do not zoom properly,
        //this requires all entity textures to be accessed somewhere, potentially from GameState potentially
        //from a different manager class for entities
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

    public void nextTurn(){
        if (players.indexOf(currentPlayer) < players.size() - 1) {
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
            System.out.println("next turn");
        } else {
            currentPlayer = players.get(0);
        }
    }

    public void draw() {
        map.draw();
        //for (Player player: players)
        currentPlayer.draw();

        for (Entity[] row: entities) {
            for (Entity entity: row) {
                if(entity != null) {
                    entity.draw();
                }
            }
        }
    }

    public static void save(){
        JSONObject gamestate = new JSONObject();
    }

    public static void load(){

    }


}
