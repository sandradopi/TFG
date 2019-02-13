package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Sport;



public interface SportDAO {

	 public void save(Sport sport);
	 public List <Sport> findAll();
	 public Sport findById(Long idSport) ;
	 public void deleteById(Long idSport) ;
	 public Long countLocations(Long idSport);
}
