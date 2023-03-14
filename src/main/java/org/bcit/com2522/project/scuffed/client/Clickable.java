package org.bcit.com2522.project.scuffed.client;

import processing.core.PApplet;

public class Clickable {
  private PApplet p;
  private float x, y, w, h;
  private boolean clicked;
  private boolean hovering;
  private Runnable onClick;
  private Runnable onHover;

  public Clickable(PApplet p, float x, float y, float w, float h, Runnable onClick) {
    this.p = p;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.onClick = onClick;
    this.onHover = null;
  }

  public Clickable(PApplet p, float x, float y, float w, float h, Runnable onClick, Runnable onHover) {
    this.p = p;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.onClick = onClick;
    this.onHover = onHover;
  }


  public void checkClicked() {
    if (p.mousePressed && p.mouseButton == PApplet.LEFT && p.mouseX >= x && p.mouseX <= x + w && p.mouseY >= y && p.mouseY <= y + h) {
      clicked = true;
      onClick.run();
    } else {
      clicked = false;
    }
  }

  public boolean isClicked() {
    return clicked;
  }

  public void checkHover() {
    if (p.mouseX >= x && p.mouseX <= x + w && p.mouseY >= y && p.mouseY <= y + h) {
      if (!hovering) {
        hovering = true;
        if (onHover != null) {
          onHover.run();
        }
      }
    } else {
      hovering = false;
    }
  }

  public void moveBounds(float x, float y, float w, float h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }

  public void delete() {
    p = null;
    onClick = null;
    onHover = null;
  }
}
