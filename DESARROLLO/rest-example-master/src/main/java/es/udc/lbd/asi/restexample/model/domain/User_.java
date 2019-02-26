package es.udc.lbd.asi.restexample.model.domain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Table(name="User_")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)


public class User_ {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idUser")
	private Long idUser;
	
	@Column(unique = true)
	@NotEmpty
	private String login;
	
	@NotEmpty
	private String password;
	
	@Column(unique = true)
	@NotEmpty
	private String email;
	
	@Column(name="name")
	@NotEmpty
	private String name;
	
	@Column(name="surname1")
	@NotEmpty
	private String surname1;
	
	@Column(name="surname2")
	@NotEmpty
	private String surname2;
	
	@Enumerated(EnumType.STRING)
    private UserAuthority authority;
	
	
	
	public User_() {
		
    }
	
	

	public User_(@NotEmpty String login, @NotEmpty String password, @NotEmpty String email,
			@NotEmpty String name, @NotEmpty String surname1, @NotEmpty String surname2, UserAuthority authority) {
		this.login = login;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname1 = surname1;
		this.surname2 = surname2;
		this.authority = authority;
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


	
	
}
