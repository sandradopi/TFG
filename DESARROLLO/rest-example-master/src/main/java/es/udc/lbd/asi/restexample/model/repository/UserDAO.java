package es.udc.lbd.asi.restexample.model.repository;

import java.util.Collection;
import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;


public interface UserDAO {
    public User_ findByLogin(String Login) ;
    public NormalUser findByLoginNormal(String Login) ; //usado
    public List <User_> findAll();
    public List <Game> findAllGamesCreated(String login);
    public void save(User_ user);//usado
    public User_ findById(Long idUser) ;
    public NormalUser findByIdNormal(Long idUser) ;
	public List<NormalUser> findAllNoAdmin();
	public User_ findByEmail(String email);
	public void deleteById(Long idUser) ;
	public List<Game> findAllGamesNext(String login);
	public List<User_> findAllNoAdminU();
	public List<Game> findByCreatorResultado(String login);
	public List<Game> findRecomendadosPlayers(String login);
	public List<Game> findAllGamesPlayed(String login);
	public List<Game> findAllGamesPlayedValoration(String login);
	public List<String> findComentarios(String login);


	
   
}
