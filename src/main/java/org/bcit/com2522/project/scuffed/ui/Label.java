package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

public class Label{
    private int x, y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public Window getScene() {
        return scene;
    }

    public void setScene(Window scene) {
        this.scene = scene;
    }

    private String text;
    private int textSize;

    private Window scene;

    public Label(int x, int y, String text, int textSize, Window scene) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.textSize = textSize;
        this.scene = scene;
    }

    public void draw() {
        scene.pushStyle();
        scene.fill(255, 255, 255);
        scene.textSize(textSize);
        scene.text(text, x, y);
        scene.popStyle();
    }
}