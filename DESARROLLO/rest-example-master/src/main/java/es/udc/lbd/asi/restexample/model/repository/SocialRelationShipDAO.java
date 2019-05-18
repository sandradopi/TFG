package es.udc.lbd.asi.restexample.model.repository;

import java.util.Collection;
import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.Comment;
import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.SocialBlock;
import es.udc.lbd.asi.restexample.model.domain.SocialFriendShip;
import es.udc.lbd.asi.restexample.model.domain.SocialRelationShip;
import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;


public interface SocialRelationShipDAO {
   
    public void save(SocialRelationShip socialRelationShip);
	public SocialFriendShip findByLoginsFriend(String loginFrom,String loginTo) ;
	public List<SocialFriendShip> findByLoginFollowed(String login) ;
	public List<SocialFriendShip> findByLoginFollowers(String login) ;
	public SocialBlock findByLoginsBlock(String loginFrom,String loginTo) ;
	public void deleteById(String loginFrom,String loginTo, Boolean type);
    

	
   
}
