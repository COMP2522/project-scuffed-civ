package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

public abstract class UIState {
    protected ClickableManager clickableManager;
    protected GraphicManager graphicManager;

    protected UI UI;

    public UIState(UI UI) {
        this.UI = UI;
        this.graphicManager = new GraphicManager();
        this.clickableManager = new ClickableManager();
    }

    public void draw(Window scene) {
        clickableManager.draw(scene);
        graphicManager.draw(scene);
    }

    public boolean clicked(int mouseX, int mouseY) {
        return clickableManager.checkClick(mouseX, mouseY);;
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
