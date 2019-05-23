package es.udc.lbd.asi.restexample.config;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import es.udc.lbd.asi.restexample.model.service.SportService;
import es.udc.lbd.asi.restexample.model.service.UserService;
import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Player;
import es.udc.lbd.asi.restexample.model.domain.PlayerValoration;
import es.udc.lbd.asi.restexample.model.domain.SocialFriendShip;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.Team;
import es.udc.lbd.asi.restexample.model.exception.PasswordTooShort;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.repository.GameDAO;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.PlayerDAO;
import es.udc.lbd.asi.restexample.model.repository.PlayerValorationDAO;
import es.udc.lbd.asi.restexample.model.repository.SocialRelationShipDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;
import es.udc.lbd.asi.restexample.model.repository.TeamDAO;
import es.udc.lbd.asi.restexample.model.repository.UserDAO;


@Configuration
public class DatabaseLoader {
	private final Logger logger = LoggerFactory.getLogger(DatabaseLoader.class);

   
    @Autowired
    private DatabaseLoader databaseLoader;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private SportDAO sportService;
    
    @Autowired
    private UserDAO userDAO;
    
    
    @Autowired
    private TeamDAO teamService;
    
    @Autowired
    private GameDAO gameDAO;
    
    @Autowired
    private PlayerDAO playerDAO;
    
    @Autowired
    private PlayerValorationDAO playerValorationDAO;
    
    @Autowired
    private LocationDAO locationService;
    
    @Autowired
    private SocialRelationShipDAO socialDAO;
    
    static final LocalDate FECHA = LocalDate.now();
    static final LocalTime HORA = LocalTime.now();

    /*
     * Para hacer que la carga de datos sea transacional, hay que cargar el propio
     * objeto como un bean y lanzar el método una vez cargado, ya que en el
     * PostConstruct (ni similares) se tienen en cuenta las anotaciones de
     * transaciones.
     */
    @PostConstruct
    public void init() throws ParseException, RequiredFieldsException, PasswordTooShort {
    	 try {
             databaseLoader.loadData();
         } catch (UserLoginEmailExistsException e) {
             logger.error(e.getMessage(), e);
         }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void loadData() throws UserLoginEmailExistsException, ParseException, RequiredFieldsException, PasswordTooShort{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
    	
    userService.registerUser("pepe","pepe@pepe.com", "pepe", true,"Pepe", "Lopez","Fernandez", "Madrid", sdf.parse("1997-01-24"),null);
    userService.registerUser("maria","maria@maria.com","maria",true,"Maria","Aira", "Del Valle", "Barcelona", sdf.parse("1999-11-15"),null);
    userService.registerUser("laura", "guajndos@gmail.com","laura", false,"Laura", "Insua","Regueiro","Bilbao", sdf.parse("1960-12-18"),"laura.jpg");
    userService.registerUser("sandra","vicfic23@gmail.com","sandra", false,"Sandra","Dopico","Cantarero","A Coruña", sdf.parse("1997-08-20"),"sandra.jpg");
    userService.registerUser("lucas", "sandra111@outlook.com","lucas", false,"Lucas", "Vazquez", "Lorenzo","Madrid", sdf.parse("2003-03-17"),"lucas.jpg");
  
    Location location1=new Location("Parque de Oza", new Double(43.350538),new Double(-8.401573));
    Location location2=new Location("Bastiagueiro",  new Double(43.340167),new Double(-8.35426));
    Location location3=new Location("Paddel Plus",  new Double(43.301454),new Double(-8.373114));
    locationService.save(location1);
    locationService.save(location2);
    locationService.save(location3);
    
    
    
    Set <Location>locationsFutbol = new <Location> HashSet();
    Set <Location>locationsTennis = new <Location> HashSet();
    Set <Location>locationsBaloncesto = new <Location> HashSet();
    Set <Location>locationsPaddel = new <Location> HashSet();
    locationsFutbol.add(location1);
    locationsFutbol.add(location2);
    locationsTennis.add(location2);
    locationsBaloncesto.add(location1);
    locationsPaddel.add(location3);
    
    Sport sport1=new Sport("Futbol","FutbolForm","FutbolResult",locationsFutbol,"Futbol.png");
    Sport sport2= new Sport("Tennis","TennisForm","TennisResult",locationsTennis,"Tennis.png");
    Sport sport3= new Sport("Baloncesto","FutbolForm","FutbolResult",locationsBaloncesto,"Baloncesto.png");
    Sport sport4 =new Sport("Paddel","TennisForm","TennisResult",locationsPaddel,"Paddel.png");
    sportService.save(sport1);
    sportService.save(sport2);
    sportService.save(sport3);
    sportService.save(sport4);
    
   
    
    Team team1= new Team("Deportivo",sport1);
    Team team2= new Team("Lakers",sport3);
    Team team3= new Team("CasinoClub",sport2);
    teamService.save(team1);
    teamService.save(team2);
    teamService.save(team3);
    
    Game bdGame1 = new Game(FECHA.plusDays(5),HORA.minusHours(1), HORA,new Long(4),new Long(2));
	
	bdGame1.setSport(sport1);
	bdGame1.setLocation(location2);
	bdGame1.setCreator(userDAO.findByLoginNormal("sandra"));
	gameDAO.save(bdGame1);
	
	Game bdGame2 = new Game(FECHA.minusDays(5),HORA.plusHours(1), HORA.plusHours(2),new Long(4),new Long(2));	
	bdGame2.setSport(sport2);
	bdGame2.setLocation(location2);
	bdGame2.setCreator(userDAO.findByLoginNormal("sandra"));
	gameDAO.save(bdGame2);
		
	Player bdPlayer = new Player("A");
	bdPlayer.setGame(bdGame2);
	NormalUser playerUser= (NormalUser) userDAO.findByLoginNormal("lucas");
	bdPlayer.setPlayer(playerUser);
	playerDAO.save(bdPlayer);

	Player bdPlayer1 = new Player("B");
	bdPlayer1.setGame(bdGame2);
	NormalUser playerUser1= (NormalUser) userDAO.findByLoginNormal("laura");
	bdPlayer1.setPlayer(playerUser1);
	playerDAO.save(bdPlayer1);
	
	Player bdPlayer4 = new Player("B");
	bdPlayer4.setGame(bdGame2);
	NormalUser playerUser4= (NormalUser) userDAO.findByLoginNormal("sandra");
	bdPlayer4.setPlayer(playerUser4);
	playerDAO.save(bdPlayer4);
	
	PlayerValoration bdPlayerValoration = new PlayerValoration(new Long(4));
	bdPlayerValoration.setReview("Es muy buena jugadora, sabe jugar en equipo y tiene mucha técnica");
	bdPlayerValoration.setPlayer(bdPlayer1);
	bdPlayerValoration.setUser(playerUser1);
	
	playerValorationDAO.save(bdPlayerValoration);
	playerUser1.setExperience(4);
	userDAO.save(playerUser1);

	PlayerValoration bdPlayerValoration1 = new PlayerValoration(new Long(5));
	bdPlayerValoration1.setReview("Es una de las personas que más he visto correr, nadie podía alcanzarla!");
	bdPlayerValoration1.setPlayer(bdPlayer4);
	bdPlayerValoration1.setUser(playerUser4);
	playerValorationDAO.save(bdPlayerValoration1);
	playerUser4.setExperience(5);
	userDAO.save(playerUser4);
	
		
	Game bdGame3 = new Game(FECHA.plusDays(4),HORA.minusHours(3), HORA.minusHours(2),new Long(4),new Long(2));	
	bdGame3.setSport(sport4);
	bdGame3.setLocation(location3);
	bdGame3.setCreator(userDAO.findByLoginNormal("lucas"));
	gameDAO.save(bdGame3);
		
	Player bdPlayer3 = new Player("A");
	bdPlayer3.setGame(bdGame3);
	NormalUser playerUser3= (NormalUser) userDAO.findByLoginNormal("sandra");
	bdPlayer3.setPlayer(playerUser3);
	playerDAO.save(bdPlayer3);
	
	Game bdGame4 = new Game(FECHA.plusDays(2),HORA.minusHours(2), HORA.minusHours(1),new Long(4),new Long(2));	
	bdGame4.setSport(sport2);
	bdGame4.setLocation(location2);
	bdGame4.setCreator(userDAO.findByLoginNormal("laura"));
	gameDAO.save(bdGame4);
	
	Game bdGame5 = new Game(FECHA.plusDays(1),HORA.minusHours(1), HORA,new Long(4),new Long(2));	
	bdGame5.setSport(sport3);
	bdGame5.setLocation(location1);
	bdGame5.setCreator(userDAO.findByLoginNormal("sandra"));
	gameDAO.save(bdGame5);
	
	
	Player bdPlayer5 = new Player("A");
	bdPlayer5.setGame(bdGame5);
	NormalUser playerUser5= (NormalUser) userDAO.findByLoginNormal("sandra");
	bdPlayer5.setPlayer(playerUser5);
	playerDAO.save(bdPlayer5);
	
	Player bdPlayer6 = new Player("B");
	bdPlayer6.setGame(bdGame5);
	NormalUser playerUser6= (NormalUser) userDAO.findByLoginNormal("laura");
	bdPlayer6.setPlayer(playerUser6);
	playerDAO.save(bdPlayer6);
	    
	SocialFriendShip socialship =new SocialFriendShip();
	socialship.setLastUpdate(LocalDateTime.now());
	socialship.setNotification(false);
	socialship.setUserFrom(playerUser3);
	socialship.setUserTo(playerUser6);
	socialDAO.save(socialship);
	
	SocialFriendShip socialship1 =new SocialFriendShip();
	socialship1.setLastUpdate(LocalDateTime.now());
	socialship1.setNotification(false);
	socialship1.setUserFrom(playerUser3);
	socialship1.setUserTo(playerUser);
	socialDAO.save(socialship1);
	
	SocialFriendShip socialship2 =new SocialFriendShip();
	socialship2.setLastUpdate(LocalDateTime.now());
	socialship2.setNotification(false);
	socialship2.setUserTo(playerUser3);
	socialship2.setUserFrom(playerUser6);
	socialDAO.save(socialship2);
	
	
    }
    
   
    
}
