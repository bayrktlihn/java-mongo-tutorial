package io.bayrktlihn.mongotutorial.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import io.bayrktlihn.mongotutorial.entity.Person;
import io.bayrktlihn.mongotutorial.mapper.PersonMapper;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PersonMongoRepository extends SimpleMongoRepository<Person, String> {

    private final ObjectMapper objectMapper;
    private final PersonMapper personMapper;


    public PersonMongoRepository(MongoClient mongoClient, PersonMapper personMapper) {
        super(mongoClient);
        this.personMapper = personMapper;
        this.objectMapper = defaultObjectMapper();
    }


    @Override
    public String getDatabaseName() {
        return "bayrktlihn";
    }

    @Override
    public String getCollection() {
        return "person";
    }

    @Override
    public List<Person> findAll() {

        MongoCollection<Document> collection = getMongoCollection();


        List<Person> result = new ArrayList<>();
        for (Document document : collection.find()) {
            Person p = new Person();
            p.setFirstName(document.getString("firstName"));
            p.setLastName(document.getString("lastName"));
            p.setId(document.getString("_id"));
            result.add(p);
        }


        return new ArrayList<>(result);
    }


    @Override
    public Person save(Person entity) {
        MongoCollection<Document> mongoCollection = getMongoCollection();

        if (entity.getId() == null) {
            Document doc = personMapper.personToDocument(entity);
            doc.put("_id", UUID.randomUUID().toString());
            InsertOneResult insertOneResult = mongoCollection.insertOne(doc);

            entity.setId(doc.get("_id", UUID.randomUUID().toString()));

            return entity;
        }

        Document filter = new Document("_id", entity.getId());
        Document update = personMapper.personToDocument(entity);
        UpdateResult updateResult = mongoCollection.updateOne(filter, update);

        return entity;
    }

    @Override
    public Optional<Person> findById(String id) {

        MongoCollection<Document> mongoCollection = getMongoCollection();

        FindIterable<Document> documents = mongoCollection.find(Filters.eq("_id", id));

        List<Document> foundDocuments = new ArrayList<>();
        for (Document document : documents) {
            foundDocuments.add(document);
        }

        if (foundDocuments.isEmpty()) {
            return Optional.empty();
        }

        if (foundDocuments.size() > 1) {
            throw new IllegalStateException("Found more than one document");
        }

        Document document = foundDocuments.get(0);
        Person person = personMapper.documentToPerson(document);

        return Optional.ofNullable(person);

    }

    @Override
    public void deleteById(String id) {
        MongoCollection<Document> mongoCollection = getMongoCollection();

        mongoCollection.deleteOne(Filters.eq("_id", id));
    }



    private static ObjectMapper defaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
