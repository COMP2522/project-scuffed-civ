package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PImage;

import static processing.awt.ShimAWT.loadImage;

public class OnlineMenuState extends MenuState {

    public OnlineMenuState(Window scene, Menu menu) {
        super(scene, menu, new ButtonManager(scene));
        setup();
    }
    @Override
    public void setup() {
        // Create and add the buttons for the main menu

        // TODO: fix start game button
        Button backButton = new Button(50, 500, 250, 550, () -> onBackClicked(), "back", menu.buttonBackground, menu.buttonHoverBackground, menu.buttonClickBackground, scene);
        Button hostButton = new Button(50, 600, 250, 650, () -> onHostClicked(), "Host Game", menu.buttonBackground, menu.buttonHoverBackground, menu.buttonClickBackground, scene);
        Button joinButton = new Button(50, 600, 250, 650, () -> onJoinClicked(), "Host Game", menu.buttonBackground, menu.buttonHoverBackground, menu.buttonClickBackground, scene);

        // Add the buttons to the button manager
        buttonManager.add(backButton);
        buttonManager.add(hostButton);
        buttonManager.add(joinButton);
    }

    public void onBackClicked() {
        // Change the menu state to the New Game state
        menu.setState(new MainMenuState(scene, menu));
    }

    public void onHostClicked() {
        // Change the menu state to the New Game state
        menu.setState(new HostGameMenuState(scene, menu));
    }

    public void onJoinClicked() {
        // Change the menu state to the New Game state
        menu.setState(new JoinGameMenuState(scene, menu));
    }
}
