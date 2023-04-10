package org.bcit.com2522.project.scuffed.uicomponents;

import java.util.ArrayList;
import org.bcit.com2522.project.scuffed.client.Window;

/**
 * Represents a ButtonManager that handles the addition, removal, drawing, and
 * management of multiple Button objects.
 */
public class ButtonManager {
  /**
   * The collection of Button objects.
   */
  public ArrayList<Button> buttons = new ArrayList<Button>();

  /**
   * The Window scene where the buttons are displayed.
   */
  Window scene;

  /**
   * Constructs a new ButtonManager associated with the given Window scene.
   *
   * @param scene the Window scene where the buttons will be displayed
   */
  public ButtonManager(Window scene) {
    this.scene = scene;
  }

  /**
   * Adds a new Button to the ButtonManager.
   *
   * @param button the Button to add
   */
  public void add(Button button) {
    buttons.add(button);
  }

  /**
   * Removes a specific Button from the ButtonManager.
   *
   * @param button the Button to remove
   */
  public void remove(Button button) {
    buttons.remove(button);
  }

  /**
   * Draws all buttons managed by the ButtonManager on the specified Window scene.
   *
   * @param scene the Window scene where the buttons will be drawn
   */
  public void draw(Window scene) {
    for (Button button : buttons) {
      button.draw(scene);
    }
  }

  /**
   * Deletes all buttons managed by the ButtonManager.
   */
  public void wipe() {
    buttons.clear();
  }
}
