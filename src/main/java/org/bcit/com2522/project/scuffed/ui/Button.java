package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Clickable;
import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PImage;

import static org.bcit.com2522.project.scuffed.client.Window.UIImages;

public class Button {
  int x1, y1, x2, y2;
  Runnable callback;
  Clickable clickable;
  String text;
  PImage background;
  PImage hoverBackground;
  PImage clickBackground;

  PImage disabledBackground;
  Window scene;

  int fontSize = 32;

  int offsetX = 0;
  int offsetY = 0;

  boolean isClickable = true;

  // Standard Button, has text and can not be disabled
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

  // Button with no text
  public Button(int x1, int y1, int x2, int y2, Runnable callback, PImage background, PImage hoverBackground, PImage clickBackground, Window scene) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.callback = callback;
    this.text = "";
    this.background = background;
    this.hoverBackground = hoverBackground;
    this.clickBackground = clickBackground;


    this.clickable = new Clickable(x1, y1, x2, y2, callback, callback);
    scene.addClickable(this.clickable);
  }

  // Button that can be disabled
  public Button(int x1, int y1, int x2, int y2, Runnable callback, String text, PImage background, PImage hoverBackground, PImage clickBackground, Window scene, PImage disabledBackground, boolean isClickable) {
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
    this.disabledBackground = disabledBackground;
    this.isClickable = isClickable;
    scene.addClickable(this.clickable);
  }

  // Button that can be disabled and has no text
  public Button(int x1, int y1, int x2, int y2, Runnable callback, PImage background, PImage hoverBackground, PImage clickBackground, Window scene, PImage disabledBackground, boolean isClickable) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.callback = callback;
    this.text = "";
    this.background = background;
    this.hoverBackground = hoverBackground;
    this.clickBackground = clickBackground;
    this.clickable = new Clickable(x1, y1, x2, y2, callback, callback);
    this.disabledBackground = disabledBackground;
    this.isClickable = isClickable;
    scene.addClickable(this.clickable);
  }

  // Button that uses default images
  public Button(int x1, int y1, int x2, int y2, Runnable callback, String text, Window scene) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.callback = callback;
    this.text = text;
    this.background = UIImages.get("buttonBackground");
    this.hoverBackground = UIImages.get("buttonHoverBackground");
    this.clickBackground = UIImages.get("buttonClickBackground");
    this.clickable = new Clickable(x1, y1, x2, y2, callback, callback);
    scene.addClickable(this.clickable);
  }

  // Versions of button but with text size added
  public Button(int x1, int y1, int x2, int y2, Runnable callback, String text, PImage background, PImage hoverBackground, PImage clickBackground, Window scene, PImage disabledBackground, boolean isClickable, int textSize, int offsetX, int offsetY) {
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
    this.disabledBackground = disabledBackground;
    this.isClickable = isClickable;
    this.fontSize = textSize;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    scene.addClickable(this.clickable);
  }

  public Button(int x1, int y1, int x2, int y2, Runnable callback, String text, PImage background, PImage hoverBackground, PImage clickBackground, Window scene, PImage disabledBackground, boolean isClickable, int textSize) {
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
    this.disabledBackground = disabledBackground;
    this.isClickable = isClickable;
    this.fontSize = textSize;
    scene.addClickable(this.clickable);
  }

  public void setTextSize(int textSize) {
    this.fontSize = textSize;
  }

  public void setClickable(boolean isClickable) {
    this.isClickable = isClickable;
  }

  public void draw(Window scene) {
    if (!isClickable && disabledBackground != null) {
      scene.image(disabledBackground, x1, y1, x2 - x1, y2 - y1);

    } else if (clickable.isHovered(scene.mouseX, scene.mouseY) && scene.mousePressed) {
      scene.image(clickBackground, x1, y1, x2 - x1, y2 - y1);
    } else if (clickable.isHovered(scene.mouseX, scene.mouseY)) {
      scene.image(hoverBackground, x1, y1, x2 - x1, y2 - y1);
    } else {
      scene.image(background, x1, y1, x2 - x1, y2 - y1);
    }
    if (text != null) {
      scene.textSize(fontSize);
      scene.text(text, x1 + 10 + offsetX, y1 + fontSize + offsetY);
      scene.textSize(32);
    }

  }

  public void click() {
    if (!isClickable) {
      return;
    }
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
