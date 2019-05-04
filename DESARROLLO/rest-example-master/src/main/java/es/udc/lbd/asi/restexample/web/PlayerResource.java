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
import org.springframework.web.bind.annotation.RestController;

import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.exception.MaxPlayersException;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.exception.TeamExistsException;
import es.udc.lbd.asi.restexample.model.service.GameService;
import es.udc.lbd.asi.restexample.model.service.PlayerService;
import es.udc.lbd.asi.restexample.model.service.SportService;
import es.udc.lbd.asi.restexample.model.service.TeamService;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;
import es.udc.lbd.asi.restexample.model.service.dto.TeamDTO;
import es.udc.lbd.asi.restexample.web.exception.IdAndBodyNotMatchingOnUpdateException;
import es.udc.lbd.asi.restexample.web.exception.InstanceNotFoundExceptionHIB;
import es.udc.lbd.asi.restexample.web.exception.RequestBodyNotValidException;



@RestController
@RequestMapping("/api/players")
public class PlayerResource {

    @Autowired
    private PlayerService playerService;

    @GetMapping
    public List<PlayerDTO> findAll() {
        return playerService.findAll();
    }
    
    @GetMapping("/{idGame}")
    public List<PlayerDTO> findAllByGame(@PathVariable Long idGame) {
    	return playerService.findAllByGame(idGame);
    }
    
    @GetMapping("/{idGame}/{login}")
    public PlayerDTO findPlayer(@PathVariable Long idGame, @PathVariable String login) {
    	return playerService.findPlayer(idGame,login);
    }
    
    @GetMapping("findPlayer/{idPlayer}")
    public PlayerDTO findPlayer(@PathVariable Long idPlayer) {
    	return playerService.findPlayerByPlayer(idPlayer);
    }
    
    
    @PostMapping
    public PlayerDTO save(@RequestBody PlayerDTO player, Errors errors) throws MaxPlayersException, AddressException, MessagingException, ParseException {
        return playerService.save(player);
    }
    
    @PutMapping("/{idPlayer}/{valorationGame}")
    public PlayerDTO update(@PathVariable Long idPlayer, @PathVariable Long valorationGame){
           
        return playerService.update(idPlayer,valorationGame);
    }
    
    @PutMapping("/{idPlayer}/team/{equipo}")
    public PlayerDTO updateTeam(@PathVariable Long idPlayer, @PathVariable String equipo){
           
        return playerService.updateTeam(idPlayer,equipo);
    }
    
    
    @DeleteMapping("/{idPlayer}")
    public void delete(@PathVariable Long idPlayer) throws AddressException, MessagingException, ParseException {
        playerService.deleteById(idPlayer);
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
