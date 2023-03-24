package org.bcit.com2522.project.scuffed.ui;
import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PImage;

/* Manages Non-Functional UI Elements
*
*
*/
public class Graphic {
  float x;
  float y;
  protected PImage texture;

  public Graphic(float x, float y, PImage texture) {
    this.x = x;
    this.y = y;
    this.texture = texture;
  }

  public void draw(Window scene) {
    scene.image(texture, x, y);
  }

  public void moveGraphic(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }
}
