package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

/**
 * The type Menu.
 */
public class Menu {
  /**
   * The Current state.
   */
  public MenuState currentState;
  /**
   * The Scene.
   */
  Window scene;

  /**
   * Instantiates a new Menu.
   *
   * @param scene the scene
   */
  public Menu(Window scene) {
        this.scene = scene;
        this.currentState = new MainMenuMenuState(scene, this);
    }

  /**
   * Sets state.
   *
   * @param newState the new state
   */
  public void setState(MenuState newState) {
        scene.wipeGraphics();
        this.currentState = newState;
    }

  /**
   * Draw.
   */
  public void draw() {
        currentState.draw();
    }

  /**
   * Clicked boolean.
   *
   * @param xpos the xpos
   * @param ypos the ypos
   * @return the boolean
   */
  public boolean clicked(int xpos, int ypos) {
        return currentState.clicked(xpos, ypos);
    }

 }
