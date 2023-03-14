package org.bcit.com2522.project.scuffed.client;

import processing.core.PApplet;

/**
 * Creates Clickable object with callback.
 * @author Emma MB
 * @version 1.1
 */
public class Clickable {
  private PApplet p;
  private float x, y, w, h;
  private boolean clicked;
  private Runnable callback;

  public Clickable(PApplet p, float x, float y, float w, float h, Runnable callback) {
    this.p = p;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.callback = callback;
  }

  public void display() {
    p.fill(255);
    p.rect(x, y, w, h);
  }

  public void checkClicked() {
    if (p.mousePressed && p.mouseButton == PApplet.LEFT && p.mouseX >= x && p.mouseX <= x + w && p.mouseY >= y && p.mouseY <= y + h) {
      clicked = true;
      callback.run();
    } else {
      clicked = false;
    }
  }

  public boolean isClicked() {
    return clicked;
  }

  public void moveBounds(float x, float y, float w, float h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }

  public void delete() {
    p = null;
    callback = null;
  }
}