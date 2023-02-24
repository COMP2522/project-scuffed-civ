package org.bcit.com2522.project.scuffed.client;

import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

public abstract class Player {
  private ArrayList<Entity> entities;
  private int resources;


  public Player () {

  }

  public boolean hasLost(){
    return entities.size() <= 0;
  }
}
