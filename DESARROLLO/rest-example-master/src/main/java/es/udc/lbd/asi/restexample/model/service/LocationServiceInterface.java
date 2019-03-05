package es.udc.lbd.asi.restexample.model.service;

import java.util.List;

import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;


public interface LocationServiceInterface {
	  public List<LocationDTO> findAll();
	  public LocationDTO findById(Long idLocation);
	  public void deleteById(Long idLocation);
	  public LocationDTO update(LocationDTO location);



}
