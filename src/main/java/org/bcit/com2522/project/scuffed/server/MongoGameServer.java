package org.bcit.com2522.project.scuffed.server;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bcit.com2522.project.scuffed.client.GameState;
import org.bson.Document;

public class MongoGameServer {

    private MongoDatabase database;

    /**
     * Connects to MongoDB server and initializes the database.
     */
    private void connectToMongoDB() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://cam:passWord@scuffedciv.nwbjiwh.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("ScuffedCiv");
    }


    public void createNewGame(GameState gameState) {
        database.createCollection(gameState.getGameID());
        Document document = Document.parse(gameState.toJSONObject().toJSONString());
        database.getCollection(gameState.getGameID()).insertOne(document);
    }

    public void gameStateToDocument(GameState gameState) {

    }
    public void joinGame() {


    }


}
