package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Game;




public interface GameDAO {

	 public void save(Game game);
	 public List <Game> findAll();
	 public Game findById(Long idGame) ;
	 public void deleteById(Long idGame) ;

}
