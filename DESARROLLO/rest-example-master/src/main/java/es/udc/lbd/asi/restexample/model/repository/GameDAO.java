package es.udc.lbd.asi.restexample.model.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;




public interface GameDAO {

	 public void save(Game game);
	 public List <Game> findAll();
	 public Game findById(Long idGame) ;
	 public void deleteById(Long idGame) ;
	 public Long findBySport(Sport sport);
	 public Long findByLocation(Location location);
	 public Long findByCreator(Long idUser);
	 public List<Game> findAllLocation(Long idLocation);
	public List<Game> findAllFiltros(List<String> sport, String user, Integer sportEv, Integer userEv);
	Long findColapso(LocalDate date, LocalTime timeStart, LocalTime timeEnd);

}
