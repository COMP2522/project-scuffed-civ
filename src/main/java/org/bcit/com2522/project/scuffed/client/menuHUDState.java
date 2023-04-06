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
    panel = hud.scene.loadImage("sprites/workPlease.png");

    buttonManager = new ButtonManager(hud.scene);

    Button returnToMainMenuButton = new Button(centerX - 150, centerY - 260, centerX + 150, centerY - 210, () -> {
      onMenuClicked();
    }, "Main Menu", panel, panel, panel, hud.scene,
        panel, true, 28, 65, 8);

    Button save = new Button(centerX - 150, centerY - 160, centerX + 150, centerY - 110, () -> {
      hud.scene.saveGame();
    }, "Save", panel, panel, panel, hud.scene,
        panel, true, 28, 105, 8);

    Button settings = new Button(centerX - 150, centerY - 60, centerX + 150, centerY - 10, () -> {
      onMenuClicked();
    }, "Settings", panel, panel, panel, hud.scene,
        panel, true, 28, 85, 8);

    Button exitButton = new Button(centerX - 150, centerY + 40, centerX + 150, centerY + 90,
        () -> {
          hud.scene.exit();
        }, "Exit", panel, panel, panel, hud.scene
        , rivetPanel, true, 28, 110, 8);

    Button resumeGame = new Button(centerX - 150, centerY + 140, centerX + 150, centerY + 190,
        () -> {
          hud.setState(new inGameHUD(hud));
        }, "Resume Game", panel, panel, panel, hud.scene,
        panel, true, 28, 40, 8);

    buttonManager.add(resumeGame);
    buttonManager.add(exitButton);
    buttonManager.add(settings);
    buttonManager.add(save);
    buttonManager.add(returnToMainMenuButton);

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
   *
   * @param scene
   */
  @Override
  public void draw(Window scene) {
    scene.image(panel, centerX - 200, centerY - 300, centerX - 150, centerY + 200);
    buttonManager.draw();
  }
}
