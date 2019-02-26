package es.udc.lbd.asi.restexample.model.domain;



import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

	private String city;

	@Column(name="experience")
	private Integer experience;
	
	
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
    		name = "USER_MYGROUPS",
    		joinColumns = {@JoinColumn(name = "idUser")},
    		inverseJoinColumns = {@JoinColumn(name = "idTeam")}
    		)
    private Set<Team> juego = new HashSet<Team>();
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
    		name = "USER_FAVGROUPS",
    		joinColumns = {@JoinColumn(name = "idUser")},
    		inverseJoinColumns = {@JoinColumn(name = "idTeam")}
    		)
    private Set<Team> favoritos = new HashSet<Team>();
	
	public NormalUser() {
		super();
	}

	public NormalUser( @NotEmpty String login, @NotEmpty String password, @NotEmpty String email,
			@NotEmpty String name, @NotEmpty String surname1, @NotEmpty String surname2,
			UserAuthority authority,String city,Integer experience, Date birthday, Set<Team> juego, Set<Team> favoritos) {
		super(login, password, email, name, surname1, surname2, authority);
		this.city= city;
		this.experience=experience;
		this.birthday=birthday;
		this.juego=juego;
		this.favoritos=favoritos;
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

	public Set<Team> getJuego() {
		return juego;
	}

	public void setJuego(Set<Team> juego) {
		this.juego = juego;
	}

	public Set<Team> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(Set<Team> favoritos) {
		this.favoritos = favoritos;
	}


	
	


	

	

}
