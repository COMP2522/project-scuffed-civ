package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.ui.Button;
import org.bcit.com2522.project.scuffed.ui.ButtonManager;
import org.bcit.com2522.project.scuffed.ui.MainMenuMenuState;
import org.bcit.com2522.project.scuffed.ui.Menu;

/**
 * Provides a specific implementation for the menu in-game HUD state.
 * @author Brendan Doyle
 * @version 1.0
 */
public class menuHUDState extends HUDState {

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

    buttonManager = new ButtonManager(hud.scene);

    Button hudMenuButton = new Button(centerX - 540, centerY - 360, centerX - 390, centerY - 310, () -> {
      hud.setState(new menuHUDState(hud));
    }, "Menu", rivetPanel, rivetPanel, rivetPanel, hud.scene);

    Button hudEndTurnButton = new Button(centerX + 390, centerY - 360, centerX + 540, centerY - 310, () -> {
      hud.scene.nextTurn();
    }, "End Turn", rivetPanel, rivetPanel, rivetPanel, hud.scene);

    Button returnToMainMenuButton = new Button(centerX - 100, centerY - 100, centerX + 100, centerY + 100, () -> {
      onMenuClicked();
    }, "Return to Main Menu", rivetPanel, rivetPanel, rivetPanel, hud.scene);

    buttonManager.add(hudEndTurnButton);
    buttonManager.add(returnToMainMenuButton);
    buttonManager.add(hudMenuButton);
  }

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

    // draw the players information
    scene.image(rivetPanel, centerX - 540, centerY - 360, 150, 50);  // menu box, top left
    scene.image(rivetPanel, centerX + 390, centerY - 360, 150, 50);  // end turn box, top right
    scene.image(rivetPanel, centerX - 100, centerY - 360, 200, 50);  // Player selected name box, top middle
    scene.image(rivetPanel, centerX - 540, centerY + 160, 200, 200);  //  selected char box, bottom left
    scene.image(rivetPanel, centerX + 340, centerY + 160, 200, 200); // player buttons bottom right
    scene.image(rivetPanel, centerX - 540, centerY - 150, 150, 200);   // player resources middle left

    scene.textFont(fontLarge);
    scene.text("Player " + (hud.currentPlayer.getplayerNum() + 1), centerX - 50, centerY - 335); //print current player

    scene.textFont(fontSmall);
    scene.text("Player" + (hud.currentPlayer.getplayerNum() + 1) + "\n" +"Resources " + (hud.currentPlayer.getResources()), centerX - 530, centerY - 120); //print player resources

    buttonManager.draw();
  }


}
