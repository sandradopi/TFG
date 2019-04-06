package es.udc.lbd.asi.restexample.model.service;



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
import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.exception.EventBeforeDayException;
import es.udc.lbd.asi.restexample.model.exception.MaxPlayersException;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.repository.GameDAO;
import es.udc.lbd.asi.restexample.model.repository.UserDAO;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.PlayerDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;

import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PlayerService implements PlayerServiceInterface{


  @Autowired
  private GameDAO gameDAO;
  @Autowired
  private PlayerDAO playerDAO;
  @Autowired
  private UserDAO userDAO;

@Override
public List<PlayerDTO> findAll() {
	return playerDAO.findAll().stream().map(player -> new PlayerDTO(player)).collect(Collectors.toList());
	    
}

@PreAuthorize("hasAuthority('USER')")
@Transactional(readOnly = false)
@Override
public void deleteById(Long idPlayer) {
	Player player= playerDAO.findById(idPlayer);
	playerDAO.deleteById(idPlayer);
	
				
}
	
@PreAuthorize("hasAuthority('USER')")
@Transactional(readOnly = false)
@Override
public PlayerDTO save(PlayerDTO player) throws MaxPlayersException{
	
	Player bdPlayer = new Player(player.getEquipo());
	Game game= gameDAO.findById(player.getGame().getIdGame());
	if(playerDAO.findAllByGameCount(game.getIdGame())<game.getMaxPlayers()){
		
	bdPlayer.setGame(game);
	NormalUser playerUser= (NormalUser) userDAO.findById(player.getPlayer().getIdUser());
	bdPlayer.setPlayer(playerUser);

	
	playerDAO.save(bdPlayer);
    return new PlayerDTO(bdPlayer);}
	else{
		throw new MaxPlayersException("El cupo de participantes ya está cubierto");
	}
}

public List<PlayerDTO> findAllByGame(Long idGame) {
	return playerDAO.findAllByGame(idGame).stream().map(player -> new PlayerDTO(player)).collect(Collectors.toList());
}


}
