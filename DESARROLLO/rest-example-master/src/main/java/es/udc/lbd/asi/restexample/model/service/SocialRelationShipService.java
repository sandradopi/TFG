package es.udc.lbd.asi.restexample.model.service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.udc.lbd.asi.restexample.model.domain.AdminUser;
import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.GameMessage;
import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Player;
import es.udc.lbd.asi.restexample.model.domain.PlayerValoration;
import es.udc.lbd.asi.restexample.model.domain.SocialBlock;
import es.udc.lbd.asi.restexample.model.domain.SocialFriendShip;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.UserAuthority;
import es.udc.lbd.asi.restexample.model.domain.UserMessage;
import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.exception.EmailIncorrect;
import es.udc.lbd.asi.restexample.model.exception.PasswordTooShort;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.repository.CommentDAO;
import es.udc.lbd.asi.restexample.model.repository.GameDAO;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.PlayerDAO;
import es.udc.lbd.asi.restexample.model.repository.PlayerValorationDAO;
import es.udc.lbd.asi.restexample.model.repository.SocialRelationShipDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;
import es.udc.lbd.asi.restexample.model.repository.TeamDAO;
import es.udc.lbd.asi.restexample.model.repository.UserDAO;
import es.udc.lbd.asi.restexample.model.service.dto.AdminUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.GameMessageDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;
import es.udc.lbd.asi.restexample.model.service.dto.RecomendacionDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SocialBlockDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SocialFriendShipDTO;
import es.udc.lbd.asi.restexample.model.service.dto.TeamDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserMessageDTO;
import es.udc.lbd.asi.restexample.security.SecurityUtils;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SocialRelationShipService implements SocialRelationShipServiceInterface{

  @Autowired
  private SocialRelationShipDAO socialRelationShipDAO;
  @Autowired
  private UserDAO userDAO;
 
  @Transactional(readOnly = false)
  @Override
	 public SocialFriendShipDTO save(SocialFriendShipDTO social){
 	   LocalDateTime ahora = LocalDateTime.now();
 	   SocialFriendShip socialship =new SocialFriendShip();
 	   socialship.setLastUpdate(ahora);
 	   socialship.setNotification(social.getNotification());
 	   socialship.setUserFrom(userDAO.findByLoginNormal(social.getUserFrom().getLogin()));
 	   socialship.setUserTo(userDAO.findByLoginNormal(social.getUserTo().getLogin()));
 	   

	   return new SocialFriendShipDTO(socialship);
		         
 	 
  }
  
  @Transactional(readOnly = false)
  @Override
  public SocialBlockDTO save(SocialBlockDTO social){
	 LocalDateTime ahora = LocalDateTime.now();
	   SocialBlock block =new SocialBlock();
	   block.setLastUpdate(ahora);
	   block.setUserFrom(userDAO.findByLoginNormal(social.getUserFrom().getLogin()));
	   block.setUserTo(userDAO.findByLoginNormal(social.getUserTo().getLogin()));
	  
    return new SocialBlockDTO(block);
	         
	     

      
  }
  

	     
	    

		

}
