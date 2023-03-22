package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

public class Label implements UIElement{
    private int x, y;
    private String text;
    private Window scene;
    private int textSize;

    public Label(int x, int y, String text, int textSize, Window scene) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.scene = scene;
        this.textSize = textSize;
    }

    public void draw() {
        scene.pushStyle();
        scene.fill(0);
        scene.textSize(textSize);
        scene.text(text, x, y);
        scene.popStyle();
    }
}