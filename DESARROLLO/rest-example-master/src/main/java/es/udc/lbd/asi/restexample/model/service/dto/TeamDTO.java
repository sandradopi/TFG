package es.udc.lbd.asi.restexample.model.service.dto;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import es.udc.lbd.asi.restexample.model.domain.Team;

public class TeamDTO {


	private Long idTeam;
	@NotEmpty
	private String name;
    @NotNull
    private SportDTO sport;
	
	public TeamDTO(Team team) {
		this.idTeam = team.getIdTeam();
		this.name=team.getName();
		this.sport = new SportDTO(team.getSport());
	
	}

	public TeamDTO() {
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

	public SportDTO getSport() {
		return sport;
	}

	public void setSport(SportDTO sport) {
		this.sport = sport;
	}

	
	
	
	
}
