package org.bcit.com2522.project.scuffed.server;

import org.bcit.com2522.project.scuffed.client.GameState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

    public void start(GameState gameState, int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.gameState = gameState;

        while(true){
            try {
                new ClientHandler(serverSocket.accept()).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.gameState = gameState;

        while(true){
            try {
                new ClientHandler(serverSocket.accept()).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void loadGame(GameState gameState) {
        this.gameState = gameState;
    }

    public void saveGame(GameState gameState) {
        this.gameState = gameState;
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

        public ClientHandler(Socket socket){
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
            try {
                GameState clientGameState = (GameState) ois.readObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                ois.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void run(){
            GameState received;
            GameState returned;
            while(clientSocket.isConnected()){

            }
        }
    }

}

