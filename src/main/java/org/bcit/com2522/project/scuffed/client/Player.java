package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;
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

  /**
   * Creates a player from a JSONObject w/o setting the map or scene
   *
   * @param playerObject
   * @return
   */
  public static Player fromJSONObject(JSONObject playerObject) {
    Player player = new Player(null, (int) (long) playerObject.get("playerNum"));
    player.resources = (int)(long) playerObject.get("resources");
    return player;
  }

    /**
     * Creates a player from a JSONObject and sets the map and scene
     *
     * @param playerObject
     * @param map
     * @param scene
     * @return
     */
  public static Player fromJSONObject(JSONObject playerObject, Window scene, Map map) {
    Player player = new Player(scene, (int)(long) playerObject.get("playerNum"));
    player.resources = (int)(long) playerObject.get("resources");
    player.scene = scene;
    player.map = map;
    return player;
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

  public JSONObject toJSONObject() {
    JSONObject player = new JSONObject();
    player.put("playerNum", playerNum);
    player.put("resources", resources);
    return player;
  }
}
