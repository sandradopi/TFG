package es.udc.lbd.asi.restexample;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.exception.LocationExistsException;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.service.LocationService;
import es.udc.lbd.asi.restexample.model.service.SportService;
import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;



@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
public class TestPlay2Gether {


	@Autowired
	private  LocationService locationService;
	
	@Autowired
	private  SportService sportService;
	
	static final LocalDate FECHA = LocalDate.now();
    static final LocalTime HORA = LocalTime.now();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


	
	public void testCompleto() throws LocationExistsException, RequiredFieldsException {
			a_Test_Location();
			a_Test_Location_locationExist();
			a_Test_Location_RequiredFieldsException();
			a_Test_Sport();
			
		
	}
	
	///////////////////////////////////LOCATIONS///////////////////////////////////////////
	@Test
	public void a_Test_Location() {
		//Devolver el total de localizaciones
		List<LocationDTO>listaxeLocation= new ArrayList();
		listaxeLocation= locationService.findAll();
		assertEquals (3,listaxeLocation.size());
		
		//Crear Localización
		 Location location1 = new Location("Culleredo",  new Double(46.301454),new Double(-4.373114));
		 LocationDTO location1d = new LocationDTO(location1);
		 try {
			locationService.save(location1d);
		} catch (LocationExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RequiredFieldsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	    try {
			locationService.deleteById(new Long(4));
		} catch (SportDeleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public void a_Test_Sport()  {
		//Devolver el total de deportes
		List<SportDTO>listaxeSport= new ArrayList();
		listaxeSport= sportService.findAll();
		assertEquals (4,listaxeSport.size());
		
		//Crear un Deporte
		 Set <Location>locationsGolf = new <Location> HashSet();
		 Sport sport1=new Sport("Golf","GolfForm","GolfResult",locationsGolf,"Golf.png");
		 SportDTO sport1d= new SportDTO(sport1);
		 try {
			sportService.save(sport1d);
		} catch (SportExistsException | RequiredFieldsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
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
		try {
			NameUpdated = sportService.update(sportForUpdate).getType();
		} catch (SportExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RequiredFieldsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  assertEquals (sportService.findById(new Long(5)).getType(),NameUpdated);
		  

	   //Borrar una localización
		
		 try {
			sportService.deleteById(new Long(5));
		} catch (SportDeleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	
	
}
