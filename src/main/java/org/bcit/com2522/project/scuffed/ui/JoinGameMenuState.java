package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PImage;

import static processing.awt.ShimAWT.loadImage;

public class JoinGameMenuState extends MenuState {
    public JoinGameMenuState(Window scene, Menu menu) {
        super(scene, menu, new ButtonManager(scene));
        setup();
    }
    @Override
    public void setup() {
        Button backButton = new Button(50, 500, 250, 550, () -> onBackClicked(), "back", menu.buttonBackground, menu.buttonHoverBackground, menu.buttonClickBackground, scene);

        // Add the buttons to the button manager
        buttonManager.add(backButton);
    }

    public void onBackClicked() {
        // Change the menu state to the New Game state
        menu.setState(new OnlineMenuState(scene, menu));
    }
}
