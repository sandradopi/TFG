package es.udc.lbd.asi.restexample.model.domain;



import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;

@Entity
@Table(name="SocialRelationShip")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)


public class SocialRelationShip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idSocial")
	private Long idSocial;
	
	@Column(name = "lastUpdate")
	@NotNull
	private LocalDateTime lastUpdate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idUser",insertable=false, updatable=false)
    @NotNull
    private NormalUser userFrom;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idUser",insertable=false, updatable=false)
    @NotNull
    private NormalUser userTo;
	
	
	public SocialRelationShip() {
		
    }


	public SocialRelationShip(Long idSocial, @NotNull LocalDateTime lastUpdate, @NotNull NormalUser userFrom,
			@NotNull NormalUser userTo) {
		super();
		this.idSocial = idSocial;
		this.lastUpdate = lastUpdate;
		this.userFrom = userFrom;
		this.userTo = userTo;
	}




	public NormalUser getUserFrom() {
		return userFrom;
	}



	public void setUserFrom(NormalUser userFrom) {
		this.userFrom = userFrom;
	}



	public NormalUser getUserTo() {
		return userTo;
	}

	public void setUserTo(NormalUser userTo) {
		this.userTo = userTo;
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
	
	
	
	

	
	
}
