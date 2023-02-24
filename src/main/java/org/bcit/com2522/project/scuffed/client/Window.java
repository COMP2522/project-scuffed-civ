package org.bcit.com2522.project.scuffed.client;

import processing.core.PApplet;

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
    gameState = new GameState(this);

    gameState.init();

    menu = new Menu(this);
  }

  @Override
  public void keyPressed() {

  }

  @Override
  public void mouseClicked() {
    //ellipse(mouseX, mouseY, 20, 20);
    if(inGame) {
      Position position = new Position((int) (mouseX / 32), (int) (mouseY / 32));

      gameState.clicked(position);
    } else {
      menu.clicked();
    }
  }

  /**
   * Called on every frame. Updates scene object
   * state and redraws the scene. Drawings appear
   * in order of function calls.
   */
  public void draw() {
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