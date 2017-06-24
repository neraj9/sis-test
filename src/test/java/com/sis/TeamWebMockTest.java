package com.sis;

import com.sis.controller.TeamController;
import com.sis.model.Team;
import com.sis.repository.TeamRepository;
import com.sis.service.TeamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is a sample test for the web layer to showcase mockMVC based tests.
 * Comprehensive integration tests are written in TeamIntegrationTest.java
 */

@RunWith(SpringRunner.class)
@WebMvcTest(TeamController.class)
public class TeamWebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService service;

    @Test
    public void getTeamsShouldReturnTeamsFromService() throws Exception {

        List<Team> teams = getTeams();
        when(service.getAllTeams()).thenReturn(teams);

        MvcResult result = mockMvc.perform(get("/teams"))
                .andExpect(status().isOk())
                .andReturn();
        String stringResult = result.getResponse().getContentAsString();
        boolean containsTeamName = stringResult.contains("\"name\":\"ManchesterCity\"");

        assertTrue(containsTeamName);
    }

    public List<Team> getTeams() {
        List<Team> allTeams = new ArrayList<Team>();
        Team chelsea = new Team("Chelsea", "London", "Abramovich", 80000, "Premier League", 18, "13-06-1970");
        Team manchesterCity = new Team("ManchesterCity", "Manchester", "Qatar Airways", 72000, "Premier League-2015", 21, "13-06-1997");

        Team manUnited = new Team("ManchesterUnited", "Manchester", "ABC", 100000, "Premier League-2013", 18, "23-06-1950");
        allTeams.add(chelsea);
        allTeams.add(manchesterCity);
        allTeams.add(manUnited);

        return allTeams;
    }
}
