package org.bcit.com2522.project.scuffed.hud;

import org.bcit.com2522.project.scuffed.client.Window;
import org.bcit.com2522.project.scuffed.uiComponents.Button;
import org.bcit.com2522.project.scuffed.uiComponents.ButtonManager;
import processing.core.PFont;
import processing.core.PImage;

/**
 * Represents the state of the HUD and manages the buttons, inputs,
 * and UI elements for that particular state. Designed to be extended by other
 * classes to create specific HUD states.
 *
 * @author Brendan Doyle
 * @version 1.0
 */
public abstract class HudState {
  /**
   * The Hud.
   */
  public Hud hud;
  /**
   * The Rivet panel.
   */
  public PImage rivetPanel;
  /**
   * The Soldier selected img.
   */
  public PImage soldierSelectedImg;
  /**
   * The Building selected img.
   */
  public PImage buildingSelectedImg;
  /**
   * The Worker selected img.
   */
  public PImage workerSelectedImg;
  /**
   * The Font large.
   */
  public PFont fontLarge;
  /**
   * The Font medium.
   */
  public PFont fontMedium;
  /**
   * The Font small.
   */
  public PFont fontSmall;
  /**
   * The Rusted metal img.
   */
  public PImage rustedMetalImg;
  /**
   * The Coin img.
   */
  public PImage coinImg;
  /**
   * The Move img.
   */
  public PImage moveImg;
  /**
   * The Health img.
   */
  public PImage healthImg;
  /**
   * The Attack img.
   */
  public PImage attackImg;
  /**
   * The Resources img.
   */
  public PImage resourcesImg;
  /**
   * The Range img.
   */
  public PImage rangeImg;
  /**
   * The Rusted metal.
   */
  public PImage rustedMetal;
  /**
   * The Icon b.
   */
  public PImage iconB;
  /**
   * The Icon c.
   */
  public PImage iconC;
  /**
   * The Icon f.
   */
  public PImage iconF;
  /**
   * The Icon m.
   */
  public PImage iconM;
  /**
   * The Icon x.
   */
  public PImage iconX;
  /**
   * The Icon wasd.
   */
  public PImage iconWasd;
  /**
   * The Arrow keys img.
   */
  public PImage arrowKeysImg;
  /**
   * The Panel.
   */
  public PImage panel;
  /**
   * The Gun button icon.
   */
  public PImage gunButtonIcon;
  /**
   * The Building button icon.
   */
  public PImage buildingButtonIcon;
  /**
   * The Worker button icon.
   */
  public PImage workerButtonIcon;
  /**
   * The Center x.
   */
// center of the screen
  int centerX;
  /**
   * The Center y.
   */
  int centerY;

  /**
   * The Button manager.
   */
  ButtonManager buttonManager;

  /**
   * Constructor initializes the HUDState object with a given HUD object.
   * Calculates the center of the screen.
   * Calls the abstract setup method that are implemented by
   * subclasses to set up buttons, inputs, and shapes.
   *
   * @param hud the hud
   */
  public HudState(Hud hud) {
    this.hud = hud;
    centerX = hud.scene.width / 2;
    centerY = hud.scene.height / 2;
    setup();
  }

  /**
   * Abstract method that implements subclasses to set up
   * buttons, inputs, and shapes for a specific HUD state.
   */
  public abstract void setup();

  /**
   * Checks mouse position against a list of buttons and
   * calls the click method of the button that was clicked.
   *
   * @param xpos the xpos
   * @param ypos the ypos
   * @return boolean boolean
   */
  public boolean clicked(int xpos, int ypos) {
    for (Button button : buttonManager.buttons) {
      if (button.isClicked(xpos, ypos)) {
        button.click();
        return true;
      }
    }
    return false;
  }

  /**
   * Abstract method that implements subclasses to draw
   * the specific HUD state on the screen using the provided scene.
   *
   * @param scene the scene
   */
  public abstract void draw(Window scene);


}
