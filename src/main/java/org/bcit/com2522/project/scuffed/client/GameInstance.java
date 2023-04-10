package org.bcit.com2522.project.scuffed.client;

import org.bcit.com2522.project.scuffed.server.GameServer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processing.core.PVector;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The type Game instance.
 */
public class GameInstance {
    /**
     * The Hud.
     */
    public Hud hud;
    /**
     * The Game state.
     */
    public GameState gameState;
    /**
     * The Vs ai.
     */
    boolean vsAI = false;
    /**server variables**/
    private Socket socket;
    private String hostIP;
    private int port;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    /**
     * The Game server.
     */
    public GameServer gameServer;

    /**
     * The Is online.
     */
    boolean isOnline = false;

    /**
     * The Scene.
     */
    public Window scene;
    public int clientID;


    /**
     * Instantiates a new Game instance.
     *
     * @param scene the scene
     */
    public GameInstance(Window scene) {
        this.scene = scene;
        hud = new Hud(scene);
        gameState = new GameState();
    }

    /**
     * Instantiates a new Game instance.
     *
     * @param hud       the hud
     * @param gameState the game state
     */
    public GameInstance(Hud hud, GameState gameState) {
        this.hud = hud;
        this.gameState = gameState;
    }

    /**
     * Draw.
     *
     * @param scene the scene
     */
    public void draw(Window scene) {
        gameState.draw(scene);
        hud.draw(scene);
    }

    /**
     * Clicked.
     *
     * @param mousePos the mouse pos
     * @param scene    the scene
     */
    public void clicked(PVector mousePos, Window scene) {
        if(isOnline){
            if (hud.clicked(mousePos)){
            } else if(gameState.clickedMap(mousePos) && clientID == gameState.getCurrentPlayerID()){
                gameState.clicked(mousePos, scene);
            } else if (gameState.clickedMap(mousePos) && clientID != gameState.getCurrentPlayerID()){
                System.out.println("Not your turn!");
            }
        } else {
            if (hud.clicked(mousePos)){

            } else if(gameState.clickedMap(mousePos)){
                gameState.clicked(mousePos, scene);
            }
        }
    }

    /**
     * Key pressed.
     *
     * @param key   the key
     * @param scene the scene
     */
    public void keyPressed(char key, Window scene) {
        gameState.keyPressed(key, scene);
    }

    /**
     * Converts the game state to a JSON object and saves it to the save.json file.
     *
     * @param
     * @throws IOException the io exception
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
     * @return current player
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a new game by initializing the game state and setting the current player of the
     * hud to the current player of the game state.
     */
    public void newGame() {
        gameState.init();
    }

    public void nextTurn(){
        gameState.nextTurn();
        if(isOnline){
            sendGameState(gameState);
        }
    }

    /**
     * Send game state.
     *
     * @param gameState the game state
     */
    public void sendGameState(GameState gameState) {
        try {
            // Send endturn message
            oos.writeObject("nextTurn");
            oos.flush();

            // Send updated game state
            JSONObject gameStateJSON = gameState.toJSONObject();
            oos.writeObject(gameStateJSON.toJSONString());
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Receive game state.
     */
    public void receiveGameState() {
        try {
            JSONParser jsonParser = new JSONParser();
            String jsonString = (String) ois.readObject();
            System.out.println("Received game state: " + jsonString);
            JSONObject gameStateJSON = (JSONObject) jsonParser.parse(jsonString);
            gameState = GameState.fromJSONObject(gameStateJSON);
            scene.inGame = true;
        } catch (IOException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }


    public void joinGame(String hostIP, int port, String clientUsername) {
        isOnline = true;
        System.out.println("Joining game at " + hostIP + ":" + port);
        this.hostIP = hostIP;
        this.port = port;
        try {
            socket = new Socket(hostIP, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            // Send the client's username
            oos.writeObject(clientUsername);
            oos.flush();

            clientID = (int)ois.readObject();
            System.out.println("Client ID: " + clientID);

            // Continuously send and receive
            Thread serverListener = new Thread(() -> {
                try {
                    while (true) {
                        String serverMessage = (String) ois.readObject();
                        if(serverMessage.equals("start")){
                            System.out.println("Starting game");
                            break;
                        }
                        System.out.println("Received message from server: " + serverMessage);

                        oos.writeObject("Hello, server! This is a message from " + clientUsername + ".");
                        oos.flush();
                    }

                    // Receive initial game state from server after all clients connect
                    oos.writeObject("received");
                    oos.flush();
                    receiveGameState();

                    // Continuously receive game state updates from server
                    while (true) {
                        String serverMessage = (String) ois.readObject();
                        if (serverMessage.equals("Your turn!")) {
                            System.out.println("It's my turn!");
                            // Handle the turn on the client-side
                        } else if (serverMessage.equals("Waiting for other players...")) {
                            System.out.println("Waiting for other players to finish...");
                        } else if (serverMessage.equals("update")) {
                            System.out.println("Received update message on client: " + clientID);
                            receiveGameState();
                            hud.setState(new InGameHud(hud));
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            serverListener.start();

        } catch (Exception e) {
            System.out.println("Error connecting to server at " + hostIP + ":" + port);
            e.printStackTrace();
        }
    }




    /**
     * Sets game state.
     *
     * @param gameState the game state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Sets game server.
     *
     * @param gameServer the game server
     */
    public void setGameServer(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    /**
     * Start server.
     */
    public void startServer() {
        isOnline = true;
        Thread server = new Thread(gameServer);
        server.start();
    }


    /**
     * Gets connected players.
     *
     * @return the connected players
     */
    public HashSet<String> getConnectedPlayers() {
        return gameServer.getConnectedPlayers();
    }
}
