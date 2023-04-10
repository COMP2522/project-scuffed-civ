package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

/**
 * Class representing a worker unit that can build buildings and harvest resources.
 * Each player starts with one worker unit.
 */
public class Worker extends Unit {

  /**
   * The constant cost.
   */
  public static final int cost = 1;
  /**
   * The constant health.
   */
  public static final int health = 100;
  /**
   * The constant speed.
   */
  public static final int speed = 5;

  /**
   * Instantiates a new Worker.
   *
   * @param ownerID the owner id
   * @param health  the health
   * @param cost    the cost
   * @param speed   the speed
   */
  public Worker(int ownerID, int health, int cost, int speed) {
    super(ownerID, health, cost, speed);
    entityType = "worker";
//        texture = GameImages.get(entityType);
  }

  /**
   * From json object worker.
   *
   * @param workerObject the worker object
   * @return the worker
   */
  public static Worker fromJSONObject(JSONObject workerObject) {
    if (workerObject == null) {
      return null;
    }
    Worker worker = new Worker(
            (int) (long) workerObject.get("ownerId"),
            (int) (long) workerObject.get("maxHealth"),
            Worker.cost,
            (int) (long) workerObject.get("speed"));
    worker.currentHealth = (int) (long) workerObject.get("currentHealth");
    return worker;
  }

  public JSONObject toJSONObject() {
    JSONObject workerObject = super.toJSONObject();
    workerObject.put("speed", speed);
    return workerObject;
  }

  /**
   * Build building entity.
   *
   * @param entities the entities
   * @return the entity
   */
  public Entity buildBuilding(Entity[][] entities) {
    Building building = null;
    if (this instanceof Worker) {
      Position free = getFreePosition(entities);
      if (canBuild(free, Building.cost)) {
        building = new Building(ownerID, Building.health, Building.cost);
        entities[free.getX()][free.getY()] = building;
        remainAction--;
        owner.spendResources(Building.cost);
      }
    }
    return building;
  }

  /**
   * Collect.
   *
   * @param tile the tile
   */
  public void collect(Tile tile) {
    if (cannotAct()) {
      System.out.println("you do not have enough actions left");
      return;
    }
    if (tile.getType() <= 0) {
      System.out.println("there is nothing to collect here");
      return;
    }
    owner.increaseResources(tile.takeResources());
    remainAction--;
    remainMove = 0;
  }
}
