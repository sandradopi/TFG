package es.udc.lbd.asi.restexample.model.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.lbd.asi.restexample.model.domain.Comment;
import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.SocialBlock;
import es.udc.lbd.asi.restexample.model.domain.SocialFriendShip;
import es.udc.lbd.asi.restexample.model.domain.SocialRelationShip;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.Team;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.repository.util.GenericDAOHibernate;

@Repository
public class SocialRelationShipDAOHibernate extends GenericDAOHibernate implements SocialRelationShipDAO {

	@Override
	public void save(SocialRelationShip socialRelationShip) {
		getSession().saveOrUpdate(socialRelationShip);
		
	}

	@Override
	public SocialFriendShip findByLoginsFriend(String loginFrom,String loginTo) {
		 return (SocialFriendShip) getSession().createQuery("select g from SocialFriendShip g inner join g.userFrom u inner join g.userTo t where u.login = :loginFrom AND t.login = :loginTo ").setParameter("loginFrom", loginFrom).setParameter("loginTo", loginTo).uniqueResult();
	}
	@Override
	public SocialBlock findByLoginsBlock(String loginFrom,String loginTo) {
		 return (SocialBlock) getSession().createQuery("select g from SocialBlock g inner join g.userFrom u inner join g.userTo t where u.login = :loginFrom AND t.login = :loginTo ").setParameter("loginFrom", loginFrom).setParameter("loginTo", loginTo).uniqueResult();
	}


	@Override
	public void deleteById(String loginFrom, String loginTo, Boolean type) {
		if(type==true){
			
			System.out.print(findByLoginsFriend(loginFrom,loginTo).getIdSocial());
			 getSession().delete(findByLoginsFriend(loginFrom,loginTo));
			
		}else{
		 getSession().delete(findByLoginsBlock(loginFrom,loginTo));
		 }
		
	}

	@Override//La gente que sigo yo, en userto están mis seguidos
	public List<SocialFriendShip> findByLoginFollowers(String login) {
		 return getSession().createQuery("select g from SocialFriendShip g inner join g.userFrom u  where u.login = :login ").setParameter("login", login).list();
	}
	@Override//La gente que me sigue a mi, en userFrom están mis seguidores
	public List<SocialFriendShip> findByLoginFollowed(String login) {
		 return getSession().createQuery("select g from SocialFriendShip g inner join g.userTo u  where u.login = :login ").setParameter("login", login).list();
	}
	
}
