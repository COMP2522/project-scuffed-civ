package org.bcit.com2522.project.scuffed.uicomponents;

import java.util.ArrayList;
import org.bcit.com2522.project.scuffed.client.Window;

/**
 * The type Button manager.
 */
public class ButtonManager {
  /**
   * The Buttons.
   */
  public ArrayList<Button> buttons = new ArrayList<Button>();
  /**
   *  The scene.
   */
  Window scene;

  /**
   * Instantiates a new Button manager.
   *
   * @param scene the scene
   */
  public ButtonManager(Window scene) {
    this.scene = scene;
  }

  /**
   * Adds a button.
   *
   * @param button the button
   */
  public void add(Button button) {
    buttons.add(button);
  }

  /**
   * Removes a specific button.
   *
   * @param button the button
   */
  public void remove(Button button) {
    buttons.remove(button);
  }

  /**
   * Draws all buttons.
   *
   * @param scene the scene
   */
  public void draw(Window scene) {
    for (Button button : buttons) {
      button.draw(scene);
    }
  }

  /**
   * Deletes all buttons.
   */
  public void wipe() {
    buttons.clear();
  }
}
