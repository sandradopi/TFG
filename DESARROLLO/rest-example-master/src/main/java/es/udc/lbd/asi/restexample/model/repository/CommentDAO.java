package es.udc.lbd.asi.restexample.model.repository;

import java.util.Collection;
import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Comment;
import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.GameMessage;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;


public interface CommentDAO {
   
    public void save(Comment game);//usado
    public List<Comment> findAllByGame(Long idGame);
    

	
   
}
