package io.bayrktlihn.mongotutorial.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.bayrktlihn.mongotutorial.entity.Person;
import io.bayrktlihn.mongotutorial.mapper.PersonMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonMongoRepositoryTest {


    private PersonMongoRepository personMongoRepository;

    @BeforeEach
    void setUp() {
        String connectionString = "mongodb://bayrktlihn:bayrktlihn@localhost:27017/bayrktlihn?authSource=admin";
        MongoClient mongoClient = MongoClients.create(connectionString);

        personMongoRepository = new PersonMongoRepository(mongoClient, PersonMapper.INSTANCE);
    }


    @Test
    void findAll() {
        List<Person> people = personMongoRepository.findAll();
    }

    @Test
    void findById() {
        Optional<Person> foundPerson = personMongoRepository.findById("c4d7efb9-3098-40c7-b65d-1f8ea6da6736");
    }

    @Test
    void deleteById() {
        personMongoRepository.deleteById("c4d7dfb9-3098-40c7-b65d-1f8ea6da6736");
    }

    @Test
    void save() {
        Person person = new Person();
        person.setFirstName("Alihan");
        person.setLastName("Bayraktar");

        Person savedPerson = personMongoRepository.save(person);

        Assertions.assertNotNull(savedPerson.getId());
    }


}