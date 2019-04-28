package es.udc.lbd.asi.restexample.model.repository;

import java.util.Collection;
import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;

public interface LocationDAO {

	 public void save(Location location);
	 public List <Location> findAll();
	 public Location findById(Long idLocation) ;
	 public void deleteById(Long idLocation) ;
	 public Long countSportsOfaLocation(Long idLocation);
	 public List<Location> findAllSport(Long idSport);
     public Location findByName(String name);
	 public Location findByUbicacion(Double latitud, Double longitud);
	 public Long countLocations();
}
