package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PImage;

import java.io.File;

import static processing.awt.ShimAWT.loadImage;

public class MainMenuState extends MenuState {

    private Label errorMessage;
    public MainMenuState(Window scene, Menu menu) {
        super(scene, menu, new ButtonManager(scene));
        setup();
    }
    @Override
    public void setup() {
        // Create buttons
        Button newGameButton = new Button(50, 100, 250, 150, () -> onNewGameClicked() , "New Game", scene);
        Button loadGameButton = new Button(50, 200, 250, 250, () -> onLoadGameClicked(), "Load Game", scene);
        Button onlineButton = new Button(50, 300, 250, 350, () -> onOnlineClicked(), "Online", scene);
        Button exitButton = new Button(50, 500, 250, 550, () -> onBackClicked(), "Exit", scene);

        //Create error message label
        errorMessage = new Label(50, 400, "No save file found", 14, scene);

        // Add buttons to ButtonManager
        buttonManager.add(newGameButton);
        buttonManager.add(loadGameButton);
        buttonManager.add(onlineButton);
        buttonManager.add(exitButton);

        // TODO: Add settings button
//        buttonManager.add(settingsButton);

    }

    public void onNewGameClicked() {
        // Change the menu state to the New Game state
        menu.setState(new NewGameMenuState(scene, menu));
    }

    public void onLoadGameClicked() {
        // Change the menu state to the Load Game state
        if (new File("saves/save.json").exists()) {
            menu.setState(new LoadingMenuState(scene, menu));

            // Run the loading process in a separate thread
            scene.loadGame();


        } else {
            errorMessage.draw();
        }
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
