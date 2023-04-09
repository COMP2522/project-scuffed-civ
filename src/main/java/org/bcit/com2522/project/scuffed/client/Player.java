package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.awt.*;
import java.util.Random;

/**
 * The type Player.
 */
public class Player { // gamestate is the player manager
  private int resources;
  private final int playerNum;
  boolean hasLost;
  private Color color;
  private boolean isAi;

  private int xShift;
  private int yShift;

  private String username; //for use with the server

  /**
   * Instantiates a new Player.
   *
   * @param playerNum the player num
   */
  public Player(int playerNum) {
    this.playerNum = playerNum;
    Random random = new Random();
    color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    hasLost = false;
    resources = 2;
    isAi = false;
//    if (playerNum == 1) { // TODO: change this logic later
//      isAi = true;
//    } else {
//      isAi = false;
//    }
  }

  /**
   * Instantiates a new Player.
   *
   * @param playerNum the player num
   * @param color     the color
   */
  public Player(int playerNum, Color color) {
    this.playerNum = playerNum;
    this.color = color;
    resources = 1;
    hasLost = false;
  }

  public Player(Player player) { // creates a deep copy of player
    this.resources = player.resources;
    this.playerNum = player.playerNum;
    this.hasLost = player.hasLost;
    this.color = player.color;
    this.isAi = player.isAi;
  }

  public void addEntity(Position position) {
    // entities.add(new Entity(scene, position, this));
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Player) {
      Player temp = (Player) obj;
      return temp.getPlayerNum() == this.getPlayerNum();
    }
    return false;
  }

  /**
   * Gets player num.
   *
   * @return the player num
   */
  public int getPlayerNum() {
    return playerNum;
  }

  /**
   * Has lost boolean.
   *
   * @return the boolean
   */
  public boolean hasLost() {
    // return entities.size() == 0;
    return false;
  }

  /**
   * Creates a player from a JSONObject
   *
   * @param playerObject the player object
   * @return Player object from the JSON
   */
  public static Player fromJSONObject(JSONObject playerObject) {
    Player player = new Player((int) (long) playerObject.get("playerNum"));
    player.resources = (int) (long) playerObject.get("resources");
    player.color = Color.decode((String) playerObject.get("color"));
    player.isAi = (boolean) playerObject.get("isAi");
    return player;
  }

  /**
   * To json object json object.
   *
   * @return the json object
   */
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

  /**
   * Lose.
   */
  public void lose() {
    hasLost = true;
  }

  /**
   * Gets has lost.
   *
   * @return the has lost
   */
  public boolean getHasLost() {
    return hasLost;
  }

  /**
   * Gets color.
   *
   * @return the color
   */
  public Color getColor() {
    return color;
  }

  /**
   * Sets color.
   *
   * @param color the color
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public int getID() {
    return playerNum; // TODO: change to unique int or string that represents the player
  }

  /**
   * Is ai boolean.
   *
   * @return the boolean
   */
  public boolean isAI() {
    return isAi;
  }

  /**
   * Increase resources.
   *
   * @param gained the gained
   */
  public void increaseResources(int gained) {
    resources += gained;
  }

  /**
   * Spend resources.
   *
   * @param lost the lost
   */
  public void spendResources(int lost) {
    resources -= lost;
  }

  /**
   * Gets resources.
   *
   * @return the resources
   */
  public int getResources() {
    return resources;
  }

  /**
   * Sets shift.
   *
   * @param x the x
   * @param y the y
   */
  public void setShift(int x, int y) {
    xShift = x;
    yShift = y;
  }

  /**
   * Gets y shift.
   *
   * @return the y shift
   */
  public int getYShift() {
    return yShift;
  }

  /**
   * Gets x shift.
   *
   * @return the x shift
   */
  public int getXShift() {
    return xShift;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
