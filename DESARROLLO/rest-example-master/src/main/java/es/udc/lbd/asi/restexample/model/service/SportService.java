package es.udc.lbd.asi.restexample.model.service;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import es.udc.lbd.asi.restexample.config.Properties;
import es.udc.lbd.asi.restexample.model.domain.Location;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.domain.Team;
import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.exception.SportExistsException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.repository.GameDAO;
import es.udc.lbd.asi.restexample.model.repository.LocationDAO;
import es.udc.lbd.asi.restexample.model.repository.SportDAO;
import es.udc.lbd.asi.restexample.model.repository.TeamDAO;
import es.udc.lbd.asi.restexample.model.repository.UserDAO;
import es.udc.lbd.asi.restexample.model.service.dto.LocationDTO;
import es.udc.lbd.asi.restexample.model.service.dto.SportDTO;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SportService implements SportServiceInterface{

  @Autowired
  private SportDAO sportDAO;
  @Autowired
  private GameDAO gameDAO;
  @Autowired
  private LocationDAO locationDAO;
  @Autowired
  private TeamDAO teamDAO;
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private Properties properties;  
  private Path location;



  @PostConstruct
  public void initSportService() {
      this.location = Paths.get(properties.getResourcePathSport());
      try {
          Files.createDirectories(this.location);
      } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
  }
  

@Override
public List<SportDTO> findAll() {
	return sportDAO.findAll().stream().map(sport -> new SportDTO(sport)).collect(Collectors.toList());
	    
}

@Override
public SportDTO findById(Long idSport) {
	return new SportDTO(sportDAO.findById(idSport));
}

@PreAuthorize("hasAuthority('ADMIN')")
@Transactional(readOnly = false)
@Override
public SportDTO save(SportDTO sport) throws SportExistsException, RequiredFieldsException {
	
	 if(sport.getType() == null){ //Nombre vacio
	  	  throw new RequiredFieldsException("El nombre es un campo requerido");
	   }
	 
	if (sportDAO.findByType(sport.getType()) != null) {
         throw new SportExistsException("El deporte " + "'"+ sport.getType() + "'"+ " ya está en su BD");
    }
	Set<Location> l = new HashSet<Location>();
		
	Sport bdSport = new Sport(sport.getType(),null);
	for(LocationDTO a: sport.getLocations()){
		Location location= locationDAO.findById(a.getIdLocation());
		l.add(location);
    }
	
	bdSport.setLocations(l);
	bdSport.setComponenteEntrada(sport.getComponenteEntrada());
	bdSport.setComponenteVisualizacion(sport.getComponenteVisualizacion());
	sportDAO.save(bdSport);
    return new SportDTO(bdSport);
}

@PreAuthorize("hasAuthority('ADMIN')")
@Transactional(readOnly = false)
@Override
public SportDTO update(SportDTO sport) throws SportExistsException, RequiredFieldsException{
    Sport bdSport = sportDAO.findById(sport.getIdSport());
    
    if(sport.getType() == null){ //Nombre vacio
  	  throw new RequiredFieldsException("El nombre es un campo requerido");
   }
   
    if (sportDAO.findByType(sport.getType()) != null && !(bdSport.getType().equals(sport.getType()))) {
        throw new SportExistsException("El deporte " + "'"+ sport.getType() + "'"+ " ya está en su BD");
        
   }
    
    bdSport.setType(sport.getType());
    bdSport.setComponenteVisualizacion(sport.getComponenteVisualizacion());
    bdSport.setComponenteEntrada(sport.getComponenteEntrada());
   
    bdSport.getLocations().clear();
    sport.getLocations().forEach(loc -> {
        bdSport.getLocations().add(locationDAO.findById(loc.getIdLocation()));
    });
    
    sportDAO.save(bdSport);
    return new SportDTO(bdSport);
    }

@PreAuthorize("hasAuthority('ADMIN')")
@Transactional(readOnly = false)
@Override
public void deleteById(Long idSport) throws SportDeleteException {
	Boolean bol=false;
	Sport bdSport = sportDAO.findById(idSport);
	if (gameDAO.findBySport(bdSport)!=0){
		throw new SportDeleteException("Este deporte tiene asociados actualmente partidos");
	}

		List<Team> team= teamDAO.findBySport(bdSport);
		if (team.size()>0){
			List<NormalUser> user= userDAO.findAllNoAdmin();
			for(NormalUser a:user){
				Set<Team> fav = a.getFavoritos();
				Set<Team> jug = a.getJuego();
				for(Team b:fav){
					if(b.getSport()==bdSport){
						fav.remove(b);
					}
					
				}
				for(Team c:jug){
					if(c.getSport()==bdSport){
						jug.remove(c);
					}
					
				}
			}
			
			for(Team a:team){
				teamDAO.deleteById(a.getIdTeam());
				
			}
		}
		
		List<Location> loc= sportDAO.findLocationsOfSport(idSport);
		
		if (loc.size()==0){
			sportDAO.deleteById(idSport);
		}
		for(Location a:loc){
			
			Long count= locationDAO.countSportsOfaLocation(a.getIdLocation());
			
			if (count==1 && bol==false){
				sportDAO.deleteById(idSport);
				
				
				 
				locationDAO.deleteById(a.getIdLocation());
				bol= true;
			}else{
				if(bol==false && count!=1){
					sportDAO.deleteById(idSport);
					bol= true;
				}else if (bol==true){
					
				}
			}
		}
		
	
	
	
	
}
public void store(MultipartFile file) throws Exception {
    String filename = StringUtils.cleanPath(file.getOriginalFilename()); //Return the original filename in the client's filesystem.

    try {
        if (file.isEmpty()) {
            throw new Exception("Error al almacenar la foto " + filename);
        }
        if (filename.contains("..")) {
            throw new Exception(
                    "Cannot store file with relative path outside current directory "
                            + filename);
        }
        try (InputStream inputStream = file.getInputStream()) { //This method returns he input stream connected to the normal output of the subprocess.
            Files.copy(inputStream, this.location.resolve(filename),
                StandardCopyOption.REPLACE_EXISTING);
        }
    }
    catch (Exception e) {
        throw new Exception("Failed to store file " + filename, e);
    }
}


public Resource getImageAsResource(String fileName) throws Exception {
    try {
        Path file =location.resolve(fileName);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        }
        else {
            throw new Exception(
                    "Could not read file: " + fileName);

        }
    }
    catch (MalformedURLException e) {
        throw new Exception("Could not read file: " + fileName, e);
    }
}   

  
	
				
	     
   

}
