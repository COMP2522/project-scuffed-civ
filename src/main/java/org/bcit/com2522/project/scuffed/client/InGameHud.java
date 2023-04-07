package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.ui.Button;
import org.bcit.com2522.project.scuffed.ui.ButtonManager;
import processing.core.PImage;

/**
 * The inGameHUD class is the HUD that is displayed when playing the game.
 * It provides specific implementation for the in-game HUD state
 *
 * @author Brendan Doyle
 * @version 2.0
 */
public class InGameHud extends HudState {
  private static final int FONTSIZELARGE = 30;
  private static final int FONTSIZEMEDIUM = 24;
  private static final int FONTSIZESMALL = 15;
  private static final int MENU_BUTTON_Y = 360;
  private static final int MENU_BUTTON_X2 = 390;
  private static final int MENU_BUTTON_Y2 = 310;
  private static final int BUTTON_FONT_SIZE = 28;
  private static final int BUTTON_FONT_Y = 5;
  private static final int BUTTON_FONT_X = 20;
  private static final int END_BUTTON_FONT_X = 10;
  private static final int MENU_BUTTON_X = 540;
  private static final int PLAYER_PANEL_X1 = 100;
  private static final int PLAYER_PANEL_Y1 = 360;
  private static final int PLAYER_PANEL_X2 = 200;
  private static final int PLAYER_PANEL_Y2 = 50;
  private static final int SELECTED_PANEL_X1 = 540;
  private static final int SELECTED_PANEL_Y1 = 160;
  private static final int SELECTED_BUTTONS_PANEL_X1 = 340;
  private static final int SELECTED_BUTTONS_PANEL_Y1 = 160;
  private static final int BOTTOM_PANEL_WIDTH = 200;
  private static final int BOTTOM_PANEL_HEIGHT = 200;
  private static final int RESOURCE_PANEL_X1 = 540;
  private static final int RESOURCE_PANEL_Y1 = 165;
  private static final int RESOURCE_PANEL_WIDTH = 125;
  private static final int RESOURCE_PANEL_HEIGHT = 280;
  private static final int KEYBINDS_PANEL_X1 = 340;
  private static final int KEYBINDS_PANEL_Y1 = 165;
  private static final int KEYBINDS_PANEL_WIDTH = 200;
  private static final int KEYBINDS_PANEL_HEIGHT = 280;
  private static final int STAT_ICON_X1 = 365;
  private static final int COLLECTED_ICON_Y1 = 120;
  private static final int HEALTH_ICON_Y1 = 90;
  private static final int DAMAGE_ICON_Y1 = 60;
  private static final int RANGE_ICON_Y1 = 30;
  private static final int MOVEMENT_ICON_Y1 = 0;
  private static final int COST_ICON_Y1 = 30;
  private static final int ZOOM_ICON_Y1 = 125;
  private static final int WASD_ICON_Y1 = 100;
  private static final int B_ICON_Y1 = 75;
  private static final int C_ICON_Y1 = 50;
  private static final int F_ICON_Y1 = 25;
  private static final int M_ICON_Y1 = 0;
  private static final int X_ICON_Y1 = 25;
  private static final int ICON_WIDTHHEIGHT = 25;
  private static final int INFO_ICON_X1 = 520;
  private static final int PLAYER_NUM_X1 = 55;
  private static final int PLAYER_NUM_Y1 = 330;
  private static final int PLAYER_RESOURCES_X1 = 495;
  private static final int PLAYER_RESOURCES_Y1 = 100;
  private static final int PLAYER_MOVE_Y1 = 20;
  private static final int SELECTED_IMG_WIDTH = 187;
  private static final int SELECTED_IMG_HEIGHT = 180;
  private static final int SELECTED_IMG_X1 = 532;
  private static final int SELECTED_IMG_Y1 = 180;
  private static final int SELECTED_NAME_X = 470;
  private static final int SELECTED_NAME_Y = 175;
  private static final int SELECTED_TEXT_X = 495;
  private static final int HEALTH_TEXT_Y = 70;
  private static final int DAMAGE_TEXT_Y = 40;
  private static final int RANGE_TEXT_Y = 10;
  private static final int COST_TEXT_Y = 50;
  private static final int CONTROL_TEXT_X = 395;
  private static final int ARROW_TEXT_Y = 105;
  private static final int WASD_TEXT_Y = 80;
  private static final int B_TEXT_Y = 55;
  private static final int C_TEXT_Y = 30;
  private static final int F_TEXT_Y = 5;
  private static final int M_TEXT_Y = 20;
  private static final int X_TEXT_Y = 45;
  private static final int BUILD_BUILDING_BUTTON_X1 = 350;
  private static final int BUILD_BUILDING_BUTTON_Y1 = 250;
  private static final int BUILD_BUILDING_BUTTON_X2 = 440;
  private static final int BUILD_BUILDING_BUTTON_Y2 = 320;
  private static final int BUILD_SOLDIER_BUTTON_X1 = 350;
  private static final int BUILD_SOLDIER_BUTTON_Y1 = 180;
  private static final int BUILD_SOLDIER_BUTTON_X2 = 440;
  private static final int BUILD_SOLDIER_BUTTON_Y2 = 250;
  private static final int BUILD_WORKER_BUTTON_X1 = 440;
  private static final int BUILD_WORKER_BUTTON_Y1 = 177;
  private static final int BUILD_WORKER_BUTTON_X2 = 530;
  private static final int BUILD_WORKER_BUTTON_Y2 = 249;
  private static final int DEFAULT_STAT = 00;

  /**
   * The Selected.
   */
  public Boolean selected;
  /**
   * The Selected high-res.
   */
  public PImage selectedHighRes;

  /**
   * The Selected name.
   */
  public String selectedName;
  /**
   * The Selected health.
   */
  public int selectedHealth;
  /**
   * The Selected attack.
   */
  public int selectedAttack;
  /**
   * The Selected range.
   */
  public int selectedRange;
  /**
   * The Selected movement.
   */
  public int selectedMovement;
  /**
   * The Selected cost.
   */
  public int selectedCost;
  /**
   * The Selected entity.
   */
  public Entity selectedEntity;


  /**
   * Instantiates a new In game hud.
   *
   * @param hud the hud
   */
  public InGameHud(Hud hud) {
    super(hud);
    selected = false;
  }

  /**
   * Sets up the buttons and UI elements for the inGameStartHUD state.
   * Creates the buttons and adds them to the button manager.
   */
  @Override
  public void setup() {
    loadFonts();
    loadImages();
    createButtonManager();

    Button hudMenuButton = createMenuButton();
    Button hudEndTurnButton = createEndTurnButton();

    buttonManager.add(hudMenuButton);
    buttonManager.add(hudEndTurnButton);
  }

  private void loadFonts() {
    fontLarge = hud.scene.createFont("fonts/Retro Gaming.ttf", FONTSIZELARGE);
    fontMedium = hud.scene.createFont("fonts/Retro Gaming.ttf", FONTSIZEMEDIUM);
    fontSmall = hud.scene.createFont("fonts/Retro Gaming.ttf", FONTSIZESMALL);
  }

  private void loadImages() {
    rivetPanel = hud.scene.loadImage("sprites/RivetPanel.png");
    panel = hud.scene.loadImage("sprites/workPlease.png");
    soldierSelectedImg = hud.scene.loadImage("sprites/highResSoldier.jpg");
    buildingSelectedImg = hud.scene.loadImage("sprites/highResFactory.jpg");
    workerSelectedImg = hud.scene.loadImage("sprites/highResWorker.jpg");
    rustedMetalImg = hud.scene.loadImage("sprites/rustedMetalIMG.jpg");
    coinImg = hud.scene.loadImage("sprites/coin.png");
    moveImg = hud.scene.loadImage("sprites/movement.png");
    healthImg = hud.scene.loadImage("sprites/Health.png");
    attackImg = hud.scene.loadImage("sprites/damage.png");
    resourcesImg = hud.scene.loadImage("sprites/resources.png");
    rangeImg = hud.scene.loadImage("sprites/range.png");
    iconB = hud.scene.loadImage("sprites/iconB.png");
    iconC = hud.scene.loadImage("sprites/iconC.png");
    iconF = hud.scene.loadImage("sprites/iconF.png");
    iconM = hud.scene.loadImage("sprites/iconM.png");
    iconX = hud.scene.loadImage("sprites/iconX.png");
    iconWasd = hud.scene.loadImage("sprites/WASD.png");
    arrowKeysImg = hud.scene.loadImage("sprites/iconArrows.png");
    gunButtonIcon = hud.scene.loadImage("sprites/gunButtonIcon.png");
    buildingButtonIcon = hud.scene.loadImage("sprites/buildingButtonIcon.png");
    workerButtonIcon = hud.scene.loadImage("sprites/workerButtonIcon.png");
  }

  private void createButtonManager() {
    buttonManager = new ButtonManager(hud.scene);
  }

  private Button createMenuButton() {
    return new Button(
        centerX - MENU_BUTTON_X, centerY - MENU_BUTTON_Y,
        centerX - MENU_BUTTON_X2, centerY - MENU_BUTTON_Y2,
        () -> {
          hud.setState(new Menuhudstate(hud));
        }, "Menu", panel, panel, panel, hud.scene,
        rivetPanel, true, BUTTON_FONT_SIZE, BUTTON_FONT_X, BUTTON_FONT_Y);
  }

  private Button createEndTurnButton() {
    return new Button(
        centerX + MENU_BUTTON_X2, centerY - MENU_BUTTON_Y,
        centerX + MENU_BUTTON_X, centerY - MENU_BUTTON_Y2,
        () -> {
          hud.scene.nextTurn();
        }, "End Turn", panel, panel, panel, hud.scene,
        rivetPanel, true, BUTTON_FONT_SIZE, END_BUTTON_FONT_X, BUTTON_FONT_Y);
  }

  /**
   * Draws the in-game starting HUD state rendering player information
   * and the buttons.
   *
   * @param scene the scene
   */
  @Override
  public void draw(Window scene) {
    drawUiPanels(scene);
    drawIcons(scene);
    drawPlayerInfo(scene);
    drawSelectedInfo(scene);
    drawControls(scene);
    drawPlayerNum(scene);
    drawPlayerResources(scene);
    buttonManager.draw();
  }

  private void drawUiPanels(Window scene) {
    scene.image(panel, centerX - PLAYER_PANEL_X1, centerY - PLAYER_PANEL_Y1,
        PLAYER_PANEL_X2, PLAYER_PANEL_Y2);  // Player selected name box, top middle
    scene.image(panel, centerX - SELECTED_PANEL_X1, centerY + SELECTED_PANEL_Y1,
        BOTTOM_PANEL_WIDTH, BOTTOM_PANEL_HEIGHT); //  selected char box, bottom left
    scene.image(panel, centerX + SELECTED_BUTTONS_PANEL_X1, // player buttons bottom right
        centerY + SELECTED_BUTTONS_PANEL_Y1, BOTTOM_PANEL_WIDTH, BOTTOM_PANEL_HEIGHT);
    scene.image(panel, centerX - RESOURCE_PANEL_X1, centerY - RESOURCE_PANEL_Y1,
        RESOURCE_PANEL_WIDTH, RESOURCE_PANEL_HEIGHT); // player resources middle left
    scene.image(panel, centerX + KEYBINDS_PANEL_X1, centerY - KEYBINDS_PANEL_Y1,
        KEYBINDS_PANEL_WIDTH, KEYBINDS_PANEL_HEIGHT); // player stats middle right
  }

  private void drawIcons(Window scene) {
    scene.image(arrowKeysImg, centerX + STAT_ICON_X1, centerY - ZOOM_ICON_Y1,
        ICON_WIDTHHEIGHT, ICON_WIDTHHEIGHT);  // arrow keys icon
    scene.image(iconWasd, centerX + STAT_ICON_X1, centerY - WASD_ICON_Y1,
        ICON_WIDTHHEIGHT, ICON_WIDTHHEIGHT);  // WASD icon
    scene.image(iconB, centerX + STAT_ICON_X1, centerY - B_ICON_Y1,
        ICON_WIDTHHEIGHT, ICON_WIDTHHEIGHT);  // B icon
    scene.image(iconC, centerX + STAT_ICON_X1, centerY - C_ICON_Y1,
        ICON_WIDTHHEIGHT, ICON_WIDTHHEIGHT);  // C icon
    scene.image(iconF, centerX + STAT_ICON_X1, centerY - F_ICON_Y1,
        ICON_WIDTHHEIGHT, ICON_WIDTHHEIGHT);  // F icon
    scene.image(iconM, centerX + STAT_ICON_X1, centerY - M_ICON_Y1,
        ICON_WIDTHHEIGHT, ICON_WIDTHHEIGHT);  // M icon
    scene.image(iconX, centerX + STAT_ICON_X1, centerY + X_ICON_Y1,
        ICON_WIDTHHEIGHT, ICON_WIDTHHEIGHT);  // X icon
  }

  private void drawPlayerNum(Window scene) {
    scene.textFont(fontMedium);
    scene.text("Player " + (hud.currentPlayer.getPlayerNum() + 1),
        centerX - PLAYER_NUM_X1, centerY - PLAYER_NUM_Y1);
  }

  private void drawPlayerResources(Window scene) {
    // Displays the current player's resources
    scene.textFont(fontMedium);
    scene.text(": " + (hud.currentPlayer.getResources()), centerX - PLAYER_RESOURCES_X1,
        centerY - PLAYER_RESOURCES_Y1); //print player resources
    scene.text(": "
        + (selectedEntity != null && selectedEntity instanceof Unit ? ((Unit) selectedEntity)
        .getRemainMove() : 0), centerX - PLAYER_RESOURCES_X1, centerY + PLAYER_MOVE_Y1);
  }

  private void drawPlayerInfo(Window scene) {
    scene.image(resourcesImg, centerX - INFO_ICON_X1, centerY - COLLECTED_ICON_Y1,
        ICON_WIDTHHEIGHT, ICON_WIDTHHEIGHT);  // resources icon
    scene.image(healthImg, centerX - INFO_ICON_X1, centerY - HEALTH_ICON_Y1,
        ICON_WIDTHHEIGHT, ICON_WIDTHHEIGHT);  // health icon
    scene.image(attackImg, centerX - INFO_ICON_X1, centerY - DAMAGE_ICON_Y1,
        ICON_WIDTHHEIGHT, ICON_WIDTHHEIGHT);  // attack icon
    scene.image(rangeImg, centerX - INFO_ICON_X1, centerY - RANGE_ICON_Y1,
        ICON_WIDTHHEIGHT, ICON_WIDTHHEIGHT);  // range icon
    scene.image(moveImg, centerX - INFO_ICON_X1, centerY + MOVEMENT_ICON_Y1,
        ICON_WIDTHHEIGHT, ICON_WIDTHHEIGHT);  // move icon
    scene.image(coinImg, centerX - INFO_ICON_X1, centerY + COST_ICON_Y1,
        ICON_WIDTHHEIGHT, ICON_WIDTHHEIGHT);  // coin icon


  }

  private void drawSelectedInfo(Window scene) {
    if (selected) {
      hud.scene.image(selectedHighRes, centerX - SELECTED_IMG_X1,
          centerY + SELECTED_IMG_Y1, SELECTED_IMG_WIDTH, SELECTED_IMG_HEIGHT);
      scene.textFont(fontSmall);
      scene.text(selectedName, centerX - SELECTED_NAME_X, centerY + SELECTED_NAME_Y);
      scene.textFont(fontMedium);
      scene.text(": " + selectedHealth, centerX - SELECTED_TEXT_X, centerY - HEALTH_TEXT_Y);
      scene.text(": " + selectedAttack, centerX - SELECTED_TEXT_X, centerY - DAMAGE_TEXT_Y);
      scene.text(": " + selectedRange, centerX - SELECTED_TEXT_X, centerY - RANGE_TEXT_Y);
      scene.text(": " + selectedCost, centerX - SELECTED_TEXT_X, centerY + COST_TEXT_Y);
    } else {
      scene.textFont(fontMedium);
      scene.text(": 0" + selectedHealth, centerX - SELECTED_TEXT_X, centerY - HEALTH_TEXT_Y);
      scene.text(": 0" + selectedAttack, centerX - SELECTED_TEXT_X, centerY - DAMAGE_TEXT_Y);
      scene.text(": 0" + selectedRange, centerX - SELECTED_TEXT_X, centerY - RANGE_TEXT_Y);
      scene.text(": 0" + selectedCost, centerX - SELECTED_TEXT_X, centerY + COST_TEXT_Y);
    }
  }

  private void drawControls(Window scene) {
    scene.textFont(fontSmall);
    scene.text(": Zoom In/Out", centerX + CONTROL_TEXT_X, centerY - ARROW_TEXT_Y);
    scene.text(": Move Map", centerX + CONTROL_TEXT_X, centerY - WASD_TEXT_Y);
    scene.text(": Build", centerX + CONTROL_TEXT_X, centerY - B_TEXT_Y);
    scene.text(": Collect", centerX + CONTROL_TEXT_X, centerY - C_TEXT_Y);
    scene.text(": Make Soldier", centerX + CONTROL_TEXT_X, centerY - F_TEXT_Y);
    scene.text(": Make Worker", centerX + CONTROL_TEXT_X, centerY + M_TEXT_Y);
    scene.text(": Deselect", centerX + CONTROL_TEXT_X, centerY + X_TEXT_Y);
  }

  /**
   * Unit selected.
   *
   * @param selected the selected
   * @param entities the entities
   */
  public void unitSelected(Entity selected, Entity[][] entities) {
    setSelectedEntityInfo(selected);
    setUnitSpecificButtons(entities);
  }

  private void setSelectedEntityInfo(Entity selected) {
    this.selected = true;
    this.selectedEntity = selected;
    if (selected instanceof Soldier) {
      setSoldierInfo((Soldier) selected);
    } else if (selected instanceof Building) {
      setBuildingInfo((Building) selected);
    } else if (selected instanceof Worker) {
      setWorkerInfo((Worker) selected);
    }
  }

  private void setSoldierInfo(Soldier unit) {
    selectedHighRes = soldierSelectedImg;
    selectedName = "Soldier";
    selectedHealth = unit.getHealth();
    selectedAttack = unit.getDamage();
    selectedRange = unit.getRange();
    selectedMovement = unit.getRemainMove();
    selectedCost = unit.getCost();
  }

  private void setBuildingInfo(Building unit) {
    selectedHighRes = buildingSelectedImg;
    selectedName = "Building";
    selectedHealth = unit.getHealth();
    selectedAttack = DEFAULT_STAT;
    selectedRange = DEFAULT_STAT;
    selectedMovement = DEFAULT_STAT;
    selectedCost = unit.getCost();
  }

  private void setWorkerInfo(Worker unit) {
    selectedHighRes = workerSelectedImg;
    selectedName = "Worker";
    selectedHealth = unit.getHealth();
    selectedAttack = DEFAULT_STAT;
    selectedRange = DEFAULT_STAT;
    selectedMovement = unit.getRemainMove();
    selectedCost = unit.getCost();
  }

  private void setUnitSpecificButtons(Entity[][] entities) {
    Button buildBuildingButton = createBuildBuildingButton(entities);
    Button buildSoldierButton = createBuildSoldierButton(entities);
    Button buildWorkerButton = createBuildWorkerButton(entities);

    buttonManager.add(buildWorkerButton);
    buttonManager.add(buildSoldierButton);
    buttonManager.add(buildBuildingButton);
  }

  private Button createBuildBuildingButton(Entity[][] entities) {
    return new Button(
        centerX + BUILD_BUILDING_BUTTON_X1, centerY + BUILD_BUILDING_BUTTON_Y1,
        centerX + BUILD_BUILDING_BUTTON_X2, centerY + BUILD_BUILDING_BUTTON_Y2,
        () -> {
          if (selectedEntity instanceof Worker) {
            ((Worker) selectedEntity).buildBuilding(entities);
          }
        },
        " ", buildingButtonIcon, buildingButtonIcon, buildingButtonIcon, hud.scene);
  }

  private Button createBuildSoldierButton(Entity[][] entities) {
    return new Button(
        centerX + BUILD_SOLDIER_BUTTON_X1, centerY + BUILD_SOLDIER_BUTTON_Y1,
        centerX + BUILD_SOLDIER_BUTTON_X2, centerY + BUILD_SOLDIER_BUTTON_Y2,
        () -> {
          if (selectedEntity instanceof Building) {
            ((Building) selectedEntity).buildSoldier(entities, 1, 1, 1, 1);
          }
        },
        " ", gunButtonIcon, gunButtonIcon, gunButtonIcon, hud.scene);
  }

  private Button createBuildWorkerButton(Entity[][] entities) {
    return new Button(
        centerX + BUILD_WORKER_BUTTON_X1, centerY + BUILD_WORKER_BUTTON_Y1,
        centerX + BUILD_WORKER_BUTTON_X2, centerY + BUILD_WORKER_BUTTON_Y2,
        () -> {
          if (selectedEntity instanceof Building) {
            ((Building) selectedEntity).buildWorker(entities);
          }
        },
        " ", workerButtonIcon, workerButtonIcon, workerButtonIcon, hud.scene);
  }

}



