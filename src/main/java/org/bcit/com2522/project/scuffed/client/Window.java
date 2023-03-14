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
  }

  public void initGame(int numplayers, int mapwidth, int mapheight) {
    gameState = new GameState(this, numplayers, mapwidth, mapheight);
  }

  @Override
  public void keyPressed() {
    if(inGame) {
      gameState.keyPressed(key);
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
  }


  /**
   * Main function.
   *
   * @param passedArgs arguments from command line
   */
  public static void main(String[] passedArgs) {
    String[] appletArgs = new String[]{"eatBubbles"};
    Window eatBubbles = new Window();
    PApplet.runSketch(appletArgs, eatBubbles);
  }
}