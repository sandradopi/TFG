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
import es.udc.lbd.asi.restexample.model.repository.GameDAO;
import es.udc.lbd.asi.restexample.model.repository.UserDAO;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.PlayerDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;

import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PlayerService implements PlayerServiceInterface{


  @Autowired
  private GameDAO gameDAO;
  @Autowired
  private PlayerDAO playerDAO;

@Override
public List<PlayerDTO> findAll() {
	return playerDAO.findAll().stream().map(player -> new PlayerDTO(player)).collect(Collectors.toList());
	    
}
	
@PreAuthorize("hasAuthority('USER')")
@Transactional(readOnly = false)
@Override
public PlayerDTO save(PlayerDTO player){
	
	Player bdPlayer = new Player(player.getValorationGame(), player.getEquipo());
	Game game= gameDAO.findById(player.getGame().getIdGame());
	bdPlayer.setGame(game);

	
	playerDAO.save(bdPlayer);
    return new PlayerDTO(bdPlayer);
}				
	     
   

}
