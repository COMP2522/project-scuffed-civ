package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

import java.util.ArrayList;

public class ClickableManager {
    ArrayList<Clickable> clickables = new ArrayList<Clickable>();
    int xOffset = 0;
    int yOffset = 0;

    public ClickableManager(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public ClickableManager() {

    }

    public void add(Clickable clickable) {
        if(clickables.size() >= 1){
            clickable.offsetX(xOffset);
            clickable.offsetY(yOffset);
            clickables.add(clickable);
        } else {
            clickables.add(clickable);
        }
    }

    public void remove(Clickable clickable) {
        clickables.remove(clickable);
    }

    public boolean checkClick(int mouseX, int mouseY) {
        for (Clickable clickable : clickables) {
            if (clickable.isHovered(mouseX, mouseY)) {
                clickable.click();
                return true;
            }
        }
        return false;
    }

    public void checkHover(int mouseX, int mouseY) {
        for (Clickable clickable : clickables) {
            if (clickable.isHovered(mouseX, mouseY)) {
                clickable.hover();
            }
        }
    }

    public void draw(Window scene) {
        for (Clickable clickable : clickables){
            clickable.draw(scene);
        }
    }
}