package org.bcit.com2522.project.scuffed.client;

/**
 * The type Debug menu.
 */
public class DebugMenu {

  /**
   * The Scene.
   */
  Window scene;

  /**
   * Instantiates a new Debug menu.
   *
   * @param scene the scene
   */
  public DebugMenu(Window scene) {
    this.scene = scene;
  }

  /**
   * Draw.
   *
   * @param scene the scene
   */
  public void draw(Window scene) {
    scene.fill(255);
    scene.rect(0, 0, 150, 100);
    scene.fill(0);
    scene.textSize(15);
    scene.text(("mX=" + scene.mouseX + " mY=" + scene.mouseY), 5, 15);
    scene.text(("currentPlayer=" + scene.getCurrentPlayer()), 5, 30);
  }

}
