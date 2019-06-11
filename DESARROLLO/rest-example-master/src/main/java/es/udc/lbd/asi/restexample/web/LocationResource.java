package es.udc.lbd.asi.restexample.web;


import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.exception.LocationExistsException;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.DeleteException;
import es.udc.lbd.asi.restexample.model.exception.TeamExistsException;
import es.udc.lbd.asi.restexample.model.service.LocationService;
import es.udc.lbd.asi.restexample.model.service.SportService;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.web.exception.IdAndBodyNotMatchingOnUpdateException;
import es.udc.lbd.asi.restexample.web.exception.InstanceNotFoundExceptionHIB;
import es.udc.lbd.asi.restexample.web.exception.RequestBodyNotValidException;



@RestController
@RequestMapping("/api/locations")
public class LocationResource {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<LocationDTO> findAll() {
        return locationService.findAll();
    }
    
    @GetMapping("/filter/{idSport}")
    public List <LocationDTO> findAllSport(@PathVariable Long idSport) {
    	 return locationService.findAllSport(idSport);
    }
    
    @GetMapping("/{idLocation}")
    public LocationDTO findOne(@PathVariable Long idLocation) throws InstanceNotFoundExceptionHIB{
        LocationDTO location = locationService.findById(idLocation);
    	return location;
    }
    
    @DeleteMapping("/{idLocation}")
    public void delete(@PathVariable Long idLocation) throws InstanceNotFoundExceptionHIB, DeleteException{
        locationService.deleteById(idLocation);
    }
    
    @PutMapping("/{idLocation}")
    public LocationDTO update(@PathVariable Long idLocation, @RequestBody @Valid LocationDTO location, Errors errors)
            throws IdAndBodyNotMatchingOnUpdateException, RequestBodyNotValidException {
        errorHandler(errors);
        if (idLocation != location.getIdLocation()) {
            throw new IdAndBodyNotMatchingOnUpdateException(Sport.class);
        }
        return locationService.update(location);
    }
    
    @PostMapping
    public LocationDTO save(@RequestBody LocationDTO location, Errors errors) throws LocationExistsException, RequiredFieldsException {
       
        return locationService.save(location);
    }
    
    private void errorHandler(Errors errors) throws RequestBodyNotValidException {
        if (errors.hasErrors()) {
            String errorMsg = errors.getFieldErrors().stream()
                    .map(fe -> String.format("%s.%s %s", fe.getObjectName(), fe.getField(), fe.getDefaultMessage()))
                    .collect(Collectors.joining("; "));
            throw new RequestBodyNotValidException(errorMsg);
        }
    }
   
  
}
