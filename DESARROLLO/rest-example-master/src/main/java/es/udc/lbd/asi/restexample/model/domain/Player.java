package es.udc.lbd.asi.restexample.model.domain;


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


@Entity
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idPlayer")
	private Long idPlayer;
	
	@Column(name = "valorationGame")
	private Long valorationGame;
	
	@Column(name = "equipo")
	@NotEmpty
	private String equipo;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idGame")
    @NotNull
    private Game game;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idUser")
    @NotNull
    private NormalUser player;
	
	public Player() {
				
			}



	public Player( Long valorationGame, @NotEmpty String equipo, @NotNull Game game,
			@NotNull NormalUser player) {
		this.valorationGame = valorationGame;
		this.equipo = equipo;
		this.game = game;
		this.player = player;
	}
	
	public Player(@NotEmpty String equipo) {
		this.equipo = equipo;
	}
	
	public Player(@NotEmpty String equipo, @NotNull Game game,
			@NotNull NormalUser player) {
		this.equipo = equipo;
		this.game = game;
		this.player = player;
	}
	
	

	public Player(Long valorationGame, @NotEmpty String equipo) {
		this.valorationGame = valorationGame;
		this.equipo = equipo;
	}



	public Long getIdPlayer() {
		return idPlayer;
	}


	public void setIdPlayer(Long idPlayer) {
		this.idPlayer = idPlayer;
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


	public Game getGame() {
		return game;
	}


	public void setGame(Game game) {
		this.game = game;
	}



	public NormalUser getPlayer() {
		return player;
	}



	public void setPlayer(NormalUser player) {
		this.player = player;
	}
	
	
	
	
}
