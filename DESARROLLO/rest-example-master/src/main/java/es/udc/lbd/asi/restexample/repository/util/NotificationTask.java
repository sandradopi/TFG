package es.udc.lbd.asi.restexample.repository.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import es.udc.lbd.asi.restexample.model.domain.Game;
import es.udc.lbd.asi.restexample.model.domain.NormalUser;
import es.udc.lbd.asi.restexample.model.domain.Player;
import es.udc.lbd.asi.restexample.model.repository.GameDAO;
import es.udc.lbd.asi.restexample.model.repository.PlayerDAO;
import es.udc.lbd.asi.restexample.model.repository.UserDAO;
import es.udc.lbd.asi.restexample.model.service.GameService;



@Component
@EnableTransactionManagement
@Transactional
public class NotificationTask {
	@Autowired
	UserDAO userDAO;
	@Autowired
	GameDAO gameDAO;
	
	private Properties properties = new Properties();
	private Session session;
	private final static Logger log = Logger.getLogger("SMS");

	private void init() {
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.port","587");
		properties.setProperty("mail.smtp.user", "marsusanez@gmail.com");
		properties.setProperty("mail.smtp.auth", "true");
		session = Session.getDefaultInstance(properties);
	}
	

    public void reportCurrentTime(Long idGame, String mensaje) throws AddressException, MessagingException, ParseException {
    	init();
    	Game partido=gameDAO.findById(idGame);
    	List<NormalUser> usuarios =userDAO.findAllNoAdmin();
    	List<NormalUser> notificados= new ArrayList();
    	for(NormalUser user: usuarios){
    		Set<Game> games= user.getNotifications();
    		for (Game game:games){
    			if(game.getIdGame()==idGame){
    				notificados.add(user);
    			}
    		}
    	}
    	
    	for(NormalUser usuario : notificados){
		    	    	try{
			    	    		MimeMessage message = new MimeMessage(session);
			    	    		message.setFrom(new InternetAddress("marsusanez@gmail.com"));
			    				message.addRecipient(Message.RecipientType.TO, new InternetAddress(usuario.getEmail()));
			    				message.setSubject("Se han producido cambios en su partido!");
			    				message.setText("Hola Señor/Señora "+usuario.getName()+".\n" +"Este email es para comunicarle que se han producido cambios en su partido"
			    				+" que iba a tener lugar en "+ partido.getLocation().getName() +" el día "+ partido.getDate()+ " a la hora "+ partido.getTimeStart()+ "."+"\n"+"\n"
			    				+"El cambio que se ha producido es el siguiente: "+mensaje +"\n"+"\n"+ "Espero que esta información te haya sido de utilidad, un saludo de tu app preferida: Play2Gether <3");
			    				Transport t = session.getTransport("smtp");
			    				t.connect("marsusanez@gmail.com","asiasi2018");
			    				t.sendMessage(message, message.getAllRecipients());
			    				t.close();
			    		}catch (MessagingException me){
			    			return;
			    				}
		    	    	
    	}    		
    }
    
}
    	

