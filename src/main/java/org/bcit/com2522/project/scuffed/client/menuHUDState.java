package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.ui.Button;
import org.bcit.com2522.project.scuffed.ui.ButtonManager;

/**
 * Provides a specific implementation for the menu in-game HUD state.
 *
 * @author Brendan Doyle
 * @version 1.0
 */
public class menuHUDState extends HUDState {

  /**
   * Instantiates a new Menu hud state.
   *
   * @param hud the hud
   */
// Constructor takes a HUD object and calls the super constructor
  public menuHUDState(HUD hud) {
    super(hud);
  }

  /**
   * Sets up the buttons and UI elements for the menuHUDState.
   * Creates the buttons and adds them to the button manager.
   */
  @Override
  public void setup() {
    fontLarge = hud.scene.createFont("fonts/Retro Gaming.ttf", 30);
    fontMedium = hud.scene.createFont("fonts/Retro Gaming.ttf", 24);
    fontSmall = hud.scene.createFont("fonts/Retro Gaming.ttf", 15);
    rivetPanel = hud.scene.loadImage("sprites/RivetPanel.png");
    rustedMetal = hud.scene.loadImage("sprites/rustedMetalIMG.jpg");

    buttonManager = new ButtonManager(hud.scene);

//    Button hudMenuButton = new Button(
//        centerX - 540, centerY - 360, centerX - 390, centerY - 310,
//        () -> {hud.setState(new menuHUDState(hud));
//    }, "Menu", rivetPanel, rivetPanel, rivetPanel, hud.scene,
//        rivetPanel, false, 28, 20, 5);
//
//    Button hudEndTurnButton = new Button(
//        centerX + 390, centerY - 360, centerX + 540, centerY - 310,
//        () -> {hud.scene.nextTurn();
//    }, "End Turn", rivetPanel, rivetPanel, rivetPanel, hud.scene,
//        rivetPanel, false, 20, 5, 10);

    Button returnToMainMenuButton = new Button(centerX - 150, centerY - 260, centerX + 150, centerY - 210, () -> {
      onMenuClicked();
    }, "Main Menu", rivetPanel, rivetPanel, rivetPanel, hud.scene,
        rivetPanel, true, 28, 55, 8);

    Button save = new Button(centerX - 150, centerY - 160, centerX + 150, centerY - 110, () -> {
      hud.scene.saveGame();
    }, "Save", rivetPanel, rivetPanel, rivetPanel, hud.scene,
        rivetPanel, true, 28, 95, 8);

    Button settings = new Button(centerX - 150, centerY - 60, centerX + 150, centerY -10, () -> {
      onMenuClicked();
    }, "Settings", rivetPanel, rivetPanel, rivetPanel, hud.scene,
        rivetPanel, true, 28, 65, 8);

    Button exitButton = new Button(centerX - 150, centerY + 40, centerX + 150, centerY + 90,
        () -> {hud.scene.exit();
    }, "Exit", rivetPanel, rivetPanel, rivetPanel, hud.scene
    , rivetPanel, true, 28, 100, 8);

    Button resumeGame = new Button(centerX - 150, centerY + 140, centerX + 150, centerY + 190,
        () -> {hud.setState(new inGameHUD(hud));
    }, "Resume Game", rivetPanel, rivetPanel, rivetPanel, hud.scene,
        rivetPanel, true, 28, 25, 8);

    buttonManager.add(resumeGame);
    buttonManager.add(exitButton);
    buttonManager.add(settings);
    buttonManager.add(save);
    buttonManager.add(returnToMainMenuButton);
//    buttonManager.add(hudMenuButton);
//    buttonManager.add(hudEndTurnButton);

  }

  /**
   * On menu clicked.
   */
  public void onMenuClicked() {
    hud.scene.inGame = false;
  }

  /**
   * Draws the in-game starting HUD state rendering player information
   * and the buttons.
   * @param scene
   */
  @Override
  public void draw(Window scene) {
//
//    // draw the players information
//    scene.image(rivetPanel, centerX - 100, centerY - 360, 200, 50);  // Player selected name box, top middle
//    scene.image(rivetPanel, centerX - 540, centerY + 160, 200, 200);  //  selected char box, bottom left
//    scene.image(rivetPanel, centerX + 340, centerY + 160, 200, 200); // player buttons bottom right
//    scene.image(rivetPanel, centerX - 540, centerY - 150, 125, 230);   // player resources middle left
//    scene.image(rivetPanel, centerX - 540, centerY - 360, 150, 50);  // menu box, top left
//    scene.image(rivetPanel, centerX + 390, centerY - 360, 150, 50);  // end turn box, top right
//
//    scene.textFont(fontLarge);
//    scene.text("Player " + (hud.currentPlayer.getPlayerNum() + 1),
//        centerX - 75, centerY - 325); //print current player
    scene.image(rustedMetal, centerX - 200, centerY - 300, centerX - 150, centerY + 200);
    buttonManager.draw();
  }


}
