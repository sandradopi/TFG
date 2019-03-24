package es.udc.lbd.asi.restexample.model.service.dto;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import org.json.JSONObject;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Player;



public class PlayerDTO {

	private Long idPlayer;
	private Long valorationGame;
	@NotEmpty
	private String equipo;
    @NotNull
    private GameDTO game;
    @NotNull
    private NormalUserDTO player;
	

    public PlayerDTO() {
	} 
    
	public PlayerDTO(Player player) {
		this.idPlayer = player.getIdPlayer();
		this.valorationGame = player.getValorationGame();
		this.equipo = player.getEquipo();
		this.game =  new GameDTO(player.getGame());
		this.player =  new NormalUserDTO(player.getPlayer());
	}

	public Long getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(Long idPlayer) {
		this.idPlayer = idPlayer;
	}
	

	public NormalUserDTO getPlayer() {
		return player;
	}

	public void setPlayer(NormalUserDTO player) {
		this.player = player;
	}

	public Long getValorationGame() {
		return valorationGame;
	}

	public void setValorationGame(Long valorationGame) {
		this.valorationGame = valorationGame;
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public GameDTO getGame() {
		return game;
	}

	public void setGame(GameDTO game) {
		this.game = game;
	}

		
	
  
	
	
	
	
	
	
}
