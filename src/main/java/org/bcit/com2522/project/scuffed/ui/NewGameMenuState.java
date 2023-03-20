package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PImage;

import static processing.awt.ShimAWT.loadImage;

public class NewGameMenuState extends MenuState {
    public NewGameMenuState(Window scene, Menu menu) {
        super(scene, menu, new ButtonManager(scene));
        setup();
    }
    @Override
    public void setup() {
        // Create and add the buttons for the main menu
        PImage background = loadImage(scene, "background.png");
        PImage button = loadImage(scene, "button_blank.png");
        PImage buttonPressed = loadImage(scene, "button_blank_pressed.png");
    }

    public void onBackClicked() {
        // Change the menu state to the New Game state
        menu.setState(new MainMenuState(scene, menu));
    }
}
