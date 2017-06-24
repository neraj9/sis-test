package com.sis.service;

import java.util.List;

import com.sis.model.Team;

public interface TeamService {

	List<Team> getAllTeams();

	Team getByTeamName(String teamName);

	void addTeam(Team team);

	List<Team> getAllTeamsSortedByStadiumCapacity();

}
