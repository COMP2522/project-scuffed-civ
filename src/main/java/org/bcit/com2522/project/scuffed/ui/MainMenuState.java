package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PImage;

import static processing.awt.ShimAWT.loadImage;

public class MainMenuState extends MenuState {
    public MainMenuState(Window scene, Menu menu) {
        super(scene, menu, new ButtonManager(scene));
        setup();
    }
    @Override
    public void setup() {
//        PImage buttonBackground = loadImage(scene, "sprites/Menu/background.png");
//        PImage buttonHoverBackground = loadImage(scene, "sprites/Menu/button_blank.png");
//        PImage buttonClickBackground = loadImage(scene, "sprites/Menu/button_blank_pressed.png");

        // Create buttons
        Button newGameButton = new Button(50, 100, 250, 150, () -> onNewGameClicked() , "New Game", menu.buttonBackground, menu.buttonHoverBackground, menu.buttonClickBackground, scene);
        Button loadGameButton = new Button(50, 200, 250, 250, () -> onLoadGameClicked(), "Load Game", menu.buttonBackground, menu.buttonHoverBackground, menu.buttonClickBackground, scene);
        Button onlineButton = new Button(50, 300, 250, 350, () -> onOnlineClicked(), "Online Multiplayer", menu.buttonBackground, menu.buttonHoverBackground, menu.buttonClickBackground, scene);
        Button exitButton = new Button(50, 500, 250, 550, () -> onBackClicked(), "Exit", menu.buttonBackground, menu.buttonHoverBackground, menu.buttonClickBackground, scene);

        // Add buttons to ButtonManager
        buttonManager.add(newGameButton);
        buttonManager.add(loadGameButton);
        buttonManager.add(onlineButton);
        buttonManager.add(exitButton);
//        buttonManager.add(loadGameButton);
//        buttonManager.add(onlineMultiplayerButton);
//        buttonManager.add(settingsButton);

    }

    public void onNewGameClicked() {
        // Change the menu state to the New Game state
        menu.setState(new NewGameMenuState(scene, menu));
    }

    public void onLoadGameClicked() {
        // Change the menu state to the Load Game state
        scene.loadGame();
    }

    public void onOnlineClicked() {
        // Change the menu state to the Online Multiplayer state
        menu.setState(new OnlineMenuState(scene, menu));
    }

//    public void onSettingsClicked() {
//        // Change the menu state to the Settings state
//        menu.setState(new SettingsMenuState(scene, menu));
//    }

    public void onBackClicked() {
        // exit the game
        scene.exit();
    }
}
