package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.server.GameServer;
import org.json.simple.JSONObject;
import processing.core.PVector;

import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameInstance {
    public HUD hud;
    public GameState gameState;
    boolean vsAI = false;
    /**server variables**/
    private Socket socket;
    private int clientId;
    private String hostIP;
    private int port;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    public GameServer gameServer;

    public Window scene;


    public GameInstance(Window scene) {
        this.scene = scene;
        hud = new HUD(scene);
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
        if (hud.clicked(mousePos)){
            hud.clicked(mousePos);
        } else if(gameState.clickedMap(mousePos)){
            gameState.clicked(mousePos, scene);
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
        System.out.println("next turn in game instance was called");
        gameState.nextTurn();
        hud.currentPlayer = gameState.currentPlayer;
    }

    public void newGame() {
        gameState.init();
        hud.currentPlayer = gameState.currentPlayer;
    }

    public void sendGameState(GameState gameState) {
        try {
            oos.writeObject(gameState.toJSONObject());
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        public void receiveGameState() {
        try {
            gameState = GameState.fromJSONObject((JSONObject) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void joinGame(String hostIP, int port) {
        System.out.println("Joining game at " + hostIP + ":" + port);
        this.hostIP = hostIP;
        this.port = port;
        try {
            socket = new Socket(hostIP, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            GameState serverGameState = GameState.fromJSONObject((JSONObject) ois.readObject());
            GameInstance gameInstance = new GameInstance(new HUD(scene), serverGameState);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
