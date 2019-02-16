package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.repository.util.GenericDAOHibernate;

@Repository
public class SportDAOHibernate extends GenericDAOHibernate implements SportDAO {

	@Override
	public void save(Sport sport) {
		getSession().saveOrUpdate(sport);
		
	}

	@Override
	public List<Sport> findAll() {
		return getSession().createQuery("from Sport s order by s.type").list();
	}

	@Override
	public Sport findById(Long idSport) {
		 return (Sport) getSession().createQuery("from Sport s where s.idSport = :idSport").setParameter("idSport", idSport).uniqueResult();
	}

	@Override
	public void deleteById(Long idSport) {
		 getSession().delete(findById(idSport));
		
	}
	@Override
	public Long countLocations(Long idSport) {
		 return (Long) getSession().createQuery("select count(*) from Sport as s inner join s.location as l where s.idSport= :idSport").setParameter("idSport", idSport).uniqueResult();
	}

	@Override
	public Sport findByType(String type) {
		 return (Sport) getSession().createQuery("from Sport s where s.type = :type").setParameter("type", type).uniqueResult();
	}
	

}
