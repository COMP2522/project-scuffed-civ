package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.io.Serializable;

import static org.bcit.com2522.project.scuffed.client.Window.PImages;
import static processing.awt.ShimAWT.loadImage;

public class Worker extends Unit{
    public Worker(Position position, Player player) {
        super(position, player);
        entityType = "worker";
        texture = PImages.get(entityType);
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject workerObject = new JSONObject();
        workerObject.put("entityType", entityType);
        workerObject.put("position", position.toJSONObject());
        workerObject.put("ownerId", ownerID);
        workerObject.put("currentHealth", currentHealth);
        return workerObject;
    }

    public static Worker fromJSONObject(JSONObject workerObject, Window scene) {
        if(workerObject == null) {
            return null;
        }
        Worker worker = new Worker(Position.fromJSONObject((JSONObject) workerObject.get("position")), Player.fromJSONObject((JSONObject) workerObject.get("owner")));
        worker.currentHealth = (int) (long) workerObject.get("currentHealth");
        return worker;
    }

    public void collect() {

    }
}
