package org.bcit.com2522.project.scuffed.server;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;

import com.mongodb.reactivestreams.client.*; //http://mongodb.github.io/mongo-java-driver/4.9/apidocs/mongodb-driver-reactivestreams/com/mongodb/reactivestreams/client/package-summary.html


import org.bcit.com2522.project.scuffed.client.GameState;
import org.bson.Document;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import org.json.simple.JSONObject;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.FullDocument;
import com.mongodb.client.model.changestream.ChangeStreamDocument;


import org.bcit.com2522.project.scuffed.server.SubscriberHelpers.PrintDocumentSubscriber;

public class MongoGameServer {

    private MongoDatabase database;
    private static String defaultUsername = "cam";
    private static String defaultPassword = "passWord";

    private MongoCollection collection;
    private String serverID;

    /**
     * Connects to MongoDB server and initializes the database.
     *
     * @param username as a String
     * @param password as a String
     */
    private void connectToDatabase(String username, String password) {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://"+ username + ":" + password + "@scuffedciv.nwbjiwh.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("ScuffedCiv");
    }

    /**
     * Creates a new game by connecting to the database and creating a new collection.
     *
     * @param gameState the GameState initialized by the host
     */
    public void createNewGame(GameState gameState) {
        connectToDatabase(defaultUsername, defaultPassword);
        setServerID(gameState.getGameID());
        database.createCollection(serverID);
        JSONObject gameJSONObject = gameState.toJSONObject();
        String gameJSON = gameJSONObject.toJSONString();
        System.out.println(gameJSON);
        Document gameStateDocument = Document.parse(gameJSON);
        database.getCollection(gameState.getGameID()).insertOne(gameStateDocument);
        joinGame("host", serverID);
    }

    /**
     * Joins a game by connecting to the database and subscribing to the game's collection.
     *
     * @param serverID as a String
     */
    public void joinGame(String userID, String serverID) {
        if(database == null){
            connectToDatabase(defaultUsername, defaultPassword);
            setServerID(serverID);
        }
        collection = database.getCollection(serverID);

        collection.watch().subscribe(new PrintDocumentSubscriber());
        //Publisher<Document> publisher = collection.find(eq("gameID", serverID)).first();
        //System.out.println("Successfully retrieved game " + serverID + "!");

    }

    public void deleteAllGames(){

    }

    /**
     * Sets the serverID to the given gameID.
     *
     * @param gameID as a String
     */
    private void setServerID(String gameID) {
        serverID = gameID;
    }

    /**
     * Main function. for testing purposes.
     *
     * @param args arguments from command line
     */
    public static void main(String[] args) {
        String userID = "bob";
        MongoGameServer server = new MongoGameServer();
        GameState gameState = new GameState(4, 20, 20);
        gameState.init();
        server.createNewGame(gameState);
        //server.joinGame(userID, server.serverID);

    }


}
