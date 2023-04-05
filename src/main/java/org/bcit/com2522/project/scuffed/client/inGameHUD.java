package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.ui.Button;
import org.bcit.com2522.project.scuffed.ui.ButtonManager;
import processing.core.PImage;

/**
 * The inGameStartHUD class is the HUD that is displayed when the game is started.
 * It provides specific implementation for the in-game starting HUD state
 *
 * @author Brendan Doyle
 * @version 1.0
 */
public class inGameHUD extends HUDState {

  public Boolean selected;
  public PImage selectedHighRes;
  public String selectedName;
  public int selectedHealth;
  public int selectedAttack;
  public int selectedRange;
  public int selectedMovement;
  public int selectedCost;
  public Entity selectedEntity;


  // Constructor takes a HUD object and calls the super constructor
  public inGameHUD(HUD hud) {
    super(hud);
    selected = false;
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
        rivetPanel,true, 20, 5, 10);
//
//    Button buildSoldierButton = new Button(
//        centerX + 360, centerY + 180, centerX + 440, centerY + 260,
//        () -> this.buildSoldier()
//        , "Soldier", rustedMetalIMG, rustedMetalIMG, rustedMetalIMG, hud.scene,
//        rustedMetalIMG,true, 15, - 10, 30);
//
//    Button buildWorkerButton = new Button(
//        centerX + 440, centerY + 180, centerX + 520, centerY + 260,
//        () -> this.buildWorker()
//        , "Worker", rustedMetalIMG, rustedMetalIMG, rustedMetalIMG, hud.scene,
//        rustedMetalIMG,true, 15, -10, 30);
//
//    Button buildBuildingButton = new Button(
//        centerX + 360, centerY + 260, centerX + 440, centerY + 340,
//        () -> selectedEntities.buildBuilding()
//        , "Building", rustedMetalIMG, rustedMetalIMG, rustedMetalIMG, hud.scene,
//        rustedMetalIMG,true, 15, -10, 30);
//
//    buttonManager.add(buildBuildingButton);
//    buttonManager.add(buildSoldierButton);
//    buttonManager.add(buildWorkerButton);

    buttonManager.add(hudMenuButton);
    buttonManager.add(hudEndTurnButton);
  }

//  public void buildSoldier() {}
//  public void buildWorker() {}
//  public void buildBuilding() {}

    /**
     * Draws the in-game starting HUD state rendering player information
     * and the buttons.
     * @param scene
     */
  @Override
  public void draw(Window scene) {

    // UI panels
    scene.image(rivetPanel, centerX - 100, centerY - 360, 200, 50);  // Player selected name box, top middle
    scene.image(rivetPanel, centerX - 540, centerY + 160, 200, 200);  //  selected char box, bottom left
    scene.image(rivetPanel, centerX + 340, centerY + 160, 200, 200); // player buttons bottom right
    scene.image(rivetPanel, centerX - 540, centerY - 150, 125, 230);   // player resources middle left
    scene.image(resourcesIMG, centerX - 520, centerY - 120, 25, 25);
    scene.image(healthIMG, centerX - 520, centerY - 90, 25, 25);
    scene.image(attackIMG, centerX - 520, centerY - 60, 25, 25);
    scene.image(rangeIMG, centerX - 520, centerY - 30, 25, 25);
    scene.image(moveIMG, centerX - 520, centerY - 0, 25, 25);
    scene.image(coinIMG, centerX - 520, centerY + 30, 25, 25);


    // Displays the current player's name
    scene.textFont(fontLarge);
    scene.text("Player " + (hud.currentPlayer.getPlayerNum() + 1), centerX - 75, centerY - 325); //print current player

    // Displays the current player's resources
    scene.textFont(fontMedium);
    scene.text(": " + (hud.currentPlayer.getResources()), centerX - 495, centerY - 100); //print player resources
    scene.text(": " + (selectedEntity != null && selectedEntity instanceof Unit ? ((Unit) selectedEntity).getRemainMove() : 0), centerX - 495, centerY + 20);

    //load selected unit picture, name, and stats in bottom left corner
    //load selected units buttons in bottom right corner
    if(selected) {
      hud.scene.image(selectedHighRes, centerX - 523, centerY + 177, 165, 165);
      scene.textFont(fontMedium);
      scene.text(selectedName, centerX - 490, centerY + 150);
      scene.textFont(fontMedium);
      scene.text(": " + selectedHealth, centerX - 495, centerY - 70);
      scene.text(": " + selectedAttack, centerX - 495, centerY - 40);
      scene.text(": " + selectedRange, centerX - 495, centerY - 10);
      scene.text(": " + selectedCost, centerX - 495, centerY + 50);

    }

    // draw the buttons
    buttonManager.draw();
  }

  public void unitSelected(Entity selected){
    this.selected = true;
    this.selectedEntity = selected;
    if (selected instanceof Soldier) {
      Soldier unit = (Soldier) selected;
      selectedHighRes = soldierSelectedIMG;
      selectedName = "Soldier";
      selectedHealth = unit.getHealth();
      selectedAttack = unit.getDamage();
      selectedRange = unit.getRange();
      selectedMovement = unit.getRemainMove();
      selectedCost = unit.getCost();
//      buildSoldierButton.setActive(false);
//      buildWorkerButton.setActive(false);
//      buildBuildingButton.setActive(false);

    } else if (selected instanceof Building) {
      Building unit = (Building) selected;
      selectedHighRes = buildingSelectedIMG;
      selectedName = "Building";
      selectedHealth = unit.getHealth();
      selectedAttack = 0;
      selectedRange = 0;
      selectedMovement = 0;
      selectedCost = unit.getCost();
//      buildSoldierButton.setActive(true);
//      buildWorkerButton.setActive(true);
//      buildBuildingButton.setActive(false);

    } else if (selected instanceof Worker) {
      Unit unit = (Unit) selected;
      selectedHighRes = workerSelectedIMG;
      selectedName = "Worker";
      selectedHealth = unit.getHealth();
      selectedAttack = 0;
      selectedRange = 0;
      selectedMovement = unit.getRemainMove();
      selectedCost = unit.getCost();
//      buildSoldierButton.setActive(false);
//      buildWorkerButton.setActive(false);
//      buildBuildingButton.setActive(true);
    }
  }
}


