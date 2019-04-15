package es.udc.lbd.asi.restexample.model.service;



import java.text.ParseException;
import java.util.List;
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
import es.udc.lbd.asi.restexample.repository.util.NotificationTask;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PlayerService implements PlayerServiceInterface{


  @Autowired
  private GameDAO gameDAO;
  @Autowired
  private PlayerDAO playerDAO;
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private NotificationTask notificationTask;

@Override
public List<PlayerDTO> findAll() {
	return playerDAO.findAll().stream().map(player -> new PlayerDTO(player)).collect(Collectors.toList());
	    
}

@PreAuthorize("hasAuthority('USER')")
@Transactional(readOnly = false)
@Override
public void deleteById(Long idPlayer) throws AddressException, MessagingException, ParseException {
	Player player= playerDAO.findById(idPlayer);
	String mensaje="Se acaba de desapuntar de este partido el usuario "+player.getPlayer().getName()+" "+player.getPlayer().getSurname1()+" "+player.getPlayer().getSurname2()+".";
	notificationTask.reportCurrentTime(player.getGame().getIdGame(), mensaje,false);
	playerDAO.deleteById(idPlayer);
	
}
	
@PreAuthorize("hasAuthority('USER')")
@Transactional(readOnly = false)
@Override
public PlayerDTO save(PlayerDTO player) throws MaxPlayersException, AddressException, MessagingException, ParseException{
	
	Player bdPlayer = new Player(player.getEquipo());
	Game game= gameDAO.findById(player.getGame().getIdGame());
	if(playerDAO.findAllByGameCount(game.getIdGame())<game.getMaxPlayers()){
		
	bdPlayer.setGame(game);
	NormalUser playerUser= (NormalUser) userDAO.findById(player.getPlayer().getIdUser());
	bdPlayer.setPlayer(playerUser);

	
	playerDAO.save(bdPlayer);
	String mensaje="Se acaba de apuntar a este partido el usuario "+player.getPlayer().getName()+" "+player.getPlayer().getSurname1()+" "+player.getPlayer().getSurname2()+".";
	notificationTask.reportCurrentTime(player.getGame().getIdGame(), mensaje,false);
    return new PlayerDTO(bdPlayer);}
	else{
		throw new MaxPlayersException("El cupo de participantes ya está cubierto");
	}
}

public List<PlayerDTO> findAllByGame(Long idGame) {
	return playerDAO.findAllByGame(idGame).stream().map(player -> new PlayerDTO(player)).collect(Collectors.toList());
}

public PlayerDTO findPlayer(Long idGame, String login) {
	return new PlayerDTO(playerDAO.findPlayerGame(login, idGame));
}

@Transactional(readOnly = false)
@Override
public PlayerDTO update(Long idPlayer, Long valorationGame) {	
	Player bdPlayer = (Player) playerDAO.findById(idPlayer);
	bdPlayer.setValorationGame(valorationGame);
	
	    playerDAO.save(bdPlayer);
	    return new PlayerDTO(bdPlayer);
	    }

}
