package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Location;

public interface LocationDAO {

	 public void save(Location location);
	 public List <Location> findAll();
	 public Location findById(Long idLocation) ;
	 public void deleteById(Long idLocation) ;
	 public Long countSportsOfaLocation(Long idLocation);
}
