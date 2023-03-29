package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.ui.Button;
import org.bcit.com2522.project.scuffed.ui.ButtonManager;
import org.bcit.com2522.project.scuffed.ui.MenuState;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Represents the state of the HUD and manages the buttons, inputs,
 * and UI elements for that particular state. Designed to be extended by other
 * classes to create specific HUD states.
 * @author Brendan Doyle
 * @version 1.0
 */

public abstract class HUDState {
    public HUD hud; // reference to the HUD object that this HUDState belongs to
    public PImage rivetPanel;
    public PFont fontLarge;
    public PFont fontMedium;
    public PFont fontSmall;

    // center of the screen
    int centerX;
    int centerY;

    // Button manager for this state
    ButtonManager buttonManager;

    /**
     * Constructor initializes the HUDState object with a given HUD object.
     * Calculates the center of the screen.
     * Calls the abstract setup method that are implemented by
     * subclasses to set up buttons, inputs, and shapes.
     * @param hud
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
     * @param xpos
     * @param ypos
     * @return
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
     * @param scene
     */
    public abstract void draw(Window scene);


}
