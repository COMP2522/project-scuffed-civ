package org.bcit.com2522.project.scuffed.client;

import processing.core.PVector;

public class HUD {
    public Player currentPlayer;
    public static void clicked(PVector mousePos , Window scene){
        if (mousePos.x >= 700 && mousePos.y >= 500) {
            scene.saveGame();
            scene.nextTurn();
        }
    }

    public void draw(Window scene) {
        currentPlayer.draw(scene);
    }
}
