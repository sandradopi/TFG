package es.udc.lbd.asi.restexample.model.service;

import java.util.List;

import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.exception.TeamExistsException;
import es.udc.lbd.asi.restexample.model.service.dto.OpeningDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.model.service.dto.TeamDTO;


public interface OpenServiceInterface {
	  public List<OpeningDTO> findAll();
	  public OpeningDTO save(OpeningDTO open);
	  public List<OpeningDTO> findAllLocation(Long idOpening);
	 


}
