package es.udc.lbd.asi.restexample.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idTeam")
	private Long idTeam;
	
	@Column(unique = true)
	@NotEmpty
	private String name;
	
	
	public Team() {
		
	}


	public Team(Long idTeam, @NotEmpty String name) {
		super();
		this.idTeam = idTeam;
		this.name = name;
	}


	public Long getIdTeam() {
		return idTeam;
	}


	public void setIdTeam(Long idTeam) {
		this.idTeam = idTeam;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
}
