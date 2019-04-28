package es.udc.lbd.asi.restexample.model.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.Team;
import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;
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

	@Override
	public List<Location> findAllSport(Long idSport) {
		return getSession().createQuery("select l from Location l inner join l.sports s where s.idSport= :idSport ").setParameter("idSport", idSport).list();
	}
	
	@Override
	public Location findByName(String name) {
		return (Location) getSession().createQuery("from Location t where t.name = :name").setParameter("name", name).uniqueResult();
	}

	@Override
	public Location findByUbicacion(Double latitud, Double longitud) {
		return (Location) getSession().createQuery("from Location t where t.latitud = :latitud AND t.longitud = :longitud").setParameter("latitud", latitud).setParameter("longitud", longitud).uniqueResult();
	}

	@Override
	public Long countLocations() {
		return (Long) getSession().createQuery("select count(*) from Location").uniqueResult();
		
	}

}
