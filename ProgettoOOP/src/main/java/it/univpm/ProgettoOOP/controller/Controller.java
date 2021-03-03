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

@RestController
public class Controller {


	@PostMapping("/Cerca")
	 public ArrayList<Evento> getEvento(@RequestBody JsonObject body) {
		 String stateCode= body.get("stato").getAsString();
		 ArrayList<Evento> eve = new ArrayList<Evento>();                              
		 Evento e = new Evento();
		 String url="https://app.ticketmaster.com/discovery/v2/events.json?stateCode="+stateCode+"&countryCode=US&apikey=02znw2Zzu1vGIRauqzXnI595CY7TlXX1&page=0&size=200";
		 String evento_stato=CercaEvento.getEvento(url);
		 System.out.println(evento_stato);
		 JsonObject Obj = (JsonObject)JsonParser.parseString(evento_stato);
		 JsonObject embedded = Obj.get("_embedded").getAsJsonObject();
		 JsonArray arrEventi = embedded.get("events").getAsJsonArray();
		 JsonObject objEvent = arrEventi.get(0).getAsJsonObject();
		 	e.setName(objEvent.get("name").getAsString());
		 JsonObject dates = objEvent.get("dates").getAsJsonObject();
		 JsonObject start = dates.get("start").getAsJsonObject();
		 	e.setDataInizio(start.get("dateTime").getAsString());
		 JsonObject classification = objEvent.get("classifications").getAsJsonObject();
		 JsonObject genere = classification.get("segment").getAsJsonObject();
		  e.setGenere(genere.get("name").getAsString());
		 JsonObject ambedded2 = objEvent.get("_embedded").getAsJsonObject();
		 JsonArray venues = ambedded2.get("venues").getAsJsonArray();
		 JsonObject stato = venues.get(0).getAsJsonObject();
		 JsonObject statecode = stato.get("state").getAsJsonObject();
		  e.setStateCode(statecode.get("name").getAsString()); 
		 JsonArray attractions = ambedded2.get("attractions").getAsJsonArray();
		 JsonObject sito = venues.get(0).getAsJsonObject();
		 JsonObject sito2 = stato.get("upcomingEvents").getAsJsonObject();
		 try {
			  if(statecode.get("ticketmaster").getAsInt()!=0) {
				  e.setSito("ticketmaster");
			  }
		} catch (Exception e2) {
			// TODO: handle exception
		}
		 try {
			 if(statecode.get("universe").getAsInt()!=0) {
				  e.setSito("universe");
			  }
		} catch (Exception e2) {
			// TODO: handle exception
		}
		 try {
			 if(statecode.get("tmr").getAsInt()!=0) {
				  e.setSito("tmr");
			  }
		} catch (Exception e2) {
			// TODO: handle exception
		}
		 try {
			 if(statecode.get("frontgate").getAsInt()!=0) {
				  e.setSito("frontgate");
			  }
		} catch (Exception e2) {
			// TODO: handle exception
		}
		 JsonObject page = Obj.get("page").getAsJsonObject();
		 	int totevents = page.get("totalElements").getAsInt();
		 System.out.println(totevents);
		 return eve;
	}
}
