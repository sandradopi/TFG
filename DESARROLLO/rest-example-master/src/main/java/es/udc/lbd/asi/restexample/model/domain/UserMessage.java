package es.udc.lbd.asi.restexample.model.domain;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@PrimaryKeyJoinColumn(name="idComent")
public class UserMessage extends Comment{


	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idUserTo")
    @NotNull
    private NormalUser toUser;

	
	
	
	public UserMessage() {
				
			}


	public UserMessage(@NotEmpty String content, @NotNull LocalDateTime date, @NotNull NormalUser fromUser, @NotNull NormalUser toUser) {
		super(content, date, fromUser);
		this.toUser = toUser;
	}




	public NormalUser getToUser() {
		return toUser;
	}




	public void setToUser(NormalUser toUser) {
		this.toUser = toUser;
	}

	
	




	
	
	
	
}
