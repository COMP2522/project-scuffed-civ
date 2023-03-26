package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PConstants;

import java.io.File;

import static org.bcit.com2522.project.scuffed.client.Window.UIImages;

public class MainMenuUIState extends UIState implements PConstants {

    GraphicManager graphicManager;

    private Label errorMessage;
    public MainMenuUIState(Window scene, Menu menu) {
        super(scene, menu, new ButtonManager(scene));
        graphicManager = scene.getGraphicManager();

        setup();
    }
    @Override
    public void setup() {
        // Load images

        int height = scene.height;
        File saveFile = new File("saves/save.json");
        if (saveFile.isFile()) {
            // Load Continue button
            Button loadGameButton = new Button(50, height - 450, 250, height - 400, () -> onLoadGameClicked(),
                "", UIImages.get("menuLoad"), UIImages.get("menuLoadHov"), UIImages.get("menuLoadSel"), scene);
            buttonManager.add(loadGameButton);
        } else {
            // Gray out Continue button
            graphicManager.addGraphic(50, height - 450, UIImages.get("menuLoadGry"));
        }

        // Create buttons
        Button newGameButton = new Button(50, height - 350, 250, height - 300, () -> onNewGameClicked(),
            "", UIImages.get("menuNew"), UIImages.get("menuNewHov"), UIImages.get("menuNewSel"), scene);

        Button onlineButton = new Button(50, height - 250, 250, height - 200, () -> onOnlineClicked(),
            "", UIImages.get("menuOnline"), UIImages.get("menuOnlineHov"), UIImages.get("menuOnlineSel"), scene);
        Button exitButton = new Button(50, height - 100, 250, height - 50, () -> onBackClicked(),
            "", UIImages.get("menuExit"), UIImages.get("menuExitHov"), UIImages.get("menuExitSel"), scene);

        graphicManager.addGraphic(500, height - 500, UIImages.get("logo"));
        System.out.println("Logo loaded");
        //Create error message label
        errorMessage = new Label(50, height - 200, "No save file found", 14, scene);


        // Add buttons to ButtonManager
        buttonManager.add(newGameButton);

        buttonManager.add(onlineButton);
        buttonManager.add(exitButton);

        // TODO: Add settings button
//        buttonManager.add(settingsButton);

    }

    public void onNewGameClicked() {
        // Change the menu state to the New Game state
        menu.setState(new NewGameUIState(scene, menu));
    }

    public void onLoadGameClicked() {
        // Change the menu state to the Load Game state
        if (new File("saves/save.json").exists()) {
            menu.setState(new LoadingUIState(scene, menu));

            // Run the loading process in a separate thread
            scene.loadGame();
            menu.setState(new MainMenuUIState(scene, menu));

        } else {
            errorMessage.draw();
        }
    }

    public void onOnlineClicked() {
        // Change the menu state to the Online Multiplayer state
        menu.setState(new OnlineUIState(scene, menu));
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
