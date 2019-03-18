package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Player;


public interface PlayerDAO {

	 public void save(Player player);
	 public List <Player> findAll();
	 public Player findById(Long idPlayer) ;
	 public void deleteById(Long idPlayer) ;

}
