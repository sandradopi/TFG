package es.udc.lbd.asi.restexample.model.domain;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Embeddable
public class OpeningTime {
	
	@Column(name = "dayOfTheWeek")
	@NotEmpty
	private String dayOfTheWeek;
	
	@Column(name = "to")
	@NotNull
	private LocalTime to;
	
	@Column(name="from")
	@NotNull
	private LocalTime from;

	public OpeningTime() {
		
	}

	public OpeningTime(@NotEmpty String dayOfTheWeek, @NotNull LocalTime to, @NotNull LocalTime from) {
		super();
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
	
	
	
}
