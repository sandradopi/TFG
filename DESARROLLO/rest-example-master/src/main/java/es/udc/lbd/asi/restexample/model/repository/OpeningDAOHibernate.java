package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.OpeningTime;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.Team;
import es.udc.lbd.asi.restexample.repository.util.GenericDAOHibernate;

@Repository
public class OpeningDAOHibernate extends GenericDAOHibernate implements OpenDAO {

	@Override
	public void save(OpeningTime open) {
		getSession().saveOrUpdate(open);
		
	}

	@Override
	public List<OpeningTime> findAll() {
		return getSession().createQuery("from OpeningTime t order by t.dayOfTheWeek").list();
	}

	@Override
	public OpeningTime findById(Long idOpening) {
		 return (OpeningTime) getSession().createQuery("from OpeningTime t where t.idOpening = :idOpening").setParameter("idOpening", idOpening).uniqueResult();
	}

	@Override
	public void deleteById(Long idOpening) {
		 getSession().delete(findById(idOpening));
		
	}

	
	@Override
	public List<OpeningTime> findByLocation(Long idLocation) {
		return getSession().createQuery("Select o from OpeningTime o inner join o.location l where l.idLocation = :idLocation").setParameter("idLocation", idLocation).list();
	}
	
	

}
