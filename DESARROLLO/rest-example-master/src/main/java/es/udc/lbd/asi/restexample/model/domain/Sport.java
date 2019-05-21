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
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Sport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idSport")
	private Long idSport;
	
	@Column(unique = true)
	@NotEmpty
	private String type;
	@Column(name="rutaImagen")
	private String rutaImagen;
	
	
	@Column(name = "componenteEntrada")
	//@NotEmpty
	private String componenteEntrada;
	
	@Column(name="componenteVisualizacion")
	//@NotEmpty
	private String componenteVisualizacion;

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "SPORT_LOCATION",
    		joinColumns = {@JoinColumn(name = "idSport")},
    		inverseJoinColumns = {@JoinColumn(name = "idLocation")}
    		)
    private Set<Location> locations = new HashSet<Location>();
	
	public Sport() {
		
	}
	
	public Sport(@NotEmpty String type, String componenteEntrada,
			 String componenteVisualizacion, @NotNull Set<Location> location,String rutaImagen) {
		this.type = type;
		this.componenteEntrada = componenteEntrada;
		this.componenteVisualizacion = componenteVisualizacion;
		this.locations=location;
		this.rutaImagen=rutaImagen;
	}
	
	public Sport(@NotEmpty String type, String componenteEntrada,
			 String componenteVisualizacion) {
		this.type = type;
		this.componenteEntrada = componenteEntrada;
		this.componenteVisualizacion = componenteVisualizacion;

	}

	public Sport(@NotEmpty String type,  @NotNull Set<Location> location) {
		this.type = type;
		this.locations=location;
	}
	
	public Sport(@NotEmpty String type) {
		this.type = type;

	}


	public Set<Location> getLocations() {
		return locations;
	}

	public void setLocations(Set<Location> locations) {
		this.locations = locations;
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

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	
	
	
	
}
