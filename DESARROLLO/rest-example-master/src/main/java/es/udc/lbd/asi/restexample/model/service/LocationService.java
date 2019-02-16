package es.udc.lbd.asi.restexample.model.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;
import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class LocationService implements LocationServiceInterface{

 
  @Autowired
  private LocationDAO locationDAO;


@Override
public List<LocationDTO> findAll() {
	return locationDAO.findAll().stream().map(location -> new LocationDTO(location)).collect(Collectors.toList());
	    
}
  
	
				
	     
   

}
