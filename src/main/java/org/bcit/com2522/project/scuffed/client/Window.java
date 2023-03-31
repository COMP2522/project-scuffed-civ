package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.ui.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.HashMap;

/**
 *
 * @author bean
 *
 */
public class Window extends PApplet {
  public static HashMap<String, PImage> GameImages;
  public static HashMap<String, PImage> UIImages;

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
    //Images for the GameBoard. Tiles, Units, Buildings, etc.
    GameImages = new HashMap<String, PImage>();
    GameImages.put("grassTile", loadImage("sprites/Menu/tile_grass.png"));
    GameImages.put("rockTile", loadImage("sprites/Menu/tile_rocks.png"));
    GameImages.put("waterTile", loadImage("sprites/Menu/tile_water.png"));
    GameImages.put("sandTile", loadImage("sprites/Menu/tile_sand.png"));
    GameImages.put("soldier", loadImage( "sprites/soldier.png"));
    GameImages.put("worker", loadImage("sprites/worker.png"));
    GameImages.put("building", loadImage("sprites/building.png"));
    GameImages.put("select", loadImage("sprites/select.png"));

    //Images for the UI. Buttons, Menus, Backgrounds, etc.
    UIImages = new HashMap<String, PImage>();
    UIImages.put("logo", loadImage("sprites/logo.png"));
    UIImages.put("select", loadImage("sprites/select.png"));
    UIImages.put("menuNew", loadImage("sprites/Menu/New.png"));
    UIImages.put("menuNewHov", loadImage("sprites/Menu/New_Hov.png"));
    UIImages.put("menuNewSel", loadImage("sprites/Menu/New_Sel.png"));
    UIImages.put("menuLoad", loadImage("sprites/Menu/Load.png"));
    UIImages.put("menuLoadHov", loadImage("sprites/Menu/Load_Hov.png"));
    UIImages.put("menuLoadSel", loadImage("sprites/Menu/Load_Sel.png"));
    UIImages.put("menuLoadGry", loadImage("sprites/Menu/Load_Gry.png"));
    UIImages.put("menuExit", loadImage("sprites/Menu/Exit.png"));
    UIImages.put("menuExitHov", loadImage("sprites/Menu/Exit_Hov.png"));
    UIImages.put("menuExitSel", loadImage("sprites/Menu/Exit_Sel.png"));
    UIImages.put("menuOnline", loadImage("sprites/Menu/Online.png"));
    UIImages.put("menuOnlineHov", loadImage("sprites/Menu/Online_hov.png"));
    UIImages.put("menuOnlineSel", loadImage("sprites/Menu/Online_sel.png"));
    UIImages.put("buttonBackground", loadImage("sprites/Menu/background.png"));
    UIImages.put("buttonHoverBackground", loadImage("sprites/Menu/button_blank.png"));
    UIImages.put("buttonClickBackground", loadImage("sprites/Menu/button_blank_pressed.png"));
    UIImages.put("background", loadImage("sprites/backgroundDirt.png"));
    UIImages.put("logo", loadImage("sprites/logo.png"));
    UIImages.put("backgroundMenu", loadImage("sprites/Menu/backgroundMenu.png"));
  }

  public void initGame(int numplayers, int mapwidth, int mapheight) {
    gameInstance = new GameInstance(new HUD(this), new GameState(numplayers, mapwidth, mapheight));
    gameInstance.newGame();
  }

  public PImage getPImage(String name) {
    return GameImages.get(name);
  }

  public PImage loadImage2(String path) {
    return loadImage(path);
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
    if(menu.currentState instanceof NewGameMenuState){
        NewGameMenuState newGameMenuState = (NewGameMenuState) menu.currentState;
        newGameMenuState.keyPressed(key);
    }
    if(menu.currentState instanceof HostGameMenuState){
        HostGameMenuState hostGameMenuState = (HostGameMenuState) menu.currentState;
        hostGameMenuState.keyPressed(key);
    }
    if(menu.currentState instanceof JoinGameMenuState){
        JoinGameMenuState joinGameMenuState = (JoinGameMenuState) menu.currentState;
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

      background(UIImages.get("background"));
      gameInstance.draw(this);
    } else {
      background(222);
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
    return GameImages.get(name);
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
    gameInstance = new GameInstance(this);
    System.out.println("Loading game");
    gameInstance.loadGame();
    inGame = true;
  }

  public void joinGame(String hostIP, int port) {
    gameInstance = new GameInstance(this);
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