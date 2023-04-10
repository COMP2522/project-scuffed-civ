package org.bcit.com2522.project.scuffed.uicomponents;

import static org.bcit.com2522.project.scuffed.client.Window.UIImages;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PFont;
import processing.core.PImage;


/**
 * A class representing a graphical button that can be clicked by the user.
 *
 * <p>The Button class has various properties that can be customized, such as its
 * size, position, text, and background images. It also supports a callback function
 * that can be executed when the button is clicked. Additionally, the button can be
 * disabled to prevent the user from clicking it.</p>
 */
public class Button {
  /**
   * The coordinates of the button's top-left and bottom-right corners.
   */
  int x1, y1, x2, y2;

  /**
   * A Runnable that will be executed when the button is clicked.
   */
  Runnable callback;

  /**
   * A Clickable object that can be used to detect mouse clicks on the button.
   */
  Clickable clickable;

  /**
   * The text that will be displayed on the button.
   */
  String text;

  /**
   * The background image that will be displayed when the button is in its default state.
   */
  PImage background;

  /**
   * The background image that will be displayed when the mouse is hovering over the button.
   */
  PImage hoverBackground;

  /**
   * The background image that will be displayed when the button is clicked.
   */
  PImage clickBackground;

  /**
   * The background image that will be displayed when the button is disabled.
   */
  PImage disabledBackground;

  /**
   * The Window that the button belongs to.
   */
  Window scene;

  /**
   * The font that will be used to display the button's text.
   */
  PFont font;

  /**
   * The size of the tooltip that will be displayed when the mouse is hovering over the button.
   */
  int tooltipSize;

  /**
   * The text that will be displayed in the tooltip when the mouse is hovering over the button.
   */
  String tooltip;

  /**
   * The font size that will be used to display the button's text.
   */
  int fontSize = 32;

  /**
   * The x offset that will be applied to the button's position.
   */
  int offsetX = 0;

  /**
   * The y offset that will be applied to the button's position.
   */
  int offsetY = 0;

  /**
   * A flag indicating whether the button is clickable or not.
   */
  private boolean isClickable = true;

  /**
   * A flag indicating whether the button is currently active or not.
   */
  private boolean active;

  /**
   * Creates a new Button object with text that cannot be disabled.
   *
   * @param x1              the x-coordinate of the button's top-left corner
   * @param y1              the y-coordinate of the button's top-left corner
   * @param x2              the x-coordinate of the button's bottom-right corner
   * @param y2              the y-coordinate of the button's bottom-right corner
   * @param callback        the callback function to be executed when the button is clicked
   * @param text            the text to be displayed on the button
   * @param background      the background image to be displayed when the button is in its default state
   * @param hoverBackground the background image to be displayed when the mouse is hovering over the button
   * @param clickBackground the background image to be displayed when the button is clicked
   * @param scene           the Window object that the button belongs to
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
   * Creates a new Button object without text that cannot be disabled.
   *
   * @param x1              the x-coordinate of the button's top-left corner
   * @param y1              the y-coordinate of the button's top-left corner
   * @param x2              the x-coordinate of the button's bottom-right corner
   * @param y2              the y-coordinate of the button's bottom-right corner
   * @param callback        the callback function to be executed when the button is clicked
   * @param background      the background image to be displayed when the button is in its default state
   * @param hoverBackground the background image to be displayed when the mouse is hovering over the button
   * @param clickBackground the background image to be displayed when the button is clicked
   * @param scene           the Window object that the button belongs to
   */
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
   * Creates a new Button object with text that can be disabled.
   *
   * @param x1                 the x-coordinate of the button's top-left corner
   * @param y1                 the y-coordinate of the button's top-left corner
   * @param x2                 the x-coordinate of the button's bottom-right corner
   * @param y2                 the y-coordinate of the button's bottom-right corner
   * @param callback           the callback function to be executed when the button is clicked
   * @param text               the text to be displayed on the button
   * @param background         the background image to be displayed when the button is in its default state
   * @param hoverBackground    the background image to be displayed when the mouse is hovering over the button
   * @param clickBackground    the background image to be displayed when the button is clicked
   * @param scene              the Window object that the button belongs to
   * @param disabledBackground the background image to be displayed when the button is disabled
   * @param isClickable        a flag indicating whether the button is clickable or not
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
   * Creates a new Button object without text that can be disabled.
   *
   * @param x1                 the x-coordinate of the button's top-left corner
   * @param y1                 the y-coordinate of the button's top-left corner
   * @param x2                 the x-coordinate of the button's bottom-right corner
   * @param y2                 the y-coordinate of the button's bottom-right corner
   * @param callback           the callback function to be executed when the button is clicked
   * @param background         the background image to be displayed when the button is in its default state
   * @param hoverBackground    the background image to be displayed when the mouse is hovering over the button
   * @param clickBackground    the background image to be displayed when the button is clicked
   * @param scene              the Window object that the button belongs to
   * @param disabledBackground the background image to be displayed when the button is disabled
   * @param isClickable        a flag indicating whether the button is clickable or not
   */
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
   * Creates a new Button object with text that cannot be disabled and uses default images.
   *
   * @param x1              the x-coordinate of the button's top-left corner
   * @param y1              the y-coordinate of the button's top-left corner
   * @param x2              the x-coordinate of the button's bottom-right corner
   * @param y2              the y-coordinate of the button's bottom-right corner
   * @param callback        the callback function to be executed when the button is clicked
   * @param text            the text to be displayed on the button
   * @param scene           the Window object that the button belongs to
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
   * Creates a new Button object without text that cannot be disabled and uses default images.
   *
   * @param x1       the x-coordinate of the button's top-left corner
   * @param y1       the y-coordinate of the button's top-left corner
   * @param x2       the x-coordinate of the button's bottom-right corner
   * @param y2       the y-coordinate of the button's bottom-right corner
   * @param callback the callback function to be executed when the button is clicked
   * @param scene    the Window object that the button belongs to
   * @param offsetX  the x-coordinate offset of the text
   * @param offsetY  the y-coordinate offset of the text
   * @param textSize the size of the text
   *
   */
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
   * Creates a new Button object with text that can be disabled and uses default images.
   *
   * @param x1              the x-coordinate of the button's top-left corner
   * @param y1              the y-coordinate of the button's top-left corner
   * @param x2              the x-coordinate of the button's bottom-right corner
   * @param y2              the y-coordinate of the button's bottom-right corner
   * @param callback        the callback function to be executed when the button is clicked
   * @param text            the text to be displayed on the button
   * @param scene           the Window object that the button belongs to
   * @param disabledBackground the background image to be displayed when the button is disabled
   * @param isClickable        a flag indicating whether the button is clickable or not
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
   * Creates a new Button object with text that can be disabled and uses default images.
   *
   * @param x1              the x-coordinate of the button's top-left corner
   * @param y1              the y-coordinate of the button's top-left corner
   * @param x2              the x-coordinate of the button's bottom-right corner
   * @param y2              the y-coordinate of the button's bottom-right corner
   * @param callback        the callback function to be executed when the button is clicked
   * @param text            the text to be displayed on the button
   * @param scene           the Window object that the button belongs to
   * @param disabledBackground the background image to be displayed when the button is disabled
   * @param isClickable        a flag indicating whether the button is clickable or not
   * @param disabledBackground the background image to be displayed when the button is disabled
   * @param isClickable        a flag indicating whether the button is clickable or not
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
    this.scene = scene;
    this.disabledBackground = disabledBackground;
    this.isClickable = false;

  }

  /**
   * Initializes a new button with a tooltip.
   *
   * @param x1 the x-coordinate of the top-left corner of the button
   * @param y1 the y-coordinate of the top-left corner of the button
   * @param x2 the x-coordinate of the bottom-right corner of the button
   * @param y2 the y-coordinate of the bottom-right corner of the button
   * @param callback the function to be executed when the button is clicked
   * @param text the text to be displayed on the button
   * @param background the background image of the button
   * @param hoverBackground the background image of the button when it is being hovered over
   * @param clickBackground the background image of the button when it is being clicked
   * @param scene the window in which the button will be displayed
   * @param disabledBackground the background image of the button when it is disabled
   * @param isClickable a boolean value indicating whether the button is clickable
   * @param textSize the size of the text on the button
   * @param tooltip the text to be displayed as a tooltip when the button is hovered over
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
   * @param isClickable Enable or disable the button
   */
  public void setClickable(boolean isClickable) {
    this.isClickable = isClickable;
  }

  /**
   * Draws the button.
   *
   * @param scene scene
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
   * Click functionality.
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
   * Changes the position of the button by updating its bounds.
   * @param x1 the x-coordinate of the upper-left corner of the button
   * @param y1 the y-coordinate of the upper-left corner of the button
   * @param x2 the x-coordinate of the lower-right corner of the button
   * @param y2 the y-coordinate of the lower-right corner of the button
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
   * @param text string to be displayed on the button
   */
  public void changeText(String text) {
    this.text = text;
  }

  /**
   * Change background.
   *
   * @param background background image
   */
  public void changeBackground(PImage background) {
    this.background = background;
  }

  /**
   * Change hover background.
   *
   * @param hoverBackground hover image
   */
  public void changeHoverBackground(PImage hoverBackground) {
    this.hoverBackground = hoverBackground;
  }

  /**
   * Change click background.
   *
   * @param clickBackground on click background image
   */
  public void changeClickBackground(PImage clickBackground) {
    this.clickBackground = clickBackground;
  }

  /**
   * Checks if the button is clicked.
   *
   * @param x Mouse x
   * @param y Mouse y
   * @return is clicked
   */
  public boolean isClicked(int x, int y) {
    if (this.clickable == null || !isClickable) {
      System.out.println("Button is not clickable");
      return false;
    }
    return clickable.isHovered(x, y);
  }

  /**
   * Deletes button
   */
  public void delete() {
    this.callback = null;
    this.text = null;
    this.background = null;
    this.hoverBackground = null;
    this.clickBackground = null;
    scene.removeClickable(clickable);
  }

  /**
   * Sets active.
   *
   * @param toggle the toggle
   */
  public void setActive(boolean toggle) {
  }
}
