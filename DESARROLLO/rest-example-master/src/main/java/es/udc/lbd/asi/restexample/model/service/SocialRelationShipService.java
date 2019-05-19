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
import es.udc.lbd.asi.restexample.model.domain.SocialRelationShip;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.UserAuthority;
import es.udc.lbd.asi.restexample.model.domain.UserMessage;
import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.exception.EmailIncorrect;
import es.udc.lbd.asi.restexample.model.exception.PasswordTooShort;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
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
import es.udc.lbd.asi.restexample.model.service.dto.SocialRelationShipDTO;
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
 	   socialship.setNotification(false);
 	   socialship.setUserFrom(userDAO.findByLoginNormal(social.getUserFrom().getLogin()));
 	   socialship.setUserTo(userDAO.findByLoginNormal(social.getUserTo().getLogin()));
 	   socialRelationShipDAO.save(socialship);
 	   return new SocialFriendShipDTO(socialship);
 	   

	  
		         
 	 
  }
  
  @Transactional(readOnly = false)
  @Override
  public SocialBlockDTO save(SocialBlockDTO social){
	  //SI LE SIGUE TIENES QUE ELIMINAR SU AMISTAD 
	  SocialFriendShip friendsShip= socialRelationShipDAO.findByLoginsFriend(social.getUserFrom().getLogin(), social.getUserTo().getLogin());
	  if(friendsShip!=null){
		  socialRelationShipDAO.deleteById(social.getUserFrom().getLogin(), social.getUserTo().getLogin(),true);
	  }
	   LocalDateTime ahora = LocalDateTime.now();
	   SocialBlock block =new SocialBlock();
	   block.setLastUpdate(ahora);
	   block.setUserFrom(userDAO.findByLoginNormal(social.getUserFrom().getLogin()));
	   block.setUserTo(userDAO.findByLoginNormal(social.getUserTo().getLogin()));
	   socialRelationShipDAO.save(block);
 	   return new SocialBlockDTO(block);
	         
	     

      
  }
  @Transactional(readOnly = false)
  @Override
  public void delete(String loginFrom, String loginTo, Boolean typeRelation) {
	  socialRelationShipDAO.deleteById(loginFrom, loginTo,typeRelation);
	
}

@Override
public SocialFriendShipDTO findAllUserFromUser(String loginFrom, String loginTo) {
	try{
		
		return new SocialFriendShipDTO(socialRelationShipDAO.findByLoginsFriend(loginFrom, loginTo));
		
	}catch(Exception e){
		return null;
	}
	
}
@Override
public List<SocialFriendShipDTO> findAllUser(String login, Boolean type) {
	if(type==true){//Seguidos
		return socialRelationShipDAO.findByLoginFollowers(login).stream().map(social -> new SocialFriendShipDTO(social)).collect(Collectors.toList());
	}else{//Seguidores
		return socialRelationShipDAO.findByLoginFollowed(login).stream().map(social -> new SocialFriendShipDTO(social)).collect(Collectors.toList());
	}
	
}
@Transactional(readOnly = false)
@Override
public SocialFriendShipDTO update(String loginFrom, String loginTo, Boolean notification) {
	SocialFriendShip social= socialRelationShipDAO.findByLoginsFriend(loginFrom, loginTo);
	
	social.setNotification(notification);
	socialRelationShipDAO.save(social);
	return new SocialFriendShipDTO(social);
}
  

	     
	    

		

}
