package es.udc.lbd.asi.restexample.repository.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
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
import es.udc.lbd.asi.restexample.model.domain.Player;
import es.udc.lbd.asi.restexample.model.repository.GameDAO;
import es.udc.lbd.asi.restexample.model.repository.PlayerDAO;
import es.udc.lbd.asi.restexample.model.service.GameService;



@Component
@EnableTransactionManagement
@Transactional
public class ScheduledTask {
	
	@Autowired
	GameService gameService;
	@Autowired
	GameDAO gameDAO;
	@Autowired
	PlayerDAO playerDAO;
	
	private List<Game> games= null;
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
	
    @Scheduled(cron = "0 27 20 * * * ")
    public void reportCurrentTime() throws AddressException, MessagingException, ParseException {
    	init();
    	LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        
        System.out.println("RESISTENCIA"+ tomorrow);
      

        games=gameDAO.findAllTomorrow(tomorrow);
        
        if(games !=null){
        for (Game game: games){
        		List<Player> players= playerDAO.findAllByGame(game.getIdGame());
        		if(players.size()< game.getMinPlayers()){
        			for(Player player :players){
		    	    	try{
			    	    		MimeMessage message = new MimeMessage(session);
			    	    		message.setFrom(new InternetAddress("marsusanez@gmail.com"));
			    				message.addRecipient(Message.RecipientType.TO, new InternetAddress(player.getPlayer().getEmail()));
			    				message.setSubject("URGENTE: Se ha cancelado tu partido!!!");
			    				message.setText("Hola Señor/Señora "+ player.getPlayer().getName() +" :"+"\n" +"Este email es para comunicarle que su partido"
			    				+" que iba a tener lugar en "+game.getLocation().getName()+ " el día " + game.getDate()+" a la hora "+game.getTimeStart()+" ha sido cancelado"
			    				+ " por que no ha llegado al mínimo de personas apuntadas" +"\n"+"\n"+ "Sentimos las molestias, Play2Gether <3");
			    				Transport t = session.getTransport("smtp");
			    				t.connect("marsusanez@gmail.com","asiasi2018");
			    				t.sendMessage(message, message.getAllRecipients());
			    				t.close();
			    		}catch (MessagingException me){
			    			return;
			    				}
		    	    	
		    	    		playerDAO.deleteById(player.getIdPlayer());
	    	    	}
	    	    	gameDAO.deleteById(game.getIdGame());
        		}
        		
        		
        	  }
        }
    }
    
}
    	

