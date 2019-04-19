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
import es.udc.lbd.asi.restexample.model.domain.PlayerValoration;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.exception.EventBeforeDayException;
import es.udc.lbd.asi.restexample.model.exception.MaxPlayersException;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.repository.GameDAO;
import es.udc.lbd.asi.restexample.model.repository.UserDAO;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.PlayerDAO;
import es.udc.lbd.asi.restexample.model.repository.PlayerValorationDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;

import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerValorationDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.repository.util.NotificationTask;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PlayerValorationService implements PlayerValorationServiceInterface{


  @Autowired
  private PlayerValorationDAO playerValorationDAO;
  @Autowired
  private PlayerDAO playerDAO;
  @Autowired
  private UserDAO userDAO;


@Override
public List<PlayerValorationDTO> findAll() {
	return playerValorationDAO.findAll().stream().map(player -> new PlayerValorationDTO(player)).collect(Collectors.toList());
	    
}

@PreAuthorize("hasAuthority('USER')")
@Transactional(readOnly = false)
@Override
public void deleteById(Long idPlayerValoration)  {
	PlayerValoration playerValoration= playerValorationDAO.findById(idPlayerValoration);
	playerValorationDAO.deleteById(idPlayerValoration);
	
}
	
@PreAuthorize("hasAuthority('USER')")
@Transactional(readOnly = false)
@Override
public PlayerValorationDTO save(PlayerValorationDTO playerValoration) {
	
	PlayerValoration bdPlayerValoration = new PlayerValoration(playerValoration.getValoration());
	Player player= playerDAO.findById(playerValoration.getPlayer().getIdPlayer());
	NormalUser user= (NormalUser) userDAO.findById(playerValoration.getUser().getIdUser());
	
	bdPlayerValoration.setReview(playerValoration.getReview());
	bdPlayerValoration.setPlayer(player);
	bdPlayerValoration.setUser(user);
	
	playerValorationDAO.save(bdPlayerValoration);
	
	Integer experienciaTotal=0;
	Long experience= (playerValoration.getValoration()+user.getExperience());
	Long valoraciones= playerValorationDAO.countValoration(user.getLogin());
	experienciaTotal=(int) (experience/valoraciones);
	System.out.println("acumulado"+experience);
	System.out.println("totally"+valoraciones);
	
	user.setExperience(experienciaTotal);
	userDAO.save(user);
	
	return new PlayerValorationDTO(bdPlayerValoration);
	
}





}
