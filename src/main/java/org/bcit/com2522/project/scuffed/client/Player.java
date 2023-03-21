package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.awt.*;

public class Player { //gamestate is the player manager
  private int resources;
  private int playerNum;
  boolean hasLost;
  private Color color;

  public Player (int playerNum) {
    this.playerNum = playerNum;
    hasLost = false;
  }

  /**
   * Creates a player from a JSONObject
   *
   * @param JSONObject playerObject
   * @return Player object from the JSON
   */
  public static Player fromJSONObject(JSONObject playerObject) {
    Player player = new Player((int)(long) playerObject.get("playerNum"));
    player.resources = (int)(long) playerObject.get("resources");
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

  public void draw(Window scene) {
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

  @Override
  public String toString() {
    return "Player{" +
            "resources=" + resources +
            ", playerNum=" + playerNum +
            '}';
  }

  public void lose() {
    hasLost = true;
  }

  public boolean getHasLost() {
    return hasLost;
  }
  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public int getID() {
    return playerNum; //TODO: change to unique int or string that represents the player
  }
}
