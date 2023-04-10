package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;
import processing.core.PImage;

/**
 * The type Entity.
 */
public abstract class Entity {
  /**
   * The Max action.
   */
  protected int maxAction;
  /**
   * The Remain action.
   */
  protected int remainAction;
  /**
   * The Owner id.
   */
  protected int ownerID;
  /**
   * The Owner.
   */
  protected Player owner;
  /**
   * The Max health.
   */
  protected int maxHealth;
  /**
   * The Current health.
   */
  protected int currentHealth;
  /**
   * The Resource cost.
   */
  protected int resourceCost;
  /**
   * The Texture.
   */
  protected PImage texture;
  /**
   * The Entity type.
   */
  protected String entityType; //building, soldier, worker

  /**
   * Instantiates a new Entity.
   *
   * @param ownerId the owner id
   * @param health  the health
   * @param cost    the cost
   */
  public Entity(int ownerId, int health, int cost) {
    this.ownerID = ownerId;
    this.owner = GameState.getPlayer(ownerId);
    //this.color = pColor;
    maxAction = 1;
    remainAction = 1;

    maxHealth = health;
    currentHealth = maxHealth;

    resourceCost = cost;
  }

  /**
   * From json object entity.
   *
   * @param entityObject the entity object
   * @return the entity
   */
  public static Entity fromJSONObject(JSONObject entityObject) {
    if (entityObject == null || entityObject.isEmpty()) {
      return null;
    }
    String entityType = (String) entityObject.get("entityType");
    System.out.println(entityType);
    switch (entityType) {
      case "building":
        Building building = Building.fromJSONObject(entityObject);
        //building.color = Color.decode((String) entityObject.get("color"));
        return building;
      case "soldier":
        Soldier soldier = Soldier.fromJSONObject(entityObject);
        //soldier.color = Color.decode((String) entityObject.get("color"));
        return soldier;
      case "worker":
        Worker worker = Worker.fromJSONObject(entityObject);
        //worker.color = Color.decode((String) entityObject.get("color"));
        return worker;
      default:
        throw new IllegalArgumentException("this is not a valid entityType: " + entityType);
    }

  }

  /**
   * Gets position.
   *
   * @param entities the entities
   * @return the position
   */
  public Position getPosition(Entity[][] entities) {
    for (int i = 0; i < entities.length; i++) {
      for (int j = 0; j < entities[0].length; j++) {
        if (this.equals(entities[i][j])) {
          return new Position(i, j);
        }
      }
    }
    return null;
  }

  /**
   * Finds the nearest non-filled position.
   *
   * @param entities list of entities
   * @return the free position
   */
  public Position getFreePosition(Entity[][] entities) {
    //the position above the entity
    if (getPosition(entities).getY() != 0
        && entities[getPosition(entities).getX()][getPosition(entities).getY() - 1] == null) {
      return new Position(getPosition(entities).getX(), getPosition(entities).getY() - 1);
    }
    //the position to the right of the entity
    else if (getPosition(entities).getX() != entities.length - 1
        && entities[getPosition(entities).getX() + 1][getPosition(entities).getY()] == null) {
      return new Position(getPosition(entities).getX() + 1, getPosition(entities).getY());
    }
    //the position below the entity
    else if (getPosition(entities).getY() != entities[0].length - 1
        && entities[getPosition(entities).getX()][getPosition(entities).getY() + 1] == null) {
      return new Position(getPosition(entities).getX(), getPosition(entities).getY() + 1);
    }
    //the position to the left of the entity
    else if (getPosition(entities).getX() != 0
        && entities[getPosition(entities).getX() - 1][getPosition(entities).getY()] == null) {
      return new Position(getPosition(entities).getX() - 1, getPosition(entities).getY());
    }
    //there are no free positions
    else {
      return null;
    }
  }

  /**
   * Reset action.
   */
  public void resetAction() {
    remainAction = maxAction;
  }

  /**
   * Gets owner id.
   *
   * @return the owner id
   */
  public int getOwnerID() {
    return ownerID;
  }

  /**
   * To json object json object.
   *
   * @return the json object
   */
  public JSONObject toJSONObject() {
    JSONObject entityObject = new JSONObject();
    //entityObject.put("position", position.toJSONObject());
    entityObject.put("ownerId", ownerID);
    //entityObject.put("color", "#" + Integer.toHexString(color.getRGB()).substring(2));
    entityObject.put("maxAction", maxAction);
    entityObject.put("maxHealth", maxHealth);
    entityObject.put("currentHealth", currentHealth);
    entityObject.put("remainAction", remainAction);
    entityObject.put("entityType", entityType);
    return entityObject;
  }

  /**
   * Cannot act boolean.
   *
   * @return the boolean
   */
  public boolean cannotAct() {
    return remainAction < 1;
  }

  //public void act() {remainAction--;}

  /**
   * Take damage.
   *
   * @param damageDealt the damage dealt
   */
  public void takeDamage(int damageDealt) { //returns true if dead
    currentHealth -= damageDealt;
  }

  /**
   * Can build boolean.
   *
   * @param free      the free
   * @param resources the resources
   * @return the boolean
   */
  public boolean canBuild(Position free, int resources) {
    if (free == null) {
      System.out.println("there are no available spaces to place a entity");
      return false;
    }
    if (cannotAct()) {
      System.out.println("this entity is out of actions");
      return false;
    }
    if (owner.getResources() < resources) {
      System.out.println("you don't have enough resources");
      return false;
    }
    return true;
  }


  /**
   * Gets health.
   *
   * @return the health
   */
  public int getHealth() {
    return currentHealth;
  }

  /**
   * Gets cost.
   *
   * @return the cost
   */
  public int getCost() {
    return resourceCost;
  }

  /**
   * Gets owner.
   *
   * @return the owner
   */
  public Player getOwner() {
    return owner;
  }

  /**
   * Gets remain action.
   *
   * @return the remain action
   */
  public int getRemainAction() {
    return remainAction;
  }

}
