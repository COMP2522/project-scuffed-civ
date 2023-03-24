package org.bcit.com2522.project.scuffed.ui;

import java.io.File;

public class MainMenuUIState extends UIState {

    private Label errorMessage;
    public MainMenuUIState(UI UI) {
        super(UI);
        graphicManager = UI.scene.getGraphicManager();
        setup();
    }
    @Override
    public void setup() {
        // Create buttons
        Button newGameButton = new Button(50, 100, 250, 150, () -> onNewGameClicked() , "New Game");
        Button loadGameButton = new Button(50, 200, 250, 250, () -> onLoadGameClicked(), "Load Game");
        Button onlineButton = new Button(50, 300, 250, 350, () -> onOnlineClicked(), "Online");
        Button exitButton = new Button(50, 500, 250, 550, () -> onBackClicked(), "Exit");



        graphicManager.addGraphic(500, 100, UI.scene.getLoadedPImage("logo"));
        



        //Create error message label
        errorMessage = new Label(50, 400, "No save file found", 14);

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
        UI.setState(new NewGameUIState(UI));
    }

    public void onLoadGameClicked() {
        // Change the menu state to the Load Game state
        if (new File("saves/save.json").exists()) {
            UI.setState(new LoadingUIState(UI));

            // Run the loading process in a separate thread
            UI.scene.loadGame();
            UI.setState(new MainMenuUIState(UI));

        } else {
            errorMessage.draw(UI.scene);
        }
    }

    public void onOnlineClicked() {
        // Change the menu state to the Online Multiplayer state
        UI.setState(new OnlineUIState(UI));
    }

//    public void onSettingsClicked() {
//        // Change the menu state to the Settings state
//        menu.setState(new SettingsMenuState(scene, menu));
//    }

    public void onBackClicked() {
        // exit the game
        UI.scene.exit();
    }
}
