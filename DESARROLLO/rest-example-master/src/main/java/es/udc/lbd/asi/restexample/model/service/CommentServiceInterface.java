package es.udc.lbd.asi.restexample.model.service;


import java.time.LocalDateTime;
import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.service.dto.GameMessageDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserMessageDTO;



public interface CommentServiceInterface {
    public UserMessageDTO saveComment(UserMessageDTO user);
    public GameMessageDTO saveComment(GameMessageDTO game);
   

  


}
