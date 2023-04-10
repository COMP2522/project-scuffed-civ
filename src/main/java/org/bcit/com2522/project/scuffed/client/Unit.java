package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

/**
 * The type Unit.
 */
public abstract class Unit extends Entity { // things that can move
  /**
   * The Max move.
   */
  int maxMove;
  /**
   * The Remain move.
   */
  int remainMove;

  /**
   * Instantiates a new Unit.
   *
   * @param ownerId the owner id
   * @param health  the health
   * @param cost    the cost
   * @param speed   the speed
   */
  public Unit(int ownerId, int health, int cost, int speed) {
    super(ownerId, health, cost);
    this.maxMove = speed;
    this.remainMove = maxMove;
  }

  /**
   * Reset move.
   */
  public void resetMove() {
    remainMove = maxMove;
  }

  @Override
  public JSONObject toJSONObject() {
    JSONObject unitObject = super.toJSONObject();
    unitObject.put("maxMove", maxMove);
    unitObject.put("remainMove", remainMove);
    return unitObject;
  }

  /**
   * Within move range boolean.
   *
   * @param position the position
   * @param entities the entities
   * @return the boolean
   */
  public Boolean withinMoveRange(Position position, Entity[][] entities) {
    return Math.abs(position.getX() - this.getPosition(entities).getX())
            + Math.abs(position.getY() - this.getPosition(entities).getY()) <= remainMove &&
            position.getX() >= 0 && position.getX() < entities.length && position.getY() >= 0
            && position.getY() < entities[0].length;
  }

  /**
   * Move.
   *
   * @param entities current list of entities on the map
   * @param position the position to travel to
   */
  public void move(Entity[][] entities, Position position) {
    if (entities[position.getX()][position.getY()] != null) {
      System.out.println("that position is occupied");
    } else if (!withinMoveRange(position, entities)) {
      System.out.println("you can't move that far");
    } else if (position.getX() < 0 || position.getX() >= entities.length || position.getY() < 0
            || position.getY() >= entities[0].length) {
      System.out.println("that position is outside of the map");
    } else {
      Position oldPos = getPosition(entities);
      remainMove -= Math.abs(position.getX() - this.getPosition(entities).getX())
              + Math.abs(position.getY() - this.getPosition(entities).getY());
      entities[oldPos.getX()][oldPos.getY()] = null;
      entities[position.getX()][position.getY()] = this;
    }
  }

  /**
   * Gets remain move.
   *
   * @return the remain move
   */
  public int getRemainMove() {
    return remainMove;
  }

  /**
   * Gets max move.
   *
   * @return the max move
   */
  public int getMaxMove() {
    return maxMove;
  }
}
