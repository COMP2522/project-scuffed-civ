package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

import java.util.ArrayList;

public class ButtonManager implements Drawable{
  ArrayList<Button> buttons = new ArrayList<Button>();

  public ButtonManager() {

  }
  public void add(Button button) {
    buttons.add(button);
  }
  public void remove(Button button) {
    buttons.remove(button);
  }
  public void draw(Window scene) {
    for (Button button : buttons) {
      button.draw(scene);
    }
  }

  public void wipe() {
    buttons.clear();
  }
}
