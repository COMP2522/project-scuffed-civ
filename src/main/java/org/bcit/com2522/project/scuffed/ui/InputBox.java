package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PApplet;

public class InputBox extends Clickable {
    private int x, y, width, height;
    private String text;
    private String type;
    private boolean selected;
    private int minValue, maxValue;

    public InputBox(int x, int y, int width, int height, int minValue, int maxValue) {
        super
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = "";
        this.selected = false;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.type = "int";
    }

    public InputBox(int x, int y, int width, int height, int minValue, int maxValue) {
        this.x = 50;
        this.y = 50;
        this.width = width;
        this.height = height;
        this.text = "";
        this.selected = false;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.type = "int";
    }

    public InputBox(int x, int y, int width, int height, int minValue, int maxValue, String defaultText) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = defaultText;
        this.selected = false;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.type = "int";
    }

    public InputBox(int x, int y, int width, int height, String defaultText) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = defaultText;
        this.selected = false;
        this.minValue = Integer.MIN_VALUE;
        this.maxValue = Integer.MAX_VALUE;
    }


    public InputBox(int x, int y, int width, int height, String defaultText, String type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = defaultText;
        this.selected = false;
        this.minValue = Integer.MIN_VALUE;
        this.maxValue = Integer.MAX_VALUE;
        this.type = type;
    }

    public void draw(Window scene) {
        scene.pushStyle();
        scene.stroke(0);
        if(selected) {
            scene.fill(220);
        } else {
            scene.fill(255);
        }
        scene.rect(x, y, width, height);
        scene.textSize(16);
        scene.fill(0);
        scene.text(text, x + 10, y + height - 10);

        scene.popStyle();
    }

    public boolean isSelected() {
        return selected;
    }

    public void select() {
        this.selected = true;
    }

    public void deselect() {
        this.selected = false;
    }

    public void addCharacter(char c) {
        if(type.equalsIgnoreCase("string")) {
            text += c;
            return;
        }
        if (Character.isDigit(c)) {
            text += c;
        }
    }

    public void removeCharacter() {
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
        }
    }

    public int getIntValue() {
        if(text.equals("")) {
            return 0;
        }
        int value = Integer.parseInt(text);
        return PApplet.constrain(value, minValue, maxValue);
    }

    public String getStringValue() {
        return text;
    }
    public boolean isClicked(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}