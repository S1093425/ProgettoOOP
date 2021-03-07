package it.univpm.ProgettoOOP.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import it.univpm.ProgettoOOP.log.Log;
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
		 int y= (page.get("totalElements").getAsInt())-1;
		 if(x>5) x=5;
		 for(int j=0;j<=x;j++) {
		 	String url="https://app.ticketmaster.com/discovery/v2/events.json?stateCode="+stateCode+"&countryCode=US&apikey=02znw2Zzu1vGIRauqzXnI595CY7TlXX1&page="+j+"&size=199";
		 	String evento_stato=CercaEvento.getEvento(url);
		 	System.out.println(evento_stato);
		 	if(j==x){
		 		y-= j*199;
		 		for(int i=0; i<y;i++) {
				 	e=jtc.getClassFromJson(evento_stato,i);
				 	eve.add(e);
				 }
		 	}else {
		 		for(int i=0; i<199;i++) {
		 			e=jtc.getClassFromJson(evento_stato,i);
		 			eve.add(e);
		 		}
		 	}
		 	
		 }
		 return eve;
	}
	
	@PostMapping("/Stats")
	public JsonObject getStats(){
		ArrayList<Stato> stati = new ArrayList<Stato>();
		JsonObject JsonFinale= new JsonObject();
		JsonArray JsonStatistiche= new JsonArray();
		JsonFinale.add("Statistiche", JsonStatistiche );
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(new File("sigle.json")));
			JsonObject jo = JsonParser.parseReader(buffer).getAsJsonObject(); 
			JsonArray array_sigle = jo.get("sigle").getAsJsonArray();
			JsonArray array_source = jo.get("source").getAsJsonArray();
			JsonArray array_generi = jo.get("generi").getAsJsonArray();
			for (int i = 0; i < array_sigle.size(); i++) {
				Stato s= new Stato();
				String ursigle="https://app.ticketmaster.com/discovery/v2/events.json?stateCode="+array_sigle.get(i).getAsString()+"&countryCode=US&apikey=02znw2Zzu1vGIRauqzXnI595CY7TlXX1&page=0&size=199";
				 String statsigle=CercaEvento.getEvento(ursigle);
				 JsonObject Objsigle = (JsonObject)JsonParser.parseString(statsigle);
				 JsonObject embedded = Objsigle.get("_embedded").getAsJsonObject();
				 JsonArray arrEventi = embedded.get("events").getAsJsonArray();
				 JsonObject objEvent = arrEventi.get(0).getAsJsonObject();
				 JsonObject ambedded2 = objEvent.get("_embedded").getAsJsonObject();
				 JsonArray venues = ambedded2.get("venues").getAsJsonArray();
				 JsonObject stato = venues.get(0).getAsJsonObject();
				 JsonObject statecode = stato.get("state").getAsJsonObject();
				 s.setStateCode(statecode.get("name").getAsString()); 
				 JsonObject pagesigle = Objsigle.get("page").getAsJsonObject();
				 s.setEventi_Totali(pagesigle.get("totalElements").getAsInt());
				 int[] array = new int[5];
				for(int j=0;j<array_source.size();j++) {
					String urlsource="https://app.ticketmaster.com/discovery/v2/events.json?stateCode="+array_sigle.get(i).getAsString()+"&countryCode=US&source="+array_source.get(j).getAsString()+"&apikey=02znw2Zzu1vGIRauqzXnI595CY7TlXX1&size=199";
					 String statsource=CercaEvento.getEvento(urlsource);
					 JsonObject Objsource = (JsonObject)JsonParser.parseString(statsource);
					 JsonObject pagesource = Objsource.get("page").getAsJsonObject();
					 array[j]= pagesource.get("totalElements").getAsInt();
				}
				s.setSource(array);
				int[] array1 = new int[4];
				for(int j=0;j<array_generi.size();j++) {
					String urlgenere="https://app.ticketmaster.com/discovery/v2/events.json?stateCode="+array_sigle.get(i).getAsString()+"&countryCode=US&apikey=02znw2Zzu1vGIRauqzXnI595CY7TlXX1&size=199&classificationName="+array_generi.get(j).getAsString();
					 String statgenere=CercaEvento.getEvento(urlgenere);
					 JsonObject Objgenere = (JsonObject)JsonParser.parseString(statgenere);
					 JsonObject pagegenere = Objgenere.get("page").getAsJsonObject();
					 array1[j]= pagegenere.get("totalElements").getAsInt();
				}
				s.setGeneri(array1);
				stati.add(s);
				JsonObject JsonStato = new JsonObject();
				JsonStatistiche.add(JsonStato);
				JsonStato.addProperty("State Code", s.getStateCode());
				JsonArray JsonAGeneri= new JsonArray();
				JsonStato.add("Generi", JsonAGeneri);
				JsonObject JsonGeneri = new JsonObject();
				JsonAGeneri.add(JsonGeneri);
				JsonGeneri.addProperty("Musica", s.getGeneri()[0]);
				JsonGeneri.addProperty("Sport", s.getGeneri()[1]);
				JsonGeneri.addProperty("Arte e Teatro", s.getGeneri()[2]);
				JsonGeneri.addProperty("Misto", s.getGeneri()[3]);
				JsonArray JsonASource= new JsonArray();
				JsonStato.add("Source", JsonASource);
				JsonObject JsonSource = new JsonObject();
				JsonASource.add(JsonSource);
				JsonSource.addProperty("Ticketmaster", s.getSource()[0]);
				JsonSource.addProperty("Universe", s.getSource()[1]);
				JsonSource.addProperty("Ticketmaster Resale", s.getSource()[2]);
				JsonSource.addProperty("FrontGate Tickets", s.getSource()[3]);
				JsonSource.addProperty("Ticketweb", s.getSource()[4]);
			}
			buffer.close();

		
		}catch (JsonSyntaxException e) {
			Log.report("FILE sigle.json NON TROVATO", e.getMessage());
		} catch (IOException e) {
			Log.report("ERRORE LETTURA FILE", e.getMessage());
		}
		return JsonFinale;
	}
	
}