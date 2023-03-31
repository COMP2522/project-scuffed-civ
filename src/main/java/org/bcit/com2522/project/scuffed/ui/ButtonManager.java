package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;

import java.util.ArrayList;

public class ButtonManager{
  public ArrayList<Button> buttons = new ArrayList<Button>();
  Window scene;

  public ButtonManager(Window scene) {
    this.scene = scene;
  }
  public void add(Button button) {
    buttons.add(button);
  }
  public void remove(Button button) {
    buttons.remove(button);
  }
  public void draw() {
    for (Button button : buttons) {
      button.draw(scene);
    }
  }

  public void wipe() {
    buttons.clear();
  }
}
