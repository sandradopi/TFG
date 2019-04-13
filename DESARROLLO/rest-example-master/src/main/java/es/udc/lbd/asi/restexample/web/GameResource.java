package es.udc.lbd.asi.restexample.web;


import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.exception.EventBeforeDayException;
import es.udc.lbd.asi.restexample.model.exception.GameColapseException;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.exception.TeamExistsException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.service.GameService;
import es.udc.lbd.asi.restexample.model.service.SportService;
import es.udc.lbd.asi.restexample.model.service.TeamService;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.model.service.dto.TeamDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserDTO;
import es.udc.lbd.asi.restexample.web.exception.IdAndBodyNotMatchingOnUpdateException;
import es.udc.lbd.asi.restexample.web.exception.InstanceNotFoundExceptionHIB;
import es.udc.lbd.asi.restexample.web.exception.RequestBodyNotValidException;



@RestController
@RequestMapping("/api/games")
public class GameResource {

    @Autowired
    private GameService gameService;

    @GetMapping
    public List<GameDTO> findAll() {
        return gameService.findAll();
    }
    
    @GetMapping("/filtro")
    public List<GameDTO> findAllFiltros(@RequestParam String creator, @RequestParam String sport) {
        return gameService.findAllFiltros(sport, creator);
    }
    
   
    @GetMapping("/locations/{idLocation}")
    public List <GameDTO> findAllLocation(@PathVariable Long idLocation) {
    	 return gameService.findAllLocation(idLocation);
    }
    
    @GetMapping("/{idGame}")
    public GameDTO findOne(@PathVariable Long idGame) throws InstanceNotFoundExceptionHIB{
        GameDTO game = gameService.findById(idGame);
    	return game;
    }
    
    
    @PostMapping
    public GameDTO save(@RequestBody @Valid GameDTO game, Errors errors) throws RequestBodyNotValidException, RequiredFieldsException, GameColapseException, ParseException, EventBeforeDayException {
        errorHandler(errors); 
        return gameService.save(game);
    }
    @DeleteMapping("/{idGame}")
    public void delete(@PathVariable Long idGame) throws AddressException, MessagingException, ParseException {
        gameService.deleteById(idGame);
    }
    
    @PutMapping("/{idGame}")
    public GameDTO update(@PathVariable Long idGame, @RequestBody @Valid String result, Errors errors){
           
        return gameService.update(idGame,result);
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
