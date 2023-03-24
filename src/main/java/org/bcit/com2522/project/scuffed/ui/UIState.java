package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.ClickableManager;
import org.bcit.com2522.project.scuffed.client.Window;

public abstract class UIState {
    protected ButtonManager buttonManager;
    protected GraphicManager graphicManager;
    protected ClickableManager clickableManager;

    protected UI UI;

    public UIState(UI UI) {
        this.UI = UI;
    }

    public void draw(Window scene) {
        buttonManager.draw(scene);
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

    /**
     * SetUp the buttons and inputs for the menu state
     */
    public abstract void setup();

    /**
     * Called when the back/exit button is clicked
     */
    public abstract void onBackClicked(); // go back to the previous menu state
}
