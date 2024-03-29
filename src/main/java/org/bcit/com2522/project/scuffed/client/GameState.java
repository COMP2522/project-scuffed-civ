package org.bcit.com2522.project.scuffed.client;

import static org.bcit.com2522.project.scuffed.client.Window.GameImages;
import static processing.core.PConstants.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Collectors;
import org.bcit.com2522.project.scuffed.ai.AIManager;
import org.bcit.com2522.project.scuffed.hud.InGameHud;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * The type Game state.
 */
public class GameState { // everything manager this is the player manager

  /**
   * The Players.
   */
  public ArrayDeque<Player> players; // made this a doubly ended queue so we can easily cycle through players
  /**
   * The Current player.
   */
  public Player currentPlayer;
  /**
   * The Select position.
   */
  public Position selectPosition;
  /**
   * The Map.
   */
  Map map;
  /**
   * The Zoom amount.
   */
  int zoomAmount;
  /**
   * The X shift.
   */
  int xShift; // only used to change where the map is drawn
  /**
   * The Y shift.
   */
  int yShift; // only used to change where the map is drawn
  private String gameID;
  private Entity[][] entities;
  public Entity selected;

  /**
   * Constructor used for creating a new game.
   *
   * @param numplayers number of players in the game
   * @param mapwidth   width of the map
   * @param mapheight  height of the map
   * @param numAI      the num ai
   */
  public GameState(int numplayers, int mapwidth, int mapheight, int numAI) {
    this.gameID = "Game" + new Random().nextInt(10000); // make a random gameId
    players = new ArrayDeque<>(numplayers + numAI);
    entities = new Entity[mapwidth][mapheight];
    map = new Map(mapwidth, mapheight);
    for (int i = 0; i < numplayers; i++) {
      players.add(new Player(i));
    }
    for (int i = numplayers; i < numplayers + numAI; i++) {
      players.add(new Player(i));
      players.getLast().setAI(true);
    }

    zoomAmount = 32;
    xShift = (1080 / zoomAmount - mapwidth) / 2;
    yShift = (720 / zoomAmount - mapwidth) / 2;

    for (Player player : players) {
      player.setShift((1080 / zoomAmount - mapwidth) / 2, (720 / zoomAmount - mapwidth) / 2); // change to center
      // on their worker
    }
  }

  /**
   * Constructor used primarily when loading a game state from a JSON file.
   */
  public GameState() {
    zoomAmount = 32;
  }

  /**
   * this function is used by the AI to create a deep copy of the GameState
   *
   * @param state the state to make a copy of
   */
  public GameState(GameState state) {
    this.gameID = state.gameID;
    this.map = new Map(state.map);
    this.players = new ArrayDeque<>(state.players.size());

    for (Player value : state.players) {
      this.players.add(new Player(value));

      if (state.currentPlayer.equals(value)) {
        this.currentPlayer = players.getLast();
      }
    }

    this.entities = new Entity[state.entities.length][state.entities[0].length];

    for (int i = 0; i < entities.length; i++) {
      for (int j = 0; j < entities[0].length; j++) {
        Entity entity = state.entities[i][j];
        if (entity != null) {
          if (state.entities[i][j] instanceof Worker)
            this.entities[i][j] = new Worker(entity.getOwnerID(), this, entity.getHealth(), entity.getCost(),
                ((Unit) entity).getMaxMove());
          else if (state.entities[i][j] instanceof Building)
            this.entities[i][j] = new Building(entity.getOwnerID(), this, entity.getHealth(), entity.getCost());
          else
            this.entities[i][j] = new Soldier(entity.getOwnerID(), this, entity.getHealth(), entity.getCost(),
                ((Soldier) entity).getMaxMove(), ((Soldier) entity).getDamage(),
                ((Soldier) entity).getRange());
        }
      }
    }
    this.selected = null;

    zoomAmount = 32;
    xShift = (1080 / zoomAmount - map.width) / 2;
    yShift = (720 / zoomAmount - map.width) / 2;

    for (Player player : players) {
      player.setShift((1080 / zoomAmount - map.width) / 2, (720 / zoomAmount - map.width) / 2); // change to center
      // on their worker
    }

    selectPosition = null;
  }

  /**
   * Gets player.
   *
   * @param ownerId the owner id
   * @return the player
   */
  public Player getPlayer(int ownerId) {
    for (Player player : players) {
      if (player.getID() == ownerId) {
        return player;
      }
    }
    return null;
  }

  /**
   * Reads JSON and creates a game state from it
   *
   * @param gameStateJSON the json to read
   * @return the game state
   */
  public static GameState fromJSONObject(JSONObject gameStateJSON) {
    GameState gameState = new GameState();
    gameState.gameID = (String) (gameStateJSON.get("gameID"));
    gameState.map = Map.fromJSONObject((JSONObject) gameStateJSON.get("map"));
    gameState.xShift = (1080 / gameState.zoomAmount - gameState.map.width) / 2;
    gameState.yShift = (720 / gameState.zoomAmount - gameState.map.width) / 2;
    gameState.currentPlayer = Player.fromJSONObject((JSONObject) gameStateJSON.get("currentPlayer"));
    JSONArray playersArray = (JSONArray) gameStateJSON.get("players");
    gameState.players = (ArrayDeque<Player>) playersArray.stream()
        .map(playerObject -> Player.fromJSONObject((JSONObject) playerObject))
        .collect(Collectors.toCollection(ArrayDeque::new));
    JSONArray entitiesArray = (JSONArray) gameStateJSON.get("entities");
    Entity[][] entities = new Entity[gameState.map.width][gameState.map.width];
    for (int i = 0; i < entitiesArray.size(); i++) {
      JSONArray row = (JSONArray) entitiesArray.get(i);
      for (int j = 0; j < row.size(); j++) {
        entities[i][j] = Entity.fromJSONObject((JSONObject) row.get(j), gameState);
      }
    }
    gameState.entities = entities;
    return gameState;
  }

  /**
   * Loads a gameState from a json file in the "saves" folder
   * if there's an error, try adding a saves folder to the root directory
   *
   * @return the loaded gameState
   * @throws FileNotFoundException the file not found exception
   */
  public static GameState load() throws FileNotFoundException {
    GameState loadedGameState = new GameState();
    JSONParser parser = new JSONParser();
    File saveFile = new File("library/saves.json");
    try (FileReader saveReader = new FileReader(saveFile)) {
      loadedGameState = fromJSONObject((JSONObject) parser.parse(saveReader));
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
    return loadedGameState;
  }

  /**
   * Method called to initialize the game state that sets up a new worker entity
   * for each player.
   */
  public void init() {
    // Initialize the currentPlayer as the first player in the queue
    currentPlayer = players.peek();

    int rows = entities.length;
    int cols = entities[0].length;

    // Calculate the number of grid sections along the x and y axes
    // System.out.println(players.size());
    ArrayDeque<Player> playersQueueCopy = new ArrayDeque<>(players);
    if (players.size() == 2) {
      Player player1 = playersQueueCopy.poll();
      Player player2 = playersQueueCopy.poll();

      entities[0][0] = new Worker(player1.getID(), this, 100, 1, 6);
      entities[rows - 1][cols - 1] = new Worker(player2.getID(), this, 100, 1, 6);
    } else {
      int xSections = (int) Math.ceil(Math.sqrt(players.size()));
      int ySections = (int) Math.ceil((double) players.size() / xSections);

      // Calculate the width and height of each grid section
      int sectionWidth = rows / xSections;
      int sectionHeight = cols / ySections;

      // Create a copy of the playersQueue to iterate over without affecting the
      // original queue

      for (int y = 0; y < ySections; y++) {
        for (int x = 0; x < xSections; x++) {
          if (!playersQueueCopy.isEmpty()) {
            Player player = playersQueueCopy.poll();

            // Calculate the position of the worker for the current player
            int xPos;
            int yPos;
            xPos = x * sectionWidth;
            yPos = y * sectionHeight;
            entities[xPos][yPos] = new Worker(player.getID(), this, 100, 1, 6);
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
    int x = (int) (mousePos.x / zoomAmount) - xShift;
    int y = (int) (mousePos.y / zoomAmount) - yShift;
    return x >= 0 && x < entities.length && y >= 0 && y < entities[0].length;
  }

  /**
   * Method called when the map is clicked.
   *
   * @param mousePos the position of the mouse in pixels
   * @param scene    the scene
   */
  public void clicked(PVector mousePos, Window scene) {
    int x = (int) (mousePos.x / zoomAmount) - xShift;
    int y = (int) (mousePos.y / zoomAmount) - yShift;
    Entity clicked = entities[x][y];
    if (clicked == null && selected == null) { // select empty tile

      System.out.println("Nothing Selected");
    } else if (clicked != null && clicked.getOwnerID() == currentPlayer.getID()) { // select own entity
      selected = clicked;
      ((InGameHud) scene.gameInstance.hud.currentState).unitSelected(selected, entities);
      System.out.println("Selected entity class: " + selected.getClass().getName());
      System.out.println("Selected entity ownerID: " + selected.getOwnerID());
      System.out.println("Selected entity position: " + selected.getPosition(entities));
      System.out.println("Selected entity health: " + selected.getHealth());

    } else if (clicked != null && selected == null) {
      selected = clicked;
      System.out.println("Selected entity class: " + selected.getClass().getName());
      System.out.println("Selected entity ownerID: " + selected.getOwnerID());
      System.out.println("Selected entity position: " + selected.getPosition(entities));
      System.out.println("Selected entity health: " + selected.getHealth());
      System.out.println("Selected enemy resources: " + selected.getOwner().getResources());
      selected = null;
    } else if (clicked != null && selected instanceof Soldier && clicked.getOwnerID() != currentPlayer.getID()) { // attack
      // with
      // soldier
      ((Soldier) selected).attack(entities, clicked);
    } else if (clicked == null && selected instanceof Unit) { // move
      ((Unit) selected).move(entities, new Position(x, y));
    } else {
      System.out.println("Invalid selection");
    }
  }

  /**
   * Key pressed.
   *
   * @param key   the key
   * @param scene the scene
   */
  public void keyPressed(char key, Window scene) {
    if (key == 'w') {
      shift(0, 1);
    } else if (key == 'a') {
      shift(1, 0);
    } else if (key == 's') {
      shift(0, -1);
    } else if (key == 'd') {
      shift(-1, 0);
    } else if (key == ' ') {
      resetShift();
    } else if (key == 'b' && (selected instanceof Worker || selected instanceof Building)) { // creates a building
      ((Worker) selected).buildBuilding(entities);
    } else if (key == 'm' && selected instanceof Building) { // creates a worker (maker)
      ((Building) selected).buildWorker(entities);
    } else if (key == 'f' && selected instanceof Building) { // creates a soldier (fighter)
      ((Building) selected).buildSoldier(entities);
    } else if (key == 'c' && selected instanceof Worker) { // collects
      ((Worker) selected).collect(map.get(selected.getPosition(entities)));
    } else if (key == 'x') { // deselect any entity
      selected = null;
    }

    // else if (key == 'u' && selected instanceof Building) { // fighter with more
    // health
    // ((Building) selected).buildSoldier(entities, 200, 50, 6, 6);
    // } else if (key == 'i' && selected instanceof Building) { // fighter with more
    // damage
    // ((Building) selected).buildSoldier(entities, 100, 100, 6, 6);
    // } else if (key == 'o' && selected instanceof Building) { // fighter with more
    // speed
    // ((Building) selected).buildSoldier(entities, 100, 50, 12, 6);
    // } else if (key == 'p' && selected instanceof Building) { // fighter with more
    // range
    // ((Building) selected).buildSoldier(entities, 100, 50, 6, 12);
    // }

    else if (key == '\n' || key == '\r') {
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

  /**
   * Resize pixels int [ ].
   *
   * @param pixels the pixels
   * @param w1     the w 1
   * @param h1     the h 1
   * @param w2     the w 2
   * @param h2     the h 2
   * @return the int [ ]
   */
  public int[] resizePixels(int[] pixels, int w1, int h1, int w2, int h2) {
    int[] temp = new int[w2 * h2];
    int x_ratio = ((w1 << 16) / w2) + 1;
    int y_ratio = ((h1 << 16) / h2) + 1;
    int x2, y2;
    for (int i = 0; i < h2; i++) {
      for (int j = 0; j < w2; j++) {
        x2 = ((j * x_ratio) >> 16);
        y2 = ((i * y_ratio) >> 16);
        temp[(i * w2) + j] = pixels[(y2 * w1) + x2];
      }
    }
    return temp;
  }

  /**
   * Nearest neighbor resize p image.
   *
   * @param image       the image
   * @param amount      the amount
   * @param currentSize the current size
   * @return the p image
   */
  public PImage nearestNeighborResize(PImage image, float amount, int currentSize) {
    int[] imagePixels = new int[1024];
    int scale = currentSize / 32;

    for (int x = 0; x < 32; x++) {
      for (int y = 0; y < 32; y++) {
        imagePixels[32 * y + x] = image.get(x * scale, y * scale);
      }
    }
    if (amount > 1) { // zoom in
      imagePixels = resizePixels(imagePixels, 32, 32, currentSize * 2, currentSize * 2);
      image = new PImage(currentSize * 2, currentSize * 2, imagePixels, true, new PApplet());
    } else { // zoom out
      imagePixels = resizePixels(imagePixels, 32, 32, currentSize / 2, currentSize / 2);
      image = new PImage(currentSize / 2, currentSize / 2, imagePixels, true, new PApplet());
    }
    return image;
  }

  /**
   * Zoom.
   *
   * @param amount the amount
   */
  public void zoom(float amount) {
    if (!(zoomAmount <= 32 && amount < 1) && !(zoomAmount >= 512 && amount > 1)) {
      for (java.util.Map.Entry<String, PImage> mapElement : GameImages.entrySet()) {
        // mapElement.getValue().resize(zoomAmount, 0);
        mapElement.setValue(nearestNeighborResize(mapElement.getValue(), amount, zoomAmount));
      }

      zoomAmount = (int) (zoomAmount * amount);
      if (amount > 1)
        shift((int) (1080 / zoomAmount / amount), (int) (720 / zoomAmount / amount));
      else
        shift((int) (1080 / zoomAmount * -amount / 2), (int) (720 / zoomAmount * -amount / 2));
    }
  }

  /**
   * Shift.
   *
   * @param x the x
   * @param y the y
   */
  // moving around the map, does not take unit movement into account.
  public void shift(int x, int y) {
    xShift -= x;
    yShift -= y;
  }

  /**
   * Reset shift.
   */
  public void resetShift() {
    xShift = (1080 / zoomAmount - map.width) / 2;
    yShift = (720 / zoomAmount - map.height) / 2;
  }

  /**
   * Sets the current player to the next in the queue and checks win conditions
   */
  public void nextTurn() {
    System.out.println("next turn");
    resetEntityActions();
    moveToNextPlayer();
    checkPlayerLoss();
    checkVictoryCondition();

    // randomly regenerates more resources for certain squares
    map.regenResources();

    selected = null;

    if (currentPlayer.isAI()) {
      AIManager.start(this);
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
    currentPlayer.setShift(xShift, yShift);

    players.offer(players.poll());
    currentPlayer = players.peek();

    for (int i = 0; i < 2; i++) {
      zoom(0.5f);
    }

    yShift = currentPlayer.getYShift();
    xShift = currentPlayer.getXShift();
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
      System.out.print("Player " + (alivePlayers.get(0).getID() + 1));
      if (alivePlayers.get(0).isAI())
        System.out.print(" (AI)");
      System.out.println(" won");
      System.out.println("hit escape to return to the main menu!");
    }
  }

  /**
   * Draws the map and all entities in the gamestate
   *
   * @param scene the window to draw to
   */
  public void draw(Window scene) {
    map.draw(zoomAmount, scene, xShift, yShift);

    for (int i = 0; i < entities.length; i++) { // prints the maps entities
      for (int j = 0; j < entities[0].length; j++) {
        if (entities[i][j] != null) {
          Entity entity = entities[i][j];
          Color color = entity.getOwner().getColor();
          scene.tint(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
          scene.image(GameImages.get(entity.entityType), (i + xShift) * zoomAmount,
              (j + yShift) * zoomAmount);
          scene.noTint();
        }
      }
    }

    if (selected != null) { // prints box around selected entity
      selectPosition = selected.getPosition(entities);
      scene.image(GameImages.get("select"), (selectPosition.getX() + xShift) * zoomAmount,
          (selectPosition.getY() + yShift) * zoomAmount);
    }
  }

  /**
   * Prints all entities in the gamestate to the console for debugging purposes
   */
  public void printEntities() {
    for (Entity[] row : entities) {
      for (Entity element : row) {
        if (element != null) {
          System.out.println(element);
        }
      }
    }
  }

  /**
   * Creates a JSONObject from the current gamestate
   *
   * @return the json object
   */
  public JSONObject toJSONObject() {
    JSONObject gameState = new JSONObject();
    gameState.put("gameID", this.gameID);
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
   * Get entities entity [ ] [ ].
   *
   * @return the entity [ ] [ ]
   */
  public Entity[][] getEntities() {
    return entities;
  }

  /**
   * Sets entities.
   *
   * @param entities the entities
   */
  public void setEntities(Entity[][] entities) {
    this.entities = entities;
  }

  /**
   * Gets current player id.
   *
   * @return the current player id
   */
  public int getCurrentPlayerID() {
    return currentPlayer.getID();
  }

  /**
   * Gets map.
   *
   * @return the map
   */
  public Map getMap() {
    return map;
  }

  /**
   * Sets map.
   *
   * @param map the map
   */
  public void setMap(Map map) {
    this.map = map;
  }

  /**
   * Gets game id.
   *
   * @return the game id
   */
  public String getGameID() {
    return gameID;
  }

  /**
   * assumes quality is for currentPlayer.
   * <p>
   * returns 0 if the gameStates have equal value.
   * returns -1 if this has lower value than passed in gameState
   * returns 1 if this has higher value than passed in gameState
   *
   * @param gameState the game state
   * @return int
   */
  public int compareTo(GameState gameState) {
    if (this.getValue() > gameState.getValue()) {
      return 1;
    } else if (this.getValue() < gameState.getValue()) {
      return -1;
    } else {
      return 0;
    }
  }

  /**
   * Gets value.
   *
   * @return the value of the current gameState depending on the currentPlayer
   */
  public int getValue() {
    int playerHP = 0;
    int playerDMG = 0;
    int enemyHP = 0;
    int enemyDMG = 0;

    int totalEnemies = 0;
    Position avgEnemyPos = new Position(0, 0);


    for (int i = 0; i < entities.length; i++)
      for (int j = 0; j < entities[0].length; j++) {
        Entity entity = entities[i][j];
        if (entity != null) {
          if (!entity.getOwner().equals(currentPlayer)) {
            totalEnemies++;

            enemyHP += entity.getHealth();

            avgEnemyPos.setX(avgEnemyPos.getX() + entity.getPosition(entities).getX());
            avgEnemyPos.setY(avgEnemyPos.getY() + entity.getPosition(entities).getY());

            if (entity instanceof Soldier soldier) {
              enemyDMG += soldier.getDamage();
            }
          }
        }
      }
    if (totalEnemies > 0) {
      avgEnemyPos.setX(avgEnemyPos.getX() / totalEnemies);
      avgEnemyPos.setY(avgEnemyPos.getY() / totalEnemies);
    }

    for (int i = 0; i < entities.length; i++)
      for (int j = 0; j < entities[0].length; j++) {
        Entity entity = entities[i][j];
        if (entity != null) {
          if (entity.getOwner().equals(currentPlayer)) {
            playerHP += entity.getHealth();
            if (entity instanceof Soldier soldier) {
              playerDMG += soldier.getDamage() + 151;
              enemyDMG += Math.abs(avgEnemyPos.getX() - entity.getPosition(entities).getX());
              enemyDMG += Math.abs(avgEnemyPos.getY() - entity.getPosition(entities).getY());
            }
          }
        }
      }


    return ((playerHP + playerDMG) - (enemyDMG + enemyHP) +
            currentPlayer.getResources());

    //return currentPlayer.getResources();
  }

  public static void main (String [] args) {
    GameState gameState = new GameState(1, 16, 16, 1);

    gameState.init();

    GameState gameState1 = new GameState(gameState);

    gameState.currentPlayer.spendResources(2);

    GameState gameState2 = new GameState(gameState);

    System.out.println(gameState.currentPlayer.getResources());
    System.out.println(gameState1.currentPlayer.getResources());

    System.out.println(gameState.players);

    System.out.println(gameState1.players);

    System.out.println(gameState2.players);
  }
}