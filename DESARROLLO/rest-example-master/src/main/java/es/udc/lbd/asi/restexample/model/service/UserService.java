package es.udc.lbd.asi.restexample.model.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.udc.lbd.asi.restexample.model.domain.AdminUser;
import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.UserAuthority;
import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.exception.EmailIncorrect;
import es.udc.lbd.asi.restexample.model.exception.PasswordTooShort;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.repository.GameDAO;
import es.udc.lbd.asi.restexample.model.repository.TeamDAO;
import es.udc.lbd.asi.restexample.model.repository.UserDAO;
import es.udc.lbd.asi.restexample.model.service.dto.AdminUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.TeamDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserDTO;
import es.udc.lbd.asi.restexample.security.SecurityUtils;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserService implements UserServiceInterface{

  @Autowired
  private UserDAO userDAO;
  @Autowired
  private TeamDAO teamDAO;
  @Autowired
  private GameDAO gameDAO;
  
  
  @Autowired
  private PasswordEncoder passwordEncoder;

  @PreAuthorize("hasAuthority('USER')")
  @Override
	public List<GameDTO> findGamesCreated(String login) {
	  return userDAO.findAllGamesCreated(login).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
	  }
	 
		@PreAuthorize("hasAuthority('USER')")
		@Override
		public NormalUserDTO findByLogin(String login)  {
			 NormalUser usuarioDevuelto= userDAO.findByLoginNormal(login);
		   	 NormalUserDTO userTransformado= new NormalUserDTO(usuarioDevuelto);
		   	 userTransformado.setBirthday(usuarioDevuelto.getBirthday());
		   	 userTransformado.setCity(usuarioDevuelto.getCity());
		   	 userTransformado.setExperience(usuarioDevuelto.getExperience());
		   	 
		   	 userTransformado.getFavoritos().clear();
		   	 usuarioDevuelto.getFavoritos().forEach(fav -> {
		    	 userTransformado.getFavoritos().add(new TeamDTO(teamDAO.findById(fav.getIdTeam())));
		    });
		   	 
		   	 userTransformado.getJuego().clear();
		   	 usuarioDevuelto.getJuego().forEach(fav -> {
		    	 userTransformado.getJuego().add(new TeamDTO(teamDAO.findById(fav.getIdTeam())));
		    });
		   	 
		    
	   	 return userTransformado;
	   }

  		@PreAuthorize("hasAuthority('ADMIN')")
		@Override
		public List<UserDTO> findAll() {
			 return userDAO.findAll().stream().map(user -> new UserDTO(user)).collect(Collectors.toList());}
		
		
	     @Transactional(readOnly = false)
	     @Override
		 public void registerUser(String login, String email,String password, String name, String surname1, String surname2, String city, Date birthday) throws UserLoginEmailExistsException, ParseException, RequiredFieldsException, PasswordTooShort, EmailIncorrect {
	    
	    	     String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" +
	    	    	      "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
	    	     
	    	     Pattern pattern = Pattern.compile(emailPattern);
	    	     
	    	     //Comprobaciones Servicio del Registro
	    	     
	    	    if(login == null){ //Login vacio
		        	  throw new RequiredFieldsException("El login es un campo requerido");
		         }
		        else if (userDAO.findByLogin(login) != null) {
		             throw new UserLoginEmailExistsException("El login " + login + " ya existe");//login existente
		        }
		        
		        if (email== null){//Que el email esté vacio
		        	 throw new RequiredFieldsException("The email is a required field");
		        }else if (userDAO.findByEmail(email) != null) {
		             throw new UserLoginEmailExistsException("El email " +email + " ya existe"); //email existente
		        } else{
	    	    	 Matcher matcher = pattern.matcher(email);
	    	    	 if (!matcher.matches()){
	    	    	 throw new EmailIncorrect("El email: "+ email +" no sigue el formato adecuado, revísalo! ");
	    	    	 }
	    	   
		        }
	    	    
		        if(name == null){ //Nombre vacio
		        	  throw new RequiredFieldsException("El nombre es un campo requerido");
		         }
		        
		        if(surname1 == null){ //Apellido1 vacio
		        	  throw new RequiredFieldsException("El primer apellido es un campo requerido");
		         }
		        
		        if(surname2 == null){ //Apellido2 vacio
		        	  throw new RequiredFieldsException("El segundo apellido es un campo requerido");
		         }
		        
		       
		      
		        if(password == null){ //password vacio
		        	  throw new RequiredFieldsException("La contraseña es un campo requerido");
		           
		        }else if(password.length()<4){ //Password muy corta
		        	throw new PasswordTooShort("La contraseña es muy corta, minimo 4 digitos");
		        }

	    	     registerUser(login,email, password, false, name, surname1, surname2, city,birthday);
	     }
	     
	     @Transactional(readOnly = false)
	     @Override
	     public void registerUser(String login,String email,String password, boolean isAdmin,  String name, String surname1, String surname2, String city, Date birthday) throws UserLoginEmailExistsException, RequiredFieldsException, PasswordTooShort {
	       
	        
	         String encryptedPassword = passwordEncoder.encode(password);

	         if (isAdmin) {
	        	 AdminUser user = new AdminUser();
	        	 user.setLogin(login);
	        	 user.setPassword(encryptedPassword);
	        	 user.setAuthority(UserAuthority.ADMIN);
		         user.setEmail(email);
		         user.setName(name);
		         user.setSurname1(surname1);
		         user.setSurname2(surname2);
		         
		         userDAO.save(user);
	         }else{
	        	 NormalUser user = new NormalUser();
		         user.setLogin(login);
	        	 user.setPassword(encryptedPassword);
		         user.setAuthority(UserAuthority.USER);
		         user.setEmail(email);
		         user.setName(name);
		         user.setSurname1(surname1);
		         user.setSurname2(surname2);
		         user.setBirthday(birthday);
		         user.setCity(city);
		         userDAO.save(user);
		         }

	         
	     }
	     
	     public AdminUserDTO getCurrentUserWithAuthority() {
	         String currentUserLogin = SecurityUtils.getCurrentUserLogin();
	         if (currentUserLogin != null) {
	             return new AdminUserDTO(userDAO.findByLogin(currentUserLogin));
	         }
	         return null;
	     }
	     
	     public NormalUserDTO getCurrentUserWithoutAuthority() {
	         String currentUserLogin = SecurityUtils.getCurrentUserLogin();
	         if (currentUserLogin != null) {
	             return new NormalUserDTO(userDAO.findByLogin(currentUserLogin));
	         }
	         return null;
	     }
	     public UserDTO getCurrentUserWithAuthorityUser() {
	         String currentUserLogin = SecurityUtils.getCurrentUserLogin();
	         if (currentUserLogin != null) {
	             return new UserDTO(userDAO.findByLogin(currentUserLogin));
	         }
	         return null;
	     }

		@Transactional(readOnly = false)
		@Override
		public NormalUserDTO update(NormalUserDTO user) throws UserLoginEmailExistsException {	
	    	NormalUser bdUser = (NormalUser) userDAO.findById(user.getIdUser());
	    		bdUser.setCity(user.getCity());
	    		bdUser.setBirthday(user.getBirthday());
	    		bdUser.setEmail(user.getEmail());
	    		bdUser.setSurname1(user.getSurname1());
	    		bdUser.setSurname2(user.getSurname2());
	    		bdUser.setName(user.getName());
	    		
	    		bdUser.getFavoritos().clear();
	    		    user.getFavoritos().forEach(fav -> {
	    		        bdUser.getFavoritos().add(teamDAO.findByName(fav.getName()));
	    		    });
	    		    
	    		        
	    	
	    		    
	    		bdUser.getJuego().clear();
	    		   user.getJuego().forEach(jug -> {
	    		        bdUser.getJuego().add(teamDAO.findByName(jug.getName()));
	    		    });
	    		    
	    		
	    		User_ user1= userDAO.findByEmail(user.getEmail());
	    		if ( user1!= null&& user1.getIdUser()!=user.getIdUser()) {
		             throw new UserLoginEmailExistsException("The email " +user.getEmail() + " already exists");
	    		}else{
	    			bdUser.setEmail(user.getEmail());
	    		}
	    		
			   
			    
			    userDAO.save(bdUser);
			    return new NormalUserDTO(bdUser);
			    }

		@Transactional(readOnly = false)
		@Override
		public UserDTO changePermission(Long idUser) {
			User_ bdUser =  userDAO.findById(idUser);
			if(bdUser.getAuthority()==UserAuthority.USER){
				bdUser.setAuthority(UserAuthority.ADMIN);
			}else{
				bdUser.setAuthority(UserAuthority.USER);
			}
			
			userDAO.save(bdUser);
		    return new UserDTO(bdUser);
		}

		
		@PreAuthorize("hasAuthority('ADMIN')")
		@Transactional(readOnly = false)
		@Override
		public void deleteById(Long idUser) throws SportDeleteException {
			if (gameDAO.findByCreator(idUser)!=0){
				throw new SportDeleteException("Este usuario tiene asociados actualmente partidos");
			}
			User_ bdUser = userDAO.findById(idUser);
			userDAO.deleteById(idUser);
			
						
		}

		
   

}
