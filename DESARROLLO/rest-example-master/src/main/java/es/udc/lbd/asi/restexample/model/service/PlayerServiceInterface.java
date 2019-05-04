package es.udc.lbd.asi.restexample.model.service;

import java.text.ParseException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import es.udc.lbd.asi.restexample.model.exception.MaxPlayersException;
import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.exception.TeamExistsException;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.model.service.dto.TeamDTO;


public interface PlayerServiceInterface {
	  public List<PlayerDTO> findAll();
	  public PlayerDTO save(PlayerDTO player) throws MaxPlayersException, AddressException, MessagingException, ParseException;
	  public void deleteById(Long idPlayer) throws AddressException, MessagingException, ParseException;
	  public PlayerDTO update(Long idPlayer, Long valorationGame);
	  public PlayerDTO updateTeam(Long idPlayer, String equipo);
	  public PlayerDTO findPlayerByPlayer(Long idPlayer);
	  public PlayerDTO findPlayer(Long idGame, String login);
	  public List<PlayerDTO> findAllByGame(Long idGame);

	 


}
