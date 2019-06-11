package es.udc.lbd.asi.restexample.web;


import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.DeleteException;
import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.service.SportService;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.web.exception.IdAndBodyNotMatchingOnUpdateException;
import es.udc.lbd.asi.restexample.web.exception.InstanceNotFoundExceptionHIB;
import es.udc.lbd.asi.restexample.web.exception.RequestBodyNotValidException;



@RestController
@RequestMapping("/api/sports")
public class SportResource {

    @Autowired
    private SportService sportService;

    @GetMapping
    public List<SportDTO> findAll() {
        return sportService.findAll();
    }
    
    @GetMapping("/{idSport}")
    public SportDTO findOne(@PathVariable Long idSport) throws InstanceNotFoundExceptionHIB{
        SportDTO sport = sportService.findById(idSport);
    	return sport;
    }
    
    @PutMapping("/{idSport}")
    public SportDTO update(@PathVariable Long idSport, @RequestBody  SportDTO sport, Errors errors)
            throws IdAndBodyNotMatchingOnUpdateException, SportExistsException, RequiredFieldsException {
        if (idSport != sport.getIdSport()) {
            throw new IdAndBodyNotMatchingOnUpdateException(Sport.class);
        }
        return sportService.update(sport);
    }
    
    
    @PostMapping
    public SportDTO save(@RequestBody SportDTO sport, Errors errors) throws  SportExistsException, RequiredFieldsException {
        return sportService.save(sport);
    }
    
    @DeleteMapping("/{idSport}")
    public void delete(@PathVariable Long idSport) throws InstanceNotFoundExceptionHIB, DeleteException{
        sportService.deleteById(idSport);
    }
    
    @PostMapping(value = "/uploadFile")
    public void loadImage(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws Exception {
        modelMap.addAttribute("file", file);
        sportService.store(file);
    }
    
    @GetMapping(value ="/imagenes/{type}")
    public Resource getImage(@PathVariable String type) {
        try {
			return sportService.getImageAsResource(type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return null;
    
        
    }
    
    

}
