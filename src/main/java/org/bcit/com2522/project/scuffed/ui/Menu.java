package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

import static processing.awt.ShimAWT.loadImage;

import processing.core.PImage;


//public class Menu {
//
//    Window scene;
//
//    public MenuState menuState;
//
//    public Menu(Window scene) {
//        this.scene = scene;
//    }
//
//    public void draw() {
//        menuState.draw();
//    }
//
//    public boolean clicked(float xpos, float ypos) {
//        int mapwidth = 16;
//        int mapheight = 16;
//
//        //this should probably be a box that you can enter a number instead of buttons
//        if(ypos >= 200 && ypos <= 300) {
//            //first row of boxes
//            if (xpos >= 100 && xpos <= 200) { //
//                scene.initGame(1, mapwidth, mapheight);
//                return true;
//            }
//            else if (xpos >= 300 && xpos <= 400) { //
//                scene.initGame(2, mapwidth, mapheight);
//                return true;
//            }
//            else if (xpos >= 500 && xpos <= 600) { //
//                scene.initGame(3, mapwidth, mapheight);
//                return true;
//            }
//
//        } else if (ypos >= 400 && ypos <= 500) {
//            //second row of boxes
//            if (xpos >= 100 && xpos <= 200) { //
//                scene.initGame(4, mapwidth, mapheight);
//                return true;
//            }
//            else if (xpos >= 300 && xpos <= 400) { //
//                scene.initGame(5, mapwidth, mapheight);
//                return true;
//            }
//            else if (xpos >= 500 && xpos <= 600) { //
//                scene.initGame(6, mapwidth, mapheight);
//                return true;
//            }
//        } else {
//            //clicked anywhere else (vertically)
//        }
//        return false;
//    }

 public class Menu {
     private MenuState previousState;
    private MenuState currentState;
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
