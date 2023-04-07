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

  private int xShift;
  private int yShift;


  public Player (int playerNum) {
    this.playerNum = playerNum;
    Random random = new Random();
    color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255) );
    hasLost = false;
    resources = 2;


    if (playerNum == 1) { //TODO: change this logic later
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

    public Player(Player player) {
      this.resources = player.resources;
      this.playerNum = player.playerNum;
      this.hasLost = player.hasLost;
      this.color = player.color;
      this.isAi = player.isAi;
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
    player.isAi = (boolean) playerObject.get("isAi");
    return player;
  }

  public JSONObject toJSONObject() {
    JSONObject player = new JSONObject();
    player.put("playerNum", playerNum);
    player.put("resources", resources);
    player.put("isAi", isAi);
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

  public void setShift(int x, int y) {
    xShift = x;
    yShift = y;
  }

    public int getYShift() {
      return yShift;
    }
    public int getXShift() {
      return xShift;
    }
}
