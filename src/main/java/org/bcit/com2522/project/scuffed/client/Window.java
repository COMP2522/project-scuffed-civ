package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.server.GameServer;
import org.bcit.com2522.project.scuffed.ui.*;
import org.json.simple.JSONObject;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author bean
 *
 */
public class Window extends PApplet {
  public static HashMap<String, PImage> PImages;
  public boolean inGame = false;

  public Menu menu;
  public GameInstance gameInstance;
  public Boolean debugMode = false;
  static DebugMenu debugMenu;
  public ClickableManager clickableManager;
  public GraphicManager graphicManager;


  /**
   * Called once at the beginning of the program.
   */
  public void settings() {
    size(1080, 720);
  }

  /**
   * Called once at the beginning of the program.
   * Initializes all objects.
   */
  public void setup() {
    initPImages();
    this.init();
  }

  public void init() {
    //map = new Map(this, 20, 20);
    clickableManager = new ClickableManager(this);
    graphicManager = new GraphicManager(this);
    surface.setTitle("Scuffed - Main Menu");
    menu = new Menu(this);
  }

  /**
   * This initializes one HashMap to hold the PImages for all classes
   */
  public void initPImages(){
    PImages = new HashMap<String, PImage>();
    PImages.put("grassTile", loadImage("sprites/Menu/tile_grass.png"));
    PImages.put("rockTile", loadImage("sprites/Menu/tile_rocks.png"));
    PImages.put("waterTile", loadImage("sprites/Menu/tile_water.png"));
    PImages.put("sandTile", loadImage("sprites/Menu/tile_sand.png"));
    PImages.put("buttonBackground", loadImage("sprites/Menu/background.png"));
    PImages.put("buttonHoverBackground", loadImage("sprites/Menu/button_blank.png"));
    PImages.put("buttonClickBackground", loadImage("sprites/Menu/button_blank_pressed.png"));
    PImages.put("soldier", loadImage( "sprites/soldier.png"));
    PImages.put("worker", loadImage("sprites/worker.png"));
    PImages.put("building", loadImage("sprites/building.png"));
    PImages.put("logo", loadImage("sprites/logo.png"));
    PImages.put("select", loadImage("sprites/select.png"));

  }

  public void initGame(int numplayers, int mapwidth, int mapheight) {
    gameInstance = new GameInstance(new HUD(), new GameState(numplayers, mapwidth, mapheight));
    gameInstance.newGame();
  }

  @Override
  public void keyPressed() {
    if(inGame) {
      gameInstance.keyPressed(key, this);
    }
    if (keyCode == 114) {
      debugMode = !debugMode;
    }
    if (keyCode == ESC) {
      key = 0;
    }
    if(menu.currentState instanceof NewGameUIState){
        NewGameUIState newGameMenuState = (NewGameUIState) menu.currentState;
        newGameMenuState.keyPressed(key);
    }
    if(menu.currentState instanceof HostGameUIState){
        HostGameUIState hostGameMenuState = (HostGameUIState) menu.currentState;
        hostGameMenuState.keyPressed(key);
    }
    if(menu.currentState instanceof JoinGameUIState){
        JoinGameUIState joinGameMenuState = (JoinGameUIState) menu.currentState;
        joinGameMenuState.keyPressed(key);
    }
  }

  @Override
  public void mouseClicked() {
    PVector mousePos = new PVector(mouseX, mouseY);
    if(inGame) {
      gameInstance.clicked(mousePos, this);
      surface.setTitle("Scuffed Civ");
    } else {
      menu.clicked(mouseX, mouseY);
      surface.setTitle("Scuffed - " + menu.currentState.getClass().getSimpleName());
    }
  }

  /**
   * Called on every frame. Updates scene object
   * state and redraws the scene. Drawings appear
   * in order of function calls.
   */
  public void draw() {
    background(222);
    if(inGame){
      gameInstance.draw(this);
    } else {
      menu.draw();
    }
    // Debug Info - Can be added to
    if(debugMode) {
      debugMenu.draw();
    }
    graphicManager.drawGraphics();
  }

  public ClickableManager getClickableManager() {
    return clickableManager;
  }

  public void addClickable(Clickable clickable) {
    clickableManager.add(clickable);
  }

  public void removeClickable(Clickable clickable) {
    clickableManager.remove(clickable);
  }

  public GraphicManager getGraphicManager() {
    return graphicManager;
  }

  public void wipeGraphics() {
    graphicManager.wipeGraphics();
  }

  public PImage getLoadedPImage(String name) {
    return PImages.get(name);
  }

  public Player getCurrentPlayer() {
    if(gameInstance == null){
      return null;
    }
    return gameInstance.getCurrentPlayer();
  }

  public void nextTurn() {
    gameInstance.nextTurn();
  }

  public void loadGame() {
    gameInstance = new GameInstance();
    System.out.println("Loading game");
    gameInstance.loadGame();
    inGame = true;
  }

  public void joinGame(String hostIP, int port) {
    gameInstance = new GameInstance();
    gameInstance.joinGame(hostIP, port);
  }

  public void saveGame() {
    gameInstance.saveGame();
  }


  //TODO: implement actual server
  public void initGameServer(int numplayers, int mapwidth, int mapheight, int port) {
//    this.port = port;
//    this.hostIP = "localhost";
//    gameServer = new GameServer();
//    gameState = new GameState(numplayers, mapwidth, mapheight);
//    gameServer.start(gameState, port);
//    gameState.init();
  }


  /**
   * Main function.
   *
   * @param passedArgs arguments from command line
   */
  public static void main(String[] passedArgs) {
    String[] appletArgs = new String[]{"eatBubbles"};
    Window eatBubbles = new Window();
    debugMenu = new DebugMenu(eatBubbles);
    PApplet.runSketch(appletArgs, eatBubbles);
  }



}