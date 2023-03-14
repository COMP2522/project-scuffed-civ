package org.bcit.com2522.project.scuffed.client;

import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PConstants.*;

public class GameState { //everything manager this is the player manager
    Map map;
    Player currentPlayer;
    ArrayList<Player> players;// could be a circular linked list instead might make logic easier

    Entity[][] entities;
    Entity selected;

    Window scene;

    int zoomAmount;

    public GameState(Window scene, int numplayers, int mapwidth, int mapheight) {
        players = new ArrayList<Player>();
        this.scene = scene;

        entities = new Entity[mapwidth][mapheight];
        map = new Map(scene, mapwidth, mapheight);

        //scale = 1;

        for(int i = 0; i < numplayers; i++) {
            players.add(new Player(scene, i));
        }

        zoomAmount = 32;

        init(); //inits players and starting entities on map
    }

    public void init() {
        //this is just for now more logic will have to go into making players later
        currentPlayer = players.get(0);

        //puts entities into each corner
        if(players.size() > 0) {
            entities[0][0] = new Worker(scene, new Position(0, 0), players.get(0));
        }
        if(players.size() > 1) {
            entities[entities.length - 1][entities[0].length - 1] = new Worker(scene, new Position(entities.length - 1, entities[0].length - 1), players.get(1));
        }
        if(players.size() > 2) {
            entities[entities.length - 1][0] = new Worker(scene, new Position(entities.length - 1, 0), players.get(2));
        }
        if(players.size() > 3) {
            entities[0][entities[0].length - 1] = new Worker(scene, new Position(0, entities[0].length - 1), players.get(3));
        }
        if(players.size() > 4) {
            entities[(entities.length - 1) / 2][0] = new Worker(scene, new Position((entities.length - 1) / 2, 0), players.get(4));
        }
        if(players.size() > 5) {
            entities[(entities.length - 1) / 2][entities[0].length - 1] = new Worker(scene, new Position((entities.length - 1) / 2, entities[0].length - 1), players.get(5));
        }
//        if(players.size() > 6) {
//            entities[0][0] = new Worker(scene, new Position(0, 0), currentPlayer);
//        }
//        if(players.size() > 7) {
//            entities[0][0] = new Worker(scene, new Position(0, 0), currentPlayer);
//        }
    }

    public void clicked(PVector mousePos) {
        Position position = new Position((int) (mousePos.x / 32), (int) (mousePos.y / 32));

        if (mousePos.x >= 700 && mousePos.y >= 500) { //position of nextTurn button
            nextTurn();
        }

        //TODO everything below this should be mathed out and shortened
        else if(entities[position.getX()][position.getY()] == null && selected == null) { //make new entity
            //players.get(currentTurn.getPlayerNum()).addEntity(position);
            //entities[position.getX()][position.getY()] = new Entity(scene, position, currentPlayer);
            System.out.println("You can't make entities like that!");
        } else if (entities[position.getX()][position.getY()] != null &&
                entities[position.getX()][position.getY()].getOwner() == currentPlayer) { //select existing entity
            //.getOwner().equals(currentTurn)
            selected = entities[position.getX()][position.getY()];
            System.out.println("selected");
        } else if (entities[position.getX()][position.getY()] == null && selected != null && selected instanceof Unit) { //move selected entity
            Unit unit = (Unit) selected;
            entities[selected.getPosition().getX()][selected.getPosition().getY()] = null;
            unit.moveTo(position);
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
            shift(0, 1);
        }
        else if(key == 'a') {
            shift(1, 0);
        }
        else if(key == 's') {
            shift(0, -1);
        }
        else if(key == 'd') {
            shift(-1, 0);
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

        if (!(zoomAmount <= 32 && amount < 1)) {
            //zoom for map
            zoomAmount = (int)(zoomAmount * amount);
            map.resize(zoomAmount);


            //zoom for entities
            for (Entity[] row: entities) {
                for (Entity element: row) {
                    if(element != null) {
                        element.resize(zoomAmount);
                    }

                    //scale += amount;
                }
            }
        }
    }

    //moving around the map, does not take unit movement into account.
    public void shift(int x, int y) {
        //int scale = 1;

        map.shift(x, y);

        for (Entity[] row: entities) {
            for (Entity element: row) {
                if(element != null) {
                    element.shift(new Position(element.getPosition().getX() + (x),
                            element.getPosition().getY() + (y)));
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
        map.draw(zoomAmount); //drawing the map doesn't need to be color shifted

        for (Entity[] row: entities) {
            for (Entity entity: row) {
                if(entity != null) {
                    entity.draw(zoomAmount, players.indexOf(entity.getOwner())); //should be color shifted based on player number
                }
            }
        }

        currentPlayer.draw(); //this is drawing the hud.
    }

    public static void save(){

    }
}
