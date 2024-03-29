package es.udc.lbd.asi.restexample.model.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idLocation")
	private Long idLocation;
	
	@Column(name = "name")
	@NotEmpty
	private String name;
	
	
	@Column(name="latitud")
	@NotNull
	private Double latitud;
	
	@Column(name="longitud")
	@NotNull
	private Double longitud;

	@ManyToMany(mappedBy= "locations",fetch = FetchType.LAZY)
    private Set<Sport> sports = new HashSet<Sport>();
	
	

	public Location() {
		
	}
	
	

	public Location( @NotEmpty String name, @NotNull Double latitud,
			@NotNull Double longitud, Set<Sport> sports) {
		this.name = name;
		this.latitud = latitud;
		this.longitud = longitud;
		this.sports = sports;
	}



	public Location(@NotEmpty String name,@NotEmpty Double latitud,
			@NotEmpty Double longitud) {
		this.name = name;
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
	
	public Set<Sport> getSports() {
		return sports;
	}


	public void setSports(Set<Sport> sports) {
		this.sports = sports;
	}
}
