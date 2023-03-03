package org.bcit.com2522.project.scuffed.client;

import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

public class Player { //should have a player manager (later)
  //private ArrayList<Entity> entities;
  private int resources;

  private int playerNum;
  Window scene;

  Map map;


  public Player (Window scene, int playerNum) {
    this.scene = scene;
    this.playerNum = playerNum;
    //this.playerNum = playerNum;
    //entities = new ArrayList<Entity>();
  }

  public void addEntity(Position position) {
    //entities.add(new Entity(scene, position, this));

  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Player) {
      Player temp = (Player) obj;
      return temp.getPlayerNum() == this.getPlayerNum();
    }
    return false;
  }

  public int getPlayerNum() {
    return playerNum;
  }

  public void draw() {
    scene.fill(000);
    scene.text("Player " + (playerNum + 1), 700, 150); //print current player

    scene.fill(200);
    scene.rect(700, 550, 400, 200);
    scene.fill(000);
    scene.text("end turn", 725, 675); //print end turn box
  }

  public boolean hasLost(){
    //return entities.size() == 0;
    return false;
  }
}
