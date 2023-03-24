package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processing.core.PImage;
import processing.core.PVector;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import static org.bcit.com2522.project.scuffed.client.Window.PImages;
import static processing.core.PConstants.*;

import org.bcit.com2522.project.scuffed.ai.AI;

public class GameState { //everything manager this is the player manager
    int gameId;
    Map map;
    public Player currentPlayer;
    ArrayDeque<Player> players; // made this a doubly ended queue so we can easily cycle through players
    Entity[][] entities;
    Entity selected;
    int zoomAmount = 32;
    int xShift;
    int yShift;

    PImage select;
    Position selectPosition;
    AI ai;

    /**
     * Constructor used for creating a new game.
     *
     * @param numplayers number of players in the game
     * @param mapwidth width of the map
     * @param mapheight height of the map
     */
    public GameState(int numplayers, int mapwidth, int mapheight) {
        this.gameId = new Random().nextInt(10000); //make a random gameId
        players = new ArrayDeque<>(numplayers);
        entities = new Entity[mapwidth][mapheight];
        map = new Map(mapwidth, mapheight);
        for(int i = 0; i < numplayers; i++) {
            players.add(new Player(i));
        }
        select = PImages.get("select");
        zoomAmount = 32;
        xShift = 0;
        yShift = 0;

        ai = new AI();
    }

    /**
     * Constructor used primarily when loading a game state from a JSON file.
     */
    public GameState(){
        zoomAmount = 32;
    };

    /**
     * Method called to initialize the game state that sets up a new worker entity for each player.
     */
    public void init() {
        // Initialize the currentPlayer as the first player in the queue
        currentPlayer = players.peek();

        int rows = entities.length;
        int cols = entities[0].length;

        // Calculate the number of grid sections along the x and y axes
        System.out.println(players.size());
        ArrayDeque<Player> playersQueueCopy = new ArrayDeque<>(players);
        //TODO same logic should be used for all numbers of players
        if (players.size() == 2) {
            Player player1 = playersQueueCopy.poll();
            Player player2 = playersQueueCopy.poll();

            entities[0][0] = new Worker(new Position(0, 0), player1);
            entities[rows - 1][cols - 1] = new Worker(new Position(rows - 1, cols - 1), player2);
        } else {
            int xSections = (int) Math.ceil(Math.sqrt(players.size()));
            int ySections = (int) Math.ceil((double) players.size() / xSections);

            // Calculate the width and height of each grid section
            int sectionWidth = rows / xSections;
            int sectionHeight = cols / ySections;

            // Create a copy of the playersQueue to iterate over without affecting the original queue

            for (int y = 0; y < ySections; y++) {
                for (int x = 0; x < xSections; x++) {
                    if (!playersQueueCopy.isEmpty()) {
                        Player player = playersQueueCopy.poll();

                        // Calculate the position of the worker for the current player
                        int xPos;
                        int yPos;

//                        if (x % 2 == 0) {
                            xPos = x * sectionWidth;
//                        } else {
//                            xPos = x * sectionWidth + sectionWidth - 1;
//                        }

//                        if (y % 2 == 0) {
                            yPos = y * sectionHeight;
//                        } else {
//                            yPos = y * sectionHeight + sectionHeight - 1;
//                        }

                        entities[xPos][yPos] = new Worker(new Position(xPos, yPos), player);
                    }
                }
            }
        }
    }

    /**
     * Checks whether a click was on the map.
     *
     * @param mousePos the position of the mouse
     * @return true if the click was on the map, false otherwise
     */
    public boolean clickedMap(PVector mousePos) {
        int x = (int) (mousePos.x / zoomAmount) + xShift;
        int y = (int) (mousePos.y / zoomAmount) + yShift;
        return x >= 0 && x < entities.length && y >= 0 && y < entities[0].length;
    }

    /**
     * Method called when the map is clicked.
     *
     * @param mousePos the position of the mouse in pixels
     */
    public void clicked(PVector mousePos) {
        int x = (int) (mousePos.x / zoomAmount) + xShift;
        int y = (int) (mousePos.y / zoomAmount) + yShift;
        Entity entity = entities[x][y];
        if (entity == null && selected == null) { //select empty tile
            System.out.println("Nothing Selected");
        } else if (entity != null && entity.getOwnerID() == currentPlayer.getID()) { //select own entity
            selected = entity;
            System.out.println("Selected entity class: " + selected.getClass().getName());
            System.out.println("Selected entity ownerID: " + selected.getOwnerID());
            System.out.println("Selected entity position: " + selected.getPosition());
            System.out.println("Selected entity health: " + selected.getHealth());
        } else if(entity != null && selected == null) {
            selected = entity;
            System.out.println("Selected entity class: " + selected.getClass().getName());
            System.out.println("Selected entity ownerID: " + selected.getOwnerID());
            System.out.println("Selected entity position: " + selected.getPosition());
            System.out.println("Selected entity health: " + selected.getHealth());
            selected = null;
        } else if (entity != null && selected instanceof Soldier && entity.getOwnerID() != currentPlayer.getID()) { //attack with soldier
            ((Soldier) selected).attack(entities, entity);
        } else if (entity == null && selected instanceof Unit) { //move
            ((Unit) selected).move(entities, new Position(x, y), xShift, yShift);
        } else {
            System.out.println("Invalid selection");
        }
    }

    public void keyPressed(char key, Window scene) {
        if(key == 'w') {
            shift(0, 1);
        } else if(key == 'a') {
            shift(1, 0);
        } else if(key == 's') {
            shift(0, -1);
        } else if(key == 'd') {
            shift(-1, 0);
        }

        else if(key == 'b' && (selected instanceof Worker || selected instanceof Building)) { //creates a building
            selected.buildBuilding(entities);
        } else if(key == 'm' && selected instanceof Building) {
            ((Building) selected).buildWorker(entities);
        } else if(key == 'f' && selected instanceof Building) {
            ((Building) selected).buildSoldier(entities);
        } else if(key == 'c' && selected instanceof Worker) {
            ((Worker) selected).collect(map.get(selected.getPosition().getX() + xShift, selected.getPosition().getY() + yShift));
        } else if (key == 'x') {
            selected = null;
        }

        else if (key == '\n' || key == '\r') {
            System.out.println("enter pressed");
            nextTurn();
        } else if (key == ESC) {
            key = 0;
            scene.saveGame();
            scene.inGame = false;
        } else if (key == ' ') {
            resetShift();
        }

        if (key == CODED) {
            if (scene.keyCode == UP) {
                zoom(2);
            } else if (scene.keyCode == DOWN) {
                zoom(0.5f);
            }
        }
    }

    public void zoom(float amount) {
        //TODO change to nearest neighbor resizing if possible
        if (!(zoomAmount <= 32 && amount < 1) && !(zoomAmount >= 512 && amount > 1)) {
            zoomAmount = (int)(zoomAmount * amount);
            for (java.util.Map.Entry<String, PImage> mapElement : PImages.entrySet()) {
                mapElement.getValue().resize(zoomAmount, 0);
            }
        }
    }

    //moving around the map, does not take unit movement into account.
    public void shift(int x, int y) {
        xShift -= x;
        yShift -= y;
        shift2(x, y);
    }

    public void resetShift() {
        shift2(xShift, yShift);

        xShift = 0;
        yShift = 0;
    }

    private void shift2(int xShift, int yShift) {
        map.shift(xShift, yShift);
        for (Entity[] row: entities) {
            for (Entity element: row) {
                if(element != null) {
                    element.shift(new Position(element.getPosition().getX() + (xShift),
                            element.getPosition().getY() + (yShift)));
                }
            }
        }
    }

    /**
     * Sets the current player to the next in the queue and checks win conditions
     */
    public void nextTurn() {
        resetEntityActions();
        moveToNextPlayer();
        checkPlayerLoss();
        checkVictoryCondition();

        //randomly regenerates more resources for certain squares
        map.regenResources();

        selected = null;

         if (currentPlayer.isAI()) {
             ai.start(this);
         }
    }

    /**
     * Checks if the game has been won
     */
    private void resetEntityActions() {
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
    }

    /**
     * Moves to the next player
     */
    private void moveToNextPlayer() {
        players.offer(players.poll());
        currentPlayer = players.peek();
    }

    /**
     * Checks if the current player has lost, and if so, moves to the next player
     */
    private void checkPlayerLoss() {
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
    }

    /**
     * Checks if the game is over, and if so, prints a message and exits the program
     */
    private void checkVictoryCondition() {
        ArrayList<Player> alivePlayers = new ArrayList<Player>();
        for (Player player : players) {
            if (!player.getHasLost()) {
                alivePlayers.add(player);
            }
        }
        if (alivePlayers.size() == 1) {
            System.out.print("Player " + (alivePlayers.get(0).getID()+1));
            if (alivePlayers.get(0).isAI())
                System.out.print(" (AI)");
            System.out.println(" won");
            System.out.println("hit escape to return to the main menu!");
        }
    }


    /**
     * Draws the map and all entities in the gamestate
     * @param scene the window to draw to
     */
    public void draw(Window scene) {
        map.draw(zoomAmount, scene);
        for (Entity[] row: entities) {
            for (Entity entity: row) {
                if(entity != null) {
                    entity.draw(zoomAmount, entity.color, scene);
                }
            }
        }
        if (selected != null) {
            selectPosition = selected.getPosition();
            scene.image(select, selectPosition.getX() * zoomAmount, selectPosition.getY() * zoomAmount);
        }
    }

    /**
     * Prints all entities in the gamestate to the console for debugging purposes
     */
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
     * Reads JSON and creates a gamestate from it
     * @param gameStateJSON the json to read
     */
    public static GameState fromJSONObject(JSONObject gameStateJSON) {
        GameState gameState = new GameState();
        gameState.gameId = ((Number) gameStateJSON.get("gameId")).intValue();
        gameState.map = Map.fromJSONObject((JSONObject) gameStateJSON.get("map"));
        gameState.currentPlayer = Player.fromJSONObject((JSONObject) gameStateJSON.get("currentPlayer")) ;
        JSONArray playersArray = (JSONArray) gameStateJSON.get("players");
        gameState.players = (ArrayDeque<Player>) playersArray.stream().map(playerObject -> Player.fromJSONObject((JSONObject) playerObject))
                .collect(Collectors.toCollection(ArrayDeque::new));
        JSONArray entitiesArray = (JSONArray) gameStateJSON.get("entities");
        Entity[][] entities = new Entity[gameState.map.width][gameState.map.width];
        for (int i = 0; i < entitiesArray.size(); i++) {
            JSONArray row = (JSONArray) entitiesArray.get(i);
            for (int j = 0; j < row.size(); j++) {
                entities[i][j] = Entity.fromJSONObject((JSONObject) row.get(j));
            }
        }
        gameState.entities = entities;
        return gameState;
    }

    /**
     * Loads a gamestate from a json file in the "saves" folder
     * if there's an error, try adding a saves folder to the root directory
     *
     * @return the loaded gamestate
     * @throws FileNotFoundException
     */
    public static GameState load() throws FileNotFoundException {
        GameState loadedGameState = new GameState();
        JSONParser parser = new JSONParser();
        try (FileReader saveReader = new FileReader("saves/save.json")) {
            loadedGameState = fromJSONObject((JSONObject) parser.parse(saveReader));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return loadedGameState;
    }

    public Entity[][] getEntities() {
        return entities;
    }

    public int getCurrentPlayerID() {
        return currentPlayer.getID();
    }

    public Map getMap() {
        return map;
    }
}