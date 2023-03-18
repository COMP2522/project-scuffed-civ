package org.bcit.com2522.project.scuffed.client;

import processing.core.PApplet;
import processing.core.PVector;

import java.net.Socket;

/**
 *
 * @author bean
 *
 */
public class Window extends PApplet {

  //Map map;

  boolean inGame = false;

  Menu menu;

  Socket socket;

  //ArrayList<Player> players = new ArrayList<Player>();

  GameState gameState;

  Boolean debugMode = false;
  static DebugMenu debugMenu;
  ClickableManager clickableManager;

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
    menu = new Menu(this);
    ClickableManager clickableManager = new ClickableManager(this);
  }

  public void initGame(int numplayers, int mapwidth, int mapheight) {
    gameState = new GameState(this, numplayers, mapwidth, mapheight);

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
  }

  @Override
  public void mouseClicked() {
    if(inGame) {
      PVector mousePos = new PVector(mouseX, mouseY);

      gameState.clicked(mousePos);
    } else {
      inGame = menu.clicked(mouseX, mouseY);
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