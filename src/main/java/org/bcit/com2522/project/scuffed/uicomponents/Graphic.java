package org.bcit.com2522.project.scuffed.uicomponents;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PImage;

/**
 * The type Graphic.
 */
/* Manages Non-Functional UI Elements
 *
 *
 */
public class Graphic {
  /**
   * The Texture.
   */
  protected PImage texture;
  /**
   * The X.
   */
  float x;
  /**
   * The Y.
   */
  float y;
  /**
   * The Scene.
   */
  Window scene;

  /**
   * Instantiates a new Graphic.
   *
   * @param x       the x
   * @param y       the y
   * @param texture the texture
   * @param scene   the scene
   */
  public Graphic(float x, float y, PImage texture, Window scene) {
    this.x = x;
    this.y = y;
    this.texture = texture;
    this.scene = scene;
  }

  /**
   * Draw.
   *
   * @param scene the scene
   */
  public void draw(Window scene) {
    scene.image(texture, x, y);
  }

  /**
   * Move graphic.
   *
   * @param x the x
   * @param y the y
   */
  public void moveGraphic(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets x.
   *
   * @return the x
   */
  public float getX() {
    return x;
  }

  /**
   * Gets y.
   *
   * @return the y
   */
  public float getY() {
    return y;
  }
}
