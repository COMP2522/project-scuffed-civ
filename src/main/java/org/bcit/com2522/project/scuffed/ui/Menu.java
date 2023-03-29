package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

public class Menu {
     public MenuState currentState;
     Window scene;

    public Menu(Window scene) {
        this.scene = scene;
        this.currentState = new MainMenuMenuState(scene, this);
    }

    public void setState(MenuState newState) {
        scene.wipeGraphics();
        this.currentState = newState;
    }

    public void draw() {
        currentState.draw();
    }

    public boolean clicked(int xpos, int ypos) {
        return currentState.clicked(xpos, ypos);
    }

 }
