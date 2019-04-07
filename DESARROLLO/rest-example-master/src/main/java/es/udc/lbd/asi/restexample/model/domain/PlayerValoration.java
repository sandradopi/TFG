package es.udc.lbd.asi.restexample.model.domain;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
public class PlayerValoration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idPlayerValoration")
	private Long idPlayerValoration;
	
	@Column(name = "valoration")
	@NotNull
	private Long valoration;
	
	@Column(name = "review")
	private String review;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idPlayer")
    @NotNull
    private Player player;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idUser")
    @NotNull
    private NormalUser user;
	
	
	public PlayerValoration() {
				
			}

	

	public PlayerValoration(@NotNull Long valoration,  String review, @NotNull Player player,
			@NotNull NormalUser user) {
		this.valoration = valoration;
		this.review = review;
		this.player = player;
		this.user = user;
	}



	public Long getIdPlayerValoration() {
		return idPlayerValoration;
	}


	public void setIdPlayerValoration(Long idPlayerValoration) {
		this.idPlayerValoration = idPlayerValoration;
	}


	public Long getValoration() {
		return valoration;
	}


	public void setValoration(Long valoration) {
		this.valoration = valoration;
	}


	public String getReview() {
		return review;
	}


	public void setReview(String review) {
		this.review = review;
	}


	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}


	public NormalUser getUser() {
		return user;
	}


	public void setUser(NormalUser user) {
		this.user = user;
	}

	
	
	


	
	
	
	
}
