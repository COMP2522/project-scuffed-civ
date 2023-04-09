package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

/**
 * The type Menu state.
 */
public abstract class MenuState {
  /**
   * The Button manager.
   */
  protected ButtonManager buttonManager;
  /**
   * The Scene.
   */
  protected Window scene;
  /**
   * The Menu.
   */
  protected Menu menu;

  /**
   * Instantiates a new Menu state.
   *
   * @param scene         the scene
   * @param menu          the menu
   * @param buttonManager the button manager
   */
  public MenuState(Window scene, Menu menu, ButtonManager buttonManager) {
        this.scene = scene;
        this.buttonManager = buttonManager;
        this.menu = menu;
    }

  /**
   * Draw.
   */
  public void draw() {
        buttonManager.draw();
    }

  /**
   * Clicked boolean.
   *
   * @param xpos the xpos
   * @param ypos the ypos
   * @return the boolean
   */
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
