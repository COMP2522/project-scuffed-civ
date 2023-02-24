package org.bcit.com2522.project.scuffed.client;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;
import sun.awt.windows.WPathGraphics;

import java.awt.*;
import java.net.Socket;
import java.util.ArrayList;

/**

 * @author bean
 *
 */
public class Window extends PApplet {

  Map map;

  boolean inGame;

  Socket socket;

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

  }

  @Override
  public void keyPressed() {

  }

  /**
   * Called on every frame. Updates scene object
   * state and redraws the scene. Drawings appear
   * in order of function calls.
   */
  public void draw() {
    if(inGame){
      map.draw();
    } else {
      //menu.draw();
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