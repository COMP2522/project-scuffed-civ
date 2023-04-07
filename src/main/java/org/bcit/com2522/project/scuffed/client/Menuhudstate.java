package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.ui.Button;
import org.bcit.com2522.project.scuffed.ui.ButtonManager;


/**
 * Provides a specific implementation for the menu in-game HUD state.
 *
 * @author Brendan Doyle
 * @version 1.0
 */
public class Menuhudstate extends HudState {
  private static final int FONTSIZELARGE = 30;
  private static final int FONTSIZEMEDIUM = 24;
  private static final int FONTSIZESMALL = 15;
  private static final int BUTTON_FONT_SIZE = 28;
  private static final int BUTTON_TEXT_Y = 8;
  private static final int MENU_BUTTON_X = 150;
  private static final int MAIN_MENU_BUTTON_Y = 260;
  private static final int MAIN_MENU_BUTTON_Y2 = 210;
  private static final int MAIN_MENU_TEXT_X = 65;
  private static final int SAVE_BUTTON_Y1 = 160;
  private static final int SAVE_BUTTON_Y2 = 110;
  private static final int SAVE_TEXT_X = 105;
  private static final int SETTING_BUTTON_Y1 = 60;
  private static final int SETTING_BUTTON_Y2 = 10;
  private static final int SETTING_TEXT_X = 85;
  private static final int EXIT_BUTTON_Y1 = 40;
  private static final int EXIT_BUTTON_Y2 = 90;
  private static final int EXIT_TEXT_X = 110;
  private static final int RESUME_BUTTON_Y1 = 140;
  private static final int RESUME_BUTTON_Y2 = 190;
  private static final int RESUME_TEXT_X = 40;
  private static final int MENU_BACKGROUND_X = 200;
  private static final int MENU_BACKGROUND_Y = 300;
  private static final int MENU_BACKGROUND_X2 = 150;
  private static final int MENU_BACKGROUND_Y2 = 200;

  /**
   * Instantiates a new Menu hud state.
   *
   * @param hud the hud
   */
  public Menuhudstate(Hud hud) {
    super(hud);
  }

  /**
   * Sets up the buttons and UI elements for the menuHUDState.
   * Creates the buttons and adds them to the button manager.
   */
  @Override
  public void setup() {
    setupFonts();
    setupImages();
    setupButtons();
  }

  private void setupFonts() {
    fontLarge = hud.scene.createFont("fonts/Retro Gaming.ttf", FONTSIZELARGE);
    fontMedium = hud.scene.createFont("fonts/Retro Gaming.ttf", FONTSIZEMEDIUM);
    fontSmall = hud.scene.createFont("fonts/Retro Gaming.ttf", FONTSIZESMALL);
  }

  private void setupImages() {
    rivetPanel = hud.scene.loadImage("sprites/RivetPanel.png");
    rustedMetal = hud.scene.loadImage("sprites/rustedMetalIMG.jpg");
    panel = hud.scene.loadImage("sprites/workPlease.png");
  }

  private void setupButtons() {
    buttonManager = new ButtonManager(hud.scene);

    Button returnToMainMenuButton = createReturnToMainMenuButton();
    Button saveButton = createSaveButton();
    Button settingsButton = createSettingsButton();
    Button exitButton = createExitButton();
    Button resumeGameButton = createResumeGameButton();

    buttonManager.add(resumeGameButton);
    buttonManager.add(exitButton);
    buttonManager.add(settingsButton);
    buttonManager.add(saveButton);
    buttonManager.add(returnToMainMenuButton);
  }

  private Button createReturnToMainMenuButton() {
    return new Button(centerX - MENU_BUTTON_X, centerY - MAIN_MENU_BUTTON_Y,
        centerX + MENU_BUTTON_X, centerY - MAIN_MENU_BUTTON_Y2,
        () -> {
          onMenuClicked();
        }, "Main Menu", panel, panel, panel, hud.scene, panel,
        true, BUTTON_FONT_SIZE, MAIN_MENU_TEXT_X, BUTTON_TEXT_Y);
  }

  private Button createSaveButton() {
    return new Button(centerX - MENU_BUTTON_X, centerY - SAVE_BUTTON_Y1,
        centerX + MENU_BUTTON_X, centerY - SAVE_BUTTON_Y2, () -> {
          hud.scene.saveGame();
    }, "Save", panel, panel, panel, hud.scene, panel,
        true, BUTTON_FONT_SIZE, SAVE_TEXT_X, BUTTON_TEXT_Y);
  }

  private Button createSettingsButton() {
    return new Button(centerX - MENU_BUTTON_X, centerY - SETTING_BUTTON_Y1,
        centerX + MENU_BUTTON_X, centerY - SETTING_BUTTON_Y2, () -> {
      onMenuClicked();
    }, "Settings", panel, panel, panel, hud.scene, panel,
        true, BUTTON_FONT_SIZE, SETTING_TEXT_X, BUTTON_TEXT_Y);
  }

  private Button createExitButton() {
    return new Button(centerX - MENU_BUTTON_X, centerY + EXIT_BUTTON_Y1,
        centerX + MENU_BUTTON_X, centerY + EXIT_BUTTON_Y2, () -> {
      hud.scene.exit();
    }, "Exit", panel, panel, panel, hud.scene, rivetPanel,
        true, BUTTON_FONT_SIZE, EXIT_TEXT_X, BUTTON_TEXT_Y);
  }

  private Button createResumeGameButton() {
    return new Button(centerX - MENU_BUTTON_X, centerY + RESUME_BUTTON_Y1,
        centerX + MENU_BUTTON_X, centerY + RESUME_BUTTON_Y2, () -> {
      hud.setState(new InGameHud(hud));
    }, "Resume Game", panel, panel, panel, hud.scene, panel,
        true, BUTTON_FONT_SIZE, RESUME_TEXT_X, BUTTON_TEXT_Y);
  }

  private void gameMenuBackground(Window scene) {
    scene.image(panel, centerX - MENU_BACKGROUND_X, centerY - MENU_BACKGROUND_Y,
        centerX - MENU_BACKGROUND_X2, centerY + MENU_BACKGROUND_Y2);
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
   * @param scene of the window
   */
  @Override
  public void draw(Window scene) {
    gameMenuBackground(scene);
    buttonManager.draw();
  }
}
