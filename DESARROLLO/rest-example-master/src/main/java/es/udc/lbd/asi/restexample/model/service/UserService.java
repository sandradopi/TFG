package es.udc.lbd.asi.restexample.model.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import es.udc.lbd.asi.restexample.model.domain.Player;
import es.udc.lbd.asi.restexample.model.domain.PlayerValoration;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.UserAuthority;
import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.exception.EmailIncorrect;
import es.udc.lbd.asi.restexample.model.exception.PasswordTooShort;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.repository.GameDAO;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.PlayerDAO;
import es.udc.lbd.asi.restexample.model.repository.PlayerValorationDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;
import es.udc.lbd.asi.restexample.model.repository.TeamDAO;
import es.udc.lbd.asi.restexample.model.repository.UserDAO;
import es.udc.lbd.asi.restexample.model.service.dto.AdminUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;
import es.udc.lbd.asi.restexample.model.service.dto.RecomendacionDTO;
import es.udc.lbd.asi.restexample.model.service.dto.TeamDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserDTO;
import es.udc.lbd.asi.restexample.security.SecurityUtils;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserService implements UserServiceInterface{

  @Autowired
  private UserDAO userDAO;
  @Autowired
  private SportDAO sportDAO;
  @Autowired
  private TeamDAO teamDAO;
  @Autowired
  private GameDAO gameDAO;
  @Autowired
  private PlayerDAO playerDAO;
  @Autowired
  private PlayerValorationDAO playerValorationDAO;
  @Autowired
  private LocationDAO locationDAO;
  
  
  @Autowired
  private PasswordEncoder passwordEncoder;

  @PreAuthorize("hasAuthority('USER')")
	@Override
	public List<GameDTO> findGamesPlayed(String login) {
	  return userDAO.findAllGamesPlayed(login).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
	}

  @PreAuthorize("hasAuthority('USER')")
  @Override
	public List<GameDTO> findGamesCreated(String login) {
	  return userDAO.findAllGamesCreated(login).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
	  }
  
  @PreAuthorize("hasAuthority('USER')")
  @Override
	public List<RecomendacionDTO> findGamesRecomendados(String login) {
	  List<Game> jugados=userDAO.findAllGamesPlayed(login);
	  List<Player> jugadoresJugados= new ArrayList<>();
	  List<Player> jugadoresValoradosBien= new ArrayList<>();
	  List<GameDTO> recomendados=new ArrayList<>();
	  List<GameDTO> recomendadosLimpia=new ArrayList<>();
	  List<RecomendacionDTO> recomendadosFinal= new ArrayList<RecomendacionDTO>();
	  int[] sports = new int[sportDAO.countSports().intValue()];    
	  int[] locations = new int[locationDAO.countLocations().intValue()];    
	  
	
	 
	  if(jugados.size()!=0){
		  //Recomedar partidos en los que jueguen jugadores buenos con los que ya jugó
		  for(Game game:jugados){
			  jugadoresJugados.addAll(playerDAO.findAllByGame(game.getIdGame()));
			 
		  }
		  for(Player j :jugadoresJugados ){
			if((j.getPlayer().getExperience()>new Double(3))&&(j.getPlayer().getLogin()!=getCurrentUserWithoutAuthority().getLogin())){
				jugadoresValoradosBien.add(j);
			
				
			}
			
		  }
		  for(Player j:jugadoresValoradosBien){
			  recomendados.addAll(userDAO.findRecomendadosPlayers(j.getPlayer().getLogin()).stream().map(game -> new GameDTO(game)).collect(Collectors.toList()));
			  
		  }
		 
		  
		  Map<Long,GameDTO> mapGames=new HashMap<Long, GameDTO>(recomendados.size());
			for(GameDTO g : recomendados) {
				mapGames.put(g.getIdGame(), g);
			}
			for(Entry<Long, GameDTO> g : mapGames.entrySet()) {
				recomendadosLimpia.add(g.getValue());
				
				}
			
			RecomendacionDTO recomendacion=new RecomendacionDTO();
			recomendacion.setMensaje("Por que jugaste con ellos");
			recomendacion.setGames(recomendadosLimpia);
			recomendadosFinal.add(0, recomendacion);
			
	  //Recomendar partido por el deporte que más juega
			 for(Game game:jugados){
					sports[game.getSport().getIdSport().intValue()]= sports[game.getSport().getIdSport().intValue()]+1;
				
					
				  }
			 	
				  Integer iNumeroMayor = sports[1];
				  Integer iPosicion = 0;
					
				  for (int x=1;x<sports.length;x++){
				
					  if (sports[x]>=iNumeroMayor){
						 
						   iNumeroMayor = sports[x];
						   iPosicion = x;
						} 
				  }
				
				  List<GameDTO> gamesDeportes= gameDAO.findAllSport(iPosicion.longValue()).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
				  RecomendacionDTO recomendacion1=new RecomendacionDTO();
				  recomendacion1.setMensaje("Por que te gusta este Deporte");
				  recomendacion1.setGames(gamesDeportes);
				  recomendadosFinal.add(1, recomendacion1);
				  
				//Recomendar partido por la ubicacion donde  más juega
					 for(Game game:jugados){
						 locations[game.getLocation().getIdLocation().intValue()]= locations[game.getLocation().getIdLocation().intValue()]+1;
						
						  }
					
					 	
						  Integer iNumeroMayorL = sports[1];
						  Integer iPosicionL = 0;
						  
						  for (int x=1;x<locations.length;x++){
							  if (locations[x]>=iNumeroMayorL){
								  iNumeroMayorL = locations[x];
								  iPosicionL = x;
								} 
						  }
						
						  
						  List<GameDTO> gamesLocations= gameDAO.findAllLocation(iPosicionL.longValue()).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
						  RecomendacionDTO recomendacion2=new RecomendacionDTO();
						  recomendacion2.setMensaje("Por que te gusta esta ubicación");
						  recomendacion2.setGames(gamesLocations);
						  recomendadosFinal.add(2, recomendacion2);
				 
				  
	  }
	  return recomendadosFinal;
	  }
  
  @Override
	public List<GameDTO> findByCreatorResultado(String login) {
		return userDAO.findByCreatorResultado(login).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());		
	}
  
  @Override
	public List<String> findComentarios(String login) {
		return userDAO.findComentarios(login);
	}
  
  @Override
 	public List<GameDTO> findByValoration(String login) {
	  List<GameDTO> games= userDAO.findAllGamesPlayedValoration(login).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
	  return games;
 	}
  
  @PreAuthorize("hasAuthority('USER')")
  @Override
	public Boolean getNotification(String login,Long idGame) {
	  Boolean bol=false;
	  NormalUser usuarioDevuelto= userDAO.findByLoginNormal(login);
	  for(Game noti :usuarioDevuelto.getNotifications()){
		  if(noti.getIdGame()==idGame){
			  bol= true;
		  }
	  }
	return bol;
	 
  }
  
  @PreAuthorize("hasAuthority('USER')")
  @Override
  public List<GameDTO> findGamesNext(String login) {
	  return userDAO.findAllGamesNext(login).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
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
		   	 
		   	 userTransformado.getNotifications().clear();
		   	 usuarioDevuelto.getNotifications().forEach(fav -> {
		    	 userTransformado.getNotifications().add(new GameDTO(gameDAO.findById(fav.getIdGame())));
		    });
		   	 
		    
	   	 return userTransformado;
	   }

  		@PreAuthorize("hasAuthority('ADMIN')")
		@Override
		public List<UserDTO> findAll() {
			 return userDAO.findAll().stream().map(user -> new UserDTO(user)).collect(Collectors.toList());}
		
  		@PreAuthorize("hasAuthority('USER')")
		@Override
  		public List<UserDTO> findAllNoAdmin() {
  			 return userDAO.findAllNoAdminU().stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
		}
  		
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
		         user.setExperience(0);
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
		
		@PreAuthorize("hasAuthority('USER')")
		@Transactional(readOnly = false)
		@Override
		public NormalUserDTO updateNotification(String login, Long idGame, Boolean bool)  {
			if(bool==false){
				NormalUser usuario = userDAO.findByLoginNormal(login);
				usuario.getNotifications().add(gameDAO.findById(idGame));
				userDAO.save(usuario);
				return new NormalUserDTO(usuario);
				}else{
					NormalUser usuario = userDAO.findByLoginNormal(login);
					usuario.getNotifications().remove(gameDAO.findById(idGame));
					userDAO.save(usuario);
					return new NormalUserDTO(usuario);
				}
			  

		  }

	

		
   

}
