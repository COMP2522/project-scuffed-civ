package org.bcit.com2522.project.scuffed;

import processing.core.PVector;

public class Wall extends Collidable {

  public Wall (PVector position, float size, Window scene) {
    super(position, size, scene, true);



  }
}
