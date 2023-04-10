package org.bcit.com2522.project.scuffed.client;


import static org.bcit.com2522.project.scuffed.client.Window.GameImages;

import org.json.simple.JSONObject;

/**
 * The type Building.
 */
public class Building extends Entity {
  /**
   * The constant health.
   */
  public static final int health = 200;
  /**
   * The constant cost.
   */
  public static final int cost = 2;

  /**
   * Instantiates a new Building.
   *
   * @param ownerID the owner id
   * @param health  the health
   * @param cost    the cost
   */
  public Building(int ownerID, int health, int cost) {
    super(ownerID, health, cost);
    texture = GameImages.get("building");
    entityType = "building";
  }

  /**
   * From json object building.
   *
   * @param buildingObject the building object
   * @return the building
   */
  public static Building fromJSONObject(JSONObject buildingObject) {
    if (buildingObject == null) {
      return null;
    }
    Building building = new Building(
            //Position.fromJSONObject((JSONObject) buildingObject.get("position")),
            (int) (long) buildingObject.get("ownerId"),
            //(Color) buildingObject.get("color"),
            (int) (long) buildingObject.get("maxHealth"),
            Building.cost);
    building.currentHealth = (int) (long) buildingObject.get("currentHealth");
    return building;
  }

  /**
   * Converts the Building object to a JSONObject
   *
   * @return buildingObject - the JSONObject representation of the Building object
   */
  public JSONObject toJSONObject() {
    JSONObject buildingObject = super.toJSONObject();
    return buildingObject;
  }

  /**
   * Build worker.
   *
   * @param entities the entities
   * @return entity
   */
  public Entity buildWorker(Entity[][] entities) {
    Position free = getFreePosition(entities);
    if (canBuild(free, Worker.cost)) {
      Worker worker = new Worker(ownerID, Worker.health, Worker.cost, Worker.speed);
      entities[free.getX()][free.getY()] = worker;
      remainAction--;
      owner.spendResources(1);
      return worker;
    }
    return null;
  }

  /**
   * Build soldier.
   *
   * @param entities the entities
   * @param health   the health
   * @param damage   the damage
   * @param speed    the speed
   * @param range    the range
   */
  public void buildSoldier(Entity[][] entities, int health, int damage, int speed, int range) {
    Position free = getFreePosition(entities);
    if (canBuild(free, Soldier.cost)) {
      entities[free.getX()][free.getY()] = new Soldier(ownerID, health, Soldier.cost, speed, damage, range);
      remainAction--;
      owner.spendResources(Soldier.cost);
    }
  }

  /**
   * Build soldier entity.
   *
   * @param entities the entities
   * @return the entity
   */
  public Entity buildSoldier(Entity[][] entities) {
    Position free = getFreePosition(entities);
    if (canBuild(free, Soldier.cost)) {
      Soldier soldier = new Soldier(ownerID, Soldier.health, Soldier.cost, Soldier.speed, Soldier.damage, Soldier.range);

      entities[free.getX()][free.getY()] = soldier;
      remainAction--;
      owner.spendResources(Soldier.cost);
      return soldier;
    }
    return null;
  }
}
