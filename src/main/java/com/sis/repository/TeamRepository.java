package com.sis.repository;

import com.sis.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, String>{

    public Team findByName(String name);

}