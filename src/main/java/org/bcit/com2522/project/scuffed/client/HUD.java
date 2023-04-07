package org.bcit.com2522.project.scuffed.client;

import processing.core.PVector;

/**
 * The HUD class is responsible for managing the display of
 * information, menus, and other UI elements on the screen during gameplay.
 *
 * @author Brendan Doyle
 * @version 1.0
 */
public class HUD {
  /**
   * The Current player.
   */
  public Player currentPlayer;
  /**
   * The Current state.
   */
  public HUDState currentState;
  /**
   * The Scene.
   */
  Window scene; // reference to the main window

  /**
   * Instantiates a new Hud.
   *
   * @param scene the scene
   */
// constructor initializes the HUD object with a given Window object scene
    // and sets the initial state to the inGameStartHUD state
    public HUD(Window scene) {
        this.scene = scene;
        this.currentState = new inGameHUD(this);
    }

  /**
   * Sets the current state of the HUD to a new HUDState object.
   * and wipes the graphics from the previous state.
   *
   * @param newState the new state
   */
  public void setState(HUDState newState) {
        scene.wipeGraphics();
        this.currentState = newState;
    }

  /**
   * Calls the draw method of the current HUDState objec,
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

        return currentState.clicked((int)mousePos.x, (int)mousePos.y);
    }

}