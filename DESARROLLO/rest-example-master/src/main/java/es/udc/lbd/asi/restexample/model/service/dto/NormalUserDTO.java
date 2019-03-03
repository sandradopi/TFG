package es.udc.lbd.asi.restexample.model.service.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Team;
import es.udc.lbd.asi.restexample.model.domain.UserAuthority;


public class NormalUserDTO {
	private Long idUser;
	
	@NotEmpty
	private String login;
	
	@NotEmpty
	private String email;
	
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserAuthority authority;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String surname1;
	
	@NotEmpty
	private String surname2;
	
	private String city;

	private Integer experience;
	
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
    private Set<Team> juego = new HashSet<Team>();
	private Set<Team> favoritos = new HashSet<Team>();
	
	public NormalUserDTO() {
	}
	
	
	public NormalUserDTO(User_ user) {
		this.idUser = user.getIdUser();
		this.login = user.getLogin();
		this.email=user.getEmail();
		this.authority= UserAuthority.USER;
		this.name=user.getName();
		this.surname1=user.getSurname1();
		this.surname2=user.getSurname2();
		
		
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


	public UserAuthority getAuthority() {
		return authority;
	}

	public void setAuthority(UserAuthority authority) {
		this.authority = authority;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname1() {
		return surname1;
	}


	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}


	public String getSurname2() {
		return surname2;
	}


	public void setSurname2(String surname2) {
		this.surname2 = surname2;
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
