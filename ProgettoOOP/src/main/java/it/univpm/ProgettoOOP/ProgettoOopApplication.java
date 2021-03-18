package it.univpm.ProgettoOOP;

import java.util.Timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univpm.ProgettoOOP.services.DatabaseEventi;
/**
 * 
 * @author Vecchiola Gregorio
 * @author Rongoni ALessandro
 * 
 * La seguente classe si occupa di gestire ed avviare tutti i componenti dell'applicazione 
 * e di aggiornare il database degli eventi ogni giorno
 * 
 */
@SpringBootApplication
public class ProgettoOopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgettoOopApplication.class, args);
		Timer timer = new Timer();
		DatabaseEventi db= new DatabaseEventi();
		timer.schedule(db, 0, 24*3600000);
	}
}
