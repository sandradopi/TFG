package es.udc.lbd.asi.restexample.model.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import es.udc.lbd.asi.restexample.model.domain.User_;
import es.udc.lbd.asi.restexample.model.exception.EmailIncorrect;
import es.udc.lbd.asi.restexample.model.exception.PasswordTooShort;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserDTO;


public interface UserServiceInterface {
    public NormalUserDTO findByLogin(String login);
    public void registerUser(String login,String email, String password, boolean isAdmin, String name, String surname1, String surname2, String city, Date birthday) throws UserLoginEmailExistsException, RequiredFieldsException, PasswordTooShort;
    public void registerUser(String login,String email, String password, String name, String surname1, String surname2, String city, Date birthday) throws UserLoginEmailExistsException, ParseException, RequiredFieldsException, PasswordTooShort, EmailIncorrect;
    public NormalUserDTO update(NormalUserDTO user) throws UserLoginEmailExistsException;
	public List<UserDTO> findAll();
	public UserDTO changePermission(Long idUser);
	public void deleteById(Long idUser);

  


}
