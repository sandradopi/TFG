package es.udc.lbd.asi.restexample.model.service;


import java.time.LocalDateTime;
import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.UserMessage;
import es.udc.lbd.asi.restexample.model.service.dto.GameMessageDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SocialBlockDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SocialFriendShipDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserMessageDTO;



public interface SocialRelationShipServiceInterface {
   
	 public SocialFriendShipDTO save(SocialFriendShipDTO user);
	 public SocialBlockDTO save(SocialBlockDTO game);
	 public void delete(String loginFrom, String loginTo, Boolean typeRelation);
	 public SocialFriendShipDTO findAllUserFromUser(String loginTo,String loginFrom );


}
