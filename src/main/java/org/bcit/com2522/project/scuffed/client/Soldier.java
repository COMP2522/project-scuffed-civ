package org.bcit.com2522.project.scuffed.client;

import static org.bcit.com2522.project.scuffed.client.Window.GameImages;

import org.json.simple.JSONObject;

/**
 * The type Soldier.
 */
public class Soldier extends Unit {
  /**
   * The constant cost.
   */
  public static final int cost = 1;
  /**
   * The constant speed.
   */
  public static int speed = 4;
  /**
   * The constant health.
   */
  public static int health = 50;
  /**
   * The Damage.
   */
  static int damage = 50;
  /**
   * The Range.
   */
  static int range = 6;

  /**
   * Instantiates a new Soldier.
   *
   * @param ownerId the owner id
   * @param health  the health
   * @param cost    the cost
   * @param speed   the speed
   * @param damage  the damage
   * @param range   the range
   */
  public Soldier(int ownerId, GameState gameState, int health, int cost, int speed, int damage, int range) {
    super(gameState.getPlayer(ownerId), health, cost, speed);
    entityType = "soldier";
    texture = GameImages.get("soldier");
    Soldier.damage = damage;
    Soldier.range = range;
  }

  /**
   * Instantiates a new Soldier from owner.
   *
   * @param owner the owner Player
   * @param health  the health
   * @param cost    the cost
   * @param speed   the speed
   * @param damage  the damage
   * @param range   the range
   */
  public Soldier(Player owner, int health, int cost, int speed, int damage, int range) {
    super(owner, health, cost, speed);
    entityType = "soldier";
    texture = GameImages.get("soldier");
    Soldier.damage = damage;
    Soldier.range = range;
  }

  /**
   * Creates a Soldier object from a JSONObject
   *
   * @param soldierObject the soldier object
   * @return soldier soldier
   */
  public static Soldier fromJSONObject(JSONObject soldierObject, GameState gameState) {
    if (soldierObject == null) {
      return null;
    }
    Soldier soldier = new Soldier(
            // Position.fromJSONObject((JSONObject) soldierObject.get("position")),
            (int) (long) soldierObject.get("ownerId"),
            gameState,
            (int) (long) soldierObject.get("maxHealth"),
            Soldier.cost,
            (int) (long) soldierObject.get("maxMove"),
            (int) (long) soldierObject.get("damage"),
            (int) (long) soldierObject.get("range"));
    soldier.currentHealth = (int) (long) soldierObject.get("currentHealth");
    return soldier;
  }

  /**
   * Attack.
   *
   * @param entities the entities
   * @param target   the target
   */
  public void attack(Entity[][] entities, Entity target) {
    if (target == null) {
      System.out.println("there is not an enemy there");
      return;
    } else if (!withinRange(target.getPosition(entities), entities)) {
      System.out.println("enemy is out of range");
      return;
    } else if (cannotAct()) {
      System.out.println("you are out of actions");
      return;
    } else if (target.getOwner() == this.getOwner()) {
      System.out.println("no friendly fire");
    } else {
      target.takeDamage(damage);
      System.out.println("you did some damage");
      remainAction--;
    }

    if (target.getHealth() <= 0) {
      entities[target.getPosition(entities).getX()][target.getPosition(entities).getY()] = null;
    }
  }

  public JSONObject toJSONObject() {
    JSONObject soldierObject = super.toJSONObject();
    soldierObject.put("damage", damage);
    soldierObject.put("range", range);
    return soldierObject;
  }

  /**
   * Within range boolean.
   *
   * @param position the position
   * @param entities the entities
   * @return the boolean
   */
  public boolean withinRange(Position position, Entity[][] entities) {
    if (Math.abs(position.getX() - this.getPosition(entities).getX())
            + Math.abs(position.getY() - this.getPosition(entities).getY()) <= range &&
            position.getX() >= 0 && position.getX() < entities.length && position.getY() >= 0
            && position.getY() < entities[0].length) {
      return true;
    } else {
      System.out.println("that position is out of range");
      return false;
    }
  }

  /**
   * Gets damage.
   *
   * @return the damage
   */
  public int getDamage() {
    return damage;
  }

  /**
   * Gets range.
   *
   * @return the range
   */
  public int getRange() {
    return (range);
  }

}
