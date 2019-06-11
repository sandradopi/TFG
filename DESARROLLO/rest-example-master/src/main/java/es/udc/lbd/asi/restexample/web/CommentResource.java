package es.udc.lbd.asi.restexample.web;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import es.udc.lbd.asi.restexample.model.exception.EventBeforeDayException;
import es.udc.lbd.asi.restexample.model.exception.GameColapseException;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.DeleteException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.service.CommentService;
import es.udc.lbd.asi.restexample.model.service.UserService;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.GameMessageDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;
import es.udc.lbd.asi.restexample.model.service.dto.RecomendacionDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserMessageCountDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserMessageDTO;
import es.udc.lbd.asi.restexample.web.exception.IdAndBodyNotMatchingOnUpdateException;
import es.udc.lbd.asi.restexample.web.exception.InstanceNotFoundExceptionHIB;
import es.udc.lbd.asi.restexample.web.exception.RequestBodyNotValidException;



@RestController
@RequestMapping("/api/comments")
public class CommentResource {

    @Autowired
    private CommentService commentService;

  
    @GetMapping("/game/{idGame}")
    public List<GameMessageDTO> findAllByGame(@PathVariable Long idGame) {
    	 return commentService.findAllByGame(idGame);
    }
    
    @PostMapping("/game")
    public GameMessageDTO save(@RequestBody @Valid GameMessageDTO commentGame)  { 
        return commentService.saveComment(commentGame);
    }
    @PostMapping("/user")
    public UserMessageDTO save(@RequestBody @Valid UserMessageDTO commentUser)  {
        return commentService.saveComment(commentUser);
    }
    
    @GetMapping("/user/{UserFrom}/{UserTo}")
    public List<UserMessageDTO> findAllUserFromUser(@PathVariable String UserFrom, @PathVariable String UserTo) {
    	 return commentService.findAllUserFromUser(UserFrom,UserTo);
    }
    @GetMapping("/user/{login}")
    public List<UserMessageCountDTO> findAllUserMessage(@PathVariable String login) {
    	 List<UserMessageCountDTO> userMessages= commentService.findAllUser(login);
    	
    	 return userMessages;
    }
    
    @GetMapping("/user/countMessages/{login}")
    public Long findAllMessagesNotViewed(@PathVariable String login) {
    	 return  commentService.findAllToMe(login);
    }
    
    @PutMapping("/user/{UserFrom}/{UserTo}")
    public List<UserMessageDTO> updateState(@PathVariable String UserFrom, @PathVariable String UserTo){
        return commentService.updateAllMessState(UserFrom,UserTo);
    }
    
  
    
}
