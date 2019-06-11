package es.udc.lbd.asi.restexample;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotEmpty;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.Team;
import es.udc.lbd.asi.restexample.model.domain.UserAuthority;
import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.exception.EmailIncorrect;
import es.udc.lbd.asi.restexample.model.exception.EventBeforeDayException;
import es.udc.lbd.asi.restexample.model.exception.GameColapseException;
import es.udc.lbd.asi.restexample.model.exception.LocationExistsException;
import es.udc.lbd.asi.restexample.model.exception.PasswordTooShort;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.DeleteException;
import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;
import es.udc.lbd.asi.restexample.model.repository.UserDAO;
import es.udc.lbd.asi.restexample.model.service.GameService;
import es.udc.lbd.asi.restexample.model.service.LocationService;
import es.udc.lbd.asi.restexample.model.service.SportService;
import es.udc.lbd.asi.restexample.model.service.UserService;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserDTO;



@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser(username = "sandra", authorities = { "ADMIN", "USER" })
public class TestPlay2Gether {


	@Autowired
	private  LocationService locationService;
	
	@Autowired
	private  SportService sportService;
	
	@Autowired
	private  UserService userService;
	
	@Autowired
	private  LocationDAO locationDAO;
	
	@Autowired
	private  SportDAO sportDAO;
	
	@Autowired
	private  UserDAO userDAO;
	
	@Autowired
	private  GameService gameService;
	
	static final LocalDate FECHA = LocalDate.now();
    static final LocalTime HORA = LocalTime.now();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	
	///////////////////////////////////LOCATIONS///////////////////////////////////////////
	@Test
	public void a_Test_Location() throws LocationExistsException, RequiredFieldsException, DeleteException {
		//Devolver el total de localizaciones
		List<LocationDTO>listaxeLocation= new ArrayList();
		listaxeLocation= locationService.findAll();
		assertEquals (3,listaxeLocation.size());
		
		//Crear Localización
		 Location location1 = new Location("Culleredo",  new Double(46.301454),new Double(-4.373114));
		 LocationDTO location1d = new LocationDTO(location1);
		 locationService.save(location1d);
		listaxeLocation= locationService.findAll();
		assertEquals (4,listaxeLocation.size());
		
		//Recuperar una localizacion
	    assertEquals (location1d.getLatitud(),locationService.findById(new Long(4)).getLatitud());
	    assertEquals (location1d.getLongitud(),locationService.findById(new Long(4)).getLongitud());
	    assertEquals (location1d.getName(),locationService.findById(new Long(4)).getName());
	    
	    //Recuperar una localizacion por deportes
	    assertEquals (1,locationService.findAllSport(new Long(4)).size());
	    assertEquals (2,locationService.findAllSport(new Long(1)).size());
	    assertEquals (1,locationService.findAllSport(new Long(2)).size());
	    assertEquals (1,locationService.findAllSport(new Long(3)).size());
	    
	    //Actualizar una localizacion
	    LocationDTO locationForUpdate= locationService.findById(new Long(1));
	    locationForUpdate.setName("Parque Oza");
	    String NameUpdated =locationService.update(locationForUpdate).getName();
	    assertEquals (locationService.findById(new Long(1)).getName(),NameUpdated);
	    
	  
		//Borrar una localización
	    locationService.deleteById(new Long(4));
	    listaxeLocation= locationService.findAll();
		assertEquals (3,listaxeLocation.size());
	    
		
	}
	
	@Test(expected= LocationExistsException.class)
	public void a_Test_Location_locationExist() throws LocationExistsException, RequiredFieldsException {
		//Crear localización duplicada
		 Location location2=new Location("Paddel Plus",  new Double(43.301454),new Double(-8.373114));
		 LocationDTO location2d = new LocationDTO(location2);
		 locationService.save(location2d);
		
		
	}
	
	@Test(expected= RequiredFieldsException.class)
	public void a_Test_Location_RequiredFieldsException() throws LocationExistsException, RequiredFieldsException {
		//Crear localización duplicada
	    Location location2= new Location(null, new Double(46.373774),new Double(-8.373114));
		LocationDTO location2d = new LocationDTO(location2);
	    locationService.save(location2d);
		
		
	}
	
	

	///////////////////////////////////SPORTS///////////////////////////////////////////

	@Test
	public void a_Test_Sport() throws SportExistsException, RequiredFieldsException, DeleteException  {
		//Devolver el total de deportes
		List<SportDTO>listaxeSport= new ArrayList();
		listaxeSport= sportService.findAll();
		assertEquals (4,listaxeSport.size());
		
		//Crear un Deporte
		 Set <Location>locationsGolf = new <Location> HashSet();
		 Sport sport1=new Sport("Golf","GolfForm","GolfResult",locationsGolf,"Golf.png");
		 SportDTO sport1d= new SportDTO(sport1);
		 sportService.save(sport1d);
		 listaxeSport= sportService.findAll();
		 assertEquals (5,listaxeSport.size());
		 
		//Buscar deporte
		 assertEquals (sport1d.getType(),sportService.findById(new Long(5)).getType());
		 assertEquals (sport1d.getComponenteEntrada(),sportService.findById(new Long(5)).getComponenteEntrada());
		 assertEquals (sport1d.getComponenteVisualizacion(),sportService.findById(new Long(5)).getComponenteVisualizacion());
		
		 //Actualizar un deporte
		 SportDTO sportForUpdate= sportService.findById(new Long(5));
		 sportForUpdate.setType("GolfAdvanced");
		 String NameUpdated = null;
		 NameUpdated = sportService.update(sportForUpdate).getType();
		 assertEquals (sportService.findById(new Long(5)).getType(),NameUpdated);
		  

	   //Borrar una localización
		 sportService.deleteById(new Long(5));
		 listaxeSport= sportService.findAll();
		 assertEquals (4,listaxeSport.size()); 
		 
	}
	
	@Test(expected= SportExistsException.class)
	public void a_Test_Sport_sportExist() throws SportExistsException, RequiredFieldsException  {
		 List<SportDTO>listaxeSport= new ArrayList();
		 Set <Location>locationsGolf = new <Location> HashSet();
		 Sport sport1=new Sport("Tennis","GolfForm","GolfResult",locationsGolf,"Golf.png");
		 SportDTO sport1d= new SportDTO(sport1);
		 sportService.save(sport1d);
		
		
		
	}
	
	@Test(expected= RequiredFieldsException.class)
	public void a_Test_Sport_RequiredFieldsException() throws SportExistsException, RequiredFieldsException  {
		List<SportDTO>listaxeSport= new ArrayList();
		 Set <Location>locationsGolf = new <Location> HashSet();
		 Sport sport1=new Sport(null,"GolfForm","GolfResult",locationsGolf,"Golf.png");
		 SportDTO sport1d= new SportDTO(sport1);
		 sportService.save(sport1d);
		
		
		
	}
	
///////////////////////////////////USERS///////////////////////////////////////////
	
	@Test
	public void a_Test_Users() throws UserLoginEmailExistsException, RequiredFieldsException, PasswordTooShort, ParseException, DeleteException, AddressException, MessagingException, EmailIncorrect  {
		//Devolver el total de Usuarios
		 List<UserDTO>listaxeUsuarios= new ArrayList();
		 listaxeUsuarios= userService.findAll();
		 List<UserDTO> listaxeUsuariosNormales= userService.findAllNoAdmin();
		 assertEquals (5,listaxeUsuarios.size()); 
		 assertEquals (3,listaxeUsuariosNormales.size()); 
		 
		//Crear usuario
		 NormalUser user = new NormalUser("rober","rober","rober@outlook.com","Rober","Lopez", "Lopez",UserAuthority.USER,"Madrid",null,sdf.parse("2003-03-17"), null, null);
		 userService.registerUser(user.getLogin(), user.getEmail(),user.getPassword(),user.getName(), user.getSurname1(), user.getSurname2(),user.getCity(),user.getBirthday(), "rober.jpg");
		 listaxeUsuarios= userService.findAll();
		 assertEquals (6,listaxeUsuarios.size()); 
		  
		//Buscar usuario
		assertEquals (user.getEmail(),userService.findByLogin("rober").getEmail());
		assertEquals (user.getLogin(),userService.findByLogin("rober").getLogin());
		assertEquals (user.getName(),userService.findByLogin("rober").getName());
		assertEquals (user.getSurname1(),userService.findByLogin("rober").getSurname1());
		assertEquals (user.getSurname2(),userService.findByLogin("rober").getSurname2());
		assertEquals (user.getCity(),userService.findByLogin("rober").getCity());
		
		 //Actualizar un usuario
		 NormalUserDTO userForUpdate= userService.findByLogin("rober");
		 userForUpdate.setName("Robertiño");
		 String NameUpdated = null;
		 NameUpdated = userService.update(userForUpdate).getName();
		 assertEquals (userService.findByLogin("rober").getName(),NameUpdated);
		 
		 //Actualizar contraseña
		 userForUpdate= userService.findByLogin("sandra");
		 userForUpdate.setPassword("hola");
		 assertNotEquals (userForUpdate.getPassword(),userService.updatePassword("sandra", "hola"));
		 
		 
		 //Buscar partidos jugados por ese usuario
	       assertEquals (1,userService.findGamesPlayed("sandra").size());
	       assertEquals (0,userService.findGamesPlayed("rober").size());
	       assertEquals (1,userService.findGamesPlayed("laura").size());
	       assertEquals (1,userService.findGamesPlayed("lucas").size());
	       
	     //Buscar partidos creados por ese usuario
	       assertEquals (3,userService.findGamesCreated("sandra").size());
	       assertEquals (0,userService.findGamesCreated("rober").size());
	       assertEquals (1,userService.findGamesCreated("laura").size());
	       assertEquals (1,userService.findGamesCreated("lucas").size());
	       
	     //Buscar partidos próximos para ese usuario
	       assertEquals (2,userService.findGamesNext("sandra").size());
	       assertEquals (0,userService.findGamesNext("rober").size());
	       assertEquals (1,userService.findGamesNext("laura").size());
	       assertEquals (0,userService.findGamesNext("lucas").size());
	       
	     //Buscar patidos recomendados para ese usuario
	       assertEquals (1,userService.findGamesRecomendados("sandra").get(0).getGames().size());
	       assertEquals (1,userService.findGamesRecomendados("sandra").get(1).getGames().size());
	       assertEquals (2,userService.findGamesRecomendados("sandra").get(2).getGames().size());
	       
	     //Buscar partidos pendientes de rellenar resultado por ese usuario
	       assertEquals (1,userService.findByCreatorResultado("sandra").size());
	       assertEquals (0,userService.findByCreatorResultado("laura").size());
	       assertEquals (0,userService.findByCreatorResultado("rober").size());
	       
	     //Buscar partidos pendientes de valorar por ese usuario
	       assertEquals (1,userService.findByValoration("sandra").size());
	       assertEquals (0,userService.findByValoration("rober").size());
	       assertEquals (1,userService.findByValoration("laura").size());
	       
	       //Buscar comentarios
	       assertEquals (1,userService.findComentarios("sandra").size());
	       assertEquals (1,userService.findComentarios("laura").size());
	       assertEquals (0,userService.findComentarios("rober").size());
	       
	   
	     //Notificaciones de un usuario de un partido
	       assertEquals (false,userService.getNotification("sandra",new Long(1)));
	       assertEquals (false,userService.getNotification("laura", new Long(1)));
	       assertEquals (false,userService.getNotification("sandra", new Long(2)));
	       
	       //Notificar un partido
	       userService.updateNotification("laura",new Long(1), false);
	       assertEquals (1,userService.findByLogin("laura").getNotifications().size());
	       
	     //Usuario logueado
	       assertEquals ("sandra",userService.getCurrentUserWithoutAuthority().getLogin());
	       
	       //Buscar actividades de la gente que sigue
	       assertEquals (3,userService.findActivities("sandra").size());
	       assertEquals (0,userService.findActivities("rober").size());
	       assertEquals (4,userService.findActivities("laura").size());
	     
		 //Borrar un usuario
		  userService.deleteById(new Long(6));
		  listaxeUsuarios= userService.findAll();
	      assertEquals (5,listaxeUsuarios.size());
	      
	     
		 
		 
	}
	
	@Test(expected=UserLoginEmailExistsException.class)
	public void a_Test_Users_UserLoginEmailExistsException() throws UserLoginEmailExistsException, RequiredFieldsException, PasswordTooShort, EmailIncorrect, ParseException  {
		
		//Crear usuario
		 List<UserDTO>listaxeUsuarios= new ArrayList();
		 NormalUser user = new NormalUser("sandra","sandra","sandra111@outlook.com","Rober","Lopez", "Lopez",UserAuthority.USER,"Madrid",null,sdf.parse("2003-03-17"), null, null);
		 userService.registerUser(user.getLogin(), user.getEmail(),user.getPassword(),user.getName(), user.getSurname1(), user.getSurname2(),user.getCity(),user.getBirthday(), "rober.jpg");
		 listaxeUsuarios= userService.findAll();
		 assertEquals (5,listaxeUsuarios.size()); 
		
		
	}
	
	@Test(expected=RequiredFieldsException.class)
	public void a_Test_Users_RequiredFieldsException() throws UserLoginEmailExistsException, RequiredFieldsException, PasswordTooShort, EmailIncorrect, ParseException  {
		
		//Crear usuario
		 List<UserDTO>listaxeUsuarios= new ArrayList();
		 NormalUser user = new NormalUser(null,"sandra","sandra111@outlook.com","Rober","Lopez", "Lopez",UserAuthority.USER,"Madrid",null,sdf.parse("2003-03-17"), null, null);
		 userService.registerUser(user.getLogin(), user.getEmail(),user.getPassword(),user.getName(), user.getSurname1(), user.getSurname2(),user.getCity(),user.getBirthday(), "rober.jpg");
		 listaxeUsuarios= userService.findAll();
		 assertEquals (5,listaxeUsuarios.size()); 
		
		
	}
	
	@Test(expected=PasswordTooShort.class)
	public void a_Test_Users_PasswordTooShort() throws UserLoginEmailExistsException, RequiredFieldsException, PasswordTooShort, EmailIncorrect, ParseException  {
		
		//Crear usuario
		 List<UserDTO>listaxeUsuarios= new ArrayList();
		 NormalUser user = new NormalUser("lucia","lu","lucia111@outlook.com","Rober","Lopez", "Lopez",UserAuthority.USER,"Madrid",null,sdf.parse("2003-03-17"), null, null);
		 userService.registerUser(user.getLogin(), user.getEmail(),user.getPassword(),user.getName(), user.getSurname1(), user.getSurname2(),user.getCity(),user.getBirthday(), "rober.jpg");
		 listaxeUsuarios= userService.findAll();
		 assertEquals (5,listaxeUsuarios.size()); 
		
		
	}
	
	@Test(expected=EmailIncorrect.class)
	public void a_Test_Users_EmailIncorrect() throws UserLoginEmailExistsException, RequiredFieldsException, PasswordTooShort, EmailIncorrect, ParseException  {
		
		//Crear usuario
		 List<UserDTO>listaxeUsuarios= new ArrayList();
		 NormalUser user = new NormalUser("lucia","lucia","lucia111outlook.com","Rober","Lopez", "Lopez",UserAuthority.USER,"Madrid",null,sdf.parse("2003-03-17"), null, null);
		 userService.registerUser(user.getLogin(), user.getEmail(),user.getPassword(),user.getName(), user.getSurname1(), user.getSurname2(),user.getCity(),user.getBirthday(), "rober.jpg");
		 listaxeUsuarios= userService.findAll();
		 assertEquals (5,listaxeUsuarios.size()); 
		
		
	}
///////////////////////////////////GAMES///////////////////////////////////////////
	@Test
	public void a_Test_Games() throws AddressException, RequiredFieldsException, GameColapseException, EventBeforeDayException, ParseException, MessagingException, UserLoginEmailExistsException, PasswordTooShort, EmailIncorrect, LocationExistsException, SportExistsException, DeleteException  {
		//Devolver el total de partidos
		List<GameDTO>listaxeGames= new ArrayList();
		listaxeGames= gameService.findAll();
		assertEquals (4,listaxeGames.size());
		
		//Partidos de cada deporte
		assertEquals (1, gameService.findAllSport(new Long(1)).size());
		assertEquals (1, gameService.findAllSport(new Long(2)).size());
		assertEquals (1, gameService.findAllSport(new Long(3)).size());
		assertEquals (1, gameService.findAllSport(new Long(4)).size());
		
		//Partidos de cada localizacion
		assertEquals (1, gameService.findAllLocation(new Long(1)).size());
		assertEquals (2, gameService.findAllLocation(new Long(2)).size());
		assertEquals (1, gameService.findAllLocation(new Long(3)).size());
		
		//Partidos de cada localizacion
		assertEquals (0, gameService.findAllSportLocation(new Long(1), new Long(1)).size());
		assertEquals (0, gameService.findAllSportLocation(new Long(2), new Long(1)).size());
		assertEquals (1, gameService.findAllSportLocation(new Long(3), new Long(1)).size());
		assertEquals (0, gameService.findAllSportLocation(new Long(4), new Long(1)).size());
		
		//Filtrado avanzado de partidos
		assertEquals (2, gameService.findAllFiltros("vacio", "sandra", "vacio", "vacio").size());
		assertEquals (1, gameService.findAllFiltros("Tennis", "vacio", "vacio", "vacio").size());
		assertEquals (1, gameService.findAllFiltros("vacio", "vacio", "18<edad<25", "vacio").size());
		assertEquals (1, gameService.findAllFiltros("vacio", "vacio", ">40", "vacio").size());
		assertEquals (0, gameService.findAllFiltros("vacio", "vacio", "<18", "vacio").size());
		assertEquals (1, gameService.findAllFiltros("Futbol","sandra", "vacio", "vacio").size());
		assertEquals (1, gameService.findAllFiltros("vacio", "sandra", "vacio", "Profesional").size());
		assertEquals (2, gameService.findAllFiltros("vacio", "vacio", "vacio", "Profesional").size());
		
		
		
	  //Actualizar resultado partido
	   gameService.update(new Long(1), "{equipoA:{setsGanados:0,sets:[4,2]},equipoB:{setsGanados:2,sets:[6,6]}}");
	   assertEquals ("{equipoA:{setsGanados:0,sets:[4,2]},equipoB:{setsGanados:2,sets:[6,6]}}", gameService.findById(new Long(1)).getResult());
	      
	 //Crear un Partido


		 
	  //crear,buscar y borrar
		
			 
	 
	}
	
	
			 
		
	
}
