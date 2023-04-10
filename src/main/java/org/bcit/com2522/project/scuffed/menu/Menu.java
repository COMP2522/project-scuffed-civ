package org.bcit.com2522.project.scuffed.menu;

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
    this.currentState = new MainMenuMenuState(this);
  }

  /**
   * Sets state.
   *
   * @param newState the new state
   */
  public void setState(MenuState newState) {
    this.currentState = newState;
  }

  /**
   * Draw.
   *
   * @param scene the scene
   */
  public void draw(Window scene) {
    currentState.draw(scene);
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
