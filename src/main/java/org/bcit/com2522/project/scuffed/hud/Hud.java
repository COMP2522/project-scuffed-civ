package org.bcit.com2522.project.scuffed.hud;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PVector;

/**
 * The HUD class is responsible for managing the display of
 * information, menus, and other UI elements on the screen during gameplay.
 *
 * @author Brendan Doyle
 * @version 1.0
 */
public class Hud {
  /**
   * The Current state.
   */
  public HudState currentState;
  /**
   * The Scene.
   */
  Window scene;

  /**
   * Instantiates a new Hud.
   *
   * @param scene the scene
   */
  public Hud(Window scene) {
    this.scene = scene;
    this.currentState = new InGameHud(this);
  }

  /**
   * Sets the current state of the HUD to a new HUDState object.
   * and wipes the graphics from the previous state.
   *
   * @param newState the new state
   */
  public void setState(HudState newState) {
    scene.wipeGraphics();
    this.currentState = newState;
  }

  /**
   * Calls the draw method of the current HUDState object,
   * passing the scene. Is responsible for drawing the current state.
   *
   * @param scene the scene
   */
  public void draw(Window scene) {
    currentState.draw(scene);
  }

  /**
   * Checks mouse position and calls the clicked method of the
   * current HUDState object. Is responsible for handling mouse clicks.
   *
   * @param mousePos the mouse pos
   * @return boolean boolean
   */
  public boolean clicked(PVector mousePos) {

    return currentState.clicked((int) mousePos.x, (int) mousePos.y);
  }

}