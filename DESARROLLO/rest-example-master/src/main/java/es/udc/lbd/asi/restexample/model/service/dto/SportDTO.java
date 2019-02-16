package es.udc.lbd.asi.restexample.model.service.dto;


import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.Sport;

public class SportDTO {

	private Long idSport;
	@NotEmpty
	private String type;
	private String componenteEntrada;
	private String componenteVisualizacion;
	private Set<LocationDTO> locations = new HashSet<LocationDTO>();
	
	public SportDTO(Sport sport) {
		this.idSport = sport.getIdSport();
		this.type = sport.getType();
		this.componenteEntrada = sport.getComponenteEntrada();
		this.componenteVisualizacion = sport.getComponenteVisualizacion();
		 for(Location a: sport.getLocations()){
        	 this.locations.add(new LocationDTO(a));
         }
	}

	public SportDTO() {
	}

	public Long getIdSport() {
		return idSport;
	}

	public void setIdSport(Long idSport) {
		this.idSport = idSport;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComponenteEntrada() {
		return componenteEntrada;
	}

	public void setComponenteEntrada(String componenteEntrada) {
		this.componenteEntrada = componenteEntrada;
	}

	public String getComponenteVisualizacion() {
		return componenteVisualizacion;
	}

	public void setComponenteVisualizacion(String componenteVisualizacion) {
		this.componenteVisualizacion = componenteVisualizacion;
	}

	public Set<LocationDTO> getLocations() {
		return locations;
	}

	public void setLocations(Set<LocationDTO> locations) {
		this.locations = locations;
	}

	
	
	
	
	
}
