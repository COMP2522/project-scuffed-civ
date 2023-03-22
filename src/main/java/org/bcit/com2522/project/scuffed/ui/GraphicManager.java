package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PImage;

import java.util.ArrayList;

/** Manages Graphic Class
 *
 */
public class GraphicManager {
  ArrayList<Graphic> graphicArrayList;

  Window scene;

  public GraphicManager(Window scene) {
    this.scene = scene;
  }

  public void addGraphic(Graphic graphic) {
    graphicArrayList.add(graphic);
  }

  public void addGraphic(float x, float y, PImage texture) {
    Graphic graphic = new Graphic(x, y, texture, scene);
  }

  public void removeGraphic(Graphic graphic) {
    graphicArrayList.remove(graphic);
  }

  public void drawGraphics() {
    for (Graphic graphic : graphicArrayList) {
      graphic.draw();
    }
  }

  public void wipeGraphics() {
    graphicArrayList.clear();
  }
}
