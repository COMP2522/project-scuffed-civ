package org.bcit.com2522.project.scuffed.client;

import java.util.ArrayList;

public class ClickableManager {
    ArrayList<Clickable> clickables = new ArrayList<Clickable>();
    Window scene;

    public ClickableManager(Window scene) {
        this.scene = scene;
    }

    public void add(Clickable clickable) {
        clickables.add(clickable);
    }

    public void remove(Clickable clickable) {
        clickables.remove(clickable);
    }

    public void checkClick() {
        for (Clickable clickable : clickables) {
            if (clickable.isHovered(scene.mouseX, scene.mouseY)) {
                clickable.click();
            }
        }
    }

    public void checkHover() {
        for (Clickable clickable : clickables) {
            if (clickable.isHovered(scene.mouseX, scene.mouseY)) {
                clickable.hover();
            }
        }
    }
}
