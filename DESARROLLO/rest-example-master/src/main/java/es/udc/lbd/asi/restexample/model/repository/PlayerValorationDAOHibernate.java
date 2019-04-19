package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Player;
import es.udc.lbd.asi.restexample.model.domain.PlayerValoration;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.Team;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;
import es.udc.lbd.asi.restexample.repository.util.GenericDAOHibernate;

@Repository
public class PlayerValorationDAOHibernate extends GenericDAOHibernate implements PlayerValorationDAO {

	@Override
	public void save(PlayerValoration valoration) {
		getSession().saveOrUpdate(valoration);
		
	}

	@Override
	public List<PlayerValoration> findAll() {
		return getSession().createQuery("from PlayerValoration p order by p.idPlayerValoration").list();
	}

	@Override
	public PlayerValoration findById(Long idPlayerValoration) {
		 return (PlayerValoration) getSession().createQuery("from PlayerValoration p where p.idPlayerValoration = :idPlayerValoration").setParameter("idPlayerValoration", idPlayerValoration).uniqueResult();
	}
	
	@Override
	public Long countValoration(String login) {
		 return (Long) getSession().createQuery("select count(*) from PlayerValoration p inner join p.user u where u.login = :login").setParameter("login", login).uniqueResult();
	}

	@Override
	public void deleteById(Long idPlayerValoration) {
		 getSession().delete(findById(idPlayerValoration));
		
	}

	
	

	
	

}
