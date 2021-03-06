package es.udc.lbd.asi.restexample.model.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.exception.EmailIncorrect;
import es.udc.lbd.asi.restexample.model.exception.PasswordTooShort;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.DeleteException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.service.dto.ActivitiesDTO;
import es.udc.lbd.asi.restexample.model.service.dto.AdminUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;
import es.udc.lbd.asi.restexample.model.service.dto.RecomendacionDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserDTO;


public interface UserServiceInterface {
    public NormalUserDTO findByLogin(String login);
    public void registerUser(String login,String email, String password, boolean isAdmin, String name, String surname1, String surname2, String city, Date birthday,String rutaImagen) throws UserLoginEmailExistsException, RequiredFieldsException, PasswordTooShort;
    public void registerUser(String login,String email, String password, String name, String surname1, String surname2, String city, Date birthday,String rutaImagen) throws UserLoginEmailExistsException, ParseException, RequiredFieldsException, PasswordTooShort, EmailIncorrect;
    public NormalUserDTO update(NormalUserDTO user) throws UserLoginEmailExistsException;
	public List<UserDTO> findAll();
	public List<GameDTO> findGamesCreated(String login);
	public List<RecomendacionDTO> findGamesRecomendados(String login);
	public void deleteById(Long idUser) throws DeleteException;
	public List<GameDTO> findGamesNext(String login);
	public List<GameDTO> findGamesPlayed(String login);
	public List<UserDTO> findAllNoAdmin();
	public NormalUserDTO updateNotification(String login, Long idGame, Boolean bool);
	public Boolean getNotification(String login, Long idGame);
	public List<GameDTO>findByCreatorResultado(String login) throws AddressException, MessagingException, ParseException;
	public List<GameDTO>findByValoration(String login);
	public List<String> findComentarios(String login);
	public List<ActivitiesDTO> findActivities(String login);
	public NormalUserDTO updatePassword(String login, String password);
	public Resource getImageAsResource(String fileName) throws Exception;
	public void store(MultipartFile file) throws Exception;
	public NormalUserDTO getCurrentUserWithoutAuthority();
	public AdminUserDTO getCurrentUserWithAuthority();
	public UserDTO getCurrentUserWithAuthorityUser();

  


}
