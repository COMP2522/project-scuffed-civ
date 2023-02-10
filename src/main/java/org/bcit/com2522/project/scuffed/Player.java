package org.bcit.com2522.project.scuffed;

import processing.core.PVector;

import java.awt.*;

public class Player extends Sprite {


  public Player (PVector position, PVector direction, float size, float speed, Window window) {
    super(position, direction, size, speed, new Color(119, 221, 119), window);

  }
}
