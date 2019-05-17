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



public class UserMessageCountDTO {

    @NotNull
    private NormalUserDTO fromUser;
    @NotNull
	private Long countMessagesNotViewed;
	




    public UserMessageCountDTO() {
	}


    public UserMessageCountDTO(NormalUser user) {
    	this.fromUser= new NormalUserDTO(user);
	}



	public NormalUserDTO getFromUser() {
		return fromUser;
	}





	public void setFromUser(NormalUserDTO fromUser) {
		this.fromUser = fromUser;
	}





	public Long getCountMessagesNotViewed() {
		return countMessagesNotViewed;
	}





	public void setCountMessagesNotViewed(Long countMessagesNotViewed) {
		this.countMessagesNotViewed = countMessagesNotViewed;
	} 
    


	
	
	
	
	
	
	
	
}
