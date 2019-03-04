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
import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;
import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SportService implements SportServiceInterface{

  @Autowired
    private SportDAO sportDAO;
  @Autowired
  private LocationDAO locationDAO;


@Override
public List<SportDTO> findAll() {
	return sportDAO.findAll().stream().map(sport -> new SportDTO(sport)).collect(Collectors.toList());
	    
}

@Override
public SportDTO findById(Long idSport) {
	return new SportDTO(sportDAO.findById(idSport));
}

@PreAuthorize("hasAuthority('ADMIN')")
@Transactional(readOnly = false)
@Override
public SportDTO save(SportDTO sport) throws SportExistsException {
	
	if (sportDAO.findByType(sport.getType()) != null) {
         throw new SportExistsException("El deporte " + "'"+ sport.getType() + "'"+ " ya está en su BD");
    }
	Set<Location> l = new HashSet<Location>();
		
	Sport bdSport = new Sport(sport.getType(),null);
	for(LocationDTO a: sport.getLocations()){
		Location location= locationDAO.findById(a.getIdLocation());
		l.add(location);
    }
	
	bdSport.setLocations(l);
	bdSport.setComponenteEntrada(sport.getComponenteEntrada());
	bdSport.setComponenteVisualizacion(sport.getComponenteVisualizacion());
	sportDAO.save(bdSport);
    return new SportDTO(bdSport);
}

@PreAuthorize("hasAuthority('ADMIN')")
@Transactional(readOnly = false)
@Override
public SportDTO update(SportDTO sport){
    Sport bdSport = sportDAO.findById(sport.getIdSport());
    bdSport.setType(sport.getType());
    bdSport.setComponenteVisualizacion(sport.getComponenteVisualizacion());
    bdSport.setComponenteEntrada(sport.getComponenteEntrada());
   
    bdSport.getLocations().clear();
    sport.getLocations().forEach(loc -> {
        bdSport.getLocations().add(locationDAO.findById(loc.getIdLocation()));
    });
    
    sportDAO.save(bdSport);
    return new SportDTO(bdSport);
    }

@PreAuthorize("hasAuthority('ADMIN')")
@Transactional(readOnly = false)
@Override
public void deleteById(Long idSport) {
	Boolean bol=false;
	Sport bdSport = sportDAO.findById(idSport);
	
	
		List<Location> loc= sportDAO.findLocationsOfSport(idSport);
		for(Location a:loc){
			
			Long count= locationDAO.countSportsOfaLocation(a.getIdLocation());
			
			if (count==1 && bol==false){
				sportDAO.deleteById(idSport);
				locationDAO.deleteById(a.getIdLocation());
				bol= true;
			}else{
				if(bol==false && count!=1){
					sportDAO.deleteById(idSport);
					bol= true;
				}else if (bol==true){
					
				}
			}
		}
		
	
	
	
	
}
  
	
				
	     
   

}
