package org.bcit.com2522.project.scuffed;

import processing.core.PVector;

import java.awt.*;

public class Sprite extends Collidable implements Comparable {
  private PVector position;
  private PVector direction;
  private float size;
  private float speed;
  private Color color;
  private Window window;


  public Sprite(PVector position, PVector direction, float size, float speed, Color color, Window window) {
    super(position, size, window, false);
    this.position = position;
    this.direction = direction;
    this.size = size;
    this.speed = speed;
    this.window = window;
    this.color = color;
  }

  public void bounce() {
    if (this.position.x <= 0 ||
        this.position.x >= window.width ||
        this.position.y <= 0 ||
        this.position.y >= window.height) {
      this.direction.rotate(window.HALF_PI);
    }
  }

  public PVector getDirection() {
    return direction.copy();
  }

  public PVector getPosition() {
    return position.copy();
  }

  public void update() {
    this.bounce();
    this.position = this.getPosition().add(this.direction.copy().mult(speed));
  }

  public float getSize() {
    return size;
  }
  public static boolean collided(Sprite a, Sprite b) {
    float distance = PVector.dist(a.getPosition(), b.getPosition());
    if (distance <= (a.getSize() + b.getSize())) {
      return true;
    }
    return false;
  }

  public void draw() {
    window.pushStyle();
    window.fill(this.color.getRed(), this.color.getGreen(), this.color.getBlue());
    window.ellipse(this.position.x, this.position.y, size, size);
    window.popStyle();
  }


  public void move() {
    
  }

  public void setDirection(PVector direction) {
    this.direction = direction;
  }

  public boolean equals(Object o) {
    if (o instanceof Sprite) {
      Sprite s = (Sprite) o;
      return s.size == this.size;
    }
    return false;
  }

  public int compareTo(Object o) {
    if (o instanceof Sprite) {
      Sprite s = (Sprite) o;
      return (int) (this.size - s.size);
    }
    return 0;
  }

}
