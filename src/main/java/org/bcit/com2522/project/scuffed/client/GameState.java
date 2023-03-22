package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processing.core.PVector;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Random;
import java.util.stream.Collectors;

import static processing.core.PConstants.*;

import org.bcit.com2522.project.scuffed.ai.AI;

public class GameState { //everything manager this is the player manager
    int gameId;
    Map map;
    public Player currentPlayer;
    ArrayDeque<Player> players; // made this a doubly ended queue so we can easily cycle through players
    AI ai;
    Entity[][] entities;
    Entity selected;
    Window scene;
    int zoomAmount;

    public GameState(int numplayers, int mapwidth, int mapheight) {
        this.gameId = new Random().nextInt(10000); //make a random gameId
        players = new ArrayDeque<>(numplayers);

        entities = new Entity[mapwidth][mapheight];
        map = new Map(mapwidth, mapheight);

        for(int i = 0; i < numplayers; i++) {
            players.add(new Player(i));
        }

        zoomAmount = 32;
        init(); //inits players and starting entities on map
    }

    public GameState(){
        zoomAmount = 32;
    };

    public void init() {
        // Initialize the currentPlayer as the first player in the queue
        currentPlayer = players.peek();

        int rows = entities.length;
        int cols = entities[0].length;

        // Calculate the number of grid sections along the x and y axes
        int xSections = (int) Math.ceil(Math.sqrt(players.size()));
        int ySections = (int) Math.ceil((double) players.size() / xSections);

        // Calculate the width and height of each grid section
        int sectionWidth = rows / xSections;
        int sectionHeight = cols / ySections;

        // Create a copy of the playersQueue to iterate over without affecting the original queue
        ArrayDeque<Player> playersQueueCopy = new ArrayDeque<>(players);

        for (int y = 0; y < ySections; y++) {
            for (int x = 0; x < xSections; x++) {
                if (!playersQueueCopy.isEmpty()) {
                    Player player = playersQueueCopy.poll();

                    // Calculate the position of the worker for the current player
                    int xPos = x * sectionWidth + sectionWidth / 2;
                    int yPos = y * sectionHeight + sectionHeight / 2;

                    entities[xPos][yPos] = new Worker(new Position(xPos, yPos), player);
                }
            }
        }
    }

    public void clicked(PVector mousePos, Window scene) {
        int x = (int) (mousePos.x / 32);
        int y = (int) (mousePos.y / 32);
        if(!(x >= 0 && x < entities.length && y >= 0 && y < entities[0].length)){
            if (mousePos.x >= 700 && mousePos.y >= 500) {
                scene.saveGame();
                nextTurn();
            }
            return;
        }
        Entity entity = entities[x][y];
        if (entity == null && selected == null) {
            System.out.println("You can't make entities like that!");
        } else if (entity != null && entity.getOwnerID() == currentPlayer.getID()) {
            selected = entity;
            System.out.println("Selected entity class: " + selected.getClass().getName());
            System.out.println("Selected entity ownerID: " + selected.getOwnerID());
            System.out.println("Selected entity position: " + selected.getPosition());
        } else if (entity != null && selected instanceof Soldier && entity.getOwnerID() != currentPlayer.getID()) {
            Soldier soldier = (Soldier) selected;
            if (soldier.withinRange(new Position(x, y)) && soldier.canAct()) {
                if (entity.dealDamage(soldier.attack())) {
                    entities[x][y] = null;
                }
            }
        } else if (entity == null && selected instanceof Unit) {
            Unit unit = (Unit) selected;
            Position oldPos = selected.getPosition();
            if (unit.moveTo(new Position(x, y))) {
                entities[oldPos.getX()][oldPos.getY()] = null;
                entities[x][y] = selected;
            }
            selected = null;
        } else {
            System.out.println("Invalid selection");
        }
    }

    public void keyPressed(char key, Window scene) {
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
            System.out.println("b");
            if (free != null && selected.canAct()) {
                entities[free.getX()][free.getY()] = new Building(free, currentPlayer);
                selected.act();
            } else
                System.out.println("there are no available spaces to place a builder or this entity is out of actions");
        } else if(key == 'm' && selected instanceof Building) {
            Position free = getFreePosition(selected);
            if (free != null && selected.canAct()) {
                entities[free.getX()][free.getY()] = new Worker(free, currentPlayer);
                selected.act();
            } else
                System.out.println("there are no available spaces to place a worker or this entity is out of actions");
        } else if(key == 'f' && selected instanceof Building) {
            Position free = getFreePosition(selected);
            if (free != null && selected.canAct()) {
                entities[free.getX()][free.getY()] = new Soldier(free, currentPlayer);
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

    public void nextTurn() {
        // Reset actions and moves for all entities
        for (Entity[] row : entities) {
            for (Entity element : row) {
                if (element != null) {
                    element.resetAction();
                    if (element instanceof Unit) {
                        ((Unit) element).resetMove();
                    }
                }
            }
        }

        // Move to the next player and update currentPlayer
        players.offer(players.poll());
        currentPlayer = players.peek();

        // Check if the next player has lost
        if (!currentPlayer.getHasLost()) {
            boolean hasLost = true;
            for (Entity[] row : entities) {
                for (Entity element : row) {
                    if (element != null && element.getOwnerID() == currentPlayer.getID()) {
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

        // Check if there's only one player left
        int alivePlayers = 0;
        for (Player player : players) {
            if (!player.getHasLost()) {
                alivePlayers++;
            }
        }
        if (alivePlayers <= 1) {
            System.out.println("someone won");
            int seven = 5 / 0;
        }

        // If the current player is AI, call ai.start(this)
//        if (currentPlayer.isAI()) {
//            ai.start(this);
//        }
    }

    public void draw(Window scene) {
        map.draw(zoomAmount, scene);

        for (Entity[] row: entities) {
            for (Entity entity: row) {
                if(entity != null) {
                    entity.draw(zoomAmount, entity.ownerID, scene);
                }
            }
        }

        currentPlayer.draw(scene); //this is drawing the hud.
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
     * Loads a gamestate from a json object.
     */
    public static GameState fromJSONObject(JSONObject gameStateJSON) {
        GameState loadedGameState = new GameState();
        loadedGameState.gameId = ((Number) gameStateJSON.get("gameId")).intValue();
        loadedGameState.map = Map.fromJSONObject((JSONObject) gameStateJSON.get("map"));
        loadedGameState.currentPlayer = (Player)gameStateJSON.get("currentPlayer");
        JSONArray playersArray = (JSONArray) gameStateJSON.get("players");
        loadedGameState.players = (ArrayDeque<Player>) playersArray.stream().map(playerObject -> Player.fromJSONObject((JSONObject) playerObject))
                .collect(Collectors.toCollection(ArrayDeque::new));
        JSONArray entitiesArray = (JSONArray) gameStateJSON.get("entities");
        Entity[][] entities = new Entity[loadedGameState.map.width][loadedGameState.map.width];
        for (int i = 0; i < entitiesArray.size(); i++) {
            JSONArray row = (JSONArray) entitiesArray.get(i);
            for (int j = 0; j < row.size(); j++) {
                entities[i][j] = Entity.fromJSONObject((JSONObject) row.get(j));
            }
        }
        loadedGameState.entities = entities;
        return loadedGameState;
    }

    public static GameState load() throws FileNotFoundException {
        GameState loadedGameState = new GameState();
        JSONParser parser = new JSONParser();
        try (FileReader saveReader = new FileReader("saves/save.json")) {
            JSONObject gameStateJSON = (JSONObject) parser.parse(saveReader);
            loadedGameState.gameId = ((Number) gameStateJSON.get("gameId")).intValue();
            loadedGameState.map = Map.fromJSONObject((JSONObject) gameStateJSON.get("map"));
            loadedGameState.currentPlayer = (Player) gameStateJSON.get("currentPlayerID");
            JSONArray playersArray = (JSONArray) gameStateJSON.get("players");
            loadedGameState.players = (ArrayDeque<Player>) playersArray.stream().map(playerObject -> Player.fromJSONObject((JSONObject) playerObject))
                    .collect(Collectors.toCollection(ArrayDeque::new));
            JSONArray entitiesArray = (JSONArray) gameStateJSON.get("entities");
            Entity[][] entities = new Entity[loadedGameState.map.width][loadedGameState.map.width];
            for (int i = 0; i < entitiesArray.size(); i++) {
                JSONArray row = (JSONArray) entitiesArray.get(i);
                for (int j = 0; j < row.size(); j++) {
                    entities[i][j] = Entity.fromJSONObject((JSONObject) row.get(j));
                }
            }
            loadedGameState.entities = entities;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return loadedGameState;
    }
}