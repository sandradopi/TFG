package es.udc.lbd.asi.restexample.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Sport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idSport")
	private Long idSport;
	
	@Column(unique = true)
	@NotEmpty
	private String type;
	
	
	@Column(name = "componenteEntrada")
	//@NotEmpty
	private String componenteEntrada;
	
	@Column(name="componenteVisualizacion")
	//@NotEmpty
	private String componenteVisualizacion;

	
	public Sport() {
		
	}
	
	public Sport(@NotEmpty String type, String componenteEntrada,
			 String componenteVisualizacion) {
		this.type = type;
		this.componenteEntrada = componenteEntrada;
		this.componenteVisualizacion = componenteVisualizacion;
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

	
	
	
	
}
