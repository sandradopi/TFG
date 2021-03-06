package es.udc.lbd.asi.restexample.model.service.dto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;

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



public class ActivitiesDTO {

	@NotNull
	private Long idActivities; //Guardariamos el idGame, ya que es la creacion de un juego, comentar en un juego o apuntarse a un juego
	@NotEmpty
	private String action;
	@NotNull
	private LocalDateTime date;
    @NotNull
    private NormalUserDTO friend;
    @NotNull
    private Boolean comment;//Es un booleano para saber si se trata de que un amigo añadio un comentario, ya que en ese caso no iriamos a GameDetail, si no a la pantalla de comentarios de ese juego



    public Boolean getComment() {
		return comment;
	}



	public void setComment(Boolean comment) {
		this.comment = comment;
	}



	public ActivitiesDTO(NormalUser user) {
    	this.friend= new NormalUserDTO(user);
	}



	public Long getIdActivities() {
		return idActivities;
	}



	public void setIdActivities(Long idActivities) {
		this.idActivities = idActivities;
	}



	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
	}



	public LocalDateTime getDate() {
		return date;
	}



	public void setDate(LocalDateTime date) {
		this.date = date;
	}



	public NormalUserDTO getFriend() {
		return friend;
	}



	public void setFriend(NormalUserDTO friend) {
		this.friend = friend;
	}


	

	
	
	
	
}
