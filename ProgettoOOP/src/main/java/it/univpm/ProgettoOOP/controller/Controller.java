package it.univpm.ProgettoOOP.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import it.univpm.ProgettoOOP.model.*;
import it.univpm.ProgettoOOP.services.CercaEvento;
/*
 * <b>Classe</b> controller che gestisce tutte le chiamate al server
 * @author Vecchiola Gregorio
 * @author Rongoni Alessandro
 * @version 1.0as
 */
import it.univpm.ProgettoOOP.services.JsonToClass;

@RestController
public class Controller {

	JsonToClass jtc=new JsonToClass();
	
	@PostMapping("/Cerca")
	 public ArrayList<Evento> getEvento(@RequestBody JsonObject body) {
		 String stateCode= body.get("stato").getAsString();
		 ArrayList<Evento> eve = new ArrayList<Evento>();  
		 Evento e = new Evento();
		 String urlpagine="https://app.ticketmaster.com/discovery/v2/events.json?stateCode="+stateCode+"&countryCode=US&apikey=02znw2Zzu1vGIRauqzXnI595CY7TlXX1&page=0&size=199";
		 String evento_statopagine=CercaEvento.getEvento(urlpagine);
		 JsonObject Obj = (JsonObject)JsonParser.parseString(evento_statopagine);
		 JsonObject page = Obj.get("page").getAsJsonObject();
		 
		 int x = (page.get("totalPages").getAsInt())-1;
		
		 if(x>5) x=5;
		 for(int j=0;j<=x;j++) {
		 	String url="https://app.ticketmaster.com/discovery/v2/events.json?stateCode="+stateCode+"&countryCode=US&apikey=02znw2Zzu1vGIRauqzXnI595CY7TlXX1&page="+j+"&size=199";
		 	String evento_stato=CercaEvento.getEvento(url);
		 	System.out.println(evento_stato);		
		 	for(int i=0; i<199;i++) {
			 	e=jtc.getClassFromJson(evento_stato,i);
			 	eve.add(e);
			 }
		 }
		 return eve;
	}
}
