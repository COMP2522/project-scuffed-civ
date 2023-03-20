package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

public abstract class MenuState {
    ButtonManager buttonManager;
    protected Window scene;
    protected Menu menu;

    public MenuState(Window scene, Menu menu, ButtonManager buttonManager) {
        this.scene = scene;
        this.buttonManager = buttonManager;
        this.menu = menu;
    }

    public void draw() {
        buttonManager.draw();
    }

    public boolean clicked(int xpos, int ypos) {
        // Check if any buttons were clicked and perform actions
        for (Button button : buttonManager.buttons) {
            if (button.isClicked(xpos, ypos)) {
                button.click();
                return true;
            }
        }
        return false;
    }

    public abstract void setup(); // setup the buttons for the menu state
    public abstract void onBackClicked(); // go back to the previous menu state
}