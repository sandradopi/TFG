package es.udc.lbd.asi.restexample.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idLocation")
	private Long idLocation;
	
	@Column(name = "name")
	@NotEmpty
	private String name;
	
	@Column(name="costPerHour")
	@NotEmpty
	private Long costPerHour;
	
	@Column(name="latitud")
	@NotEmpty
	private Double latitud;
	
	@Column(name="longitud")
	@NotEmpty
	private Double longitud;

	
	public Location() {
		
	}


	public Location(@NotEmpty String name, @NotEmpty Long costPerHour, @NotEmpty Double latitud,
			@NotEmpty Double longitud) {
		super();
		this.name = name;
		this.costPerHour = costPerHour;
		this.latitud = latitud;
		this.longitud = longitud;
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


	public Long getCostPerHour() {
		return costPerHour;
	}


	public void setCostPerHour(Long costPerHour) {
		this.costPerHour = costPerHour;
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
