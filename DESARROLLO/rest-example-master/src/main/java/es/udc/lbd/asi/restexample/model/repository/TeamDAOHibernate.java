package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.Team;
import es.udc.lbd.asi.restexample.repository.util.GenericDAOHibernate;

@Repository
public class TeamDAOHibernate extends GenericDAOHibernate implements TeamDAO {

	@Override
	public void save(Team team) {
		getSession().saveOrUpdate(team);
		
	}

	@Override
	public List<Team> findAll() {
		return getSession().createQuery("from Team t order by t.name").list();
	}

	@Override
	public Team findById(Long idTeam) {
		 return (Team) getSession().createQuery("from Team t where t.idTeam = :idTeam").setParameter("idTeam", idTeam).uniqueResult();
	}

	@Override
	public void deleteById(Long idTeam) {
		 getSession().delete(findById(idTeam));
		
	}

	@Override
	public Team findByName(String name) {
		return (Team) getSession().createQuery("from Team t where t.name = :name").setParameter("name", name).uniqueResult();
	}
	
	

}
