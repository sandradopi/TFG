package es.udc.lbd.asi.restexample.model.service;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Player;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
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


@Override
public List<GameDTO> findAll() {
	return gameDAO.findAll().stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
	    
}
@PreAuthorize("hasAuthority('USER')")
@Override
public List<GameDTO> findAllFiltros(String sport, String user) {
	Integer sportEv=0;
	Integer userEv=0;
	List<String> deportes =new ArrayList<String>();
	NormalUser usuario= new NormalUser();
	
		if(!(sport.equals("vacio"))){
		sportEv=1;
		String[] sports=sport.split(",");
		
			for(String a:sports){
				Sport Sportbd= sportDAO.findByType(a);
				deportes.add(Sportbd.getType());
			}
		}else{
			deportes.add("vacio");
		}
	
		
		if(!(user.equals("vacio"))){
			userEv=1;
		}
		
		return gameDAO.findAllFiltros(deportes,user,sportEv,userEv).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());		
		
	}
	
	
	
@PreAuthorize("hasAuthority('USER')")
@Transactional(readOnly = false)
@Override
public GameDTO save(GameDTO game){
	
	Game bdGame = new Game(game.getDate(),game.getTimeStart(), game.getTimeEnd(),game.getMaxPlayers(),
			game.getMinPlayers());
	
	Sport sport= sportDAO.findById(game.getSport().getIdSport());
	Location location= locationDAO.findById(game.getLocation().getIdLocation());
	NormalUser user= userDAO.findByLoginNormal(game.getCreator().getLogin());
	
		
 
	bdGame.setSport(sport);
	bdGame.setLocation(location);
	bdGame.setCreator(user);
	
	gameDAO.save(bdGame);
    return new GameDTO(bdGame);
}

public List<GameDTO> findAllLocation(Long idLocation) {
	return gameDAO.findAllLocation(idLocation).stream().map(game -> new GameDTO(game)).collect(Collectors.toList());
}

@PreAuthorize("hasAuthority('USER')")
@Transactional(readOnly = false)
@Override
public void deleteById(Long idGame) {
	List<Player> players= playerDAO.findAllByGame(idGame);
	if(players.size()>0){
		for(Player a:players){
			playerDAO.deleteById(a.getIdPlayer());
		}
	}
	Game game= gameDAO.findById(idGame);
	gameDAO.deleteById(idGame);
	
				
}

		
	     
   

}
