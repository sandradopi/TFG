package es.udc.lbd.asi.restexample.web;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import es.udc.lbd.asi.restexample.model.exception.EventBeforeDayException;
import es.udc.lbd.asi.restexample.model.exception.GameColapseException;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.service.CommentService;
import es.udc.lbd.asi.restexample.model.service.SocialRelationShipService;
import es.udc.lbd.asi.restexample.model.service.UserService;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.GameMessageDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;
import es.udc.lbd.asi.restexample.model.service.dto.RecomendacionDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SocialBlockDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SocialFriendShipDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserMessageCountDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserMessageDTO;
import es.udc.lbd.asi.restexample.web.exception.IdAndBodyNotMatchingOnUpdateException;
import es.udc.lbd.asi.restexample.web.exception.InstanceNotFoundExceptionHIB;
import es.udc.lbd.asi.restexample.web.exception.RequestBodyNotValidException;



@RestController
@RequestMapping("/api/social")
public class SocialResource {

    @Autowired
    private SocialRelationShipService socialService;

  
    @GetMapping("/{loginFrom}/{loginTo}")
    public SocialFriendShipDTO findARelation(@PathVariable String loginFrom, @PathVariable String loginTo) {
    	return socialService.findAllUserFromUser(loginFrom, loginTo);
    }
    
    @GetMapping("follow/{login}/{type}")
    public List<SocialFriendShipDTO> findRelations(@PathVariable String login,@PathVariable Boolean type) {
    	return socialService.findAllUser(login,type);
    }
    
    @PostMapping("/friendShip")
    public SocialFriendShipDTO save(@RequestBody @Valid SocialFriendShipDTO socialFriend){
        return socialService.save(socialFriend);
    }
    @PostMapping("/block")
    public SocialBlockDTO save(@RequestBody @Valid SocialBlockDTO socialFriend){
        return socialService.save(socialFriend);
    }
    
    @PutMapping("/{loginFrom}/{loginTo}/{notification}")
    public SocialFriendShipDTO update(@PathVariable String loginFrom, @PathVariable String loginTo,@PathVariable Boolean notification){
           
        return socialService.update(loginFrom,loginTo,notification);
    }
    
    @DeleteMapping("/friendShip/{loginFrom}/{loginTo}/{typeRelation}")
    public void deleteRelationShip(@PathVariable String loginFrom,@PathVariable String loginTo,@PathVariable Boolean typeRelation)  {
    	socialService.delete(loginFrom,loginTo,typeRelation);
    }
    
   

    
    
    
}
