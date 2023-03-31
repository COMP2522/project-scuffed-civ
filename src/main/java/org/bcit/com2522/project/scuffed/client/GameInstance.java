package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.server.GameServer;
import org.json.simple.JSONObject;
import processing.core.PVector;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

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

    boolean isOnline = false;

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

    /**
     * Converts the game state to a JSON object and saves it to the save.json file.
     *
     * @param client
     */
    public void saveGame() throws IOException {
        System.out.println("Saving game");
        JSONObject gameStateJSON = gameState.toJSONObject();
        File saveFile = new File("library/saves.json");
        if(saveFile.createNewFile()){
            //System.out.println("new save file created");
        } else {
           // System.out.println("overwriting save");
        }
        try (FileWriter saveWriter = new FileWriter(saveFile)) {
            saveWriter.write(gameStateJSON.toJSONString());
            saveWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the current player of the game state.
     *
     * @return
     */
    public Player getCurrentPlayer() {
        return gameState.currentPlayer;
    }

    /**
     * Loads a game from the save.json file.
     */
    public void loadGame() {
        try {
            gameState = GameState.load();
            hud.currentPlayer = gameState.currentPlayer;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ends the current player's turn and starts the next player's turn.
     */
    public void nextTurn() {
        System.out.println("next turn in game instance was called");
        gameState.nextTurn();
        hud.currentPlayer = gameState.currentPlayer;
    }

    /**
     * Starts a new game by initializing the game state and setting the current player of the
     * hud to the current player of the game state.
     */
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

    /**
     * Starts a new game as a server.
     *
     * @param hostIP
     * @param port the port to host the server
     */
    public void joinGame(String hostIP, int port, String clientUsername) {
        isOnline = true;
        System.out.println("Joining game at " + hostIP + ":" + port);
        this.hostIP = hostIP;
        this.port = port;
        try {
            socket = new Socket(hostIP, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("ois" + ois.readObject());
            this.gameState = GameState.fromJSONObject((JSONObject) ois.readObject());
        } catch (Exception e) {
            System.out.println("Error connecting to server at " + hostIP + ":" + port);
            e.printStackTrace();
            //briefly display error message in center of screen
        }
        try {
            oos.writeObject(clientUsername);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setGameServer(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public void startServer() {
        isOnline = true;
        Thread server= new Thread(gameServer);
        server.start();
    }

    public void start () {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendGameState(gameState);
            }
        }, 0, 1000);
    }

    public HashSet<String> getConnectedPlayers() {
        return gameServer.getConnectedPlayers();
    }
}
