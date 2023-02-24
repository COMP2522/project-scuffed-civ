package org.bcit.com2522.project.scuffed.client;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameState { //everything manager this is the player manager
    Map map;
    Player currentTurn;
    ArrayList<Player> players;

    Entity[][] entities;
    Entity selected;

    Window scene;

    public GameState(Window scene) {
        players = new ArrayList<Player>();
        this.scene = scene;

        entities = new Entity[20][20];

    }

    public void init() {
        map = new Map(scene, 20, 20);

        //this is just for now more logic will have to go into making players later
        players.add(new Player(scene));

        currentTurn = players.get(0);
    }

    public void clicked(Position position) {

        if(entities[position.getX()][position.getY()] == null && selected == null) { //make new entity
            //players.get(currentTurn.getPlayerNum()).addEntity(position);
            entities[position.getY()][position.getX()] = new Entity(scene, position, currentTurn);
        } else if (entities[position.getX()][position.getY()].getOwner() == currentTurn) { //select existing entity
            selected = map.contains(position);
            System.out.println("selected");
        } else if (map.contains(position) == null && selected != null) { //move selected entity
            entities[selected.getPosition().getX()][selected.getPosition().getY()] = null;
            selected.moveTo(position);
            entities[selected.getPosition().getX()][selected.getPosition().getY()] = selected;
            System.out.println("trying to move");
        }
    }

    public static void nextTurn(){

    }

    public void draw() {
        map.draw();
        for (Player player: players)
            player.draw();

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
