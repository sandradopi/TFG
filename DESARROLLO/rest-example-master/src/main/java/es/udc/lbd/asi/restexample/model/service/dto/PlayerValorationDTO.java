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
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Player;
import es.udc.lbd.asi.restexample.model.domain.PlayerValoration;



public class PlayerValorationDTO {


	private Long idPlayerValoration;
	@NotNull
	private Long valoration;
	private String review;
    @NotNull
    private PlayerDTO player;
    @NotNull
    private NormalUserDTO user;
	


    public PlayerValorationDTO() {
	} 
    
	public PlayerValorationDTO(PlayerValoration playerValoration) {
		this.idPlayerValoration = playerValoration.getIdPlayerValoration();
		this.valoration = playerValoration.getValoration();
		this.review = playerValoration.getReview();
		this.player =  new PlayerDTO(playerValoration.getPlayer());
		this.user =  new NormalUserDTO(playerValoration.getUser());

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

	public PlayerDTO getPlayer() {
		return player;
	}

	public void setPlayer(PlayerDTO player) {
		this.player = player;
	}

	public NormalUserDTO getUser() {
		return user;
	}

	public void setUser(NormalUserDTO user) {
		this.user = user;
	}

	
	
	
  
	
	
	
	
	
	
}
