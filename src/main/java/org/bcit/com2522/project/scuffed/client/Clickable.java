package org.bcit.com2522.project.scuffed.client;

public class Clickable {
  int x1, y1, x2, y2;
  Runnable callback;

  public Clickable(int x1, int y1, int x2, int y2, Runnable callback, Runnable callbackOnHover) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.callback = callback;
  }


  public boolean isHovered(int x, int y) {
    return x >= x1 && x <= x2 && y >= y1 && y <= y2;
  }

  public void click() {
    callback.run();
  }

  public void hover() {
    callback.run();
  }


  public void changeBounds(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

  public void changeCallback(Runnable callback) {
    this.callback = callback;
  }




}
