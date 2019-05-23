package es.udc.lbd.asi.restexample.model.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;


public interface SportServiceInterface {
	  public List<SportDTO> findAll();
	  public SportDTO findById(Long id);
	  public SportDTO save(SportDTO sport) throws SportExistsException, RequiredFieldsException;
	  public void deleteById(Long idSport) throws SportDeleteException;
	  public SportDTO update(SportDTO sport) throws SportExistsException, RequiredFieldsException;
	  public Resource getImageAsResource(String fileName) throws Exception;
	  public void store(MultipartFile file) throws Exception;


}
