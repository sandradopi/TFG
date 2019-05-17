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
import es.udc.lbd.asi.restexample.model.domain.UserMessage;
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

	@Override
	public List<UserMessage> findAllUserFromUser(String UserTo,String UserFrom ) {
		return getSession().createQuery("select c from Comment c inner join c.fromUser g inner join c.toUser u where (((g.login = :UserFrom) AND (u.login= :UserTo)) OR((g.login = :UserTo) AND (u.login= :UserFrom))) AND (c.gameComment=null) order by c.date ").setParameter("UserTo", UserTo).setParameter("UserFrom", UserFrom).list();
	}
	
	@Override
	public List<UserMessage> findAllUser(String login ) {
		return getSession().createQuery("select c from Comment c inner join c.fromUser g inner join c.toUser u  where ((g.login = :login OR u.login= :login) AND c.gameComment=null)  order by c.date ").setParameter("login", login).list();
	}

	@Override
	public Long findAllToMe(String login) {
		return (Long) getSession().createQuery("select count(*) from Comment c inner join c.toUser u where u.login = :login AND c.viewed=false  ").setParameter("login", login).uniqueResult();
	}

	@Override
	public Long findAllToMe(String loginTo, String loginFrom) {
		return (Long) getSession().createQuery("select count(*) from Comment c inner join c.toUser u inner join c.fromUser g where ((u.login = :loginTo) AND  (g.login = :loginFrom) AND (c.viewed=false))  ").setParameter("loginTo", loginTo).setParameter("loginFrom", loginFrom).uniqueResult();
	}


	
	

}
