package org.bcit.com2522.project.scuffed.menu;

import org.bcit.com2522.project.scuffed.client.Window;
import org.bcit.com2522.project.scuffed.uiComponents.Button;
import org.bcit.com2522.project.scuffed.uiComponents.ButtonManager;
import org.bcit.com2522.project.scuffed.uiComponents.GraphicManager;
import org.bcit.com2522.project.scuffed.uiComponents.Label;
import processing.core.PConstants;

import java.io.File;

import static org.bcit.com2522.project.scuffed.client.Window.UIImages;

/**
 * The type Main menu menu state.
 */
public class MainMenuMenuState extends MenuState implements PConstants {

  /**
   * The Graphic manager.
   */
  GraphicManager graphicManager;

    private Label errorMessage;

  /**
   * Instantiates a new Main menu menu state.
   *
   * @param scene the scene
   * @param menu  the menu
   */
  public MainMenuMenuState(Window scene, Menu menu) {
        super(scene, menu, new ButtonManager(scene));
        graphicManager = scene.getGraphicManager();

        setup();
    }
    @Override
    public void setup() {
        // Load images


        int height = scene.height;

        Button menuBackground = new Button(0, 0, scene.width, scene.height, scene, UIImages.get("backgroundMenu"));

        File saveFile = new File("library/saves.json");
        System.out.println(saveFile.isFile());
        Button loadGameButton = new Button(50, height - 450, 250, height - 400, () -> onLoadGameClicked(),
                "", UIImages.get("menuLoad"), UIImages.get("menuLoadHov"), UIImages.get("menuLoadSel"), scene, UIImages.get("menuLoadGry"), false);
        loadGameButton.setClickable(saveFile.isFile());

        // Create buttons
        Button newGameButton = new Button(50, height - 350, 250, height - 300, () -> onNewGameClicked(),
            "", UIImages.get("menuNew"), UIImages.get("menuNewHov"), UIImages.get("menuNewSel"), scene);

        Button onlineButton = new Button(50, height - 250, 250, height - 200, () -> onOnlineClicked(),
            "", UIImages.get("menuOnline"), UIImages.get("menuOnlineHov"), UIImages.get("menuOnlineSel"), scene);
        Button exitButton = new Button(50, height - 100, 250, height - 50, () -> onBackClicked(),
            "", UIImages.get("menuExit"), UIImages.get("menuExitHov"), UIImages.get("menuExitSel"), scene);


        // Non functional button
        Button logo = new Button(500, height - 500, 500 + UIImages.get("logo").width, height - 500 + UIImages.get("logo").height, scene, UIImages.get("logo"));
        if(saveFile.isFile()) {
            loadGameButton.setClickable(true);
        }
        System.out.println("Logo loaded");
        //Create error message label
        errorMessage = new Label(50, height - 200, "No save file found", 14, scene);

        buttonManager.add(menuBackground);
        buttonManager.add(loadGameButton);

        // Add buttons to ButtonManager
        buttonManager.add(newGameButton);

        buttonManager.add(onlineButton);
        buttonManager.add(exitButton);
        buttonManager.add(logo);

        // TODO: Add settings button
//        buttonManager.add(settingsButton);

    }

  /**
   * On new game clicked.
   */
  public void onNewGameClicked() {
        // Change the menu state to the New Game state
        menu.setState(new NewGameMenuState(scene, menu));
    }

  /**
   * On load game clicked.
   */
  public void onLoadGameClicked() {
        // Change the menu state to the Load Game state
        if (new File("library/saves.json").exists()) {
            menu.setState(new LoadingMenuState(scene, menu));

            // Run the loading process in a separate thread
            scene.loadGame();
            menu.setState(new MainMenuMenuState(scene, menu));

        } else {
            errorMessage.draw();
        }
    }

  /**
   * On online clicked.
   */
  public void onOnlineClicked() {
        // Change the menu state to the Online Multiplayer state
        menu.setState(new OnlineMenuState(scene, menu));
    }

    public void onBackClicked() {
        // exit the game
        scene.exit();
    }
}
