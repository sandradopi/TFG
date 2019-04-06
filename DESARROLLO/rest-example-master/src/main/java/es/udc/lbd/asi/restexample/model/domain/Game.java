package es.udc.lbd.asi.restexample.model.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.json.JSONObject;


@Entity
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idGame")
	private Long idGame;
	
	@Column(name = "date")
	@NotNull
	private LocalDate date;
	
	@Column(name = "timeStart")
	@NotNull
	private LocalTime timeStart;
	
	@Column(name = "timeEnd")
	@NotNull
	private LocalTime timeEnd;
	
	@Column(name="maxPlayers")
	@NotNull
	private Long maxPlayers;
	
	@Column(name="minPlayers")
	@NotNull
	private Long minPlayers;
	
	
	@Type(type = "string")
	@Column(name = "result")
	private String result;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idSport")
    @NotNull
    private Sport sport;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idLocation")
    @NotNull
    private Location location;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idUser")
    @NotNull
    private NormalUser creator;
	
	
	public Game() {
				
			}
	
	public Game(@NotEmpty LocalDate date, @NotEmpty LocalTime timeStart, @NotEmpty LocalTime timeEnd, @NotNull Long maxPlayers,
			@NotNull Long minPlayers,  String result, @NotNull Sport sport, @NotNull Location location,
			@NotNull NormalUser creator) {

		this.date = date;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.maxPlayers = maxPlayers;
		this.minPlayers = minPlayers;
		this.result = result;
		this.sport = sport;
		this.location = location;
		this.creator = creator;
	}
	
	public Game(@NotEmpty LocalDate date, @NotEmpty LocalTime timeStart, @NotEmpty LocalTime timeEnd,@NotNull  Long maxPlayers,
			@NotNull Long minPlayers) {

		this.date = date;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.maxPlayers = maxPlayers;
		this.minPlayers = minPlayers;
		
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

	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Sport getSport() {
		return sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public NormalUser getCreator() {
		return creator;
	}

	public void setCreator(NormalUser creator) {
		this.creator = creator;
	}
	
	
	
}
