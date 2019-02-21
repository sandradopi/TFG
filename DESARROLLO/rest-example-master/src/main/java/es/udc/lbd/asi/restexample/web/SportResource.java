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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.service.SportService;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
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
    
    
    @PostMapping
    public SportDTO save(@RequestBody @Valid SportDTO sport, Errors errors) throws RequestBodyNotValidException, SportExistsException {
        errorHandler(errors); 
        return sportService.save(sport);
    }
    
    @DeleteMapping("/{idSport}")
    public void delete(@PathVariable Long idSport) throws InstanceNotFoundExceptionHIB{
        sportService.deleteById(idSport);
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
