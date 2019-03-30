package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.Team;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.repository.util.GenericDAOHibernate;

@Repository
public class GameDAOHibernate extends GenericDAOHibernate implements GameDAO {

	@Override
	public void save(Game game) {
		getSession().saveOrUpdate(game);
		
	}

	@Override
	public List<Game> findAll() {
		return getSession().createQuery(" from Game g where ((g.date > current_date) OR (g.date = current_date AND g.timeStart >= current_time))").list();
	}

	@Override
	public Game findById(Long idGame) {
		 return (Game) getSession().createQuery("from Game g where g.idGame = :idGame").setParameter("idGame", idGame).uniqueResult();
	}
	
	@Override
	public Long findBySport(Sport sport) {
		 return  (Long) getSession().createQuery("select count(*)from Game g where g.sport = :sport").setParameter("sport", sport).uniqueResult();
	}
	@Override
	public Long findByLocation(Location location) {
		 return  (Long) getSession().createQuery("select count(*)from Game g where g.location = :location").setParameter("location", location).uniqueResult();
	}

	@Override
	public void deleteById(Long idGame) {
		 getSession().delete(findById(idGame));
		
	}

	@Override
	public Long findByCreator(Long idUser) {
		 return  (Long) getSession().createQuery("select count(*)from Game g inner join g.creator c where c.idUser = :idUser").setParameter("idUser", idUser).uniqueResult();
	}

	@Override
	public List<Game> findAllLocation(Long idLocation) {
		return getSession().createQuery("select g from Game g inner join g.location l where (l.idLocation= :idLocation) AND ((g.date > current_date) OR (g.date = current_date AND g.timeStart >= current_time))").setParameter("idLocation", idLocation).list();
	}

	@Override
	public List<Game> findAllFiltros(List<String> sport, String usuario, Integer sportEv, Integer userEv) {
		return getSession().createQuery("select g from Game g inner join g.creator u inner join g.sport s where (:sportEv=0 OR s.type in (:sport)) AND ( :userEv=0 OR u.login= :usuario ) AND ((g.date > current_date) OR (g.date = current_date AND g.timeStart >= current_time))").setParameterList("sport", sport).setParameter("usuario", usuario).setParameter("sportEv", sportEv).setParameter("userEv", userEv).list();
	}


	
	

}
