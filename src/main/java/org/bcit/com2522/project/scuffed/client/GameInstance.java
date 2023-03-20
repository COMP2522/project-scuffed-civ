package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.ui.UIState;
public class GameInstance {

    public GameState gameState;
    public UIState uiState;

    public void draw() {
        gameState.draw();
        uiState.draw();
    }

    public void clicked(int xpos, int ypos) {
        uiState.clicked(xpos, ypos);
    }
}
