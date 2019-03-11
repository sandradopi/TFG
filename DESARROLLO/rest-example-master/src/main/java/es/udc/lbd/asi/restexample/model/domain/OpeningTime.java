package es.udc.lbd.asi.restexample.model.domain;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="opening_time")
public class OpeningTime {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idOpening")
	private Long idOpening;
	
	@Column(name = "dayOfTheWeek")
	@NotEmpty
	private String dayOfTheWeek;
	
	@Column(name = "hourstart")
	@NotNull
	private LocalTime to;
	
	@Column(name="hourend")
	@NotNull
	private LocalTime from;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idLocation")
    @NotNull
    private Location location;

	public OpeningTime() {
		
	}

	public OpeningTime( @NotEmpty String dayOfTheWeek, @NotNull LocalTime to, @NotNull LocalTime from,
			@NotNull Location location) {
		this.dayOfTheWeek = dayOfTheWeek;
		this.to = to;
		this.from = from;
		this.location = location;
	}


	public OpeningTime( @NotEmpty String dayOfTheWeek, @NotNull LocalTime to, @NotNull LocalTime from) {
		this.dayOfTheWeek = dayOfTheWeek;
		this.to = to;
		this.from = from;
		
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

	public Long getIdOpening() {
		return idOpening;
	}

	public void setIdOpening(Long idOpening) {
		this.idOpening = idOpening;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	
	
}
