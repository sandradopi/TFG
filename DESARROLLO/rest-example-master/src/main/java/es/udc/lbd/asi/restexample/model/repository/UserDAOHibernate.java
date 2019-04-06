package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.repository.util.GenericDAOHibernate;

@Repository
public class UserDAOHibernate extends GenericDAOHibernate implements UserDAO {

	@Override
	public void deleteById(Long idUser) {
		 getSession().delete(findById(idUser));
		
	}
	
	@Override
	public User_ findByLogin(String login){
		return   (User_) getSession().createQuery("from User_ p where p.login = :login").setParameter("login",login).uniqueResult();
	}
	

	@Override
	public User_ findByEmail(String email){
		return   (User_) getSession().createQuery("from User_ p where p.email = :email").setParameter("email",email).uniqueResult();
	}
	
	
	@Override
	public NormalUser findByLoginNormal(String login){
		return  (NormalUser) getSession().createQuery("from User_ p where p.login = :login").setParameter("login",login).uniqueResult();
	}
	
	@Override
	public void save(User_ user) {
		getSession().saveOrUpdate(user);
	}
	
	@Override
	public List<User_> findAll() {
		return getSession().createQuery("from User_ p order by p.name").list();
	}
	
	@Override
	public List<NormalUser> findAllNoAdmin() {
		return getSession().createQuery("from User_ p where  p.authority='USER'").list();
	}
	
	@Override
	public List<User_> findAllNoAdminU() {
		return getSession().createQuery("from User_ p where  p.authority='USER'").list();
	}
	
	@Override
	public User_ findById(Long idUser){
		return (User_) getSession().createQuery("from User_ p where p.idUser = :idUser").setParameter("idUser", idUser).uniqueResult();
	}
	
	@Override
	public NormalUser findByIdNormal(Long idUser){
		return  (NormalUser) getSession().createQuery("from User_ p where p.idUser = :idUser AND p.authority='USER'").setParameter("idUser", idUser).uniqueResult();
	}

	@Override
	public List<Game> findAllGamesCreated(String login) {
		return getSession().createQuery("select g from Game g inner join g.creator c where  c.login= :login").setParameter("login", login).list();
	}

	@Override
	public List<Game> findAllGamesNext(String login) {
		return getSession().createQuery("select g from Player p inner join p.game g inner join p.player u where u.login=:login AND ((g.date > current_date) OR (g.date = current_date AND g.timeStart >= current_time))").setParameter("login", login).list();
	}

	


}
