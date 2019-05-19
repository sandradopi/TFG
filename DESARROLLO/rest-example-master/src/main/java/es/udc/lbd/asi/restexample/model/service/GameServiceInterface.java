package es.udc.lbd.asi.restexample.model.service;

import java.text.ParseException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.web.bind.annotation.PathVariable;

import es.udc.lbd.asi.restexample.model.exception.EventBeforeDayException;
import es.udc.lbd.asi.restexample.model.exception.GameColapseException;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.exception.TeamExistsException;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.model.service.dto.TeamDTO;


public interface GameServiceInterface {
	  public List<GameDTO> findAll();
	  public GameDTO save(GameDTO game) throws RequiredFieldsException, GameColapseException, ParseException, EventBeforeDayException, AddressException, MessagingException;
	  public void deleteById(Long idGame) throws AddressException, MessagingException, ParseException;
	  public List<GameDTO> findAllFiltros(String sport, String user, String edad,String dificultad);
	  public GameDTO findById(Long idGame);
	  public GameDTO update(Long idGame, String resultado);
	  public List<GameDTO> findAllLocation(Long idLocation);
	  public List<GameDTO> findAllSport(Long idSport);
	  public List <GameDTO> findAllSportLocation(@PathVariable Long idSport, @PathVariable Long idLocation);
	  
	 


}
