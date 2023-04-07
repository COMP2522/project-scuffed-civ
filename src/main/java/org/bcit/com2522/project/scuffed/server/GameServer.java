package org.bcit.com2522.project.scuffed.server;

import org.bcit.com2522.project.scuffed.client.GameState;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;

/**
 * [CURRENTLY NON-FUNCTIONAL]
 * <p>
 * Coordinates the game state between multiple players using the ClientHandler inner class to designate a new
 * Thread for each new client. This version is meant to work for clients on the same wifi network as the host.
 *
 * @author Cameron Walford
 * @version 1.0
 */
public class GameServer implements Runnable {
    private ServerSocket serverSocket;
    private GameState gameState;
    private int port;
    private String hostIP;

    /**
     * The list of clients connected to the server.
     */
    private final List<ClientHandler> clients = Collections.synchronizedList(new LinkedList<>());

  /**
   * Instantiates a new Game server.
   *
   * @param gameState the game state
   * @param port      the port
   */
  public GameServer(GameState gameState, int port) {
        this.gameState = gameState;
        this.port = port;
        this.hostIP = getLocalIpAddress();
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
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
   * @param currentPlayer    the current player's client handler
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
   * Finds the local IP address of the machine running the server. This is for playing
   * multiplayer on the same wifi network.
   *
   * @return String representation of the local IP address (IPV4)
   */
  public static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (!networkInterface.isLoopback() && networkInterface.isUp()) {
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        if (!inetAddress.isLoopbackAddress() && inetAddress.getAddress().length == 4) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

  /**
   * Gets connected players.
   *
   * @return the connected players
   */
  public HashSet<String> getConnectedPlayers() {
        HashSet<String> connectedPlayers = new HashSet<>();
        for (ClientHandler client : clients) {
            connectedPlayers.add(client.clientSocket.getInetAddress().getHostAddress());
        }
        return connectedPlayers;
    }


  /**
   * Handles each new connection made to the GameServer.
   *
   * @author Cameron Walford
   * @version 1.0
   */
  class ClientHandler extends Thread{
        private final Socket clientSocket;

    /**
     * The Client username.
     */
    public String clientUsername;
        private final ObjectOutputStream oos;
        private final ObjectInputStream ois;

    /**
     * Instantiates a new Client handler.
     *
     * @param socket the socket
     */
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
                    clientUsername = (String) ois.readObject();
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