package org.bcit.com2522.project.scuffed.uicomponents;

import java.util.ArrayList;
import org.bcit.com2522.project.scuffed.client.Window;

/**
 * Manages Clickable objects in a given Window scene. Provides functionality to
 * add, remove, and check for click and hover events on Clickable objects.
 */
public class ClickableManager {

  /**
   * The list of Clickable objects being managed.
   */
  ArrayList<Clickable> clickables = new ArrayList<Clickable>();

  /**
   * The Window scene where the Clickable objects are being managed.
   */
  Window scene;

  /**
   * Constructs a new ClickableManager object for a given Window scene.
   *
   * @param scene the Window scene to manage Clickable objects in
   */
  public ClickableManager(Window scene) {
    this.scene = scene;
  }

  /**
   * Adds a Clickable object to be managed.
   *
   * @param clickable the Clickable object to add
   */
  public void add(Clickable clickable) {
    clickables.add(clickable);
  }

  /**
   * Removes a Clickable object from being managed.
   *
   * @param clickable the Clickable object to remove
   */
  public void remove(Clickable clickable) {
    clickables.remove(clickable);
  }

  /**
   * Checks if any Clickable objects have been clicked based on the scene's mouse
   * coordinates, and if so, executes their click callback functions.
   */
  public void checkClick() {
    for (Clickable clickable : clickables) {
      if (clickable.isHovered(scene.mouseX, scene.mouseY)) {
        clickable.click();
      }
    }
  }

  /**
   * Checks if any Clickable objects are being hovered over based on the scene's
   * mouse coordinates, and if so, executes their hover callback functions.
   */
  public void checkHover() {
    for (Clickable clickable : clickables) {
      if (clickable.isHovered(scene.mouseX, scene.mouseY)) {
        clickable.hover();
      }
    }
  }
}
