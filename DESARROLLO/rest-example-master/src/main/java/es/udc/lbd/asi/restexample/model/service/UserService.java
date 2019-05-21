package es.udc.lbd.asi.restexample.model.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import es.udc.lbd.asi.restexample.config.Properties;
import es.udc.lbd.asi.restexample.model.domain.AdminUser;
import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Player;
import es.udc.lbd.asi.restexample.model.domain.PlayerValoration;
import es.udc.lbd.asi.restexample.model.domain.SocialFriendShip;
import es.udc.lbd.asi.restexample.model.domain.SocialRelationShip;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.UserAuthority;
import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.exception.EmailIncorrect;
import es.udc.lbd.asi.restexample.model.exception.PasswordTooShort;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.repository.CommentDAO;
import es.udc.lbd.asi.restexample.model.repository.GameDAO;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.PlayerDAO;
import es.udc.lbd.asi.restexample.model.repository.PlayerValorationDAO;
import es.udc.lbd.asi.restexample.model.repository.SocialRelationShipDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;
import es.udc.lbd.asi.restexample.model.repository.TeamDAO;
import es.udc.lbd.asi.restexample.model.repository.UserDAO;
import es.udc.lbd.asi.restexample.model.service.dto.ActivitiesDTO;
import es.udc.lbd.asi.restexample.model.service.dto.AdminUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.CommentDTO;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.GameMessageDTO;
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
  private SocialRelationShipDAO socialDAO;
  @Autowired
  private CommentDAO commentDAO;
  @Autowired
  private Properties properties;    
  private Path location;
  
  
  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostConstruct
  public void initUserService() {
      this.location = Paths.get(properties.getResourcePath());
      try {
          Files.createDirectories(this.location);
      } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
  }
  
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
	  List<GameDTO> recomendadosLimpia1=new ArrayList<>();
	  List<GameDTO> recomendadosLimpia2=new ArrayList<>();
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
					sports[game.getSport().getIdSport().intValue()]= (sports[game.getSport().getIdSport().intValue()]+1);
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
				  
				  for (int x=1;x<sports.length;x++){
						
					  if (sports[x]==iNumeroMayor){
						  iPosicion = x;
						  List<GameDTO> gamesDeportesSame= gameDAO.findAllSport(iPosicion.longValue()).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
						  gamesDeportes.addAll(gamesDeportesSame);
						} 
				  }
				  Map<Long,GameDTO> mapGamesSport=new HashMap<Long, GameDTO>(gamesDeportes.size());
					for(GameDTO g : gamesDeportes) {
						mapGamesSport.put(g.getIdGame(), g);
					}
					for(Entry<Long, GameDTO> g : mapGamesSport.entrySet()) {
						recomendadosLimpia1.add(g.getValue());
						
						}
				
				  RecomendacionDTO recomendacion1=new RecomendacionDTO();
				  recomendacion1.setMensaje("Por que te gusta este Deporte");
				  recomendacion1.setGames(recomendadosLimpia1);
				  recomendadosFinal.add(1, recomendacion1);
				  
				//Recomendar partido por la ubicacion donde  más juega
					 for(Game game:jugados){
						 locations[game.getLocation().getIdLocation().intValue()]= (locations[game.getLocation().getIdLocation().intValue()]+1);
						
						  }
					
					 	
						  Integer iNumeroMayorL = locations[1];
						  Integer iPosicionL = 0;
						  
						  for (int x=1;x<locations.length;x++){
							  if (locations[x]>=iNumeroMayorL){
								  iNumeroMayorL = locations[x];
								  iPosicionL = x;
								} 
						  }
						
						  List<GameDTO> gamesLocations= gameDAO.findAllLocation(iPosicionL.longValue()).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
						  
						  for (int x=1;x<locations.length;x++){
								
							  if (locations[x]==iNumeroMayor){
								  iPosicion = x;
								  List<GameDTO> gamesLocationsSame= gameDAO.findAllLocation(iPosicion.longValue()).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
								  gamesLocations.addAll(gamesLocationsSame);
								} 
						  }
						  Map<Long,GameDTO> mapGamesLocations=new HashMap<Long, GameDTO>(gamesLocations.size());
							for(GameDTO g : gamesLocations) {
								mapGamesLocations.put(g.getIdGame(), g);
							}
							for(Entry<Long, GameDTO> g : mapGamesLocations.entrySet()) {
								recomendadosLimpia2.add(g.getValue());
								
								}
						  
						  RecomendacionDTO recomendacion2=new RecomendacionDTO();
						  recomendacion2.setMensaje("Por que te gusta esta ubicación");
						  recomendacion2.setGames(recomendadosLimpia2);
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
		   	 userTransformado.setRutaImagen(usuarioDevuelto.getRutaImagen());
		   	
		   	 
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
		 public void registerUser(String login, String email,String password, String name, String surname1, String surname2, String city, Date birthday, String rutaImagen) throws UserLoginEmailExistsException, ParseException, RequiredFieldsException, PasswordTooShort, EmailIncorrect {
	    
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

	    	     registerUser(login,email, password, false, name, surname1, surname2, city,birthday,rutaImagen);
	     }
	     
	     @Transactional(readOnly = false)
	     @Override
	     public void registerUser(String login,String email,String password, boolean isAdmin,  String name, String surname1, String surname2, String city, Date birthday,String rutaImagen) throws UserLoginEmailExistsException, RequiredFieldsException, PasswordTooShort {
	       
	        
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
		         user.setRutaImagen(rutaImagen);
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
		@PreAuthorize("hasAuthority('USER')")
		@Override
		public List<ActivitiesDTO> findActivities(String login) {
			List<ActivitiesDTO> actividades= new ArrayList();
			List<SocialFriendShip> followed=socialDAO.findByLoginFollowers(login);
			if(followed.size()>0){
			List<String> friends=new ArrayList();
			for(SocialFriendShip friend:followed){
				friends.add(friend.getUserTo().getLogin());
			}
			List<GameDTO> gamesCreatedByFriends=gameDAO.findAllFriends(friends).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());

			for(GameDTO game:gamesCreatedByFriends){
				ActivitiesDTO actividad= new ActivitiesDTO(userDAO.findByLoginNormal(game.getCreator().getLogin()));
				LocalDateTime localDateTime =LocalDateTime.of(game.getDate().getYear(),game.getDate().getMonth(),game.getDate().getDayOfMonth(),game.getTimeStart().getHour(),game.getTimeStart().getMinute());
				actividad.setDate(localDateTime);
				actividad.setIdActivities(game.getIdGame());
				actividad.setAction("Ha creado un partido");
				actividad.setComment(false);
				actividades.add(actividad);
				
			}
			
			List<PlayerDTO> playerCreatedByFriends=playerDAO.findAllFriends(friends).stream().map(player -> new PlayerDTO(player)).collect(Collectors.toList());
			for(PlayerDTO player:playerCreatedByFriends){
				ActivitiesDTO actividad= new ActivitiesDTO(userDAO.findByLoginNormal(player.getPlayer().getLogin()));
				LocalDateTime localDateTime =LocalDateTime.of(player.getGame().getDate().getYear(),player.getGame().getDate().getMonth(),player.getGame().getDate().getDayOfMonth(),player.getGame().getTimeStart().getHour(),player.getGame().getTimeStart().getMinute());
				actividad.setDate(localDateTime);
				actividad.setIdActivities(player.getGame().getIdGame());
				actividad.setAction("Se ha apuntado a un partido");
				actividad.setComment(false);
				actividades.add(actividad);
				
			}
			
			List<GameMessageDTO> commentCreatedByFriends=commentDAO.findAllByGameFriends(friends).stream().map(comment -> new GameMessageDTO(comment)).collect(Collectors.toList());
			for(GameMessageDTO comment:commentCreatedByFriends){
				Game game=commentDAO.getGameComment(comment.getIdComent());
				ActivitiesDTO actividad= new ActivitiesDTO(userDAO.findByLoginNormal(comment.getFromUser().getLogin()));
				LocalDateTime localDateTime =LocalDateTime.of(game.getDate().getYear(),game.getDate().getMonth(),game.getDate().getDayOfMonth(),game.getTimeStart().getHour(),game.getTimeStart().getMinute());
				actividad.setDate(localDateTime);
				actividad.setIdActivities(game.getIdGame());
				actividad.setAction("Ha comentado en el partido");
				actividad.setComment(true);
				actividades.add(actividad);
				
			}
		 }
			Collections.sort(actividades, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
			
			return actividades;
		}

	
		  public void store(MultipartFile file) throws Exception {
		        String filename = StringUtils.cleanPath(file.getOriginalFilename()); //Return the original filename in the client's filesystem.

		        try {
		            if (file.isEmpty()) {
		                throw new Exception("Error al almacenar la foto " + filename);
		            }
		            if (filename.contains("..")) {
		                throw new Exception(
		                        "Cannot store file with relative path outside current directory "
		                                + filename);
		            }
		            try (InputStream inputStream = file.getInputStream()) { //This method returns he input stream connected to the normal output of the subprocess.
		                Files.copy(inputStream, this.location.resolve(filename),
		                    StandardCopyOption.REPLACE_EXISTING);
		            }
		        }
		        catch (Exception e) {
		            throw new Exception("Failed to store file " + filename, e);
		        }
		    }
		  
		  
		    public Resource getImageAsResource(String fileName) throws Exception {
		        try {
		            Path file =location.resolve(fileName);
		            Resource resource = new UrlResource(file.toUri());
		            if (resource.exists() || resource.isReadable()) {
		                return resource;
		            }
		            else {
		                throw new Exception(
		                        "Could not read file: " + fileName);
		 
		            }
		        }
		        catch (MalformedURLException e) {
		            throw new Exception("Could not read file: " + fileName, e);
		        }
		    }   
		   
		    
			
		
   

}
