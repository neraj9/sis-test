package com.sis.repository.neo;

import com.sis.model.neo.Person;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface PersonRepository extends GraphRepository<Person> {

    Person findByName(String name);
}
