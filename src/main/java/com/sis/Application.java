package com.sis;

import com.sis.model.Team;
import com.sis.model.neo.Person;
import com.sis.repository.TeamRepository;
import com.sis.repository.neo.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

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

    @Bean
    CommandLineRunner demo(PersonRepository personRepository) {
        return args -> {

            personRepository.deleteAll();

            Person greg = new Person("Greg");
            Person roy = new Person("Roy");
            Person craig = new Person("Craig");

            Person jim = new Person("Jim");

            jim.worksWith(roy);
            List<Person> team = Arrays.asList(greg, roy, craig);

            LOG.info("Before linking up with Neo4j...");

            team.stream().forEach(person -> LOG.info("\t" + person.toString()));

            personRepository.save(greg);
            personRepository.save(roy);
            personRepository.save(craig);
            personRepository.save(jim);


            greg = personRepository.findByName(greg.getName());
            greg.worksWith(roy);
            greg.worksWith(craig);
            personRepository.save(greg);

            roy = personRepository.findByName(roy.getName());
            roy.worksWith(craig);
            // We already know that roy works with greg
            personRepository.save(roy);

            // We already know craig works with roy and greg

            LOG.info("Lookup each person by name...");
            team.stream().forEach(person -> LOG.info(
                    "\t" + personRepository.findByName(person.getName()).toString()));
        };
    }
}
