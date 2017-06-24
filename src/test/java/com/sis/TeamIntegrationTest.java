package com.sis;

import com.sis.model.Team;
import com.sis.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.SortedSet;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TeamIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getTeamsShouldReturnTeams() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<List> returnedTeams =  restTemplate.exchange("http://localhost:" + port + "/sis-test/teams", HttpMethod.GET, entity, List.class);

        assertEquals(HttpStatus.OK, returnedTeams.getStatusCode());
        assertTrue(returnedTeams.getBody().size() >= 3);
    }

    @Test
    public void getTeamsSortedByCapacityShouldReturnTeamsInOrder() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<List<Team>> returnedTeams =  restTemplate.exchange("http://localhost:" + port + "/sis-test/teams/sorted/byCapacity", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Team>>(){});

        List<Team> teams = returnedTeams.getBody();

        assertEquals(HttpStatus.OK, returnedTeams.getStatusCode());
        assertTrue(teams.size() >= 3);

        assertEquals("ManchesterCity", teams.get(0).getName());
        assertEquals("Chelsea", teams.get(1).getName());
        assertEquals("ManchesterUnited", teams.get(2).getName());
    }

    @Test
    public void getTeamShouldReturnCorrectTeam() throws Exception {

        Team returnedTeam = this.restTemplate.getForObject("http://localhost:" + port + "/sis-test/teams/chelsea", Team.class);

        assertEquals("Chelsea", returnedTeam.getName());
        assertEquals("London", returnedTeam.getCity());
        assertTrue(returnedTeam.getNumberOfPlayers() == 18);
        assertEquals("Premier League", returnedTeam.getCompetition());
        assertEquals("13-06-1970", returnedTeam.getDateOfCreation());
        assertTrue(returnedTeam.getStadiumCapacity() == 80000);
        assertEquals("Abramovich", returnedTeam.getOwner());
    }

    @Test
    public void nonExistentTeamShouldReturn404Response() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<Team> returnedTeam =  restTemplate.exchange("http://localhost:" + port + "/sis-test/teams/non_existent", HttpMethod.GET, entity, Team.class);

        assertEquals(HttpStatus.NOT_FOUND, returnedTeam.getStatusCode());
    }

    @Test
    public void shouldAddNewTeam() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Team newTeam = new Team("Arsenal", "London", "XYZ", 130000, "Premier League-2001", 15, "23-06-1968");

        HttpEntity<Team> newTeamEntity = new HttpEntity<Team>(newTeam, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + "/sis-test/teams", newTeamEntity, Void.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertTrue(response.getHeaders().get("Location").equals(asList("/teams/Arsenal")));


        //Verify that a 4th team is now returned
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<List> returnedTeams =  restTemplate.exchange("http://localhost:" + port + "/sis-test/teams", HttpMethod.GET, entity, List.class);

        assertEquals(HttpStatus.OK, returnedTeams.getStatusCode());
        assertTrue(returnedTeams.getBody().size() >= 4);
    }

    @Test
    public void shouldNotAddNewTeam_AlreadyExists() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Team newTeam = new Team("Chelsea", "Edinburgh", "XYZ", 100000, "Scottish League-2101", 17, "23-06-1968");

        HttpEntity<Team> newTeamEntity = new HttpEntity<Team>(newTeam, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + "/sis-test/teams", newTeamEntity, Void.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    }

}
