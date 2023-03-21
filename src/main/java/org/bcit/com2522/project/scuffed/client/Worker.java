package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.io.Serializable;

import static processing.awt.ShimAWT.loadImage;

public class Worker extends Unit{
    public Worker(Window scene, Position position, Player player) {
        super(scene, position, player);
        unitType = "worker";
        texture = loadImage(scene, "sprites/hammerDude.png");
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject workerObject = new JSONObject();
        workerObject.put("entityType", entityType);
        workerObject.put("unitType", unitType);
        workerObject.put("position", position.toJSONObject());
        workerObject.put("owner", owner.toJSONObject());
        workerObject.put("currentHealth", currentHealth);
        return workerObject;
    }

    public static Worker fromJSONObject(JSONObject workerObject, Window scene) {
        if(workerObject == null) {
            return null;
        }
        Worker worker = new Worker(scene, Position.fromJSONObject((JSONObject) workerObject.get("position")), Player.fromJSONObject((JSONObject) workerObject.get("owner"), scene));
        worker.currentHealth = (int) (long) workerObject.get("currentHealth");
        return worker;
    }

    public void collect() {

    }
}
