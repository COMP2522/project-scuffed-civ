package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

public class Label implements Drawable {
    private int x, y;
    private String text;
    private Window scene;
    private int textSize;

    public Label(int x, int y, String text, int textSize) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.scene = scene;
        this.textSize = textSize;
    }

    public void draw(Window scene) {
        this.scene.pushStyle();
        this.scene.fill(0);
        this.scene.textSize(textSize);
        this.scene.text(text, x, y);
        this.scene.popStyle();
    }
}