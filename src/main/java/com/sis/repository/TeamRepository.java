package com.sis.repository;

import java.util.List;

import com.sis.model.Team;

public interface TeamRepository {

	List<Team> getAllTeams();

	Team getByTeamName(String teamName);

	void addTeam(Team team);

}
