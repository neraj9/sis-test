package com.sis.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.sis.controller.TeamController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sis.common.exception.AlreadyExistsException;
import com.sis.common.exception.NotFoundException;
import com.sis.model.Team;
import com.sis.repository.TeamRepository;

@Component
public class TeamServiceImpl implements TeamService{

	private static final Logger LOG = LoggerFactory.getLogger(TeamServiceImpl.class);


	private TeamRepository teamRepository;
	
	
	public TeamServiceImpl(TeamRepository teamRepository){
		this.teamRepository = teamRepository;
	}
	

		@Override
		public List<Team> getAllTeams(){
			LOG.info("In getAllTeams");
			return teamRepository.getAllTeams();
		}
		
		
		@Override
		public List<Team> getAllTeamsSortedByStadiumCapacity(){
			LOG.info("In getAllTeamsSortedByStadiumCapacity");
			List<Team> teams = getAllTeams();

			Comparator<Team> stadiumCapacityComparator = (o1, o2)->o1.getStadiumCapacity().compareTo(o2.getStadiumCapacity());
			teams.sort(stadiumCapacityComparator);

			return teams;
		}
		
		@Override
		public Team getByTeamName(String teamName){
			LOG.info("In getByTeamName");

			Team team =  teamRepository.getByTeamName(teamName);
			
			if(team == null){
				throw new NotFoundException();
			}
			
			return team;
		}
		
		@Override
		public void addTeam(Team team){
			LOG.info("In addTeam");

			//if team by name is already there throw an exception
			
			if(teamRepository.getByTeamName(team.getName()) != null){
				//team already exists
				throw new AlreadyExistsException();
			}
			teamRepository.addTeam(team);
		}

	}