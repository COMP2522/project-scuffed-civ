package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;
import processing.core.PImage;

public abstract class Entity {
    protected int maxAction;
    protected int remainAction;
    protected int ownerID;
    protected Player owner;
    protected int maxHealth;
    protected int currentHealth;
    protected int resourceCost;
    protected PImage texture;
    protected String entityType; //building, soldier, worker

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

    public Position getPosition(Entity[][] entities) {
        for (int i = 0; i < entities.length; i++) {
            for (int j = 0; j < entities[0].length; j++) {
                if (entities[i][j] == this) {
                    return new Position(i,j);
                }
            }
        }
        return null;
    }

    /**
     * finds the nearest non-filled position
     *
     * @param entities list of entities
     */
    public Position getFreePosition(Entity[][] entities) {
        //the position above the entity
        if (getPosition(entities).getY() != 0 && entities[getPosition(entities).getX()][getPosition(entities).getY() - 1] == null)
            return new Position(getPosition(entities).getX(), getPosition(entities).getY() - 1);
        //the position to the right of the entity
        else if (getPosition(entities).getX() != entities.length - 1 && entities[getPosition(entities).getX() + 1][getPosition(entities).getY()] == null)
            return new Position(getPosition(entities).getX() + 1, getPosition(entities).getY());
        //the position below the entity
        else if (getPosition(entities).getY() != entities[0].length - 1 && entities[getPosition(entities).getX()][getPosition(entities).getY() + 1] == null)
            return new Position(getPosition(entities).getX(), getPosition(entities).getY() + 1);
        //the position to the left of the entity
        else if (getPosition(entities).getX() != 0 && entities[getPosition(entities).getX() - 1][getPosition(entities).getY()] == null)
            return new Position (getPosition(entities).getX() - 1, getPosition(entities).getY());
        //there are no free positions
        else
            return null;
    }

    public void resetAction() {
        remainAction = maxAction;
    }

    public int getOwnerID () {
        return ownerID;
    }


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


    public static Entity fromJSONObject(JSONObject entityObject) {
        if(entityObject == null || entityObject.isEmpty()) {
            return null;
        }
        String entityType = (String) entityObject.get("entityType");
        System.out.println(entityType);
        switch(entityType) {
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

    public boolean cannotAct() {
        return remainAction < 1;
    }

    //public void act() {remainAction--;}

    public void takeDamage(int damageDealt) { //returns true if dead
        currentHealth -= damageDealt;
    }

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



    public int getHealth() {
        return currentHealth;
    }

  public int getCost() {
        return resourceCost;
  }

    public Player getOwner() {
        return owner;
    }

    public int getRemainAction() {
        return remainAction;
    }

}
