package org.bcit.com2522.project.scuffed.server;

import org.bcit.com2522.project.scuffed.client.GameState;

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

    /**
     * The list of clients connected to the server.
     */
    private final List<ClientHandler> clients = Collections.synchronizedList(new LinkedList<>());

    public void start(GameState gameState, int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.gameState = gameState;

        while(true){
            try {
                ClientHandler client = new ClientHandler(serverSocket.accept());
                clients.add(client);
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
            try {
                while (true) {
                    GameState clientGameState = (GameState) ois.readObject();
                    // Update the server's game state
                    gameState = clientGameState;
                    // Broadcast the updated game state to all clients
                    broadcastGameState(gameState, this);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    ois.close();
                    oos.close();
                    clientSocket.close();
                    clients.remove(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendGameState(GameState gameState) {
            try {
                oos.writeObject(gameState);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}