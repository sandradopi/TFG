package es.udc.lbd.asi.restexample.model.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name="idSocial")
@Table(name="SocialFriendShip")
public class SocialFriendShip extends SocialRelationShip{

	@Column(name = "notification")
	@NotNull
	private Boolean notification;
	
	public SocialFriendShip() {
		super();
	}

	
	public SocialFriendShip(Long idSocial, @NotNull LocalDateTime lastUpdate, @NotNull NormalUser userFrom,
			@NotNull NormalUser userTo) {
		super(idSocial, lastUpdate, userFrom, userTo);
		this.notification=notification;
	}


	public Boolean getNotification() {
		return notification;
	}

	public void setNotification(Boolean notification) {
		this.notification = notification;
	}

	
	
	

	

	
}
