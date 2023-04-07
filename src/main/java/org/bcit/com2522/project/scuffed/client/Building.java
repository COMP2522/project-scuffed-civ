package org.bcit.com2522.project.scuffed.client;


import org.json.simple.JSONObject;

import java.awt.*;

import static org.bcit.com2522.project.scuffed.client.Window.GameImages;

/**
 * The type Building.
 */
public class Building extends Entity{
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
     * Converts the Building object to a JSONObject
     * @return buildingObject - the JSONObject representation of the Building object
     */
    public JSONObject toJSONObject() {
        JSONObject buildingObject = super.toJSONObject();
        return buildingObject;
    }

  /**
   * From json object building.
   *
   * @param buildingObject the building object
   * @return the building
   */
  public static Building fromJSONObject(JSONObject buildingObject) {
        if(buildingObject == null) {
            return null;
        }
        Building building = new Building(
                //Position.fromJSONObject((JSONObject) buildingObject.get("position")),
                (int)(long) buildingObject.get("ownerId"),
                //(Color) buildingObject.get("color"),
                (int) buildingObject.get("maxHealth"),
                Building.cost);
        building.currentHealth = (int) (long) buildingObject.get("currentHealth");
        return building;
    }

  /**
   * Build worker.
   *
   * @param entities the entities
   */
  public void buildWorker(Entity[][] entities) {
        Position free = getFreePosition(entities);
        if (canBuild(free, Worker.cost)) {
            entities[free.getX()][free.getY()] = new Worker(ownerID, Worker.health, Worker.cost, Worker.speed);
            remainAction--;
            owner.spendResources(1);
        }
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

}
