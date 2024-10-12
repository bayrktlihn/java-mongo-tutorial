package io.bayrktlihn.mongotutorial.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public abstract class SimpleMongoRepository<TENTINTY, TID> implements MongoRepository<TENTINTY, TID> {

    private final MongoClient mongoClient;

    protected SimpleMongoRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }


    protected MongoClient getMongoClient() {
        return mongoClient;
    }

    public abstract String getDatabaseName();

    public abstract String getCollection();

    protected MongoCollection<Document> getMongoCollection() {
        MongoClient mongoClient = getMongoClient();
        MongoDatabase database = mongoClient.getDatabase(getDatabaseName());
        return database.getCollection(getCollection());
    }


}
