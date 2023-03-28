package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.ui.Button;
import org.bcit.com2522.project.scuffed.ui.ButtonManager;
import org.bcit.com2522.project.scuffed.ui.MenuState;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;

public abstract class HUDState {
    public HUD hud;
    public PImage rivetPanel;
    public PFont fontRetroGaming;

    int centerX;
    int centerY;

    ButtonManager buttonManager;

    public HUDState(HUD hud) {
        this.hud = hud;
        centerX = hud.scene.width / 2;
        centerY = hud.scene.height / 2;
        setup();
    }

    /**
     * SetUp the buttons and inputs for the menu state
     */
    public abstract void setup();


//    public static void clicked(PVector mousePos , Window scene){
//        if (mousePos.x >= 700 && mousePos.y >= 500) {
//            scene.saveGame();
//            scene.nextTurn();
//        }
//    }

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

    public abstract void draw(Window scene);


}
