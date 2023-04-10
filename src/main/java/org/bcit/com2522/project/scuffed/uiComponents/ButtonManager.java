package org.bcit.com2522.project.scuffed.uiComponents;

import org.bcit.com2522.project.scuffed.client.Window;

import java.util.ArrayList;

/**
 * The type Button manager.
 */
public class ButtonManager{
  /**
   * The Buttons.
   */
  public ArrayList<Button> buttons = new ArrayList<Button>();
  /**
   * The Scene.
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
   * Add.
   *
   * @param button the button
   */
  public void add(Button button) {
    buttons.add(button);
  }

  /**
   * Remove.
   *
   * @param button the button
   */
  public void remove(Button button) {
    buttons.remove(button);
  }

  /**
   * Draw.
   */
  public void draw() {
    for (Button button : buttons) {
      button.draw(scene);
    }
  }

  /**
   * Wipe.
   */
  public void wipe() {
    buttons.clear();
  }
}
