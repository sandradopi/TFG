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
	@NotNull
	private Long valorationGame;
	
	@Column(name = "equipo")
	@NotEmpty
	private String equipo;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idGame")
    @NotNull
    private Game game;
	
	public Player() {
				
			}


	public Player( @NotNull Long valorationGame, @NotEmpty String equipo, @NotNull Game game) {
		this.valorationGame = valorationGame;
		this.equipo = equipo;
		this.game = game;
	}

	public Player( @NotNull Long valorationGame, @NotEmpty String equipo) {
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
	
	
	
	
}
