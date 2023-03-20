package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Clickable;
import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PImage;
import static processing.awt.ShimAWT.loadImage;

public class Button {
  int x1, y1, x2, y2;
  Runnable callback;
  Clickable clickable;
  String text;
  PImage background;
  PImage hoverBackground;
  PImage clickBackground;
  Window scene;


  public Button(int x1, int y1, int x2, int y2, Runnable callback, String text, PImage background, PImage hoverBackground, PImage clickBackground, Window scene) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.callback = callback;
    this.text = text;
    this.background = background;
    this.hoverBackground = hoverBackground;
    this.clickBackground = clickBackground;
    this.clickable = new Clickable(x1, y1, x2, y2, callback, callback);
    scene.addClickable(this.clickable);
  }

  public void draw(Window scene) {
    if (clickable.isHovered(scene.mouseX, scene.mouseY) && scene.mousePressed) {
      scene.image(clickBackground, x1, y1, x2 - x1, y2 - y1);
    } else if (clickable.isHovered(scene.mouseX, scene.mouseY)) {
      scene.image(hoverBackground, x1, y1, x2 - x1, y2 - y1);
    } else {
      scene.image(background, x1, y1, x2 - x1, y2 - y1);
    }
    if (text != null) {
      scene.textSize(32);
      scene.text(text, x1 + 10, y1 + 32);
    }

  }

  public void click() {
    clickable.click();
  }


  public void changeBounds(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    clickable.changeBounds(x1, y1, x2, y2);
  }

  public void changeCallback(Runnable callback) {
    this.callback = callback;
    clickable.changeCallback(callback);
  }

  public void changeText(String text) {
    this.text = text;
  }

  public void changeBackground(PImage background) {
    this.background = background;
  }

  public void changeHoverBackground(PImage hoverBackground) {
    this.hoverBackground = hoverBackground;
  }

  public void changeClickBackground(PImage clickBackground) {
    this.clickBackground = clickBackground;
  }

  public boolean isClicked(int x, int y) {
    return clickable.isHovered(x, y);
  }

  public void delete() {
    this.callback = null;
    this.text = null;
    this.background = null;
    this.hoverBackground = null;
    this.clickBackground = null;
    scene.removeClickable(clickable);
  }


}
