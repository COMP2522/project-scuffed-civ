package org.bcit.com2522.project.scuffed.server;

import org.bcit.com2522.project.scuffed.client.GameState;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
        gameState.init();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on " + hostIP + ":" + port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (!allPlayersConnected()) {
            System.out.println("Waiting for client connections on port " + port + "...");
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, clients.size());
                client.start();
                clients.add(client);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("All players connected! Press 'Start Game' to begin.");
    }

    public boolean allPlayersConnected() {
        return clients.size() == gameState.players.size();
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
                    client.sendMessage("update");
                    client.sendGameState(updatedGameState);
                } else {
                    client.sendMessage("Your turn!");
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
        client.sendMessage("start");
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

        private final int playerID;
        private final ObjectOutputStream oos;
        private final ObjectInputStream ois;

        /**
         * Instantiates a new Client handler, associating the username with each particular client.
         *
         * @param socket   the socket
         * @param playerID
         */
        public ClientHandler(Socket socket, int playerID) {
            this.clientSocket = socket;
            this.playerID = playerID;
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

        public void run() {
            try {
                // Read the client's username
                clientUsername = (String) ois.readObject();
                System.out.println("Client connected from " + clientSocket.getInetAddress().getHostAddress() + " with username " + clientUsername + " ID: " + playerID);

                // Send player ID
                oos.writeObject(playerID);
                oos.flush();

                // Send initial message
                oos.writeObject("Hello, " + clientUsername + "! Please wait for all other clients to connect.");
                oos.flush();

                // Wait for all players to connect before sending the initial game state
                while (!allPlayersConnected()) {
                    Thread.sleep(1000);
                }

                // Send initial game state
                sendInitialGameState(this);
                String clientMessage = (String) ois.readObject();
                if (clientMessage.equals("received")) {
                    System.out.println("Client received initial game state.");
                }

                // Game loop
                while (true) {
                    String serverMessage = (String) ois.readObject();
                    if (serverMessage.equals("nextTurn")) {
                        GameState updatedGameState = receiveUpdatedGameState();
                        if (updatedGameState != null) {
                            gameState = updatedGameState;
                            broadcastGameState(gameState, this);
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public GameState receiveUpdatedGameState() {
            try {
                String gameStateString = (String) ois.readObject();
                JSONObject gameStateJSON = (JSONObject) new JSONParser().parse(gameStateString);
                System.out.println("Received updated game state: " + gameStateJSON.toJSONString());
                return GameState.fromJSONObject(gameStateJSON);
            } catch (IOException | ClassNotFoundException | ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Sends the game state as a JSONObject to the client.
         *
         * @param gameState the game state to send
         */
        public void sendGameState(GameState gameState) {
            try {
                JSONObject gameStateJSON = gameState.toJSONObject();
                oos.writeObject(gameStateJSON.toJSONString());
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(String message) {
            try {
                oos.writeObject(message);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}