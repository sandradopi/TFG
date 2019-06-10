package es.udc.lbd.asi.restexample.model.service;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Player;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.exception.EventBeforeDayException;
import es.udc.lbd.asi.restexample.model.exception.GameColapseException;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.repository.GameDAO;
import es.udc.lbd.asi.restexample.model.repository.UserDAO;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.PlayerDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;

import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserDTO;
import es.udc.lbd.asi.restexample.repository.util.NotificationTask;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class GameService implements GameServiceInterface{


  @Autowired
  private GameDAO gameDAO;
  @Autowired
  private SportDAO sportDAO;
  @Autowired
  private LocationDAO locationDAO;
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private PlayerDAO playerDAO;
  @Autowired
  private NotificationTask notificationTask;

@PreAuthorize("hasAuthority('USER')")
@Override
public List<GameDTO> findAll() {
	return gameDAO.findAll().stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
	    
}
@PreAuthorize("hasAuthority('USER')")
@Override
public List<GameDTO> findAllFiltros(String sport, String user, String edad, String dificultad) {
	Integer sportEv=0;
	Integer userEv=0;
	Integer edadEv=0;
	Integer dificultadEv=0;
	Double edadMin=new Double(0);
	Double edadMax=new Double(100);
	Double expMin=new Double(0);
	Double expMax=new Double(5);
	List<String> deportes =new ArrayList<String>();
	List<Long> gamesRange =new ArrayList<Long>();
	List<GameDTO> gamesFiltros =new ArrayList<GameDTO>();
	List<GameDTO> gamesPreFiltros =new ArrayList<GameDTO>();

	
		if(!(sport.equals("vacio"))){
		System.out.println("aqui hay deporte");
		sportEv=1;
		String[] sports=sport.split(",");
		
			for(String a:sports){
				Sport Sportbd= sportDAO.findByType(a);
				deportes.add(Sportbd.getType());
			}
		}else{
			System.out.println("aqui no hay deporte");
			deportes.add("vacio");
		}
	
		
		if(!(user.equals("vacio"))){
			System.out.println("aqui hay creador");
			userEv=1;
		}
		
		if(!(edad.equals("vacio"))){
			System.out.println("aqui hay edad");
			edadEv=1;
			
			if(edad.equals("<18")){
				edadMin=new Double(10);
				edadMax=new Double(18);
		
				
			}else if(edad.equals("18<edad<25")){
				edadMin=new Double(18);
				edadMax=new Double(25);
			
			}else if(edad.equals("25<edad<40")){
				edadMin=new Double(25);
				edadMax=new Double(40);
		
				
			}else{
				edadMin=new Double(40);
				edadMax=new Double(100);
		
			}
		}
		
		if(!(dificultad.equals("vacio"))){
			System.out.println("aqui hay dificultad");
			dificultadEv=1;
			
			if(dificultad.equals("Principiante")){
				expMin=new Double(0);
				expMax=new Double(1);
		
				
			}else if(dificultad.equals("Aficionado")){
				expMin=new Double(2);
				expMax=new Double(3);
			
		
				
			}else if(dificultad.equals("Profesional")){
				expMin=new Double(4);
				expMax=new Double(5);
		
			}
		}
		
		
		if(edadEv==1||dificultadEv==1){
			System.out.println("Edad y dificultad");
			gamesRange=gameDAO.findByAgeRange(edadMin, edadMax, expMin,expMax);
			gamesPreFiltros=gameDAO.findAllFiltros(deportes,user,sportEv,userEv).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
			for(GameDTO g:gamesPreFiltros){
				for(Long a:gamesRange){
				if(g.getIdGame()==a){
					gamesFiltros.add(g);
				}
			  }
			}
			return gamesFiltros;
		}else{
		return gameDAO.findAllFiltros(deportes,user,sportEv,userEv).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());	
		}	
		
	}
	
	
	
@PreAuthorize("hasAuthority('USER')")
@Transactional(readOnly = false)
@Override
public GameDTO save(GameDTO game) throws RequiredFieldsException, GameColapseException, ParseException, EventBeforeDayException, AddressException, MessagingException{
	
	  
	
	if(game.getDate() == null){ //Nombre vacio
	  	  throw new RequiredFieldsException("La fecha del evento es un campo requerido");
	   }
	
	if(game.getTimeStart() == null){ //Nombre vacio
	  	  throw new RequiredFieldsException("La hora de inicio del evento es un campo requerido");
	   }
	
	if(game.getTimeEnd() == null){ //Nombre vacio
	  	  throw new RequiredFieldsException("La hora de finalización del evento es un campo requerido");
	   }
	
	LocalDate localDate = LocalDate.now();
	  LocalTime localTime = LocalTime.now();

	 if((game.getTimeStart().compareTo(game.getTimeEnd()) >= 0) ){
			throw new EventBeforeDayException("La fecha de fin del evento debe ser posterior a la de inicio ");
		} 
	 
	if((game.getDate().compareTo(localDate) <  0)){
		throw new EventBeforeDayException("La fecha del evento tiene que ser posterior al dia de hoy");
	}
	
	if((game.getDate().compareTo(localDate) ==  0) && (game.getTimeStart().compareTo(localTime)<0)){
		throw new EventBeforeDayException("La hora del evento ya ha tenido lugar, selecciona otro horario.");
	}
	
	
	if(game.getLocation() == null){ 
	  	  throw new RequiredFieldsException("La localización del evento es un campo requerido");
	   }
	
	if(game.getSport() == null){ 
	  	  throw new RequiredFieldsException("El deporte del evento es un campo requerido");
	   }
	
	if(game.getMaxPlayers() == null){ 
	  	  throw new RequiredFieldsException("El número máximo de jugadores del evento es un campo requerido");
	   }
	
	if(game.getMinPlayers() == null){ 
	  	  throw new RequiredFieldsException("El número mínimo de jugadores del evento es un campo requerido");
	   }
	 
	if (gameDAO.findColapso(game.getDate(), game.getTimeStart(), game.getTimeEnd(),game.getLocation().getIdLocation()) > 0) {
        throw new GameColapseException("Su partido se solapa con otro previamente creado, visualice el calendario para ver las fechas y horas libres.");
   }
	
	Game bdGame = new Game(game.getDate(),game.getTimeStart(), game.getTimeEnd(),game.getMaxPlayers(),
			game.getMinPlayers());
	
	

	
	Sport sport= sportDAO.findById(game.getSport().getIdSport());
	Location location= locationDAO.findById(game.getLocation().getIdLocation());
	NormalUser user= userDAO.findByLoginNormal(game.getCreator().getLogin());
		
 
	bdGame.setSport(sport);
	bdGame.setLocation(location);
	bdGame.setCreator(user);
	
	gameDAO.save(bdGame);
	
	String mensaje="el usuario "+user.getName() +" "+user.getSurname1()+ " "+user.getSurname2()+" acaba de crear un partido"
			+ " de "+sport.getType()+ " que tendrá lugar en "+location.getName()+ " el día "+bdGame.getDate()+ " a las "+
			bdGame.getTimeStart();
	notificationTask.reportCurrentTime(bdGame.getIdGame(), mensaje,false,true,null);
    return new GameDTO(bdGame);
}
@Override
public List<GameDTO> findAllLocation(Long idLocation) {
	return gameDAO.findAllLocation(idLocation).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
}
@Override
public List<GameDTO> findAllSportLocation(Long idSport, Long idLocation) {
	return gameDAO.findAllLocationSport(idLocation, idSport).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
}



@PreAuthorize("hasAuthority('USER')")
@Transactional(readOnly = false)
@Override
public void deleteById(Long idGame) throws AddressException, MessagingException, ParseException {
	
	List<Player> players= playerDAO.findAllByGame(idGame);
	String mensaje="El partido ha sido cancelado por motivos personales del creador o por que no ha llegado "
			+ "al mínimo de personas requeridas para poderse llevar a cabo. Sentimos las molestias...";
	notificationTask.reportCurrentTime(idGame, mensaje,true,false,null);
	
	if(players.size()>0){
		for(Player a:players){
			playerDAO.deleteById(a.getIdPlayer());
		}
	}
	Game game= gameDAO.findById(idGame);
	
	
	List<NormalUser> usuarios =userDAO.findAllNoAdmin();
	for(NormalUser user: usuarios){
		Set<Game> games= user.getNotifications();
		for (Game game1:games){
			if(game1.getIdGame()==idGame){
				user.getNotifications().remove(game);
			}
		}
	}
	

	gameDAO.deleteById(idGame);
	
	
				
}
	@PreAuthorize("hasAuthority('USER')")
	@Override
	public GameDTO findById(Long idGame) {
		return new GameDTO(gameDAO.findById(idGame));
	}
	
	
	@Transactional(readOnly = false)
	@Override
	public GameDTO update(Long idGame, String resultado) {	
    	Game bdGame = (Game) gameDAO.findById(idGame);
    	bdGame.setResult(resultado);
    	
		    gameDAO.save(bdGame);
		    return new GameDTO(bdGame);
		    }
	@Override
	public List<GameDTO> findAllSport(Long idSport) {
		return gameDAO.findAllSport(idSport).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
	}
	     
   

}
