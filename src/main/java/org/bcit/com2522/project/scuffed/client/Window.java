package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.server.GameServer;
import org.bcit.com2522.project.scuffed.ui.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author bean
 *
 */
public class Window extends PApplet {

  //Map map;

  public boolean inGame = false;

  Menu menu;

  HUD hud;

  GameState gameState;

  Boolean debugMode = false;
  static DebugMenu debugMenu;
  ClickableManager clickableManager;

  /**server variables**/
  private Socket socket;
  private int clientId;
  private String hostIP;
  private int port;
  private ObjectInputStream ois;
  private ObjectOutputStream oos;

  public GameServer gameServer;

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
    this.init();
  }

  public void init() {
    //map = new Map(this, 20, 20);
    clickableManager = new ClickableManager(this);
    surface.setTitle("Scuffed - Main Menu");
    clientId = new java.util.Random().nextInt(100000);
    menu = new Menu(this);
  }

  public void initGame(int numplayers, int mapwidth, int mapheight) {
    gameState = new GameState(this, numplayers, mapwidth, mapheight);
    gameState.init();
  }

  //TODO: implement actual server
  public void initGameServer(int numplayers, int mapwidth, int mapheight, int port) {
    this.port = port;
    this.hostIP = "localhost";
    gameServer = new GameServer();
    gameState = new GameState(this, numplayers, mapwidth, mapheight);
    gameServer.start(gameState, port);


    gameState.init();
  }

  @Override
  public void keyPressed() {
    if(inGame) {
      gameState.keyPressed(key);
    }
    if (keyCode == 114) {
      debugMode = !debugMode;
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
    if(inGame) {
      PVector mousePos = new PVector(mouseX, mouseY);
      gameState.clicked(mousePos);
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
      gameState.draw();
    } else {
      menu.draw();
    }
    // Debug Info - Can be added to
    if(debugMode) {
      debugMenu.draw();
    }
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

  public Player getCurrentPlayer() {
    if(gameState == null){
      return null;
    }
    return gameState.currentPlayer;
  }

  public void loadGame() {
    try {
      this.gameState = GameState.load(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
    inGame = true;
  }

  public void saveGame() {
    try {
      gameState.save();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void joinGame(String hostIP, int port) {
    System.out.println("Joining game at " + hostIP + ":" + port);
  }

  public void endTurn() {
    gameState.nextTurn();
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