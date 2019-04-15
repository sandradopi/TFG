package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.Player;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.Team;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;
import es.udc.lbd.asi.restexample.repository.util.GenericDAOHibernate;

@Repository
public class PlayerDAOHibernate extends GenericDAOHibernate implements PlayerDAO {

	@Override
	public void save(Player player) {
		getSession().saveOrUpdate(player);
		
	}

	@Override
	public List<Player> findAll() {
		return getSession().createQuery("from Player p order by p.idPlayer").list();
	}

	@Override
	public Player findById(Long idPlayer) {
		 return (Player) getSession().createQuery("from Player p where p.idPlayer = :idPlayer").setParameter("idPlayer", idPlayer).uniqueResult();
	}

	@Override
	public void deleteById(Long idPlayer) {
		 getSession().delete(findById(idPlayer));
		
	}

	@Override
	public List<Player> findAllByGame(Long idGame) {
		return getSession().createQuery("select p from Player p inner join p.game g where g.idGame =:idGame").setParameter("idGame", idGame).list();
	}

	@Override
	public Long findAllByGameCount(Long idGame) {
		return (Long) getSession().createQuery("select count(*) from Player p inner join p.game g where g.idGame =:idGame").setParameter("idGame", idGame).uniqueResult();
	}

	@Override
	public Player findPlayerGame(String login, Long idGame) {
		 return (Player) getSession().createQuery("select p from Player p inner join p.game g inner join p.player u where g.idGame = :idGame AND u.login= :login").setParameter("login", login).setParameter("idGame", idGame).uniqueResult();
	}

	

	
	

}
