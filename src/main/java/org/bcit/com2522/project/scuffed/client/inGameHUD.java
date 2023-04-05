package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.ui.Button;
import org.bcit.com2522.project.scuffed.ui.ButtonManager;
import processing.core.PImage;
import static org.bcit.com2522.project.scuffed.client.Window.UIImages;

/**
 * The inGameStartHUD class is the HUD that is displayed when the game is started.
 * It provides specific implementation for the in-game starting HUD state
 *
 * @author Brendan Doyle
 * @version 1.0
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
    fontLarge = hud.scene.createFont("fonts/Retro Gaming.ttf", 30);
    fontMedium = hud.scene.createFont("fonts/Retro Gaming.ttf", 24);
    fontSmall = hud.scene.createFont("fonts/Retro Gaming.ttf", 15);
    rivetPanel = hud.scene.loadImage("sprites/RivetPanel.png");
    bottomCornerPanel = hud.scene.loadImage("sprites/bottomCornerPanels.png");
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



    buttonManager = new ButtonManager(hud.scene);

    Button hudMenuButton = new Button(
        centerX - 540, centerY - 360, centerX - 390, centerY - 310,
        () -> {hud.setState(new menuHUDState(hud));
    }, "Menu", rivetPanel, rivetPanel, rivetPanel, hud.scene,
        rivetPanel,true, 28 , 20, 5);

    Button hudEndTurnButton = new Button(
        centerX + 390, centerY - 360, centerX + 540, centerY - 310,
        () -> {hud.scene.nextTurn();
    }, "End Turn", rivetPanel, rivetPanel, rivetPanel, hud.scene,
        rivetPanel,true, 28, 5, 10);


    buttonManager.add(hudMenuButton);
    buttonManager.add(hudEndTurnButton);
  }

    /**
     * Draws the in-game starting HUD state rendering player information
     * and the buttons.
     * @param scene
     */
  @Override
  public void draw(Window scene) {
    // UI panels
    if (rivetPanel != null) {
      scene.image(rivetPanel, centerX - 100, centerY - 360, 200, 50);
    } else {
      System.out.println("rivetPanel is null");
    }
    scene.image(rivetPanel,   centerX - 100, centerY - 360, 200, 50);  // Player selected name box, top middle
    scene.image(bottomCornerPanel,   centerX - 540, centerY + 160, 200, 200); //  selected char box, bottom left
    scene.image(bottomCornerPanel,   centerX + 340, centerY + 160, 200, 200); // player buttons bottom right
    scene.image(rivetPanel,   centerX - 540, centerY - 150, 125, 230); // player resources middle left
    scene.image(rivetPanel,   centerX + 340, centerY - 150, 200, 230); // player stats middle right
    scene.image(resourcesIMG, centerX - 520, centerY - 120, 25,  25);  // resources icon
    scene.image(healthIMG,    centerX - 520, centerY - 90,  25,  25);  // health icon
    scene.image(attackIMG,    centerX - 520, centerY - 60,  25,  25);  // attack icon
    scene.image(rangeIMG,     centerX - 520, centerY - 30,  25,  25);  // range icon
    scene.image(moveIMG,      centerX - 520, centerY - 0,   25,  25);  // move icon
    scene.image(coinIMG,      centerX - 520, centerY + 30,  25,  25);  // coin icon
    scene.image(arrowKeysIMG, centerX + 365, centerY - 125, 25,  25);  // arrow keys icon
    scene.image(iconWASD,     centerX + 365, centerY - 100, 25,  25);  // WASD icon
    scene.image(iconB,        centerX + 365, centerY - 75, 25,  25);  // B icon
    scene.image(iconC,        centerX + 365, centerY - 50,  25,  25);  // C icon
    scene.image(iconF,        centerX + 365, centerY - 25,  25,  25);  // F icon
    scene.image(iconM,        centerX + 365, centerY , 25,  25);  // M icon
    scene.image(iconX,        centerX + 365, centerY + 25, 25,  25);  // X icon

    // Displays the current player's name
    scene.textFont(fontLarge);
    scene.text("Player " + (hud.currentPlayer.getPlayerNum() + 1),
        centerX - 75, centerY - 325); //print current player

    // Displays the current player's resources
    scene.textFont(fontMedium);
    scene.text(": " + (hud.currentPlayer.getResources()), centerX - 495, centerY - 100); //print player resources
    scene.text(": " + (selectedEntity != null && selectedEntity instanceof Unit ? ((Unit) selectedEntity)
        .getRemainMove() : 0), centerX - 495, centerY + 20);

    //load selected unit picture, name, and stats in bottom left corner
    //load selected units buttons in bottom right corner
    if(selected) {
      hud.scene.image(selectedHighRes, centerX - 523, centerY + 177, 165, 165);
      scene.textFont(fontMedium);
      scene.text(selectedName, centerX - 490, centerY + 150);
      scene.textFont(fontMedium);
      scene.text(": " + selectedHealth, centerX - 495, centerY - 70);
      scene.text(": " + selectedAttack, centerX - 495, centerY - 40);
      scene.text(": " + selectedRange,  centerX - 495, centerY - 10);
      scene.text(": " + selectedCost,   centerX - 495, centerY + 50);
    }
    scene.textFont(fontSmall);
    scene.text(": Zoom In/Out", centerX + 395, centerY - 105);
    scene.text(": Move Map", centerX + 395, centerY - 80);
    scene.text(": Build", centerX + 395, centerY - 55);
    scene.text(": Collect", centerX + 395, centerY - 30);
    scene.text(": Make Soldier", centerX + 395, centerY - 5);
    scene.text(": Make Worker", centerX + 395, centerY + 20);
    scene.text(": Deselect", centerX + 395, centerY + 45);

    buttonManager.draw();

  }

  /**
   * Unit selected.
   *
   * @param selected the selected
   */
  public void unitSelected(Entity selected, Entity[][] entities){
    this.selected = true;
    this.selectedEntity = selected;
    if (selected instanceof Soldier) {
      Soldier unit = (Soldier) selected;
      selectedHighRes  = soldierSelectedIMG;
      selectedName     = "Soldier";
      selectedHealth   = unit.getHealth();
      selectedAttack   = unit.getDamage();
      selectedRange    = unit.getRange();
      selectedMovement = unit.getRemainMove();
      selectedCost     = unit.getCost();

    } else if (selected instanceof Building) {
      Building unit = (Building) selected;
      selectedHighRes  = buildingSelectedIMG;
      selectedName     = "Building";
      selectedHealth   = unit.getHealth();
      selectedAttack   = 0;
      selectedRange    = 0;
      selectedMovement = 0;
      selectedCost     = unit.getCost();

    } else if (selected instanceof Worker) {
      Unit unit = (Unit) selected;
      selectedHighRes  = workerSelectedIMG;
      selectedName     = "Worker";
      selectedHealth   = unit.getHealth();
      selectedAttack   = 0;
      selectedRange    = 0;
      selectedMovement = unit.getRemainMove();
      selectedCost     = unit.getCost();
    }

    Button buildBuildingButton = new Button(
        centerX + 360, centerY + 260, centerX + 440, centerY + 340,
        () -> {
          if (selectedEntity instanceof Worker) {
            ((Worker) selectedEntity).buildBuilding(entities);
          }
        }
        , "Building", rustedMetalIMG, rustedMetalIMG, rustedMetalIMG, hud.scene,
        rustedMetalIMG,true, 15, -10, 30);

    Button buildSoldierButton = new Button(
        centerX + 360, centerY + 180, centerX + 440, centerY + 260,
        () -> {
          if (selectedEntity instanceof Building) {
            ((Building) selectedEntity).buildSoldier(entities, 1, 1, 1, 1);
          }
        }
        , "Soldier", rustedMetalIMG, rustedMetalIMG, rustedMetalIMG, hud.scene,
        rustedMetalIMG,true, 15, - 10, 30);


    Button buildWorkerButton = new Button(
        centerX + 440, centerY + 180, centerX + 520, centerY + 260,
        () -> {
          if (selectedEntity instanceof Building) {
            ((Building) selectedEntity).buildWorker(entities);
          }
        }
        , "Worker", rustedMetalIMG, rustedMetalIMG, rustedMetalIMG, hud.scene,
        rustedMetalIMG,true, 15, -10, 30);



    buttonManager.add(buildWorkerButton);
    buttonManager.add(buildSoldierButton);
    buttonManager.add(buildBuildingButton);
  }

}


