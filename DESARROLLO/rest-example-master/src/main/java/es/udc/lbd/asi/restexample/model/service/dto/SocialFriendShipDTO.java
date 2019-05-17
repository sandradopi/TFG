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
import es.udc.lbd.asi.restexample.model.domain.SocialFriendShip;
import es.udc.lbd.asi.restexample.model.domain.SocialRelationShip;



public class SocialFriendShipDTO {


	
	private Long idSocial;
	private LocalDateTime lastUpdate;
    @NotNull
    private NormalUserDTO userFrom;
    @NotNull
    private NormalUserDTO userTo;
	private Boolean notification;
	



    public SocialFriendShipDTO() {
	} 
    
	public SocialFriendShipDTO(SocialFriendShip social) {
		this.idSocial = social.getIdSocial();
		this.lastUpdate = social.getLastUpdate();
		this.userFrom = new NormalUserDTO(social.getUserFrom());
		this.userTo =  new NormalUserDTO(social.getUserTo());
		this.notification=social.getNotification();

	}

	public Long getIdSocial() {
		return idSocial;
	}

	public void setIdSocial(Long idSocial) {
		this.idSocial = idSocial;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public NormalUserDTO getUserFrom() {
		return userFrom;
	}

	public void setUserFrom(NormalUserDTO userFrom) {
		this.userFrom = userFrom;
	}

	public NormalUserDTO getUserTo() {
		return userTo;
	}

	public void setUserTo(NormalUserDTO userTo) {
		this.userTo = userTo;
	}

	public Boolean getNotification() {
		return notification;
	}

	public void setNotification(Boolean notification) {
		this.notification = notification;
	}

	
	
	
	
	
	
	
}
