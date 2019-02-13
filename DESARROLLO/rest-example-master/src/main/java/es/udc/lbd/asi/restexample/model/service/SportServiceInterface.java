package es.udc.lbd.asi.restexample.model.service;

import java.util.List;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;


public interface SportServiceInterface {
	  public List<SportDTO> findAll();
	  public SportDTO findById(Long id);
	  public SportDTO save(SportDTO sport);
	  public void deleteById(Long idSport);


}
