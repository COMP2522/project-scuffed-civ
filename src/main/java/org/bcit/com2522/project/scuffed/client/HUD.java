package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.ui.UIState;
import processing.core.PVector;

public class HUD {
    public Player currentPlayer;
    public UIState hudState;
    Window scene;

    public static void clicked(PVector mousePos , Window scene){
        if (mousePos.x >= 700 && mousePos.y >= 500) {
            scene.saveGame();
            scene.nextTurn();
        }
    }

    public void draw(Window scene) {
        currentPlayer.draw(scene);
    }

    public void setUIState(UIState uiState) {
        this.hudState = uiState;
    }
}
