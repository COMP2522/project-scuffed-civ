package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.ui.Button;
import org.bcit.com2522.project.scuffed.ui.ButtonManager;
import org.bcit.com2522.project.scuffed.ui.UIState;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;

public class HUD {
    public Player currentPlayer;
    public UIState hudState;
    Window scene;

    public PImage rivetPanel;
    public PFont fontRetroGaming;

    public static void clicked(PVector mousePos , Window scene){
        if (mousePos.x >= 700 && mousePos.y >= 500) {
            scene.saveGame();
            scene.nextTurn();
        }
    }

    public void draw(Window scene) {
        int centerX = scene.width / 2;
        int centerY = scene.height / 2;

        fontRetroGaming = scene.createFont("fonts/Retro Gaming.ttf", 24);

        rivetPanel = scene.loadImage("sprites/RivetPanel.png");

        // draw the players information
        scene.image(rivetPanel, centerX - 540, centerY - 360, 150, 50);  // menu box, top left
        scene.image(rivetPanel, centerX + 390, centerY - 360, 150, 50);  // end turn box, top right
        scene.image(rivetPanel, centerX - 100, centerY - 360, 200, 50);  // Player selected name box, top middle
        scene.image(rivetPanel, centerX - 540, centerY + 160, 200, 200);  //  selected char box, bottom left
        scene.image(rivetPanel, centerX + 340, centerY + 160, 200, 200); // player buttons bottom right
        scene.image(rivetPanel, centerX - 540, centerY - 150, 150, 200);   // player resources middle left

        scene.textFont(fontRetroGaming);

        scene.text("Player " + (currentPlayer.getplayerNum() + 1), centerX - 50, centerY - 335); //print current player
        scene.text("Resources " + (currentPlayer.getResources()), centerX - 500, centerY - 100); //print player resources

        ButtonManager buttonManager = new ButtonManager(scene);

//        Button hudMenuButton = new Button(centerX - 540, centerY - 360, centerX - 390, centerY - 310, () -> {
//            scene.setUIState(UIState.MENU);
//        }, "Menu", rivetPanel, rivetPanel, rivetPanel, scene);
        //buttonManager.add(hudMenuButton);
        Button hudEndTurnButton = new Button(centerX + 390, centerY - 360, centerX + 540, centerY - 310, () -> {
            scene.nextTurn();
        }, "End Turn", rivetPanel, rivetPanel, rivetPanel, scene);

        buttonManager.add(hudEndTurnButton);

        buttonManager.draw();
    }

    public void setUIState(UIState uiState) {
        this.hudState = uiState;
    }
}
