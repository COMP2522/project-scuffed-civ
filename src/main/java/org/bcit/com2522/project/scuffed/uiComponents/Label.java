package org.bcit.com2522.project.scuffed.uiComponents;

import org.bcit.com2522.project.scuffed.client.Window;

/**
 * The type Label.
 */
public class Label{
    private int x, y;

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
   * Gets scene.
   *
   * @return the scene
   */
  public Window getScene() {
        return scene;
    }

  /**
   * Sets scene.
   *
   * @param scene the scene
   */
  public void setScene(Window scene) {
        this.scene = scene;
    }

    private String text;
    private int textSize;

    private Window scene;

  /**
   * Instantiates a new Label.
   *
   * @param x        the x
   * @param y        the y
   * @param text     the text
   * @param textSize the text size
   * @param scene    the scene
   */
  public Label(int x, int y, String text, int textSize, Window scene) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.textSize = textSize;
        this.scene = scene;
    }

  /**
   * Draw.
   */
  public void draw(Window scene) {
        this.scene.pushStyle();
        this.scene.fill(255, 255, 255);
        this.scene.textSize(textSize);
        this.scene.text(text, x, y);
        this.scene.popStyle();
    }
}