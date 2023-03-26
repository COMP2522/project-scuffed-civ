package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PImage;
import processing.core.PConstants;

import java.io.File;

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
        PImage menuNew = scene.loadImage2("sprites/Menu/New.png");
        PImage menuNewHov = scene.loadImage2("sprites/Menu/New_Hov.png");
        PImage menuNewSel = scene.loadImage2("sprites/Menu/New_Sel.png");
        PImage menuLoad = scene.loadImage2("sprites/Menu/Load.png");
        PImage menuLoadGry = scene.loadImage2("sprites/Menu/Load_Gry.png");
        PImage menuLoadHov = scene.loadImage2("sprites/Menu/Load_Hov.png");
        PImage menuLoadSel = scene.loadImage2("sprites/Menu/Load_Sel.png");
        PImage menuExit = scene.loadImage2("sprites/Menu/Exit.png");
        PImage menuExitHov = scene.loadImage2("sprites/Menu/Exit_Hov.png");
        PImage menuExitSel = scene.loadImage2("sprites/Menu/Exit_Sel.png");
        PImage menuOnline = scene.loadImage2("sprites/Menu/Online.png");
        PImage menuOnlineHov = scene.loadImage2("sprites/Menu/Online_Hov.png");
        PImage menuOnlineSel = scene.loadImage2("sprites/Menu/Online_Sel.png");
        int height = scene.height;
        File saveFile = new File("saves/save.json");
        if (saveFile.isFile()) {
            // Load Continue button
            Button loadGameButton = new Button(50, height - 450, 250, height - 400, () -> onLoadGameClicked(),
                "", menuLoad, menuLoadHov, menuLoadSel, scene);
            buttonManager.add(loadGameButton);
        } else {
            // Gray out Continue button
            graphicManager.addGraphic(50, height - 450, menuLoadGry);
        }

        // Create buttons
        Button newGameButton = new Button(50, height - 350, 250, height - 300, () -> onNewGameClicked(),
            "", menuNew, menuNewHov, menuNewSel, scene);

        Button onlineButton = new Button(50, height - 250, 250, height - 200, () -> onOnlineClicked(),
            "", menuOnline, menuOnlineHov, menuOnlineSel, scene);
        Button exitButton = new Button(50, height - 100, 250, height - 50, () -> onBackClicked(),
            "", menuExit, menuExitHov, menuExitSel, scene);

        graphicManager.addGraphic(500, height - 500, scene.getLoadedPImage("logo"));
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
