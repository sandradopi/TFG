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
import es.udc.lbd.asi.restexample.model.domain.Team;
import es.udc.lbd.asi.restexample.model.exception.LocationExistsException;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.exception.TeamExistsException;
import es.udc.lbd.asi.restexample.model.repository.GameDAO;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;
import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.model.service.dto.TeamDTO;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class LocationService implements LocationServiceInterface{

 
  @Autowired
  private LocationDAO locationDAO;
  @Autowired
  private GameDAO gameDAO;
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
public void deleteById(Long idLocation) throws SportDeleteException {
	Location location=locationDAO.findById(idLocation);
	
	if (gameDAO.findByLocation(location)!=0){
		throw new SportDeleteException("Esta localización tiene asociados actualmente partidos");
	}
	
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
    bdLocation.setLatitud(location.getLatitud());
    bdLocation.setLongitud(location.getLongitud());
    
    locationDAO.save(bdLocation);
    return new LocationDTO(bdLocation);
    }
  
	
@PreAuthorize("hasAuthority('ADMIN')")
@Transactional(readOnly = false)
@Override
public LocationDTO save(LocationDTO location) throws LocationExistsException, RequiredFieldsException {
	if(location.getName() == null){
  	  throw new RequiredFieldsException("El nombre es un campo requerido");
   }
	
	if(location.getLatitud() == null && location.getLongitud() == null){ 
	  	  throw new RequiredFieldsException("La latitud y logitud son campos requerido");
	   }
	
	
	if (locationDAO.findByUbicacion(location.getLatitud(), location.getLongitud())!=null) {
         throw new LocationExistsException("Esas coordenadas ya pertenecen a una localizacion que ya está en su BD");
    }

		
	Location bdLocation = new Location(location.getName(),location.getLatitud(),location.getLongitud());
	

	locationDAO.save(bdLocation);
    return new LocationDTO(bdLocation);
}				
	     		
	     
   

}
