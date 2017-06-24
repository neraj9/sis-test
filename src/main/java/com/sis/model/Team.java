package com.sis.model;

public class Team{
	
	private String name; //assumption team name is unique
	private String city;
	private String owner;
	private Integer stadiumCapacity;
	private String competition;
	private Integer numberOfPlayers;
	private String dateOfCreation;	//DD-MM-YYYY
	
	public Team(){
	}
	
	public Team(String name, String city, String owner,
			Integer stadiumCapacity, String competition,
			Integer numberOfPlayers, String dateOfCreation) {
		super();
		this.name = name;
		this.city = city;
		this.owner = owner;
		this.stadiumCapacity = stadiumCapacity;
		this.competition = competition;
		this.numberOfPlayers = numberOfPlayers;
		this.dateOfCreation = dateOfCreation;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Integer getStadiumCapacity() {
		return stadiumCapacity;
	}
	public void setStadiumCapacity(Integer stadiumCapacity) {
		this.stadiumCapacity = stadiumCapacity;
	}
	public String getCompetition() {
		return competition;
	}
	public void setCompetition(String competition) {
		this.competition = competition;
	}
	public Integer getNumberOfPlayers() {
		return numberOfPlayers;
	}
	public void setNumberOfPlayers(Integer numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
	public String getDateOfCreation() {
		return dateOfCreation;
	}
	public void setDateOfCreation(String dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

}
