package es.udc.lbd.asi.restexample.model.service.dto;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import org.json.JSONObject;

import es.udc.lbd.asi.restexample.model.domain.Game;



public class GameDTO {

	
	private Long idGame;
	
	@NotNull
	private LocalDate date;
	
	@NotNull
	private LocalTime timeStart;
	
	@NotNull
	private LocalTime timeEnd;
	
	private Long maxPlayers;
	
	private Long minPlayers;
	
	private Long price;
	
	private JSONObject result;

    @NotNull
    private SportDTO sport;
	
    @NotNull
    private LocationDTO location;
	
	
    @NotNull
    private NormalUserDTO creator;


    public GameDTO() {
	} 
    
	public GameDTO(Game game) {
		super();
		this.idGame = game.getIdGame();
		this.date = game.getDate();
		this.timeStart = game.getTimeStart();
		this.timeEnd = game.getTimeEnd();
		this.maxPlayers = game.getMaxPlayers();
		this.minPlayers = game.getMinPlayers();
		this.price = game.getPrice();
		this.result = game.getResult();
		this.sport = new SportDTO(game.getSport());
		this.location = new LocationDTO(game.getLocation());
		this.creator =  new NormalUserDTO(game.getCreator());;
	}


	public Long getIdGame() {
		return idGame;
	}


	public void setIdGame(Long idGame) {
		this.idGame = idGame;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public LocalTime getTimeStart() {
		return timeStart;
	}


	public void setTimeStart(LocalTime timeStart) {
		this.timeStart = timeStart;
	}


	public LocalTime getTimeEnd() {
		return timeEnd;
	}


	public void setTimeEnd(LocalTime timeEnd) {
		this.timeEnd = timeEnd;
	}


	public Long getMaxPlayers() {
		return maxPlayers;
	}


	public void setMaxPlayers(Long maxPlayers) {
		this.maxPlayers = maxPlayers;
	}


	public Long getMinPlayers() {
		return minPlayers;
	}


	public void setMinPlayers(Long minPlayers) {
		this.minPlayers = minPlayers;
	}


	public Long getPrice() {
		return price;
	}


	public void setPrice(Long price) {
		this.price = price;
	}


	public JSONObject getResult() {
		return result;
	}


	public void setResult(JSONObject result) {
		this.result = result;
	}


	public SportDTO getSport() {
		return sport;
	}


	public void setSport(SportDTO sport) {
		this.sport = sport;
	}


	public LocationDTO getLocation() {
		return location;
	}


	public void setLocation(LocationDTO location) {
		this.location = location;
	}


	public NormalUserDTO getCreator() {
		return creator;
	}


	public void setCreator(NormalUserDTO creator) {
		this.creator = creator;
	}

	
  
	
	
	
	
	
	
}
