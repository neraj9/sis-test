package com.sis;

import com.sis.model.Team;
import com.sis.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
    	LOG.info("Running app");
        SpringApplication.run(Application.class, args);
    	LOG.info("Finished invoking run application ");

    }

    @Bean
    public CommandLineRunner initializeDb(TeamRepository teamRepository){
        return (args) -> {
            teamRepository.deleteAll();
            Team chelsea = new Team("Chelsea", "London", "Abramovich", 80000, "Premier League", 18, "13-06-1970");
            Team manchesterCity = new Team("ManchesterCity", "Manchester", "Qatar Airways", 72000, "Premier League-2015", 21, "13-06-1997");

            Team manUnited = new Team("ManchesterUnited", "Manchester", "ABC", 100000, "Premier League-2013", 18, "23-06-1950");

            teamRepository.save(chelsea);
            teamRepository.save(manchesterCity);
            teamRepository.save(manUnited);
        };
    }
}
