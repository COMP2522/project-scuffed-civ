package org.bcit.com2522.project.scuffed.menu;

import org.bcit.com2522.project.scuffed.uicomponents.Button;
import org.bcit.com2522.project.scuffed.uicomponents.ButtonManager;
import org.bcit.com2522.project.scuffed.uicomponents.GraphicManager;
import org.bcit.com2522.project.scuffed.uicomponents.Label;
import processing.core.PConstants;

import java.io.File;

import static org.bcit.com2522.project.scuffed.client.Window.UIImages;

/**
 * The main menu menu state. This is the first menu that the player sees when they start the game.
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
   * @param menu the menu
   */
  public MainMenuMenuState(Menu menu) {
    super(menu, new ButtonManager(menu.scene));
    graphicManager = menu.scene.getGraphicManager();

    setup();
  }

  @Override
  public void setup() {
    // Load images


    int height = menu.scene.height;

    Button menuBackground = new Button(0, 0, menu.scene.width, menu.scene.height, menu.scene, UIImages.get("backgroundMenu"));

    File saveFile = new File("library/saves.json");
    System.out.println(saveFile.isFile());
    Button loadGameButton = new Button(50, height - 450, 250, height - 400, () -> onLoadGameClicked(),
            "", UIImages.get("menuLoad"), UIImages.get("menuLoadHov"), UIImages.get("menuLoadSel"), menu.scene, UIImages.get("menuLoadGry"), false);
    loadGameButton.setClickable(saveFile.isFile());

    // Create buttons
    Button newGameButton = new Button(50, height - 350, 250, height - 300, () -> onNewGameClicked(),
            "", UIImages.get("menuNew"), UIImages.get("menuNewHov"), UIImages.get("menuNewSel"), menu.scene);

    Button onlineButton = new Button(50, height - 250, 250, height - 200, () -> onOnlineClicked(),
            "", UIImages.get("menuOnline"), UIImages.get("menuOnlineHov"), UIImages.get("menuOnlineSel"), menu.scene);
    Button exitButton = new Button(50, height - 100, 250, height - 50, () -> onBackClicked(),
            "", UIImages.get("menuExit"), UIImages.get("menuExitHov"), UIImages.get("menuExitSel"), menu.scene);


    // Non functional button
    Button logo = new Button(500, height - 500, 500 + UIImages.get("logo").width, height - 500 + UIImages.get("logo").height, menu.scene, UIImages.get("logo"));
    if (saveFile.isFile()) {
      loadGameButton.setClickable(true);
    }
    System.out.println("Logo loaded");
    //Create error message label
    errorMessage = new Label(50, height - 200, "No save file found", 14);

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
    menu.setState(new NewGameMenuState(menu));
  }

  /**
   * On load game clicked.
   */
  public void onLoadGameClicked() {
    // Change the menu state to the Load Game state
    if (new File("library/saves.json").exists()) {
      menu.setState(new LoadingMenuState(menu));

      // Run the loading process in a separate thread
      menu.scene.loadGame();
      menu.setState(new MainMenuMenuState(menu));

    } else {
      errorMessage.draw(menu.scene);
    }
  }

  /**
   * On online clicked.
   */
  public void onOnlineClicked() {
    // Change the menu state to the Online Multiplayer state
    menu.setState(new OnlineMenuState(menu));
  }

  public void onBackClicked() {
    // exit the game
    menu.scene.exit();
  }
}
