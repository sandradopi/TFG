package es.udc.lbd.asi.restexample.model.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name="idSocial")
@Table(name="SocialBlock")
public class SocialBlock extends SocialRelationShip{

	public SocialBlock() {
		super();
	}

	public SocialBlock(Long idSocial, @NotNull LocalDateTime lastUpdate, @NotNull NormalUser userFrom,
			@NotNull NormalUser userTo) {
		super(idSocial, lastUpdate, userFrom, userTo);
		
	}

	

	

	

	

	
}
