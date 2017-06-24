package com.sis.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sis.common.exception.AlreadyExistsException;
import com.sis.common.exception.NotFoundException;
import com.sis.model.Team;
import com.sis.service.TeamService;


@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService service;
    
	private static final Logger LOG = LoggerFactory.getLogger(TeamController.class);


    public TeamController(TeamService service) {
        this.service = service;
    }

    @RequestMapping(method=RequestMethod.GET)
    @ResponseStatus( HttpStatus.OK )
    public ResponseEntity<List<Team>> getAllTeams() {
        LOG.info("In getAllTeams");
        return ResponseEntity.ok(service.getAllTeams());
    }
    
    
    @RequestMapping(value="/sorted/byCapacity", method =RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK )
    public ResponseEntity<List<Team>> getAllTeamsSortedbyCapacity() {
        LOG.info("In getAllTeamsSortedbyCapacity");
        return ResponseEntity.ok(service.getAllTeamsSortedByStadiumCapacity());
    }
    
    
    @RequestMapping("/{teamName}")
    @ResponseStatus( HttpStatus.OK )
    public ResponseEntity<Team> getTeam(@PathVariable(value = "teamName") String teamName) {
    	LOG.info("In getTeam");
    	try{
    		return ResponseEntity.ok(service.getByTeamName(teamName));
    	}
    	catch(NotFoundException nfe){
    	      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    }
    
    
    @RequestMapping(method=RequestMethod.POST)
    @ResponseStatus( HttpStatus.CREATED )
    public ResponseEntity<Void> create(@RequestBody Team team) throws URISyntaxException{
        LOG.info("In create");

        try {
          service.addTeam(team);
          return ResponseEntity.created(new URI("/teams/"+team.getName())).build();
     }
     catch (AlreadyExistsException e) {
    	 //ResourceAlreadyExists
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
     }
    }

}
