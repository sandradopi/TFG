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
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="Comment")
@Inheritance(strategy=InheritanceType.JOINED)
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idComent")
	private Long idComent;
	
	@Column(name = "contentComment")
	@NotEmpty
	private String contentComment;
	
	@Column(name = "date")
	@NotNull
	private LocalDateTime date;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idUser")
    @NotNull
    private NormalUser fromUser;

	
	
	
	public Comment() {
				
			}

	



	public Comment(@NotEmpty String contentComment, @NotNull LocalDateTime date, @NotNull NormalUser fromUser) {
		super();
		this.contentComment = contentComment;
		this.date = date;
		this.fromUser = fromUser;
	}





	public NormalUser getFromUser() {
		return fromUser;
	}







	public void setFromUser(NormalUser fromUser) {
		this.fromUser = fromUser;
	}






	public Long getIdComent() {
		return idComent;
	}




	public void setIdComent(Long idComent) {
		this.idComent = idComent;
	}





	public String getContentComment() {
		return contentComment;
	}





	public void setContentComment(String contentComment) {
		this.contentComment = contentComment;
	}





	public LocalDateTime getDate() {
		return date;
	}




	public void setDate(LocalDateTime date) {
		this.date = date;
	}




	
	
	
	
}
