package org.bcit.com2522.project.scuffed.uiComponents;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PApplet;

/**
 * The type Input box.
 */
public class InputBox {
    private int x, y, width, height;
    private String text;
    private String type;
    private Window scene;
    private boolean selected;
    private int minValue, maxValue;

  /**
   * Instantiates a new Input box.
   *
   * @param x        the x
   * @param y        the y
   * @param width    the width
   * @param height   the height
   * @param scene    the scene
   * @param minValue the min value
   * @param maxValue the max value
   */
  public InputBox(int x, int y, int width, int height, Window scene, int minValue, int maxValue) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = "";
        this.scene = scene;
        this.selected = false;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.type = "int";
    }

  /**
   * Instantiates a new Input box.
   *
   * @param x           the x
   * @param y           the y
   * @param width       the width
   * @param height      the height
   * @param scene       the scene
   * @param minValue    the min value
   * @param maxValue    the max value
   * @param defaultText the default text
   */
  public InputBox(int x, int y, int width, int height, Window scene, int minValue, int maxValue, String defaultText) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = defaultText;
        this.scene = scene;
        this.selected = false;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.type = "int";
    }

  /**
   * Instantiates a new Input box.
   *
   * @param x           the x
   * @param y           the y
   * @param width       the width
   * @param height      the height
   * @param scene       the scene
   * @param defaultText the default text
   */
  public InputBox(int x, int y, int width, int height, Window scene, String defaultText) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = defaultText;
        this.scene = scene;
        this.selected = false;
        this.minValue = Integer.MIN_VALUE;
        this.maxValue = Integer.MAX_VALUE;
    }

  /**
   * Instantiates a new Input box.
   *
   * @param x           the x
   * @param y           the y
   * @param width       the width
   * @param height      the height
   * @param scene       the scene
   * @param defaultText the default text
   * @param type        the type
   */
  public InputBox(int x, int y, int width, int height, Window scene, String defaultText, String type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = defaultText;
        this.scene = scene;
        this.selected = false;
        this.minValue = Integer.MIN_VALUE;
        this.maxValue = Integer.MAX_VALUE;
        this.type = type;
    }

  /**
   * Draw.
   */
  public void draw(Window scene) {

        scene.pushStyle();
        scene.stroke(0);
        if(selected) {
            scene.fill(220);
        } else {
            scene.fill(255);
        }
        scene.rect(x, y, width, height);
        scene.textSize(16);
        scene.fill(0);
        scene.text(text, x + 10, y + height - 10);

        scene.popStyle();
    }

  /**
   * Is selected boolean.
   *
   * @return the boolean
   */
  public boolean isSelected() {
        return selected;
    }

  /**
   * Sets selected.
   *
   * @param selected the selected
   */
  public void setSelected(boolean selected) {
        this.selected = selected;
    }

  /**
   * Add character.
   *
   * @param c the c
   */
  public void addCharacter(char c) {
        if(type.equalsIgnoreCase("string")) {
            text += c;
            return;
        }
        if (Character.isDigit(c)) {
            text += c;
        }
    }

  /**
   * Remove character.
   */
  public void removeCharacter() {
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
        }
    }

  /**
   * Gets int value.
   *
   * @return the int value
   */
  public int getIntValue() {
        if(text.equals("")) {
            return 0;
        }
        int value = Integer.parseInt(text);
        return PApplet.constrain(value, minValue, maxValue);
    }

  /**
   * Gets string value.
   *
   * @return the string value
   */
  public String getStringValue() {
        return text;
    }

  /**
   * Is clicked boolean.
   *
   * @param mouseX the mouse x
   * @param mouseY the mouse y
   * @return the boolean
   */
  public boolean isClicked(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}