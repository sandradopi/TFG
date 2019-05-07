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
import es.udc.lbd.asi.restexample.model.repository.SportDAO;
import es.udc.lbd.asi.restexample.model.repository.TeamDAO;
import es.udc.lbd.asi.restexample.model.repository.UserDAO;
import es.udc.lbd.asi.restexample.model.service.dto.AdminUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.GameMessageDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;
import es.udc.lbd.asi.restexample.model.service.dto.RecomendacionDTO;
import es.udc.lbd.asi.restexample.model.service.dto.TeamDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserMessageDTO;
import es.udc.lbd.asi.restexample.security.SecurityUtils;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CommentService implements CommentServiceInterface{

  @Autowired
  private CommentDAO commentDAO;
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private GameDAO gameDAO;


	     @Transactional(readOnly = false)
	     @Override
		 public UserMessageDTO saveComment(UserMessageDTO user){
	    	  return null;
			         
	    	 
	     }
	     
	     @Transactional(readOnly = false)
	     @Override
	     public GameMessageDTO saveComment(GameMessageDTO game){
	       LocalDateTime ahora = LocalDateTime.now();
	       GameMessage gameMen =new GameMessage();
	       gameMen.setContentComment(game.getContentComment());
	       gameMen.setDate(ahora);
	       gameMen.setFromUser(userDAO.findByLoginNormal(game.getFromUser().getLogin()));
	       gameMen.setGameComment(gameDAO.findById(game.getGameComment().getIdGame()));
	       commentDAO.save(gameMen);
	       return new GameMessageDTO(gameMen);
		         
		     

	         
	     }

		public List<GameMessageDTO> findAllByGame(Long idGame) {
			return commentDAO.findAllByGame(idGame).stream().map(game -> new GameMessageDTO(game)).collect(Collectors.toList());
		}
	     
	    

		

}
