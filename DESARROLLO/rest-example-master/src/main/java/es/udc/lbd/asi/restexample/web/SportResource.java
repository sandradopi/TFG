package es.udc.lbd.asi.restexample.web;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.udc.lbd.asi.restexample.model.service.SportService;
import es.udc.lbd.asi.restexample.model.service.UserService;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.web.exception.InstanceNotFoundExceptionHIB;



@RestController
@RequestMapping("/api/sports")
public class SportResource {

    @Autowired
    private SportService sportService;

    @GetMapping
    public List<SportDTO> findAll() {
        return sportService.findAll();
    }
    
   
    
  
    
}
