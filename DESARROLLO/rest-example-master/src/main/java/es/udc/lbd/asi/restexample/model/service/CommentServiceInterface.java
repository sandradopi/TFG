package es.udc.lbd.asi.restexample.model.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.service.dto.GameMessageDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserMessageDTO;



public interface CommentServiceInterface {
    public UserMessageDTO saveComment(UserMessageDTO user);
    public GameMessageDTO saveComment(GameMessageDTO game);
    public List<GameMessageDTO> findAllByGame(Long idGame);
	public List<UserMessageDTO> findAllUserFromUser(Long idUserFrom, Long idUserTo);
	public List<UserMessageDTO> updateAllMessState(Long idUserFrom, Long idUserTo);
	public List<NormalUserDTO> findAllUser(String login);

   

  


}
