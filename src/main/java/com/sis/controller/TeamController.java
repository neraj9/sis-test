package com.sis.controller;

import com.sis.common.exception.AlreadyExistsException;
import com.sis.common.exception.NotFoundException;
import com.sis.model.Team;
import com.sis.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping(value = "/teams", produces = "application/json", consumes = "application/json")
@Api(value="onlinestore", description="Operations pertaining to teams")
public class TeamController {

    private final TeamService service;
    
	private static final Logger LOG = LoggerFactory.getLogger(TeamController.class);

    public TeamController(TeamService service) {
        this.service = service;
    }

    @ApiOperation(value = "View a list of available teams", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
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
