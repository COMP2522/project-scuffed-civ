package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.ui.MenuState;
import processing.core.PVector;

public class HUD {
    public Player currentPlayer;
    public HUDState currentState;
    Window scene;

    public HUD(Window scene) {
        this.scene = scene;
        this.currentState = new inGameStartHUD(this);
    }

    public void setState(HUDState newState) {
        scene.wipeGraphics();
        this.currentState = newState;
    }

    public void draw(Window scene) {
        currentState.draw(scene);
    }

    public boolean clicked(PVector mousePos) {

        return currentState.clicked((int)mousePos.x, (int)mousePos.y);
    }

}