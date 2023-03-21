package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processing.core.PVector;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import static processing.core.PConstants.*;

import org.bcit.com2522.project.scuffed.ai.AI;

public class GameState { //everything manager this is the player manager
    int gameId;
    Map map;
    public Player currentPlayer;
    ArrayList<Player> players;// could be a circular linked list instead might make logic easier
    //ArrayList<AI> ais;
    AI ai;
    Entity[][] entities;
    Entity selected;
    Window scene;
    int zoomAmount;

    public GameState(Window scene, int numplayers, int mapwidth, int mapheight) {
        this.gameId = new Random().nextInt(10000); //make a random gameId
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

    public GameState(){
        zoomAmount = 32;
    };

    public void init() {
        //this is just for now more logic will have to go into making players later
        currentPlayer = players.get(0);

        //puts entities into each corner
        if (players.size() > 0) {
            entities[0][0] = new Worker(scene, new Position(0, 0), players.get(0));
        }
        if (players.size() > 1) {
            entities[entities.length - 1][entities[0].length - 1] = new Worker(scene, new Position(entities.length - 1, entities[0].length - 1), players.get(1));
        }
        if (players.size() > 2) {
            entities[entities.length - 1][0] = new Worker(scene, new Position(entities.length - 1, 0), players.get(2));
        }
        if (players.size() > 3) {
            entities[0][entities[0].length - 1] = new Worker(scene, new Position(0, entities[0].length - 1), players.get(3));
        }
        if (players.size() > 4) {
            entities[(entities.length - 1) / 2][0] = new Worker(scene, new Position((entities.length - 1) / 2, 0), players.get(4));
        }
        if (players.size() > 5) {
            entities[(entities.length - 1) / 2][entities[0].length - 1] = new Worker(scene, new Position((entities.length - 1) / 2, entities[0].length - 1), players.get(5));
        }
//        if(players.size() > 6) {
//            entities[0][0] = new Worker(scene, new Position(0, 0), currentPlayer);
//        }
//        if(players.size() > 7) {
//            entities[0][0] = new Worker(scene, new Position(0, 0), currentPlayer);
//        }

        ai = new AI();
    }

    public void clicked(PVector mousePos) {
        Position position = new Position((int) (mousePos.x / 32), (int) (mousePos.y / 32));

        if (mousePos.x >= 700 && mousePos.y >= 500) { //position of nextTurn button
            //printEntities();
            scene.saveGame();
            nextTurn();

        }
        //TODO everything below this should be mathed out and shortened
        else if (entities[position.getX()][position.getY()] == null && selected == null) { //make new entity
            //players.get(currentTurn.getPlayerNum()).addEntity(position);
            //entities[position.getX()][position.getY()] = new Entity(scene, position, currentPlayer);
            System.out.println("You can't make entities like that!");
        } else if (entities[position.getX()][position.getY()] != null &&
                entities[position.getX()][position.getY()].getOwner().equals(currentPlayer)) { //select existing entity
            //.getOwner().equals(currentTurn)
            selected = entities[position.getX()][position.getY()];

            // Debugging printlns
            System.out.println("Selected entity class: " + selected.getClass().getName());
            System.out.println("Selected entity owner: " + selected.getOwner());
            System.out.println("Selected entity position: " + selected.getPosition());

            System.out.println("selected");
        } else if (entities[position.getX()][position.getY()] != null && selected != null && selected instanceof Soldier &&
                !entities[position.getX()][position.getY()].getOwner().equals(currentPlayer)) { //select enemy entity with soldier (attack)
            Soldier soldier = (Soldier) selected;
            if (soldier.withinRange(position) && soldier.canAct()) {
                if (entities[position.getX()][position.getY()].dealDamage(((Soldier) selected).attack())) {
                    entities[position.getX()][position.getY()] = null;
                }
            }

        }
        else if (entities[position.getX()][position.getY()] == null && selected != null && selected instanceof Unit) { //move selected entity
            Unit unit = (Unit) selected;
            Position oldposition = selected.getPosition();
            if (unit.moveTo(position)) {
                entities[oldposition.getX()][oldposition.getY()] = null;
                entities[position.getX()][position.getY()] = selected;
            }
            System.out.println("trying to move");
            selected = null;
        } else if (selected != null && entities[position.getX()][position.getY()] != null
                && entities[position.getX()][position.getY()].getOwner().equals(currentPlayer)) { //attack enemy entity

        } else {
            System.out.println("you can't select that token");

            // Debugging printlns
            if (entities[position.getX()][position.getY()] != null) {
                Entity clickedEntity = entities[position.getX()][position.getY()];
                System.out.println("Clicked entity class: " + clickedEntity.getClass().getName());
                System.out.println("Clicked entity owner: " + clickedEntity.getOwner());
                System.out.println("Clicked entity position: " + clickedEntity.getPosition());
            } else {
                System.out.println("Clicked on an empty tile");
            }
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
        } else if(key == 'b' && (selected instanceof Worker || selected instanceof Building)) { //creates a building TODO: fix below this to be less shit
            Position free = getFreePosition(selected);
            if (free != null && selected.canAct()) {
                entities[free.getX()][free.getY()] = new Building(scene, free, currentPlayer);
                selected.act();
            } else
                System.out.println("there are no available spaces to place a builder or this entity is out of actions");
        } else if(key == 'm' && selected instanceof Building) {
            Position free = getFreePosition(selected);
            if (free != null && selected.canAct()) {
                entities[free.getX()][free.getY()] = new Worker(scene, free, currentPlayer);
                selected.act();
            } else
                System.out.println("there are no available spaces to place a worker or this entity is out of actions");
        } else if(key == 'f' && selected instanceof Building) {
            Position free = getFreePosition(selected);
            if (free != null && selected.canAct()) {
                entities[free.getX()][free.getY()] = new Soldier(scene, free, currentPlayer);
                selected.act();
            } else
                System.out.println("there are no available spaces to place a worker or this entity is out of actions");
        } else if(key == 'c' && selected instanceof Worker) {
            ((Worker) selected).collect();
        } else if (key == '\n' || key == '\r') {
            System.out.println("enter pressed");
            nextTurn();
        } else if (key == ESC) {
            key = 0;
            scene.saveGame();
            scene.inGame = false;
        }
        if (key == CODED) {
            if (scene.keyCode == UP) {
                zoom(2);
            } else if (scene.keyCode == DOWN) {
                zoom(0.5f);
            }
        }
    }

    private Position getFreePosition(Entity selected) {
        if (selected.getPosition().getY() == 0 || entities[selected.getPosition().getX()][selected.getPosition().getY() - 1] != null) {
            if (selected.getPosition().getX() == entities.length - 1 || entities[selected.getPosition().getX() + 1][selected.getPosition().getY()] != null) {
                if (selected.getPosition().getY() == entities[0].length - 1 || entities[selected.getPosition().getX()][selected.getPosition().getY() + 1] != null) {
                    if (selected.getPosition().getX() == 0 || entities[selected.getPosition().getX() - 1][selected.getPosition().getY()] != null) {
                        return null;
                    }
                    return new Position (selected.getPosition().getX() - 1, selected.getPosition().getY());
                }
                return new Position(selected.getPosition().getX(), selected.getPosition().getY() + 1);
            }
            return new Position(selected.getPosition().getX() + 1, selected.getPosition().getY());
        }
        return new Position(selected.getPosition().getX(), selected.getPosition().getY() - 1);
    }


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
        //set remaining move to max

        for (Entity[] row: entities) {
            for (Entity element: row) {
                if (element != null) {
                    element.resetAction();
                    if (element instanceof Unit) {
                        ((Unit) element).resetMove();
                    }
                }
            }
        }

        //set currentPlayer to next player
        if (players.indexOf(currentPlayer) < players.size() - 1) {
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
            System.out.println("next turn");
        } else {
            currentPlayer = players.get(0);
        }

        if (players.indexOf(currentPlayer) == 2) {
            ai.start(this);
        }

        if (!currentPlayer.getHasLost()) {
            boolean hasLost = true;
            for (Entity[] row : entities) {
                for (Entity element : row) {
                    if (element != null && element.getOwner() == currentPlayer) {
                        hasLost = false;
                        break;
                    }
                }
            }
            if (hasLost) {
                currentPlayer.lose();
                nextTurn();
            }
        }

        int alivePlayers = 0;
        for (Player player : players) {
            if (!player.getHasLost()) {
                alivePlayers++;
            }
        }
        if (alivePlayers <= 1) {
            System.out.println("someone won");
            int seven = 5/0;
        }

    }

    public void draw() {
        map.draw(zoomAmount);

        for (Entity[] row: entities) {
            for (Entity entity: row) {
                if(entity != null) {
                    entity.draw(zoomAmount, players.indexOf(entity.getOwner()));
                }
            }
        }

        currentPlayer.draw(); //this is drawing the hud.
    }

    public void printEntities() {
        for (Entity[] row: entities) {
            for (Entity element: row) {
                if(element != null) {
                    System.out.println(element);
                }
            }
        }
    }

    /**
     * Saves the current gamestate to a json file in the "saves" folder
     * currently called at end of player turn
     */
    public JSONObject toJSONObject() {
        JSONObject gameState = new JSONObject();
        gameState.put("gameId", this.gameId);
        gameState.put("map", map.toJSONObject());
        gameState.put("currentPlayer", currentPlayer.toJSONObject());
        JSONArray playerArray = new JSONArray();
        for (Player player : players) {
            playerArray.add(player.toJSONObject());
        }
        gameState.put("players", playerArray);
        JSONArray entityArray = new JSONArray();
        for (Entity[] row : entities) {
            JSONArray rowArray = new JSONArray();
            for (Entity entity : row) {
                if (entity != null) {
                    rowArray.add(entity.toJSONObject());
                } else {
                    rowArray.add(null);
                }
            }
            entityArray.add(rowArray);
        }
        gameState.put("entities", entityArray);
        return gameState;
    }

    /**
     * Loads a gamestate from a json object. Window is set to null for each entity.
     */
    public static GameState fromJSONObject(JSONObject gameStateJSON, Window window) {
        GameState loadedGameState = new GameState();
        loadedGameState.gameId = ((Number) gameStateJSON.get("gameId")).intValue();
        loadedGameState.map = Map.fromJSONObject((JSONObject) gameStateJSON.get("map"));
        loadedGameState.currentPlayer = Player.fromJSONObject((JSONObject) gameStateJSON.get("currentPlayer"), window);
        JSONArray playersArray = (JSONArray) gameStateJSON.get("players");
        loadedGameState.players = (ArrayList<Player>) playersArray.stream().map(playerObject -> Player.fromJSONObject((JSONObject) playerObject, window))
                .collect(Collectors.toList());
        JSONArray entitiesArray = (JSONArray) gameStateJSON.get("entities");
        Entity[][] entities = new Entity[loadedGameState.map.width][loadedGameState.map.width];
        for (int i = 0; i < entitiesArray.size(); i++) {
            JSONArray row = (JSONArray) entitiesArray.get(i);
            for (int j = 0; j < row.size(); j++) {
                entities[i][j] = Entity.fromJSONObject((JSONObject) row.get(j), window);
            }
        }
        loadedGameState.entities = entities;
        return loadedGameState;
    }

    public static GameState load(Window window) throws FileNotFoundException {
        GameState loadedGameState = new GameState();
        JSONParser parser = new JSONParser();
        try (FileReader saveReader = new FileReader("saves/save.json")) {
            JSONObject gameStateJSON = (JSONObject) parser.parse(saveReader);
            loadedGameState.scene = window;
            loadedGameState.gameId = ((Number) gameStateJSON.get("gameId")).intValue();
            loadedGameState.map = Map.fromJSONObject((JSONObject) gameStateJSON.get("map"), window);
            loadedGameState.currentPlayer = Player.fromJSONObject((JSONObject) gameStateJSON.get("currentPlayer"), window);
            JSONArray playersArray = (JSONArray) gameStateJSON.get("players");
            loadedGameState.players = (ArrayList<Player>) playersArray.stream().map(playerObject -> Player.fromJSONObject((JSONObject) playerObject, window))
                    .collect(Collectors.toList());
            JSONArray entitiesArray = (JSONArray) gameStateJSON.get("entities");
            Entity[][] entities = new Entity[loadedGameState.map.width][loadedGameState.map.width];
            for (int i = 0; i < entitiesArray.size(); i++) {
                JSONArray row = (JSONArray) entitiesArray.get(i);
                for (int j = 0; j < row.size(); j++) {
                    entities[i][j] = Entity.fromJSONObject((JSONObject) row.get(j), window);
                }
            }
            loadedGameState.entities = entities;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return loadedGameState;
    }
}