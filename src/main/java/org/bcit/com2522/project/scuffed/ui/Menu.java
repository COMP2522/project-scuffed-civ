package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

import static processing.awt.ShimAWT.loadImage;

import processing.core.PImage;

 public class Menu {
     private MenuState previousState;
     public MenuState currentState;
     Window scene;

    public Menu(Window scene) {
        this.scene = scene;
        this.currentState = new MainMenuState(scene, this);
    }

    public void setState(MenuState newState) {
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
