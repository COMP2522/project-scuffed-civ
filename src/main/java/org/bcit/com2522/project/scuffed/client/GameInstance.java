package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;
import processing.core.PVector;

import java.io.FileWriter;
import java.io.IOException;

public class GameInstance {
    public HUD hud;
    public GameState gameState;
    boolean vsAI = false;

    public GameInstance() {
        hud = new HUD();
        gameState = new GameState();
    }

    public GameInstance(HUD hud, GameState gameState) {
        this.hud = hud;
        this.gameState = gameState;
    }

    public void draw(Window scene) {
        gameState.draw(scene);
        hud.draw(scene);
    }

    public void clicked(PVector mousePos, Window scene) {
        if (gameState.clickedMap(mousePos)){
            gameState.clicked(mousePos);
        } else {
            hud.clicked(mousePos, scene);
        }
    }

    public void keyPressed(char key, Window scene) {
        gameState.keyPressed(key, scene);
    }

    public void saveGame() {
        System.out.println("Saving game");
        JSONObject gameStateJSON = gameState.toJSONObject();
        try (FileWriter saveFile = new FileWriter("saves/save.json")) {
            saveFile.write(gameStateJSON.toJSONString());
            saveFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getCurrentPlayer() {
        return gameState.currentPlayer;
    }

    public void loadGame() {
        try {
            gameState = GameState.load();
            hud.currentPlayer = gameState.currentPlayer;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nextTurn() {
        gameState.nextTurn();
        hud.currentPlayer = gameState.currentPlayer;
    }

    public void newGame() {
        gameState.init();
        hud.currentPlayer = gameState.currentPlayer;
    }
}
