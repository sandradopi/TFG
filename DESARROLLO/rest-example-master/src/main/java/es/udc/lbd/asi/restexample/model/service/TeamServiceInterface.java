package es.udc.lbd.asi.restexample.model.service;

import java.util.List;

import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.exception.TeamExistsException;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.model.service.dto.TeamDTO;


public interface TeamServiceInterface {
	  public List<TeamDTO> findAll();
	  public TeamDTO save(TeamDTO team) throws TeamExistsException;
	 


}
