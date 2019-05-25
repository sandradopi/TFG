package es.udc.lbd.asi.restexample.web;


import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.udc.lbd.asi.restexample.model.domain.Sport;
import es.udc.lbd.asi.restexample.model.exception.SportDeleteException;
import es.udc.lbd.asi.restexample.model.exception.UserLoginEmailExistsException;
import es.udc.lbd.asi.restexample.model.service.UserService;
import es.udc.lbd.asi.restexample.model.service.dto.ActivitiesDTO;
import es.udc.lbd.asi.restexample.model.service.dto.GameDTO;
import es.udc.lbd.asi.restexample.model.service.dto.NormalUserDTO;
import es.udc.lbd.asi.restexample.model.service.dto.PlayerDTO;
import es.udc.lbd.asi.restexample.model.service.dto.RecomendacionDTO;
import es.udc.lbd.asi.restexample.model.service.dto.UserDTO;
import es.udc.lbd.asi.restexample.web.exception.IdAndBodyNotMatchingOnUpdateException;
import es.udc.lbd.asi.restexample.web.exception.InstanceNotFoundExceptionHIB;
import es.udc.lbd.asi.restexample.web.exception.RequestBodyNotValidException;



@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll();
    }
    
    @GetMapping("/normal")
    public List<UserDTO> findAllNormalUsers() {
        return userService.findAllNoAdmin();
    }
    
    @GetMapping("/{login}")
    public NormalUserDTO findOneUserByLogin(@PathVariable String login) throws InstanceNotFoundExceptionHIB{
        NormalUserDTO user = userService.findByLogin(login);
    	return user;
    }
    
    @GetMapping("/{login}/organizados")
    public List<GameDTO> findAllPartidosOrganizados(@PathVariable String login){
    	 List<GameDTO> games = userService.findGamesCreated(login);
    	return games;
    }
    
    @GetMapping("/{login}/recomendados")
    public List<RecomendacionDTO> findAllPartidosRecomendados(@PathVariable String login){
    	 return userService.findGamesRecomendados(login);
    	
    }
    
    @GetMapping("/{login}/proximos")
    public List<GameDTO> findAllPartidosProximos(@PathVariable String login){
    	 List<GameDTO> games = userService.findGamesNext(login);
    	return games;
    }
    
    @GetMapping("/{login}/jugados")
    public List<GameDTO> findAllPartidosJugados(@PathVariable String login){
    	 List<GameDTO> games = userService.findGamesPlayed(login);
    	return games;
    }
    
    @GetMapping("/{login}/pendingResult")
    public List<GameDTO> findAllGamePendingResult(@PathVariable String login) throws AddressException, MessagingException, ParseException{
    	 List<GameDTO> games = userService.findByCreatorResultado(login);
    	return games;
    }
    
    @GetMapping("/{login}/valoration")
    public List<GameDTO> findAllGamePendingValoration(@PathVariable String login){
    	 List<GameDTO> games = userService.findByValoration(login);
    	return games;
    }
    
    @GetMapping("/{login}/comentarios")
    public List<String> findAllComentarios(@PathVariable String login){
    	 List<String> comentarios = userService.findComentarios(login);
    	return comentarios;
    }
    @GetMapping("/activities/{login}")
    public List<ActivitiesDTO> findAllActivities(@PathVariable String login){
    	return  userService.findActivities(login);
    }
    
    
    @PutMapping("/{idUser}")
    public NormalUserDTO update(@PathVariable Long idUser, @RequestBody @Valid NormalUserDTO user, Errors errors)
            throws IdAndBodyNotMatchingOnUpdateException, RequestBodyNotValidException, UserLoginEmailExistsException {
        errorHandler(errors);
        if (idUser != user.getIdUser()) {
            throw new IdAndBodyNotMatchingOnUpdateException(Sport.class);
        }
        return userService.update(user);
    }
    @PutMapping("/{login}/{password}")
    public NormalUserDTO updatePassword(@PathVariable String login, @PathVariable String password) {
        return userService.updatePassword(login,password);
    }
    
    @PutMapping("/notifications/{login}/{idGame}/{bool}")
    public NormalUserDTO updateNotifications(@PathVariable String login,@PathVariable Long idGame, @PathVariable Boolean bool) {
        return userService.updateNotification(login,idGame, bool);
    }
    
    @GetMapping("/notifications/{login}/{idGame}")
    public Boolean getNotification(@PathVariable String login,@PathVariable Long idGame) {
        return userService.getNotification(login,idGame);
    }
    

    @DeleteMapping("/{idUser}")
    public void delete(@PathVariable Long idUser) throws InstanceNotFoundExceptionHIB, SportDeleteException{
        userService.deleteById(idUser);
    }
    
    @PostMapping(value = "/uploadFile")
    public void loadImage(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws Exception {
        modelMap.addAttribute("file", file);
        userService.store(file);
    }
    
    @GetMapping(value ="/imagenes/{login}")
    public Resource getImage(@PathVariable String login) {
        try {
			return userService.getImageAsResource(login);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return null;
    
        
    }
    
    
    private void errorHandler(Errors errors) throws RequestBodyNotValidException {
        if (errors.hasErrors()) {
            String errorMsg = errors.getFieldErrors().stream()
                    .map(fe -> String.format("%s.%s %s", fe.getObjectName(), fe.getField(), fe.getDefaultMessage()))
                    .collect(Collectors.joining("; "));
            throw new RequestBodyNotValidException(errorMsg);
        }
    }
    
}
