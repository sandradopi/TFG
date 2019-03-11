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
import es.udc.lbd.asi.restexample.model.domain.OpeningTime;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.Team;
import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.exception.TeamExistsException;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.OpenDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;
import es.udc.lbd.asi.restexample.model.repository.TeamDAO;
import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;
import es.udc.lbd.asi.restexample.model.service.dto.OpeningDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.model.service.dto.TeamDTO;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class OpenService implements OpenServiceInterface{


  @Autowired
  private OpenDAO openDAO;
  @Autowired
  private LocationDAO locationDAO;

@Override
public List<OpeningDTO> findAll() {
	return openDAO.findAll().stream().map(open -> new OpeningDTO(open)).collect(Collectors.toList());
	    
}

@Override
public List<OpeningDTO> findAllLocation(Long idOpening) {
	return openDAO.findByLocation(idOpening).stream().map(open -> new OpeningDTO(open)).collect(Collectors.toList());
	    
}
	
@PreAuthorize("hasAuthority('ADMIN')")
@Transactional(readOnly = false)
@Override
public OpeningDTO save(OpeningDTO open)  {
	
	
	OpeningTime bdOpen = new OpeningTime(open.getDayOfTheWeek(), open.getTo(),open.getFrom());
	
	Location location= locationDAO.findById(open.getLocation().getIdLocation());
	bdOpen.setLocation(location);
	
	openDAO.save(bdOpen);
    return new OpeningDTO(bdOpen);
}				
	     
   

}
