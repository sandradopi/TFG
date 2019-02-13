package es.udc.lbd.asi.restexample.model.domain;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name="idUser")
@Table(name="NormalUser")
public class NormalUser extends User_ {
	
	@Column(name="city")
	@NotEmpty
	private String city;

	@Column(name="experience")
	private Integer experience;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	public NormalUser() {
		super();
	}

	public NormalUser( @NotEmpty String login, @NotEmpty String password, @NotEmpty String email,
			@NotEmpty String name, @NotEmpty String surname1, @NotEmpty String surname2,
			UserAuthority authority,String city,Integer experience, Date birthday) {
		super(login, password, email, name, surname1, surname2, authority);
		this.city= city;
		this.experience=experience;
		this.birthday=birthday;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}


	
	


	

	

}
