package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.ui.Button;
import org.bcit.com2522.project.scuffed.ui.ButtonManager;
import org.bcit.com2522.project.scuffed.ui.MenuState;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;
import static org.bcit.com2522.project.scuffed.client.Window.UIImages;

/**
 * Represents the state of the HUD and manages the buttons, inputs,
 * and UI elements for that particular state. Designed to be extended by other
 * classes to create specific HUD states.
 *
 * @author Brendan Doyle
 * @version 1.0
 */
public abstract class HUDState {
    /**
     * The Hud.
     */
    public HUD hud;
    /**
     * The Rivet panel.
     */
    public PImage rivetPanel;
    /**
     * The Soldier selected img.
     */
    public PImage soldierSelectedIMG;
    /**
     * The Building selected img.
     */
    public PImage buildingSelectedIMG;
    /**
     * The Worker selected img.
     */
    public PImage workerSelectedIMG;
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
    public PImage rustedMetalIMG;
    /**
     * The Coin img.
     */
    public PImage coinIMG;
    /**
     * The Move img.
     */
    public PImage moveIMG;
    /**
     * The Health img.
     */
    public PImage healthIMG;
    /**
     * The Attack img.
     */
    public PImage attackIMG;
    /**
     * The Resources img.
     */
    public PImage resourcesIMG;
    /**
     * The Range img.
     */
    public PImage rangeIMG;
    /**
     * The Rusted metal.
     */
    public PImage rustedMetal;
    public PImage iconB;
    public PImage iconC;
    public PImage iconF;
    public PImage iconM;
    public PImage iconX;
    public PImage iconWASD;
    public PImage arrowKeysIMG;
    public PImage panel;
    public PImage gunButtonIcon;
    public PImage buildingButtonIcon;
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
// Button manager for this state
    ButtonManager buttonManager;

    /**
     * Constructor initializes the HUDState object with a given HUD object.
     * Calculates the center of the screen.
     * Calls the abstract setup method that are implemented by
     * subclasses to set up buttons, inputs, and shapes.
     *
     * @param hud the hud
     */
    public HUDState(HUD hud) {
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
     * @return boolean
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
