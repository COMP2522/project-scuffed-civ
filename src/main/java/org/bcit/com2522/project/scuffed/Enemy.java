package org.bcit.com2522.labs.lab03;

import processing.core.PVector;

import java.awt.*;

public class Enemy extends Sprite {

  public Enemy (PVector position, PVector direction, float size, float speed, Window window) {
    super(position, direction, size, speed, new Color(255, 105, 97), window);

  }
}
