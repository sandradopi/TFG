package es.udc.lbd.asi.restexample.model.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import es.udc.lbd.asi.restexample.model.domain.UserNoti;
import es.udc.lbd.asi.restexample.model.exception.EmailIncorrect;
import es.udc.lbd.asi.restexample.model.exception.PasswordTooShort;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;


public interface UserServiceInterface {
    public List<NormalUserDTO> findAll();
    public NormalUserDTO findById(Long idUser);
    public void registerUser(String login,String email, String password, boolean isAdmin, Date data, UserNoti noti) throws UserLoginEmailExistsException, RequiredFieldsException, PasswordTooShort;
    public void registerUser(String login,String email, String password,UserNoti noti) throws UserLoginEmailExistsException, ParseException, RequiredFieldsException, PasswordTooShort, EmailIncorrect;
	public void update(String login, String noti);
  


}
