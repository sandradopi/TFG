package es.udc.lbd.asi.restexample.model.service.dto;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.Sport;

public class LocationDTO {

	private Long idLocation;
	@NotEmpty
	private String name;
	@NotNull
	private Double latitud;
	@NotNull
	private Double longitud;

	
	public LocationDTO(Location location) {
		this.idLocation = location.getIdLocation();
		this.name=location.getName();
		this.latitud=location.getLatitud();
		this.longitud=location.getLongitud();
		
	}

	

	public LocationDTO() {
	}

	public Long getIdLocation() {
		return idLocation;
	}

	public void setIdLocation(Long idLocation) {
		this.idLocation = idLocation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	
	
	
	
}
