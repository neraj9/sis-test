package com.sis.repository;

import com.sis.model.Team;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class StubbedTeamRepository implements TeamRepositoryOld {
	
	private List<Team> allTeams;
	
	@PostConstruct
	public void init(){
		allTeams = new ArrayList<Team>();
		Team chelsea = new Team("Chelsea", "London", "Abramovich", 80000, "Premier League", 18, "13-06-1970");
		Team manchesterCity = new Team("ManchesterCity", "Manchester", "Qatar Airways", 72000, "Premier League-2015", 21, "13-06-1997");

		Team manUnited = new Team("ManchesterUnited", "Manchester", "ABC", 100000, "Premier League-2013", 18, "23-06-1950");
		allTeams.add(chelsea);
		allTeams.add(manchesterCity);
		allTeams.add(manUnited);
	}
	
	@Override
	public List<Team> getAllTeams(){
		return allTeams;
	}
	
	@Override
	public Team getByTeamName(String teamName){
		Team team = allTeams.stream()                        // Convert to steam
				.filter(aTeam -> teamName.equalsIgnoreCase(aTeam.getName()))
				.findAny()
				.orElse(null);

		return team;
	}
	
	@Override
	public void addTeam(Team team){
		allTeams.add(team);
	}

}
