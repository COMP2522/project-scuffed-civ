package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.awt.*;
import java.util.Random;

public class Player { //gamestate is the player manager
  private int resources;
  private int playerNum;
  boolean hasLost;
  private Color color;
  private boolean isAi;

  public Player (int playerNum) {
    this.playerNum = playerNum;
    Random random = new Random();
    color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255) );
    hasLost = false;
    resources = 2;


    if (playerNum == 1) {
      isAi = true;
    }
    else {
      isAi = false;
    }
  }

  public Player (int playerNum, Color color) {
    this.playerNum = playerNum;
    this.color = color;
    resources = 1;
    hasLost = false;
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
    scene.fill(color.getRGB());
    scene.text("Player " + (playerNum + 1), 700, 150); //print current player
    scene.text("Resources " + (resources), 700, 300); //print player resources
    scene.fill(200);
    scene.rect(700, 550, 400, 200);
    scene.fill(000);
    scene.text("end turn", 725, 675); //print end turn box
  }

  public boolean hasLost(){
    //return entities.size() == 0;
    return false;
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
    player.color = Color.decode((String) playerObject.get("color"));
    return player;
  }

  public JSONObject toJSONObject() {
    JSONObject player = new JSONObject();
    player.put("playerNum", playerNum);
    player.put("resources", resources);
    player.put("color", "#" + Integer.toHexString(color.getRGB()).substring(2));
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

    public boolean isAI() {
      return isAi;
    }

  public void increaseResources(int gained) {
    resources += gained;
  }

  public void spendResources(int lost) {
    resources -= lost;
  }

  public int getResources() {
    return resources;
  }
}
