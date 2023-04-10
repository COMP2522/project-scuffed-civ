package org.bcit.com2522.project.scuffed.uiComponents;

import org.bcit.com2522.project.scuffed.client.Window;

/**
 * A label that can be drawn on the screen.
 */
public class Label {
  private int x, y;
  private String text;
  private int textSize;

  /**
   * Instantiates a new Label.
   *
   * @param x        the x
   * @param y        the y
   * @param text     the text
   * @param textSize the text size
   */
  public Label(int x, int y, String text, int textSize) {
    this.x = x;
    this.y = y;
    this.text = text;
    this.textSize = textSize;
  }

  /**
   * Gets x.
   *
   * @return the x
   */
  public int getX() {
    return x;
  }

  /**
   * Sets x.
   *
   * @param x the x
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Gets y.
   *
   * @return the y
   */
  public int getY() {
    return y;
  }

  /**
   * Sets y.
   *
   * @param y the y
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * Gets text.
   *
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * Sets text.
   *
   * @param text the text
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Gets text size.
   *
   * @return the text size
   */
  public int getTextSize() {
    return textSize;
  }

  /**
   * Sets text size.
   *
   * @param textSize the text size
   */
  public void setTextSize(int textSize) {
    this.textSize = textSize;
  }

  /**
   * Draw.
   *
   * @param scene the scene
   */
  public void draw(Window scene) {
    scene.pushStyle();
    scene.fill(255, 255, 255);
    scene.textSize(textSize);
    scene.text(text, x, y);
    scene.popStyle();
  }
}