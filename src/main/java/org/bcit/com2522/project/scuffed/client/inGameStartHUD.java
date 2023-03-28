package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.ui.Button;
import org.bcit.com2522.project.scuffed.ui.ButtonManager;

public class inGameStartHUD extends HUDState {

    public inGameStartHUD(HUD hud) {
        super(hud);
    }

    @Override
    public void setup() {
        fontRetroGaming = hud.scene.createFont("fonts/Retro Gaming.ttf", 24);
        rivetPanel = hud.scene.loadImage("sprites/RivetPanel.png");
        buttonManager = new ButtonManager(hud.scene);

        Button hudMenuButton = new Button(centerX - 540, centerY - 360, centerX - 390, centerY - 310, () -> {
            hud.setState(new menuHUDState(hud));
        }, "Menu", rivetPanel, rivetPanel, rivetPanel, hud.scene);
        //buttonManager.add(hudMenuButton);
        Button hudEndTurnButton = new Button(centerX + 390, centerY - 360, centerX + 540, centerY - 310, () -> {
            hud.scene.nextTurn();
        }, "End Turn", rivetPanel, rivetPanel, rivetPanel, hud.scene);
        //ButtonManager = scene.buttonManager;
        buttonManager.add(hudEndTurnButton);
    }

    @Override
    public void draw(Window scene) {




        // draw the players information
        scene.image(rivetPanel, centerX - 540, centerY - 360, 150, 50);  // menu box, top left
        scene.image(rivetPanel, centerX + 390, centerY - 360, 150, 50);  // end turn box, top right
        scene.image(rivetPanel, centerX - 100, centerY - 360, 200, 50);  // Player selected name box, top middle
        scene.image(rivetPanel, centerX - 540, centerY + 160, 200, 200);  //  selected char box, bottom left
        scene.image(rivetPanel, centerX + 340, centerY + 160, 200, 200); // player buttons bottom right
        scene.image(rivetPanel, centerX - 540, centerY - 150, 150, 200);   // player resources middle left

        scene.textFont(fontRetroGaming);

        scene.text("Player " + (hud.currentPlayer.getplayerNum() + 1), centerX - 50, centerY - 335); //print current player
        scene.text("Resources " + (hud.currentPlayer.getResources()), centerX - 500, centerY - 100); //print player resources



        buttonManager.draw();
    }

}
