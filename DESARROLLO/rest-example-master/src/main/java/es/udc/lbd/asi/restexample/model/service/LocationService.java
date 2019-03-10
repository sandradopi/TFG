package es.udc.lbd.asi.restexample.model.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
  @Autowired
  private SportDAO sportDAO;


@Override
public List<LocationDTO> findAll() {
	return locationDAO.findAll().stream().map(location -> new LocationDTO(location)).collect(Collectors.toList());
	    
}

@Override
public List<LocationDTO> findAllSport(Long idSport) {
	return locationDAO.findAllSport(idSport).stream().map(location -> new LocationDTO(location)).collect(Collectors.toList());
	    
}

@Override
public LocationDTO findById(Long idLocation) {
	return new LocationDTO(locationDAO.findById(idLocation));
}
  

@PreAuthorize("hasAuthority('ADMIN')")
@Transactional(readOnly = false)
@Override
public void deleteById(Long idLocation) {
	Location location=locationDAO.findById(idLocation);
	
	 for(Sport a: location.getSports()){
    	a.getLocations().remove(location);
     }
	 
	locationDAO.deleteById(idLocation);
	
}

@PreAuthorize("hasAuthority('ADMIN')")
@Transactional(readOnly = false)
@Override
public LocationDTO update(LocationDTO location){
    Location bdLocation = locationDAO.findById(location.getIdLocation());
    bdLocation.setName(location.getName());
    bdLocation.setCostPerHour(location.getCostPerHour());
    
    
    locationDAO.save(bdLocation);
    return new LocationDTO(bdLocation);
    }
  
	
				
	     
   

}
