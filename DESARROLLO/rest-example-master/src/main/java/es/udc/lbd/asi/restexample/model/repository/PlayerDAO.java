package es.udc.lbd.asi.restexample.model.repository;

import java.util.Collection;
import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Player;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;


public interface PlayerDAO {

	 public void save(Player player);
	 public List <Player> findAll();
	 public Player findById(Long idPlayer) ;
	 public void deleteById(Long idPlayer) ;
	 public List<Player> findAllByGame(Long idGame);
	 public Long findAllByGameCount(Long idGame);
	 public Player findPlayerGame(String login,Long idGame);
	 
}
