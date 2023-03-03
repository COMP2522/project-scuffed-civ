package org.bcit.com2522.project.scuffed.client;

public class Menu {

    Window scene;

    public Menu(Window scene) {
        this.scene = scene;
    }
    public void draw() {
        scene.textSize(100);
        scene.fill(200);
        scene.square(100, 200, 100);
        scene.square(300, 200, 100);
        scene.square(500, 200, 100);
        scene.square(100, 400, 100);
        scene.square(300, 400, 100);
        scene.square(500, 400, 100);

        scene.fill(000);
        scene.text("how many players?", 100, 150);
        scene.text("1", 125, 275);
        scene.text("2", 325, 275);
        scene.text("3", 525, 275);
        scene.text("4", 125, 475);
        scene.text("5", 325, 475);
        scene.text("6", 525, 475);

    }

    public static void clicked() {
    }
}
