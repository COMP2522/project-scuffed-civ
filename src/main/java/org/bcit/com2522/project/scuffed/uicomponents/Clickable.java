package org.bcit.com2522.project.scuffed.uicomponents;

/**
 * The type Clickable.
 */
public class Clickable {
  /**
   * The X 1.
   */
  int x1,
  /**
   * The Y 1.
   */
  y1,
  /**
   * The X 2.
   */
  x2,
  /**
   * The Y 2.
   */
  y2;
  /**
   * The Callback.
   */
  Runnable callback;

  /**
   * Instantiates a new Clickable.
   *
   * @param x1              the x 1
   * @param y1              the y 1
   * @param x2              the x 2
   * @param y2              the y 2
   * @param callback        the callback
   * @param callbackOnHover the callback on hover
   */
  public Clickable(int x1, int y1, int x2, int y2, Runnable callback, Runnable callbackOnHover) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.callback = callback;
  }


  /**
   * Is hovered boolean.
   *
   * @param x the x
   * @param y the y
   * @return the boolean
   */
  public boolean isHovered(int x, int y) {
    return x >= x1 && x <= x2 && y >= y1 && y <= y2;
  }

  /**
   * Click.
   */
  public void click() {
    callback.run();
  }

  /**
   * Hover.
   */
  public void hover() {
    callback.run();
  }


  /**
   * Change bounds.
   *
   * @param x1 the x 1
   * @param y1 the y 1
   * @param x2 the x 2
   * @param y2 the y 2
   */
  public void changeBounds(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

  /**
   * Change callback.
   *
   * @param callback the callback
   */
  public void changeCallback(Runnable callback) {
    this.callback = callback;
  }


}
