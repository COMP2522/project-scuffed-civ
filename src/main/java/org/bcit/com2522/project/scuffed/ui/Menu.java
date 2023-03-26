package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

public class Menu {
     private UIState previousState;
     public UIState currentState;
     Window scene;

    public Menu(Window scene) {
        this.scene = scene;
        this.currentState = new MainMenuUIState(scene, this);
    }

    public void setState(UIState newState) {
        scene.wipeGraphics();
        this.previousState = this.currentState;
        this.currentState = newState;
    }

    public void draw() {
        currentState.draw();
    }

    public boolean clicked(int xpos, int ypos) {
        return currentState.clicked(xpos, ypos);
    }

 }
