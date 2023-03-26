package org.bcit.com2522.project.scuffed.server;

import org.bcit.com2522.project.scuffed.client.GameState;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Coordinates the game state between multiple clients using the ClientHandler inner class to designate a new
 * Thread for each new client. [in progress]
 *
 * @author Cameron Walford
 * @version 0.1
 */
public class GameServer {
    private ServerSocket serverSocket;
    private GameState gameState;
    private int port;
    String hostIP;

    /**
     * The list of clients connected to the server.
     */
    private final List<ClientHandler> clients = Collections.synchronizedList(new LinkedList<>());

    public void start(GameState gameState, int port) {
        try {
            serverSocket = new ServerSocket(port);
            this.port = port;
            this.hostIP = serverSocket.getInetAddress().getHostAddress();
            System.out.println("Server started on " + hostIP + ":" + port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.gameState = gameState;

        while(true){
            System.out.println("Waiting for client connections on port " + port + "...");
            try {
                ClientHandler client = new ClientHandler(serverSocket.accept());
                System.out.println("Client connected from " + client.clientSocket.getInetAddress().getHostAddress());
                clients.add(client);
                sendInitialGameState(client);
                client.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Sends the updated game state to all clients except the current player.
     *
     * @param updatedGameState the updated game state
     * @param currentPlayer the current player's client handler
     */
    public void broadcastGameState(GameState updatedGameState, ClientHandler currentPlayer) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != currentPlayer) {
                    client.sendGameState(updatedGameState);
                }
            }
        }
    }

    /**
     * Sends the initial game state to the client when they connect.
     *
     * @param client the client to send the initial game state to
     */
    public void sendInitialGameState(ClientHandler client) {
        client.sendGameState(gameState);
    }


    /**
     * Handles each new connection made to the GameServer.
     *
     * @author Cameron Walford
     * @version 1.0
     */
    class ClientHandler extends Thread{
        private final Socket clientSocket;
        private final ObjectOutputStream oos;
        private final ObjectInputStream ois;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                oos = new ObjectOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                ois = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Reads the client's game state and updates the server's game state. Then broadcasts the updated
         * game state to all clients.
         */
        public void run() {
            String clientIP = clientSocket.getInetAddress().getHostAddress();
            System.out.println("Client connected: " + clientIP);
            System.out.println(gameState.currentPlayer.getPlayerNum() + " is the current player.");

            try {
                while (true) {
                    GameState updatedGameState = GameState.fromJSONObject((JSONObject) ois.readObject());
                    gameState = updatedGameState;
                    broadcastGameState(gameState, this);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        /**
         * Sends the game state as a JSONObject to the client.
         *
         * @param gameState the game state to send
         */
        public void sendGameState(GameState gameState) {
            try {
                oos.writeObject(gameState.toJSONObject());
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}