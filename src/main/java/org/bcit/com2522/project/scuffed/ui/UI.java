package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

public class UI {
     public UIState currentState;
     Window scene;

    public UI(Window scene) {
        this.scene = scene;
        this.currentState = new MainMenuUIState(this);
    }

    public void setState(UIState newState) {
        scene.wipeGraphics();
        this.currentState = newState;
    }

    public void draw() {
        currentState.draw(scene);
    }

    public boolean clicked(int xpos, int ypos) {
        return currentState.clicked(xpos, ypos);
    }

 }
