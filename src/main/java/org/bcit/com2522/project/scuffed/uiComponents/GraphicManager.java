package org.bcit.com2522.project.scuffed.uiComponents;

import org.bcit.com2522.project.scuffed.client.Window;
import processing.core.PImage;

import java.util.ArrayList;

/**
 * Manages Graphic Class
 */
public class GraphicManager {
  /**
   * The Graphic array list.
   */
  ArrayList<Graphic> graphicArrayList = new ArrayList<Graphic>();

  /**
   * The Scene.
   */
  Window scene;

  /**
   * Instantiates a new Graphic manager.
   *
   * @param scene the scene
   */
  public GraphicManager(Window scene) {
    this.scene = scene;
  }

  /**
   * Add graphic.
   *
   * @param graphic the graphic
   */
  public void addGraphic(Graphic graphic) {
    graphicArrayList.add(graphic);
  }

  /**
   * Add graphic.
   *
   * @param x       the x
   * @param y       the y
   * @param texture the texture
   */
  public void addGraphic(float x, float y, PImage texture) {
    Graphic graphic = new Graphic(x, y, texture, scene);
    graphicArrayList.add(graphic);
  }

  /**
   * Remove graphic.
   *
   * @param graphic the graphic
   */
  public void removeGraphic(Graphic graphic) {
    graphicArrayList.remove(graphic);
  }

  /**
   * Draw graphics.
   */
  public void drawGraphics() {
    for (Graphic graphic : graphicArrayList) {
      graphic.draw(scene);
    }
  }

  /**
   * Wipe graphics.
   */
  public void wipeGraphics() {
    graphicArrayList.clear();
  }
}
