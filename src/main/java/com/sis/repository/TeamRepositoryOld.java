package com.sis.repository;

import com.sis.model.Team;

import java.util.List;

public interface TeamRepositoryOld {

	List<Team> getAllTeams();

	Team getByTeamName(String teamName);

	void addTeam(Team team);

}
