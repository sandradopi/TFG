package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.OpeningTime;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.Team;



public interface OpenDAO {

	 public void save(OpeningTime open);
	 public List <OpeningTime> findAll();
	 public OpeningTime findById(Long idOpening) ;
	 public void deleteById(Long idOpening) ;
	 public List<OpeningTime> findByLocation(Long idLocation) ;


}
