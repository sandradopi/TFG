package es.udc.lbd.asi.restexample.model.domain;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@PrimaryKeyJoinColumn(name="idUser")
@Table(name="AdminUser")
public class AdminUser extends User_{

	public AdminUser() {
		super();
	}

	public AdminUser(Long idUser, @NotEmpty String login, @NotEmpty String password, @NotEmpty String email,
			@NotEmpty String name, @NotEmpty String surname1, @NotEmpty String surname2, UserAuthority authority) {
		super(idUser, login, password, email, name, surname1, surname2, authority);
		// TODO Auto-generated constructor stub
	}

	

	

	
}
