package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.Team;
import es.udc.lbd.asi.restexample.repository.util.GenericDAOHibernate;

@Repository
public class GameDAOHibernate extends GenericDAOHibernate implements GameDAO {

	@Override
	public void save(Game game) {
		getSession().saveOrUpdate(game);
		
	}

	@Override
	public List<Game> findAll() {
		return getSession().createQuery("from Game g order by g.idGame").list();
	}

	@Override
	public Game findById(Long idGame) {
		 return (Game) getSession().createQuery("from Game g where g.idGame = :idGame").setParameter("idGame", idGame).uniqueResult();
	}

	@Override
	public void deleteById(Long idGame) {
		 getSession().delete(findById(idGame));
		
	}

	
	

}
