package es.udc.lbd.asi.restexample.model.repository;

import java.util.Collection;
import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Player;
import es.udc.lbd.asi.restexample.model.domain.PlayerValoration;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;


public interface PlayerValorationDAO {

	 public void save(PlayerValoration player);
	 public List <PlayerValoration> findAll();
	 public PlayerValoration findById(Long idPlayerValoration) ;
	 public PlayerValoration findByIdPlayer(Long idPlayer) ;
	 public void deleteById(Long idPlayerValoration) ;
	 public Long countValoration(String login);


}
