package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PImage;

import static org.bcit.com2522.project.scuffed.client.Window.PImages;

public class Button extends Clickable {

  Runnable callback;
  String text;
  PImage background;
  PImage hoverBackground;
  PImage clickBackground;

  public Button(int x1, int y1, int x2, int y2, Runnable callback, String text, PImage background, PImage hoverBackground, PImage clickBackground) {
    super(x1, y1, x2, y2, callback);
    this.text = text;
    this.background = background;
    this.hoverBackground = hoverBackground;
    this.clickBackground = clickBackground;
  }

  public Button(int x1, int y1, int x2, int y2, Runnable callback, String text) {
    super(x1, y1, x2, y2, callback);
    this.text = text;
    this.background = PImages.get("buttonBackground");
    this.hoverBackground = PImages.get("buttonHoverBackground");
    this.clickBackground = PImages.get("buttonClickBackground");
  }

  @Override
  public void draw(Window scene) {
    if (isHovered(scene.mouseX, scene.mouseY) && scene.mousePressed) {
      scene.image(clickBackground, x1, y1, x2 - x1, y2 - y1);
    } else if (isHovered(scene.mouseX, scene.mouseY)) {
      scene.image(hoverBackground, x1, y1, x2 - x1, y2 - y1);
    } else {
      scene.image(background, x1, y1, x2 - x1, y2 - y1);
    }
    if (text != null) {
      scene.textSize(32);
      scene.text(text, x1 + 10, y1 + 32);
    }
  }

  public void changeCallback(Runnable callback) {
    this.callback = callback;
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

  public void delete(Window scene) {
    this.callback = null;
    this.text = null;
    this.background = null;
    this.hoverBackground = null;
    this.clickBackground = null;
  }
}
