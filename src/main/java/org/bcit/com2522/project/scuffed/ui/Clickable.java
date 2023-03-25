package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

public abstract class Clickable {
  protected int x1, y1, x2, y2;
  protected Runnable callback;

  protected Runnable callbackOnHover;

  public Clickable(int x1, int y1, int x2, int y2, Runnable callback) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.callback = callback;
  }

  public Clickable(int x1, int y1, int x2, int y2, Runnable callback, Runnable callbackOnHover) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.callback = callback;
    this.callbackOnHover = callbackOnHover;
  }

  public boolean isHovered(int mouseX, int mouseY) {
    return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
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

  public void offsetX(int offset){
    this.x1 += offset;
    this.x2 += offset;
  }

  public void offsetY(int offset){
    this.y1 += offset;
    this.y2 += offset;
  }

  public abstract void draw(Window scene);

}
