package org.bcit.com2522.project.scuffed.client;

public class Menu {

    Window scene;

    public Menu(Window scene) {
        this.scene = scene;
    }
    public void draw() {
        scene.textSize(32);

        scene.fill(000);
        scene.text("how many players?", 100, 150);
        scene.text("1", 125, 275);
        scene.text("2", 325, 275);
        scene.text("3", 525, 275);
        scene.text("4", 125, 475);
        scene.text("5", 325, 475);
        scene.text("6", 525, 475);

    }

    public boolean clicked(float xpos, float ypos) {
        int mapwidth = 25;
        int mapheight = 20;

        //this should probably be a box that you can enter a number instead of buttons
        if(ypos >= 200 && ypos <= 300) {
            //first row of boxes
            if (xpos >= 100 && xpos <= 200) { //
                scene.initGame(1, mapwidth, mapheight);
                return true;
            }
            else if (xpos >= 300 && xpos <= 400) { //
                scene.initGame(2, mapwidth, mapheight);
                return true;
            }
            else if (xpos >= 500 && xpos <= 600) { //
                scene.initGame(3, mapwidth, mapheight);
                return true;
            }

        } else if (ypos >= 400 && ypos <= 500) {
            //second row of boxes
            if (xpos >= 100 && xpos <= 200) { //
                scene.initGame(4, mapwidth, mapheight);
                return true;
            }
            else if (xpos >= 300 && xpos <= 400) { //
                scene.initGame(5, mapwidth, mapheight);
                return true;
            }
            else if (xpos >= 500 && xpos <= 600) { //
                scene.initGame(6, mapwidth, mapheight);
                return true;
            }
        } else {
            //clicked anywhere else (vertically)
        }
        return false;
    }
}
