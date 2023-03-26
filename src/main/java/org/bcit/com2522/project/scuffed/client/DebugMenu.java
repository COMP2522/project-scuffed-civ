package org.bcit.com2522.project.scuffed.client;

public class DebugMenu {

  Window scene;

  public DebugMenu (Window scene) {
    this.scene = scene;
  }

  public void draw() {
    scene.fill(255);
    scene.rect(0, 0, 150, 100);
    scene.fill(0);
    scene.textSize(15);
    scene.text(("mX=" + scene.mouseX + " mY=" + scene.mouseY), 5, 15);
    scene.text(("currentPlayer=" + scene.getCurrentPlayer()), 5, 30);
  }

}
