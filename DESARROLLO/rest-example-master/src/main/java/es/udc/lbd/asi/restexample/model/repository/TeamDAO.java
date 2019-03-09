package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.Team;



public interface TeamDAO {

	 public void save(Team team);
	 public List <Team> findAll();
	 public Team findById(Long idTeam) ;
	 public void deleteById(Long idTeam) ;
	 public Team findByName(String name) ;
	 public List<Team> findBySport(Sport sport);

}
