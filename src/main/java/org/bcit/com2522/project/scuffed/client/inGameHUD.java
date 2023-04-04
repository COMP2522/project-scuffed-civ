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
    soldierSelectedIMG = hud.scene.loadImage("sprites/SoldierSelectedIMG.jpg");
    buildingSelectedIMG = hud.scene.loadImage("sprites/buildingSelectedIMG.jpg");
    workerSelectedIMG = hud.scene.loadImage("sprites/WorkerSelectedIMG.jpg");

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
    scene.image(rivetPanel, centerX - 100, centerY - 360, 200, 50);  // Player selected name box, top middle
    scene.image(rivetPanel, centerX - 540, centerY + 160, 200, 200);  //  selected char box, bottom left
    scene.image(rivetPanel, centerX + 340, centerY + 160, 200, 200); // player buttons bottom right
    scene.image(rivetPanel, centerX - 540, centerY - 150, 150, 200);   // player resources middle left

    // Displays the current player's name
    scene.textFont(fontLarge);
    scene.text("Player " + (hud.currentPlayer.getPlayerNum() + 1), centerX - 75, centerY - 325); //print current player

    // Displays the current player's resources
    scene.textFont(fontSmall);
    scene.text("Mats: " + (hud.currentPlayer.getResources()), centerX - 520, centerY - 117); //print player resources
    scene.text("Moves:" + (selectedEntity != null && selectedEntity instanceof Unit ? ((Unit) selectedEntity).getRemainMove() : 0), centerX - 520, centerY - 97);

    //load selected unit picture, name, and stats in bottom left corner
    //load selected units buttons in bottom right corner
    if(selected) {
      hud.scene.image(selectedHighRes, centerX - 523, centerY + 177, 165, 165);
      scene.textFont(fontMedium);
      scene.text(selectedName, centerX - 490, centerY + 150);
      scene.textFont(fontSmall);
      scene.text("Health: " + selectedHealth, centerX - 520, centerY -77);
      scene.text("Attack: " + selectedAttack, centerX - 520, centerY - 57);
      scene.text("Range: " + selectedRange, centerX - 520, centerY - 37);
      scene.text("Movement: " + selectedMovement, centerX - 523, centerY - 17);
      scene.text("Cost: " + selectedCost, centerX - 523, centerY + 3);
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

    } else if (selected instanceof Building) {
      Building unit = (Building) selected;
      selectedHighRes = buildingSelectedIMG;
      selectedName = "Building";
      selectedHealth = unit.getHealth();
      selectedAttack = 0;
      selectedRange = 0;
      selectedMovement = 0;
      selectedCost = unit.getCost();
    } else if (selected instanceof Worker) {
      Unit unit = (Unit) selected;
      selectedHighRes = workerSelectedIMG;
      selectedName = "Worker";
      selectedHealth = unit.getHealth();
      selectedAttack = 0;
      selectedRange = 0;
      selectedMovement = unit.getRemainMove();
      selectedCost = unit.getCost();
    }
  }
}


