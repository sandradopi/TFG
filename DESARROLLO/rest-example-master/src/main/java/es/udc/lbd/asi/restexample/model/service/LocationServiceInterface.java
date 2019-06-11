package es.udc.lbd.asi.restexample.model.service;

import java.util.List;

import es.udc.lbd.asi.restexample.model.exception.LocationExistsException;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.DeleteException;
import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;


public interface LocationServiceInterface {
	  public List<LocationDTO> findAll();
	  public LocationDTO findById(Long idLocation);
	  public void deleteById(Long idLocation) throws DeleteException;
	  public LocationDTO update(LocationDTO location);
	  public List<LocationDTO> findAllSport(Long idSport);
	  public LocationDTO save(LocationDTO location) throws LocationExistsException, RequiredFieldsException;



}
