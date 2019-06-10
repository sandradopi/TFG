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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.UserAuthority;
import es.udc.lbd.asi.restexample.model.exception.EmailIncorrect;
import es.udc.lbd.asi.restexample.model.exception.EventBeforeDayException;
import es.udc.lbd.asi.restexample.model.exception.GameColapseException;
import es.udc.lbd.asi.restexample.model.exception.LocationExistsException;
import es.udc.lbd.asi.restexample.model.exception.PasswordTooShort;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.service.GameService;
import es.udc.lbd.asi.restexample.model.service.LocationService;
import es.udc.lbd.asi.restexample.model.service.SportService;
import es.udc.lbd.asi.restexample.model.service.UserService;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.web.GameResource;



@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
public class TestPlay2Gether {

	@Autowired
	private  GameService gameService;
	@Autowired
	private  LocationService locationService;
	@Autowired
	private  UserService userService;
	@Autowired
	private  SportService sportService;
	static final LocalDate FECHA = LocalDate.now();
    static final LocalTime HORA = LocalTime.now();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


	@Test
	public void testCompleto() {
		try {
			a_Test_Game();
		} catch (UserLoginEmailExistsException | RequiredFieldsException | PasswordTooShort | EmailIncorrect
				| LocationExistsException | SportExistsException | GameColapseException | EventBeforeDayException
				| ParseException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void a_Test_Game() throws UserLoginEmailExistsException, RequiredFieldsException, PasswordTooShort, EmailIncorrect, ParseException, LocationExistsException, SportExistsException, AddressException, GameColapseException, EventBeforeDayException, MessagingException {
		//Devolver el total de partidos
		List<GameDTO>listaxeGamesFiltro= new ArrayList();
		listaxeGamesFiltro= gameService.findAll();
		assertEquals (4,listaxeGamesFiltro.size());
		//Añadir un partido
		Set <Location>locationsGolf = new <Location> HashSet();
		Sport sport1=new Sport("Golf","GolfForm","GolfResult",locationsGolf,"Golf.png"); 
		sportService.save(new SportDTO(sport1));
		Location location1=new Location("Culleredo", new Double(46.350538),new Double(-8.451573));
		locationService.save(new LocationDTO(location1));
		NormalUser user1= new NormalUser("maria", "maria", "maria99@outlook.com", "Maria", "Aira", "Lorenzo",UserAuthority.USER,"Madrid",0,null,null,null);
		userService.registerUser(user1.getLogin(),user1.getEmail(), user1.getPassword(),user1.getName(),user1.getSurname1(),user1.getSurname2(),user1.getCity(),user1.getBirthday(),"maria.jpg");
		Game bdGame3 = new Game(FECHA.plusDays(4),HORA.minusHours(3), HORA.minusHours(2),new Long(4),new Long(2));	
		bdGame3.setSport(sport1);
		bdGame3.setLocation(location1);
		bdGame3.setCreator(user1);
		gameService.save(new GameDTO(bdGame3));
		//Devolver el total de partidos
		listaxeGamesFiltro= gameService.findAll();	
		assertEquals (5,listaxeGamesFiltro.size());
	}

	
}
