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

import static com.mongodb.client.model.Filters.eq;

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
        database.createCollection(serverID).subscribe(new SubscriberHelpers.PrintSubscriber<>("Collection created successfully"));
        JSONObject gameJSONObject = gameState.toJSONObject();
        String gameJSON = gameJSONObject.toJSONString();
        //System.out.println(gameJSON);
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
        if (database == null) {
            connectToDatabase(defaultUsername, defaultPassword);
            setServerID(serverID);
        }
        collection = database.getCollection(serverID);

        // Watch for changes in the collection and print the updated document to the console
        collection.watch()
                .fullDocument(FullDocument.UPDATE_LOOKUP)
                .subscribe(new Subscriber<ChangeStreamDocument<Document>>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(ChangeStreamDocument<Document> changeStreamDocument) {
                        System.out.println("Change detected: " + changeStreamDocument.getFullDocument().toJson());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.err.println("Error: " + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Change stream completed");
                    }
                });

        collection.updateOne(eq("gameID", serverID), new Document("$push", new Document("players", userID)));
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
    public static void main(String[] args) throws InterruptedException {
        String userID = "bob";
        MongoGameServer server = new MongoGameServer();
        GameState gameState = new GameState(4, 20, 20);
        gameState.init();
        server.createNewGame(gameState);

        // Add some sample updates to test the change stream functionality
        server.joinGame("alice", server.serverID);
        Thread.sleep(5000);
        server.collection.updateOne(eq("gameID", server.serverID), new Document("$set", new Document("gameState.gridSize", 25)));
        Thread.sleep(5000);
        server.collection.updateOne(eq("gameID", server.serverID), new Document("$set", new Document("gameState.numberOfPlayers", 5)));
        Thread.sleep(10000);
    }
}
