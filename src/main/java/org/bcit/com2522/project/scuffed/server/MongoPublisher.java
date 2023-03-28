package org.bcit.com2522.project.scuffed.server;

import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.bson.Document;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class MongoPublisher implements Publisher<ChangeStreamDocument<Document>> {

    @Override
    public void subscribe(Subscriber<? super ChangeStreamDocument<Document>> s) {

    }
}
