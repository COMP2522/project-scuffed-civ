package org.bcit.com2522.project.scuffed.ui;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PImage;

import java.util.ArrayList;

/** Manages Graphic Class
 *
 */
public class GraphicManager implements Drawable{
  ArrayList<Graphic> graphicArrayList = new ArrayList<Graphic>();

  public GraphicManager() {

  }

  public void addGraphic(Graphic graphic) {
    graphicArrayList.add(graphic);
  }

  public void addGraphic(float x, float y, PImage texture) {
    Graphic graphic = new Graphic(x, y, texture);
    graphicArrayList.add(graphic);
  }

  public void removeGraphic(Graphic graphic) {
    graphicArrayList.remove(graphic);
  }

  public void draw(Window scene) {
    for (Graphic graphic : graphicArrayList) {
      graphic.draw(scene);
    }
  }

  public void wipeGraphics() {
    graphicArrayList.clear();
  }
}
