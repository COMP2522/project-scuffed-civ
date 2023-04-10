package org.bcit.com2522.project.scuffed.menu;

import org.bcit.com2522.project.scuffed.client.Window;
import org.bcit.com2522.project.scuffed.uiComponents.Button;
import org.bcit.com2522.project.scuffed.uiComponents.ButtonManager;

/**
 * The type Menu state. This class is the base class for all menu states. It contains a button manager a reference to the scene and menu.
 * TODO: Remove the scene reference from this and all subclasses.
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
