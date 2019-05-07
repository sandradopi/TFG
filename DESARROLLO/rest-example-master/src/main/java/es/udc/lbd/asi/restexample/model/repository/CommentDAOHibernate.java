package es.udc.lbd.asi.restexample.model.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.lbd.asi.restexample.model.domain.Comment;
import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.GameMessage;
import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.Team;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.repository.util.GenericDAOHibernate;

@Repository
public class CommentDAOHibernate extends GenericDAOHibernate implements CommentDAO {

	@Override
	public void save(Comment comment) {
		getSession().saveOrUpdate(comment);
		
	}

	@Override
	public List<Comment> findAllByGame(Long idGame) {
		return getSession().createQuery("select c from Comment c inner join c.gameComment g where g.idGame = :idGame order by c.date ").setParameter("idGame", idGame).list();
	}

	


	
	

}
