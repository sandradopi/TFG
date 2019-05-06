package es.udc.lbd.asi.restexample.model.service.dto;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

import es.udc.lbd.asi.restexample.model.domain.Comment;
import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Player;
import es.udc.lbd.asi.restexample.model.domain.PlayerValoration;



public class GameMessageDTO {


	private Long idComent;
	@NotEmpty
	private String content;
	@NotNull
	private LocalDateTime date;
    @NotNull
    private NormalUserDTO fromUser;
    @NotNull
    private GameDTO toGame;




    public GameMessageDTO() {
	} 
    
	public GameMessageDTO(Comment comment) {
		this.idComent = comment.getIdComent();
		this.content = comment.getContent();
		this.date = comment.getDate();
		this.fromUser =  new NormalUserDTO(comment.getFromUser());

	}

	public Long getIdComent() {
		return idComent;
	}

	public void setIdComent(Long idComent) {
		this.idComent = idComent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public NormalUserDTO getFromUser() {
		return fromUser;
	}

	public void setFromUser(NormalUserDTO fromUser) {
		this.fromUser = fromUser;
	}

	public GameDTO getToGame() {
		return toGame;
	}

	public void setToGame(GameDTO toGame) {
		this.toGame = toGame;
	}

	

	
	
	
	
	
	
	
}
