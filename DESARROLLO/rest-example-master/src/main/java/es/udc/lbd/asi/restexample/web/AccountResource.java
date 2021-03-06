package es.udc.lbd.asi.restexample.web;

 import java.text.ParseException;

import javax.validation.Valid;

 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.HttpHeaders;
 import org.springframework.security.authentication.AuthenticationManager;
 import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
 import org.springframework.security.core.Authentication;
 import org.springframework.security.core.AuthenticationException;
 import org.springframework.security.core.context.SecurityContextHolder;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;

import es.udc.lbd.asi.restexample.model.exception.EmailIncorrect;
import es.udc.lbd.asi.restexample.model.exception.PasswordTooShort;
import es.udc.lbd.asi.restexample.model.exception.RequiredFieldsException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
 import es.udc.lbd.asi.restexample.model.service.UserService;
import es.udc.lbd.asi.restexample.model.service.dto.AdminUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.LoginDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserDTO;
import es.udc.lbd.asi.restexample.security.JWTConfigurer;
 import es.udc.lbd.asi.restexample.security.JWTToken;
 import es.udc.lbd.asi.restexample.security.TokenProvider;
 import es.udc.lbd.asi.restexample.web.exception.CredentialsAreNotValidException;

 /**
  * Este controlador va por separado que el UserResource porque se encarga de
  * tareas relacionadas con la autenticación, registro, etc.
  * 
  * También permite a cada usuario logueado en la aplicación obtener información
  * de su cuenta
  */
 @RestController
 @RequestMapping("/api")
 public class AccountResource {
     private final Logger logger = LoggerFactory.getLogger(AccountResource.class);

     @Autowired
     private TokenProvider tokenProvider;

     @Autowired
     private AuthenticationManager authenticationManager;

     @Autowired
     private UserService userService;

     @PostMapping("/authenticate")
     public JWTToken authenticate(@RequestBody LoginDTO loginDTO) throws CredentialsAreNotValidException  {

         UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                 loginDTO.getLogin(), loginDTO.getPassword());
         
        System.out.println( loginDTO.getLogin() + loginDTO.getPassword());
         try {
             Authentication authentication = authenticationManager.authenticate(authenticationToken);
             SecurityContextHolder.getContext().setAuthentication(authentication);
             String jwt = tokenProvider.createToken(authentication);
             HttpHeaders httpHeaders = new HttpHeaders();
             httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
             return new JWTToken(jwt);
         } catch (AuthenticationException e) {
             logger.warn(e.getMessage(), e);
             throw new CredentialsAreNotValidException(e.getMessage());
         }
     }

     @GetMapping("/account")
     public AdminUserDTO getAccount() {
         return userService.getCurrentUserWithAuthority();
     }

     @PostMapping("/register")
     public void registerAccount(@RequestBody NormalUserDTO account) throws UserLoginEmailExistsException, ParseException, RequiredFieldsException, PasswordTooShort, EmailIncorrect {

    		userService.registerUser(account.getLogin(),account.getEmail(), account.getPassword(), account.getName(),account.getSurname1(),account.getSurname2(),account.getCity(),account.getBirthday(),account.getRutaImagen());}
  
     
 }