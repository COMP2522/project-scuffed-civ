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
        this.gameId = new Random().nextInt(10000); //make a random gameId
        players = new ArrayList<Player>();
        this.scene = scene;

        entities = new Entity[mapwidth][mapheight];
        map = new Map(scene, mapwidth, mapheight);

        scale = 1;

        for(int i = 0; i < numplayers; i++) {
            players.add(new Player(scene, i));
        }
    }

    public GameState(){};

    public void init() {

        //this is just for now more logic will have to go into making players later

        currentPlayer = players.get(0);
    }

    public void clicked(PVector mousePos) {
        Position position = new Position((int) (mousePos.x / 32), (int) (mousePos.y / 32));

        if (mousePos.x >= 700 && mousePos.y >= 500) { //position of nextTurn button
            save();

            try {
                GameState.load(scene, gameId);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
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

    /**
     * Saves the current gamestate to a json file in the "saves" folder
     * currently called at end of player turn
     */
    public void save(){
        JSONObject gamestate = new JSONObject();
        gamestate.put("gameId", this.gameId);
        gamestate.put("map", map.toJSONObject());
        gamestate.put("currentPlayer", currentPlayer.toJSONObject());
        JSONArray playerArray = new JSONArray();
        for (Player player: players) {
            playerArray.add(player.toJSONObject());
        }
        gamestate.put("players", playerArray);

        JSONArray entityArray = new JSONArray();
        for (Entity[] row: entities) {
            JSONArray rowArray = new JSONArray();
            for (Entity entity: row) {
                if(entity != null) {
                    rowArray.add(entity.toJSONObject());
                }
            }
            entityArray.add(rowArray);
        }
        gamestate.put("entities", entityArray);

        try(FileWriter saveFile = new FileWriter("saves/save" + gameId + ".json")) {
            saveFile.write(gamestate.toJSONString());
            saveFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses save.json for GameState
     *
     * @return loadedGameState as a GameState object
     * @throws FileNotFoundException
     */
    public static void load(Window window, int gameId) throws FileNotFoundException {
        GameState loadedGameState = new GameState();
        JSONParser parser = new JSONParser();
        try(FileReader saveReader = new FileReader("saves/save" + gameId + ".json")){
            JSONObject gameStateJSON = (JSONObject)parser.parse(saveReader);
            loadedGameState.scene = window;
            loadedGameState.gameId = ((Number)gameStateJSON.get("gameId")).intValue() + 1;
            loadedGameState.map = Map.fromJSONObject((JSONObject) gameStateJSON.get("map"), window);
            loadedGameState.currentPlayer = Player.fromJSONObject((JSONObject) gameStateJSON.get("currentPlayer"));
            JSONArray playersArray = (JSONArray) gameStateJSON.get("players");
            loadedGameState.players = (ArrayList<Player>) playersArray
                    .stream()
                    .map(playerObject ->
                            Player.fromJSONObject((JSONObject)playerObject, window, loadedGameState.map)) //TODO: Maybe remove reference to map or scene from player?
                    .collect(Collectors.toList());
            JSONArray entitiesArray = (JSONArray) gameStateJSON.get("entities");
            loadedGameState.entities = (Entity[][]) entitiesArray
                    .stream()
                    .map(row -> ((JSONArray) row)
                            .stream()
                            .map((entity) -> Entity.fromJSONObject((JSONObject) entity, window))
                            .toArray(Entity[]::new)) //the ::new calls the constructor for a new entity array, kinda neat
                    .toArray(Entity[][]::new);
        } catch (IOException  | ParseException e) {
            e.printStackTrace();
        }

        loadedGameState.save();
    }

}
