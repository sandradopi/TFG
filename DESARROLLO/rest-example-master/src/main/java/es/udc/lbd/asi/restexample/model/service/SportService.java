package es.udc.lbd.asi.restexample.model.service;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SportService implements SportServiceInterface{

  @Autowired
    private SportDAO sportDAO;

@Override
public List<SportDTO> findAll() {
	return sportDAO.findAll().stream().map(sport -> new SportDTO(sport)).collect(Collectors.toList());
	    
}

@Override
public SportDTO findById(Long idSport) {
	return new SportDTO(sportDAO.findById(idSport));
}

@Override
public SportDTO save(SportDTO sport) {
	Sport bdSport = new Sport(sport.getType(),null,null);
	sportDAO.save(bdSport);
    return new SportDTO(bdSport);
}

@Override
public void deleteById(Long idSport) {
	Sport bdSport = sportDAO.findById(idSport);
	sportDAO.deleteById(idSport);
	//borrar las localizaciones que solo estaban en ese deporte
	
}
  
	
				
	     
   

}
