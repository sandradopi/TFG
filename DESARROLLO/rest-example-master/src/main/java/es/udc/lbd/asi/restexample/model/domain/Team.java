package es.udc.lbd.asi.restexample.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idTeam")
	private Long idTeam;
	
	@Column(unique = true)
	@NotEmpty
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idSport")
    @NotNull
    private Sport sport;
	
	public Team() {
		
	}


	public Team( @NotEmpty String name, @NotNull Sport sport) {
		this.name = name;
		this.sport=sport;
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


	public Sport getSport() {
		return sport;
	}


	public void setSport(Sport sport) {
		this.sport = sport;
	}


	@Override
	public String toString() {
		return "Team [idTeam=" + idTeam + ", name=" + name + ", sport=" + sport + "]";
	}
	
	
}
