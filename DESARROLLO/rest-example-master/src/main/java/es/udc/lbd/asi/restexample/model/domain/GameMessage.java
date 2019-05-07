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
public class GameMessage extends Comment{


	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idGame")
    @NotNull
    private Game gameComment;

	
	
	
	public GameMessage() {
				
			}


	public GameMessage(@NotEmpty String content, @NotNull LocalDateTime date, @NotNull NormalUser fromUser, @NotNull Game gameComment) {
		super(content, date, fromUser);
		this.gameComment = gameComment;
	}


	public Game getGameComment() {
		return gameComment;
	}


	public void setGameComment(Game gameComment) {
		this.gameComment = gameComment;
	}


	



	
	




	
	
	
	
}
