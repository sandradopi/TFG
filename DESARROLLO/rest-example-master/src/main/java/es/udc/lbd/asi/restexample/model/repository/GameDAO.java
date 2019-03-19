package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Sport;




public interface GameDAO {

	 public void save(Game game);
	 public List <Game> findAll();
	 public Game findById(Long idGame) ;
	 public void deleteById(Long idGame) ;
	 public Long findBySport(Sport sport);
	 public Long findByLocation(Location location);
	 public Long findByCreator(Long idUser);

}
