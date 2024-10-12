package io.bayrktlihn.mongotutorial;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.bayrktlihn.mongotutorial.entity.Person;
import io.bayrktlihn.mongotutorial.mapper.PersonMapper;
import io.bayrktlihn.mongotutorial.repository.PersonMongoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoTutorialApplication {
    public static void main(String[] args) {

        log.info("Starting Mongo Tutorial Application");

        String connectionString = "mongodb://bayrktlihn:bayrktlihn@localhost:27017/bayrktlihn?authSource=admin";
        MongoClient mongoClient = MongoClients.create(connectionString);

        PersonMongoRepository personMongoRepository = new PersonMongoRepository(mongoClient, PersonMapper.INSTANCE);
//        List<Person> people = personMongoRepository.findAll();


        Person person = new Person();
        person.setFirstName("alihan");
        person.setLastName("bayraktar");

        personMongoRepository.save(person);

    }

}
