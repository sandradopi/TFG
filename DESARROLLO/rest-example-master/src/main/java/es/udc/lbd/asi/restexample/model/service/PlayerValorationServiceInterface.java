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
import es.udc.lbd.asi.restexample.model.service.dto.PlayerValorationDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.model.service.dto.TeamDTO;


public interface PlayerValorationServiceInterface {
	  public List<PlayerValorationDTO> findAll();
	  public PlayerValorationDTO save(PlayerValorationDTO player);
	  public void deleteById(Long idPlayer);
	

	 


}
