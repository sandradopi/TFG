package es.udc.lbd.asi.restexample.model.service;

import java.util.List;

import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;


public interface SportServiceInterface {
	  public List<SportDTO> findAll();
	  public SportDTO findById(Long id);
	  public SportDTO save(SportDTO sport) throws SportExistsException, RequiredFieldsException;
	  public void deleteById(Long idSport);
	  public SportDTO update(SportDTO sport) throws SportExistsException, RequiredFieldsException;


}
