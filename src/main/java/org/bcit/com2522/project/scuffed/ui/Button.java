package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Clickable;
import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PFont;
import processing.core.PImage;

import static org.bcit.com2522.project.scuffed.client.Window.UIImages;

/**
 * The type Button.
 */
public class Button {
  /**
   * The X 1.
   */
  int x1, /**
   * The Y 1.
   */
  y1, /**
   * The X 2.
   */
  x2, /**
   * The Y 2.
   */
  y2;
  /**
   * The Callback.
   */
  Runnable callback;
  /**
   * The Clickable.
   */
  Clickable clickable;
  /**
   * The Text.
   */
  String text;
  /**
   * The Background.
   */
  PImage background;
  /**
   * The Hover background.
   */
  PImage hoverBackground;
  /**
   * The Click background.
   */
  PImage clickBackground;

  /**
   * The Disabled background.
   */
  PImage disabledBackground;
  /**
   * The Scene.
   */
  Window scene;


  /**
   * The Font.
   */
  PFont font;

  /**
   * The Tooltip.
   */
  String tooltip;
  /**
   * The Tooltip size.
   */
  int tooltipSize;


  /**
   * The Font size.
   */
  int fontSize = 32;

  /**
   * The Offset x.
   */
  int offsetX = 0;
  /**
   * The Offset y.
   */
  int offsetY = 0;

  /**
   * The Is clickable.
   */
  boolean isClickable = true;
  private boolean active;

  /**
   * Instantiates a new Button.
   *
   * @param x1              the x 1
   * @param y1              the y 1
   * @param x2              the x 2
   * @param y2              the y 2
   * @param callback        the callback
   * @param text            the text
   * @param background      the background
   * @param hoverBackground the hover background
   * @param clickBackground the click background
   * @param scene           the scene
   */
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

  /**
   * Instantiates a new Button.
   *
   * @param x1              the x 1
   * @param y1              the y 1
   * @param x2              the x 2
   * @param y2              the y 2
   * @param callback        the callback
   * @param background      the background
   * @param hoverBackground the hover background
   * @param clickBackground the click background
   * @param scene           the scene
   */
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

  /**
   * Instantiates a new Button.
   *
   * @param x1                 the x 1
   * @param y1                 the y 1
   * @param x2                 the x 2
   * @param y2                 the y 2
   * @param callback           the callback
   * @param text               the text
   * @param background         the background
   * @param hoverBackground    the hover background
   * @param clickBackground    the click background
   * @param scene              the scene
   * @param disabledBackground the disabled background
   * @param isClickable        the is clickable
   */
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

  /**
   * Instantiates a new Button.
   *
   * @param x1                 the x 1
   * @param y1                 the y 1
   * @param x2                 the x 2
   * @param y2                 the y 2
   * @param callback           the callback
   * @param background         the background
   * @param hoverBackground    the hover background
   * @param clickBackground    the click background
   * @param scene              the scene
   * @param disabledBackground the disabled background
   * @param isClickable        the is clickable
   */
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

  /**
   * Instantiates a new Button.
   *
   * @param x1       the x 1
   * @param y1       the y 1
   * @param x2       the x 2
   * @param y2       the y 2
   * @param callback the callback
   * @param text     the text
   * @param scene    the scene
   */
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

  /**
   * Instantiates a new Button.
   *
   * @param x1                 the x 1
   * @param y1                 the y 1
   * @param x2                 the x 2
   * @param y2                 the y 2
   * @param callback           the callback
   * @param text               the text
   * @param background         the background
   * @param hoverBackground    the hover background
   * @param clickBackground    the click background
   * @param scene              the scene
   * @param disabledBackground the disabled background
   * @param isClickable        the is clickable
   * @param textSize           the text size
   * @param offsetX            the offset x
   * @param offsetY            the offset y
   */
// Versions of button but with text size added
  public Button(int x1, int y1, int x2, int y2, Runnable callback, String text, PImage background,
                PImage hoverBackground, PImage clickBackground, Window scene,
                PImage disabledBackground, boolean isClickable, int textSize,
                int offsetX, int offsetY) {
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

  /**
   * Instantiates a new Button.
   *
   * @param x1                 the x 1
   * @param y1                 the y 1
   * @param x2                 the x 2
   * @param y2                 the y 2
   * @param callback           the callback
   * @param text               the text
   * @param background         the background
   * @param hoverBackground    the hover background
   * @param clickBackground    the click background
   * @param scene              the scene
   * @param disabledBackground the disabled background
   * @param isClickable        the is clickable
   * @param textSize           the text size
   * @param offsetX            the offset x
   * @param offsetY            the offset y
   * @param font               the font
   */
// Button but like you can change the font lol
  public Button(int x1, int y1, int x2, int y2, Runnable callback, String text, PImage background, PImage hoverBackground, PImage clickBackground, Window scene, PImage disabledBackground, boolean isClickable, int textSize, int offsetX, int offsetY, PFont font) {
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
    this.font = font;
    scene.addClickable(this.clickable);
  }

  /**
   * Instantiates a new Button.
   *
   * @param x1                 the x 1
   * @param y1                 the y 1
   * @param x2                 the x 2
   * @param y2                 the y 2
   * @param callback           the callback
   * @param text               the text
   * @param background         the background
   * @param hoverBackground    the hover background
   * @param clickBackground    the click background
   * @param scene              the scene
   * @param disabledBackground the disabled background
   * @param isClickable        the is clickable
   * @param textSize           the text size
   */
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

  /**
   * Instantiates a new Button.
   *
   * @param x1                 the x 1
   * @param y1                 the y 1
   * @param x2                 the x 2
   * @param y2                 the y 2
   * @param scene              the scene
   * @param disabledBackground the disabled background
   */
// Non Functional Button
  public Button(int x1, int y1, int x2, int y2, Window scene, PImage disabledBackground) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.disabledBackground = disabledBackground;
    this.isClickable = false;

  }

  /**
   * Instantiates a new Button.
   *
   * @param x1                 the x 1
   * @param y1                 the y 1
   * @param x2                 the x 2
   * @param y2                 the y 2
   * @param callback           the callback
   * @param text               the text
   * @param background         the background
   * @param hoverBackground    the hover background
   * @param clickBackground    the click background
   * @param scene              the scene
   * @param disabledBackground the disabled background
   * @param isClickable        the is clickable
   * @param textSize           the text size
   * @param tooltip            the tooltip
   */
  public Button(int x1, int y1, int x2, int y2, Runnable callback, String text, PImage background, PImage hoverBackground, PImage clickBackground, Window scene, PImage disabledBackground, boolean isClickable, int textSize, String tooltip) {
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
    this.tooltip = tooltip;
    scene.addClickable(this.clickable);
  }

  /**
   * Sets text size.
   *
   * @param textSize the text size
   */
  public void setTextSize(int textSize) {
    this.fontSize = textSize;
  }

  /**
   * Sets clickable.
   *
   * @param isClickable the is clickable
   */
  public void setClickable(boolean isClickable) {
    this.isClickable = isClickable;
  }

  /**
   * Draw.
   *
   * @param scene the scene
   */
  public void draw(Window scene) {
    if ((!isClickable && disabledBackground != null) || this.clickable == null) {
      scene.image(disabledBackground, x1, y1, x2 - x1, y2 - y1);

    } else if (clickable.isHovered(scene.mouseX, scene.mouseY) && scene.mousePressed && isClickable) {
      scene.image(clickBackground, x1, y1, x2 - x1, y2 - y1);
    } else if (clickable.isHovered(scene.mouseX, scene.mouseY) && isClickable) {
      scene.image(hoverBackground, x1, y1, x2 - x1, y2 - y1);
      if (tooltip != null) {
        scene.textSize(tooltipSize);
        float tooltipWidth = scene.textWidth(tooltip);
        float tooltipX = scene.mouseX + tooltipWidth > scene.width ? scene.mouseX - tooltipWidth : scene.mouseX;
        scene.text(tooltip, tooltipX, scene.mouseY);
        scene.textSize(32);
      }
    } else if (isClickable) {
      scene.image(background, x1, y1, x2 - x1, y2 - y1);
    }
    if (text != null) {
      if (font == null) {
        font = scene.createFont("Arial", 32);
      }
      scene.textFont(font);
      scene.textSize(fontSize);
      scene.text(text, x1 + 10 + offsetX, y1 + fontSize + offsetY);
      scene.textSize(32);
    }

  }

  /**
   * Click.
   */
  public void click() {
    if (this.clickable == null || !isClickable) {
      return;
    }
    clickable.click();
  }

  /**
   * Sets tooltip.
   *
   * @param tooltip the tooltip
   */
  public void setTooltip(String tooltip) {
    this.tooltip = tooltip;
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
    clickable.changeBounds(x1, y1, x2, y2);
  }

  /**
   * Change callback.
   *
   * @param callback the callback
   */
  public void changeCallback(Runnable callback) {
    this.callback = callback;
    clickable.changeCallback(callback);
  }

  /**
   * Change text.
   *
   * @param text the text
   */
  public void changeText(String text) {
    this.text = text;
  }

  /**
   * Change background.
   *
   * @param background the background
   */
  public void changeBackground(PImage background) {
    this.background = background;
  }

  /**
   * Change hover background.
   *
   * @param hoverBackground the hover background
   */
  public void changeHoverBackground(PImage hoverBackground) {
    this.hoverBackground = hoverBackground;
  }

  /**
   * Change click background.
   *
   * @param clickBackground the click background
   */
  public void changeClickBackground(PImage clickBackground) {
    this.clickBackground = clickBackground;
  }

  /**
   * Is clicked boolean.
   *
   * @param x the x
   * @param y the y
   * @return the boolean
   */
  public boolean isClicked(int x, int y) {
    if (this.clickable == null || !isClickable) {
      System.out.println("Button is not clickable");
      return false;
    }
    return clickable.isHovered(x, y);
  }

  /**
   * Delete.
   */
  public void delete() {
    this.callback = null;
    this.text = null;
    this.background = null;
    this.hoverBackground = null;
    this.clickBackground = null;
    scene.removeClickable(clickable);
  }
}
