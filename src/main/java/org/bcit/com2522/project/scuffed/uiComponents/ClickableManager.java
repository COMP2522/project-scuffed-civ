package org.bcit.com2522.project.scuffed.uiComponents;

import java.util.ArrayList;
import org.bcit.com2522.project.scuffed.client.Window;

/**
 * The type Clickable manager.
 */
public class ClickableManager {
  /**
   * The Clickables.
   */
  ArrayList<Clickable> clickables = new ArrayList<Clickable>();
  /**
   * The Scene.
   */
  Window scene;

  /**
   * Instantiates a new Clickable manager.
   *
   * @param scene the scene
   */
  public ClickableManager(Window scene) {
    this.scene = scene;
  }

  /**
   * Add.
   *
   * @param clickable the clickable
   */
  public void add(Clickable clickable) {
    clickables.add(clickable);
  }

  /**
   * Remove.
   *
   * @param clickable the clickable
   */
  public void remove(Clickable clickable) {
    clickables.remove(clickable);
  }

  /**
   * Check click.
   */
  public void checkClick() {
    for (Clickable clickable : clickables) {
      if (clickable.isHovered(scene.mouseX, scene.mouseY)) {
        clickable.click();
      }
    }
  }

  /**
   * Check hover.
   */
  public void checkHover() {
    for (Clickable clickable : clickables) {
      if (clickable.isHovered(scene.mouseX, scene.mouseY)) {
        clickable.hover();
      }
    }
  }
}
