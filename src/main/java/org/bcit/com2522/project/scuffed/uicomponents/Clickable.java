package org.bcit.com2522.project.scuffed.uicomponents;

/**
 * Represents a clickable UI component with specific boundaries and associated
 * callback functions for click and hover actions.
 */
public class Clickable {

  /**
   * The x-coordinate of the top-left corner of the clickable area.
   */
  int x1;

  /**
   * The y-coordinate of the top-left corner of the clickable area.
   */
  int y1;

  /**
   * The x-coordinate of the bottom-right corner of the clickable area.
   */
  int x2;

  /**
   * The y-coordinate of the bottom-right corner of the clickable area.
   */
  int y2;

  /**
   * The callback function to execute when the clickable area is clicked.
   */
  Runnable callback;

  /**
   * Constructs a new Clickable object with the specified boundaries and callback functions.
   *
   * @param x1              the x-coordinate of the top-left corner
   * @param y1              the y-coordinate of the top-left corner
   * @param x2              the x-coordinate of the bottom-right corner
   * @param y2              the y-coordinate of the bottom-right corner
   * @param callback        the callback function to execute when clicked
   * @param callbackOnHover the callback function to execute when hovered
   */
  public Clickable(int x1, int y1, int x2, int y2, Runnable callback, Runnable callbackOnHover) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.callback = callback;
  }

  /**
   * Determines if the specified point (x, y) is within the bounds of the clickable area.
   *
   * @param x the x-coordinate of the point
   * @param y the y-coordinate of the point
   * @return true if the point is within the clickable area, false otherwise
   */
  public boolean isHovered(int x, int y) {
    return x >= x1 && x <= x2 && y >= y1 && y <= y2;
  }

  /**
   * Executes the click callback function.
   */
  public void click() {
    callback.run();
  }

  /**
   * Executes the hover callback function.
   */
  public void hover() {
    callback.run();
  }

  /**
   * Changes the boundaries of the clickable area.
   *
   * @param x1 the new x-coordinate of the top-left corner
   * @param y1 the new y-coordinate of the top-left corner
   * @param x2 the new x-coordinate of the bottom-right corner
   * @param y2 the new y-coordinate of the bottom-right corner
   */
  public void changeBounds(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

  /**
   * Changes the click callback function.
   *
   * @param callback the new callback function to execute when clicked
   */
  public void changeCallback(Runnable callback) {
    this.callback = callback;
  }

}
