package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.server.GameServer;
import org.bcit.com2522.project.scuffed.ui.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Window class that holds the main method and initializes the game. It provides
 *
 * @author Keagan Purtell, Emma Meredith-Black, Brenndan Doyle, and Cameron Walford
 * @Version 0.5
 */
public class Window extends PApplet {
  /**
   * The Game images.
   */
  public static HashMap<String, PImage> GameImages;
  /**
   * The Ui images.
   */
  public static HashMap<String, PImage> UIImages;
  /**
   * The In game.
   */
  public boolean inGame = false;

  /**
   * The Menu.
   */
  public Menu menu;
  /**
   * The Game instance.
   */
  public GameInstance gameInstance;
  /**
   * The Debug mode.
   */
  public Boolean debugMode = false;
  /**
   * The Debug menu.
   */
  static DebugMenu debugMenu;
  /**
   * The Clickable manager.
   */
  public ClickableManager clickableManager;
  /**
   * The Graphic manager.
   */
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

  /**
   * Init.
   */
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
    GameImages.put("soldier", loadImage( "sprites/soldierUnit.png"));
    GameImages.put("worker", loadImage("sprites/workerUnit.png"));
    GameImages.put("building", loadImage("sprites/buildingUnit.png"));
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
    UIImages.put("background", loadImage("sprites/inGameBackgroundWoods.jpg"));
    UIImages.put("logo", loadImage("sprites/logo.png"));
    UIImages.put("backgroundMenu", loadImage("sprites/Menu/backgroundMenu.png"));
    //cant figure out how to make use of these in the inGameHUD class...WIP
//    UIImages.put("rivetPanel", loadImage("sprites/RivetPanel.png"));
//    UIImages.put("soldierSelectedIMG", loadImage("sprites/highResSoldier.jpg"));
//    UIImages.put("buildingSelectedIMG", loadImage("sprites/highResFactory.jpg"));
//    UIImages.put("workerSelectedIMG", loadImage("sprites/highResWorker.jpg"));
//    UIImages.put("rustedMetalIMG", loadImage("sprites/rustedMetalIMG.jpg"));
//    UIImages.put("coinIMG", loadImage("sprites/coin.png"));
//    UIImages.put("moveIMG", loadImage("sprites/movement.png"));
//    UIImages.put("healthIMG", loadImage("sprites/Health.png"));
//    UIImages.put("attackIMG", loadImage("sprites/damage.png"));
//    UIImages.put("resourcesIMG", loadImage("sprites/resources.png"));
//    UIImages.put("rangeIMG", loadImage("sprites/range.png"));
  }

  /**
   * Init game.
   *
   * @param numplayers the numplayers
   * @param mapwidth   the mapwidth
   * @param mapheight  the mapheight
   */
  public void initGame(int numplayers, int mapwidth, int mapheight) {
    gameInstance = new GameInstance(new Hud(this), new GameState(numplayers, mapwidth, mapheight));
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


  /**
   * Add clickable.
   *
   * @param clickable the clickable
   */
  public void addClickable(Clickable clickable) {
    clickableManager.add(clickable);
  }

  /**
   * Remove clickable.
   *
   * @param clickable the clickable
   */
  public void removeClickable(Clickable clickable) {
    clickableManager.remove(clickable);
  }

  /**
   * Gets graphic manager.
   *
   * @return the graphic manager
   */
  public GraphicManager getGraphicManager() {
    return graphicManager;
  }

  /**
   * Wipe graphics.
   */
  public void wipeGraphics() {
    graphicManager.wipeGraphics();
  }

  /**
   * Gets current player.
   *
   * @return the current player
   */
  public Player getCurrentPlayer() {
    if (gameInstance == null) {
      return null;
    }
    return gameInstance.getCurrentPlayer();
  }

  /**
   * Load game.
   */
  public void loadGame() {
    gameInstance = new GameInstance(this);
    System.out.println("Loading game");
    gameInstance.loadGame();
    inGame = true;
  }

  /**
   * Join game.
   *
   * @param hostIP         the host ip
   * @param port           the port
   * @param clientUsername the client username
   */
  public void joinGame(String hostIP, int port, String clientUsername) {
    gameInstance = new GameInstance(this);
    gameInstance.joinGame(hostIP, port, clientUsername);
  }

  /**
   * Save game.
   */
  public void saveGame() {
    try {
      gameInstance.saveGame();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Init online game.
   *
   * @param numplayers the numplayers
   * @param mapwidth   the mapwidth
   * @param mapheight  the mapheight
   * @param port       the port
   */
//TODO: implement actual server
  public void initOnlineGame(int numplayers, int mapwidth, int mapheight, int port) {
    gameInstance = new GameInstance(this);
    GameState gameState = new GameState(numplayers, mapwidth, mapheight);
    GameServer gameServer = new GameServer(gameState, port);
    gameInstance.setGameState(gameState);
    gameInstance.setGameServer(gameServer);
    gameInstance.startServer();
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

  /**
   * Gets connected players.
   *
   * @return the connected players
   */
  public HashSet<String> getConnectedPlayers() {
    return gameInstance.getConnectedPlayers();
  }
}