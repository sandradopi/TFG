package es.udc.lbd.asi.restexample.model.repository;

import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.User_;


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
   
}
