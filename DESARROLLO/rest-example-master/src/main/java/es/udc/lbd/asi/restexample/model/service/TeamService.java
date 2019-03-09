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
import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.exception.TeamExistsException;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;
import es.udc.lbd.asi.restexample.model.repository.TeamDAO;
import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.model.service.dto.TeamDTO;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TeamService implements TeamServiceInterface{


  @Autowired
  private TeamDAO teamDAO;
  @Autowired
  private SportDAO sportDAO;


@Override
public List<TeamDTO> findAll() {
	return teamDAO.findAll().stream().map(team -> new TeamDTO(team)).collect(Collectors.toList());
	    
}
	
@PreAuthorize("hasAuthority('USER')")
@Transactional(readOnly = false)
@Override
public TeamDTO save(TeamDTO team) throws TeamExistsException {
	
	if (teamDAO.findByName(team.getName()) != null) {
         throw new TeamExistsException("El equipo " + "'"+ team.getName() + "'"+ " ya está en su BD");
    }

		
	Team bdTeam = new Team(team.getName(),null);
	
	Sport sport= sportDAO.findById(team.getSport().getIdSport());
		
 
	bdTeam.setSport(sport);
	
	teamDAO.save(bdTeam);
    return new TeamDTO(bdTeam);
}				
	     
   

}
