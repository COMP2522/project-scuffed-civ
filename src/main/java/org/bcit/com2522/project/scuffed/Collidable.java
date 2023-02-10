package org.bcit.com2522.project.scuffed;

import processing.core.PVector;

import static processing.core.PApplet.*;
import static processing.core.PApplet.sin;

public abstract class Collidable {
  PVector position;
  float size;
  Window scene;

  boolean isWall;

  float speed;

  PVector direction;

  public Collidable (PVector position, float size, Window scene, boolean isWall) {
    this.position = position;
    this.size = size;
    this.scene = scene;
    this.isWall = isWall;
    speed = 0;
    direction = new PVector(0.0f,0.0f);
  }

  public Collidable (PVector position, float size, Window scene, boolean isWall, float speed, PVector direction) {
    this.position = position;
    this.size = size;
    this.scene = scene;
    this.isWall = isWall;
    this.speed = speed;
    this.direction = direction;
  }
}
