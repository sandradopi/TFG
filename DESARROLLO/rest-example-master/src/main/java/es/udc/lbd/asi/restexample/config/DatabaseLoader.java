package es.udc.lbd.asi.restexample.config;


import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.exception.PasswordTooShort;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;


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
    private LocationDAO locationService;

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
        
    	
    userService.registerUser("pepe","pepe@pepe.com", "pepe", true,"Pepe", "Lopez","Fernandez", "Madrid", sdf.parse("1997-01-24"));
    userService.registerUser("maria","maria@maria.com","maria",true,"Maria","Aira", "Del Valle", "Barcelona", sdf.parse("1999-11-15"));
    userService.registerUser("laura", "guajndos@gmail.com","laura", false,"Laura", "Insua","Regueiro","Bilbao", sdf.parse("2000-12-18"));
    userService.registerUser("sandra","vicfic23@gmail.com","sandra", false,"Sandra","Dopico","Cantarero","A Coruña", sdf.parse("1997-08-20"));
    userService.registerUser("lucas", "sandra111@outlook.com","lucas", false,"Lucas", "Vazquez", "Lorenzo","Madrid", sdf.parse("1995-03-17"));
  
    Location location1=new Location("Parque de Oza", new Long(0), new Double(43.350538),new Double(-8.401573));
    Location location2=new Location("Bastiagueiro", new Long(0), new Double(43.340167),new Double(-8.35426));
    Location location3=new Location("Paddel Plus", new Long(5), new Double(43.301454),new Double(-8.373114));
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
    
    sportService.save(new Sport("Fútbol",null,null,locationsFutbol));
    sportService.save(new Sport("Tennis",null,null,locationsTennis));
    sportService.save(new Sport("Baloncesto",null,null,locationsBaloncesto));
    sportService.save(new Sport("Paddel",null,null,locationsPaddel));
    
    }

    
    
}
