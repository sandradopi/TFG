package es.udc.lbd.asi.restexample.model.service.dto;
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

import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.OpeningTime;
import es.udc.lbd.asi.restexample.model.domain.Team;

public class OpeningDTO {
	
	private Long idOpening;
	@NotEmpty
	private String dayOfTheWeek;
	@NotNull
	private LocalTime to;
	@NotNull
	private LocalTime from;
    @NotNull
    private LocationDTO location;

	public OpeningDTO(OpeningTime opening) {
		this.idOpening = opening.getIdOpening();
		this.dayOfTheWeek=opening.getDayOfTheWeek();
		this.to=opening.getTo();
		this.from=opening.getFrom();
		this.location = new LocationDTO(opening.getLocation());
	
	}

	public OpeningDTO() {
	}

	public Long getIdOpening() {
		return idOpening;
	}

	public void setIdOpening(Long idOpening) {
		this.idOpening = idOpening;
	}

	public String getDayOfTheWeek() {
		return dayOfTheWeek;
	}

	public void setDayOfTheWeek(String dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}

	public LocalTime getTo() {
		return to;
	}

	public void setTo(LocalTime to) {
		this.to = to;
	}

	public LocalTime getFrom() {
		return from;
	}

	public void setFrom(LocalTime from) {
		this.from = from;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	
	
	
	
	
}
