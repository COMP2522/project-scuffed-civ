package org.bcit.com2522.project.scuffed.client;

import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

public class Player { //should have a player manager (later)
  //private ArrayList<Entity> entities;
  private int resources;

  //private int playerNum;
  Window scene;

  Map map;


  public Player (Window scene) {
    this.scene = scene;
    //this.playerNum = playerNum;
    //entities = new ArrayList<Entity>();
  }

  public void addEntity(Position position) {
    //entities.add(new Entity(scene, position, this));

  }

//  public int getPlayerNum() {
//    return playerNum;
//  }

  public void draw() {
//    if(entities != null) {
//      for (Entity entity : entities) {
//        entity.draw();
//      }
//    }
  }

  public boolean hasLost(){
    //return entities.size() == 0;
    return false;
  }
}
