package com.sis;

import com.sis.model.Team;
import com.sis.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class TestConfig {

    @Autowired
    private TeamRepository teamRepository;

    @PostConstruct
    public void init(){
        Team chelsea = new Team("Chelsea", "London", "Abramovich", 80000, "Premier League", 18, "13-06-1970");
        Team manchesterCity = new Team("ManchesterCity", "Manchester", "Qatar Airways", 72000, "Premier League-2015", 21, "13-06-1997");

        Team manUnited = new Team("ManchesterUnited", "Manchester", "ABC", 100000, "Premier League-2013", 18, "23-06-1950");

        teamRepository.save(chelsea);
        teamRepository.save(manchesterCity);
        teamRepository.save(manUnited);
    }

    @PreDestroy
    public void destroy(){
        teamRepository.delete("Chelsea");
        teamRepository.delete("ManchesterCity");
        teamRepository.delete("ManchesterUnited");
    }
}
