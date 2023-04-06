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
public class inGameHUD extends HUDState {

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
// Constructor takes a HUD object and calls the super constructor
  public inGameHUD(HUD hud) {
    super(hud);
    selected = false;
  }

  public static void select(Entity selected) {

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

  private void loadFonts(){
    fontLarge = hud.scene.createFont("fonts/Retro Gaming.ttf", 30);
    fontMedium = hud.scene.createFont("fonts/Retro Gaming.ttf", 24);
    fontSmall = hud.scene.createFont("fonts/Retro Gaming.ttf", 15);
  }

  private void loadImages() {
    rivetPanel = hud.scene.loadImage("sprites/RivetPanel.png");
    panel = hud.scene.loadImage("sprites/workPlease.png");
    soldierSelectedIMG = hud.scene.loadImage("sprites/highResSoldier.jpg");
    buildingSelectedIMG = hud.scene.loadImage("sprites/highResFactory.jpg");
    workerSelectedIMG = hud.scene.loadImage("sprites/highResWorker.jpg");
    rustedMetalIMG = hud.scene.loadImage("sprites/rustedMetalIMG.jpg");
    coinIMG = hud.scene.loadImage("sprites/coin.png");
    moveIMG = hud.scene.loadImage("sprites/movement.png");
    healthIMG = hud.scene.loadImage("sprites/Health.png");
    attackIMG = hud.scene.loadImage("sprites/damage.png");
    resourcesIMG = hud.scene.loadImage("sprites/resources.png");
    rangeIMG = hud.scene.loadImage("sprites/range.png");
    iconB = hud.scene.loadImage("sprites/iconB.png");
    iconC = hud.scene.loadImage("sprites/iconC.png");
    iconF = hud.scene.loadImage("sprites/iconF.png");
    iconM = hud.scene.loadImage("sprites/iconM.png");
    iconX = hud.scene.loadImage("sprites/iconX.png");
    iconWASD = hud.scene.loadImage("sprites/WASD.png");
    arrowKeysIMG = hud.scene.loadImage("sprites/iconArrows.png");
    gunButtonIcon = hud.scene.loadImage("sprites/gunButtonIcon.png");
    buildingButtonIcon = hud.scene.loadImage("sprites/buildingButtonIcon.png");
    workerButtonIcon = hud.scene.loadImage("sprites/workerButtonIcon.png");
  }

  private void createButtonManager() {
    buttonManager = new ButtonManager(hud.scene);
  }

  private Button createMenuButton() {
      return new Button(
          centerX - 540, centerY - 360, centerX - 390, centerY - 310,
          () -> {
            hud.setState(new menuHUDState(hud));
          }, "Menu", panel, panel, panel, hud.scene,
          rivetPanel, true, 28, 20, 5);
  }

  private Button createEndTurnButton(){
    return new Button(
        centerX + 390, centerY - 360, centerX + 540, centerY - 310,
        () -> {
          hud.scene.nextTurn();
        }, "End Turn", panel, panel, panel, hud.scene,
        rivetPanel, true, 28, 5, 5);
  }

  /**
   * Draws the in-game starting HUD state rendering player information
   * and the buttons.
   *
   * @param scene
   */
  @Override
  public void draw(Window scene) {
    drawUIPanels(scene);
    drawIcons(scene);
    drawPlayerInfo(scene);
    drawSelectedInfo(scene);
    drawControls(scene);
    buttonManager.draw();
    }

    private void drawUIPanels(Window scene) {
      scene.image(panel, centerX - 100, centerY - 360, 200, 50);  // Player selected name box, top middle
      scene.image(panel, centerX - 540, centerY + 160, 200, 200); //  selected char box, bottom left
      scene.image(panel, centerX + 340, centerY + 160, 200, 200); // player buttons bottom right
      scene.image(panel, centerX - 540, centerY - 165, 125, 280); // player resources middle left
      scene.image(panel, centerX + 340, centerY - 165, 200, 280); // player stats middle right
    }

    private void drawIcons(Window scene) {
      scene.image(arrowKeysIMG, centerX + 365, centerY - 125, 25, 25);  // arrow keys icon
      scene.image(iconWASD, centerX + 365, centerY - 100, 25, 25);  // WASD icon
      scene.image(iconB, centerX + 365, centerY - 75, 25, 25);  // B icon
      scene.image(iconC, centerX + 365, centerY - 50, 25, 25);  // C icon
      scene.image(iconF, centerX + 365, centerY - 25, 25, 25);  // F icon
      scene.image(iconM, centerX + 365, centerY, 25, 25);  // M icon
      scene.image(iconX, centerX + 365, centerY + 25, 25, 25);  // X icon
    }

    private void drawPlayerInfo(Window scene) {
      scene.image(resourcesIMG, centerX - 520, centerY - 120, 25, 25);  // resources icon
      scene.image(healthIMG, centerX - 520, centerY - 90, 25, 25);  // health icon
      scene.image(attackIMG, centerX - 520, centerY - 60, 25, 25);  // attack icon
      scene.image(rangeIMG, centerX - 520, centerY - 30, 25, 25);  // range icon
      scene.image(moveIMG, centerX - 520, centerY - 0, 25, 25);  // move icon
      scene.image(coinIMG, centerX - 520, centerY + 30, 25, 25);  // coin icon

      scene.textFont(fontMedium);
      scene.text("Player " + (hud.currentPlayer.getPlayerNum() + 1),
          centerX - 55, centerY - 330); //print current player

      // Displays the current player's resources
      scene.textFont(fontMedium);
      scene.text(": " + (hud.currentPlayer.getResources()), centerX - 495, centerY - 100); //print player resources
      scene.text(": " + (selectedEntity != null && selectedEntity instanceof Unit ? ((Unit) selectedEntity)
          .getRemainMove() : 0), centerX - 495, centerY + 20);
    }

  private void drawSelectedInfo(Window scene) {
    if (selected) {
      hud.scene.image(selectedHighRes, centerX - 532, centerY + 180, 187, 180);
      scene.textFont(fontSmall);
      scene.text(selectedName, centerX - 470, centerY + 175);
      scene.textFont(fontMedium);
      scene.text(": " + selectedHealth, centerX - 495, centerY - 70);
      scene.text(": " + selectedAttack, centerX - 495, centerY - 40);
      scene.text(": " + selectedRange, centerX - 495, centerY - 10);
      scene.text(": " + selectedCost, centerX - 495, centerY + 50);
    }
  }

  private void drawControls(Window scene) {
    scene.textFont(fontSmall);
    scene.text(": Zoom In/Out", centerX + 395, centerY - 105);
    scene.text(": Move Map", centerX + 395, centerY - 80);
    scene.text(": Build", centerX + 395, centerY - 55);
    scene.text(": Collect", centerX + 395, centerY - 30);
    scene.text(": Make Soldier", centerX + 395, centerY - 5);
    scene.text(": Make Worker", centerX + 395, centerY + 20);
    scene.text(": Deselect", centerX + 395, centerY + 45);
  }

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
    selectedHighRes = soldierSelectedIMG;
    selectedName = "Soldier";
    selectedHealth = unit.getHealth();
    selectedAttack = unit.getDamage();
    selectedRange = unit.getRange();
    selectedMovement = unit.getRemainMove();
    selectedCost = unit.getCost();
  }

  private void setBuildingInfo(Building unit) {
    selectedHighRes = buildingSelectedIMG;
    selectedName = "Building";
    selectedHealth = unit.getHealth();
    selectedAttack = 0;
    selectedRange = 0;
    selectedMovement = 0;
    selectedCost = unit.getCost();
  }

  private void setWorkerInfo(Worker unit) {
    selectedHighRes = workerSelectedIMG;
    selectedName = "Worker";
    selectedHealth = unit.getHealth();
    selectedAttack = 0;
    selectedRange = 0;
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
        centerX + 350, centerY + 250, centerX + 440, centerY + 320,
        () -> {
          if (selectedEntity instanceof Worker) {
            ((Worker) selectedEntity).buildBuilding(entities);
          }
        }
        , " ", buildingButtonIcon, buildingButtonIcon, buildingButtonIcon, hud.scene);
  }

  private Button createBuildSoldierButton(Entity[][] entities) {
    return new Button(
        centerX + 350, centerY + 180, centerX + 440, centerY + 250,
        () -> {
          if (selectedEntity instanceof Building) {
            ((Building) selectedEntity).buildSoldier(entities, 1, 1, 1, 1);
          }
        }
        , " ", gunButtonIcon, gunButtonIcon, gunButtonIcon, hud.scene);
  }

  private Button createBuildWorkerButton(Entity[][] entities) {
    return new Button(
        centerX + 440, centerY + 177, centerX + 530, centerY + 249,
        () -> {
          if (selectedEntity instanceof Building) {
            ((Building) selectedEntity).buildWorker(entities);
          }
        }
        , " ", workerButtonIcon, workerButtonIcon, workerButtonIcon, hud.scene);
  }

}



