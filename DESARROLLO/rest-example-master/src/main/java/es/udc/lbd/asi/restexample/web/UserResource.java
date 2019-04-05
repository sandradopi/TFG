package es.udc.lbd.asi.restexample.web;


import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.service.UserService;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserDTO;
import es.udc.lbd.asi.restexample.web.exception.IdAndBodyNotMatchingOnUpdateException;
import es.udc.lbd.asi.restexample.web.exception.InstanceNotFoundExceptionHIB;
import es.udc.lbd.asi.restexample.web.exception.RequestBodyNotValidException;



@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll();
    }
    
    @GetMapping("/normal")
    public List<UserDTO> findAllNormalUsers() {
        return userService.findAllNoAdmin();
    }
    
    @GetMapping("/{login}")
    public NormalUserDTO findOneUserByLogin(@PathVariable String login) throws InstanceNotFoundExceptionHIB{
        NormalUserDTO user = userService.findByLogin(login);
    	return user;
    }
    
    @GetMapping("/{login}/organizados")
    public List<GameDTO> findAllPartidosOrganizados(@PathVariable String login){
    	 List<GameDTO> games = userService.findGamesCreated(login);
    	return games;
    }
    
    @GetMapping("/{login}/proximos")
    public List<GameDTO> findAllPartidosProximos(@PathVariable String login){
    	 List<GameDTO> games = userService.findGamesNext(login);
    	return games;
    }
    
    @PutMapping("/{idUser}")
    public NormalUserDTO update(@PathVariable Long idUser, @RequestBody @Valid NormalUserDTO user, Errors errors)
            throws IdAndBodyNotMatchingOnUpdateException, RequestBodyNotValidException, UserLoginEmailExistsException {
        errorHandler(errors);
        if (idUser != user.getIdUser()) {
            throw new IdAndBodyNotMatchingOnUpdateException(Sport.class);
        }
        return userService.update(user);
    }
    

    
    
    @DeleteMapping("/{idUser}")
    public void delete(@PathVariable Long idUser) throws InstanceNotFoundExceptionHIB, SportDeleteException{
        userService.deleteById(idUser);
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
