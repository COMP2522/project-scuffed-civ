package org.bcit.com2522.project.scuffed.client;

/**
 * A class that represents a clickable object.
 * @author Emma MB
 * @version 1.0
 */
public class Clickable {


    private int boundsFromX;
    private int boundsFromY;
    private int boundsToX;
    private int boundsToY;

  /**
   * Constructs a clickable object.
   * @param boundsFromX the x coordinate of the top left corner of the clickable object
   * @param boundsFromY the y coordinate of the top left corner of the clickable object
   * @param boundsToX the x coordinate of the bottom right corner of the clickable object
   * @param boundsToY the y coordinate of the bottom right corner of the clickable object
   */
    public Clickable(int boundsFromX, int boundsFromY, int boundsToX, int boundsToY) {
        this.boundsFromX = boundsFromX;
        this.boundsFromY = boundsFromY;
        this.boundsToX = boundsToX;
        this.boundsToY = boundsToY;
    }

    /**
     * Checks if the given coordinates are within the bounds of the clickable object.
     * @param x the x coordinate to check
     * @param y the y coordinate to check
     * @return true if the coordinates are within the bounds of the clickable object, false otherwise
     */
    public boolean isWithinBounds(int x, int y) {
      return x >= boundsFromX && x <= boundsToX && y >= boundsFromY && y <= boundsToY;
    }

    


}
