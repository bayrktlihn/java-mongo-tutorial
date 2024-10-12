package io.bayrktlihn.mongotutorial.mapper;

import io.bayrktlihn.mongotutorial.entity.Person;
import org.bson.Document;

public class PersonMapper {

    public static PersonMapper INSTANCE = new PersonMapper();


    public Person documentToPerson(Document document) {

        if (document == null) {
            return null;
        }

        Person person = new Person();
        person.setFirstName(document.getString("firstName"));
        person.setLastName(document.getString("lastName"));
        person.setId(document.getString("_id"));
        return person;
    }


    public Document personToDocument(Person person) {
        if (person == null) {
            return null;
        }

        Document document = new Document();
        document.put("firstName", person.getFirstName());
        document.put("lastName", person.getLastName());
        document.put("_id", person.getId());
        return document;
    }


}
