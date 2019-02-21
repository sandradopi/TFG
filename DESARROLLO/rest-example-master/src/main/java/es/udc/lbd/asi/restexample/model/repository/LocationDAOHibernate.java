package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.repository.util.GenericDAOHibernate;

@Repository
public class LocationDAOHibernate extends GenericDAOHibernate implements LocationDAO {

	@Override
	public void save(Location location) {
		getSession().saveOrUpdate(location);
		
	}

	@Override
	public List<Location> findAll() {
		return getSession().createQuery("from Location l order by l.name").list();
	}

	@Override
	public Location findById(Long idLocation) {
		 return (Location) getSession().createQuery("from Location l where l.idLocation = :idLocation").setParameter("idLocation", idLocation).uniqueResult();
	}

	@Override
	public void deleteById(Long idLocation) {
		 getSession().delete(findById(idLocation));
		
	}
	
	@Override
	public Long countSportsOfaLocation(Long idLocation) {
		 return (Long) getSession().createQuery("select count(*) from Sport as s inner join s.locations as l where l.idLocation= :idLocation").setParameter("idLocation", idLocation).uniqueResult();
	}

}
