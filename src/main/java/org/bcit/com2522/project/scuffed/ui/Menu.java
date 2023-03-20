package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

import static processing.awt.ShimAWT.loadImage;

import processing.core.PImage;

 public class Menu {
     private MenuState previousState;
     public MenuState currentState;
     Window scene;

     public static PImage buttonBackground;
     public static PImage buttonHoverBackground;
     public static PImage buttonClickBackground;

    public Menu(Window scene) {
        this.scene = scene;
        loadPImages();
        this.currentState = new MainMenuState(scene, this);
    }

    public void loadPImages() {
        buttonBackground = loadImage(scene, "sprites/Menu/background.png");
        buttonHoverBackground = loadImage(scene, "sprites/Menu/button_blank.png");
        buttonClickBackground = loadImage(scene, "sprites/Menu/button_blank_pressed.png");
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
