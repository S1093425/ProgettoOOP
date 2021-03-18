package it.univpm.ProgettoOOP.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import it.univpm.ProgettoOOP.exception.IllegalRequest;
import it.univpm.ProgettoOOP.exception.StateNotFound;
import it.univpm.ProgettoOOP.filter.*;
import it.univpm.ProgettoOOP.log.Log;
import it.univpm.ProgettoOOP.model.*;
import it.univpm.ProgettoOOP.services.CercaEvento;
import it.univpm.ProgettoOOP.services.DatabaseEventi;
/*
 * <b>Classe</b> controller che gestisce tutte le chiamate al server
 * @author Vecchiola Gregorio
 * @author Rongoni Alessandro
 * @version 1.0as
 */
import it.univpm.ProgettoOOP.services.JsonToClass;
import it.univpm.ProgettoOOP.services.ListaStati;
import it.univpm.ProgettoOOP.statistiche.Statistiche;
/**
 * 
 * @author Alessandro Rongoni, Gregorio Vecchiola
 *
 */
@RestController
public class Controller {

	@PostMapping("/Cerca")
	 public static ArrayList<Evento> getEvento(@RequestBody JsonObject body)  {
		 JsonToClass jtc=new JsonToClass();
		 String stateCode= body.get("stato").getAsString();
		 ArrayList<Evento> eve = new ArrayList<Evento>();  
		 Evento e = new Evento();
		 String urlpagine="https://app.ticketmaster.com/discovery/v2/events.json?stateCode="+stateCode+"&countryCode=US&apikey=02znw2Zzu1vGIRauqzXnI595CY7TlXX1&page=0&size=199";
		 String evento_statopagine=CercaEvento.getEvento(urlpagine);
		 int x = jtc.getPage(evento_statopagine)-1;
		 int y= jtc.getTotalElements(evento_statopagine);
		 if(x>5) x=5;
		 for(int j=0;j<=x;j++) {
		 	String url="https://app.ticketmaster.com/discovery/v2/events.json?stateCode="+stateCode+"&countryCode=US&apikey=02znw2Zzu1vGIRauqzXnI595CY7TlXX1&page="+j+"&size=199";
		 	String evento_stato=CercaEvento.getEvento(url);
		 	if((j==x)&&(x!=5)){
		 		y-= (j*199);
		 		for(int i=0; i<y;i++) {
				 	e=jtc.getEventoFromJson(evento_stato,i);
				 	eve.add(e);
				 }
		 	}else {
		 		for(int i=0; i<199;i++) {
		 			e=jtc.getEventoFromJson(evento_stato,i);
		 			eve.add(e);
		 		}
		 	}	 	
		 }
		  return eve;
	}
	/**
	 * 
	 */
	@PostMapping("/Stats")
	public JsonObject getStats(@RequestBody JsonObject body){
		JsonToClass jtc=new JsonToClass();
		ListaStati ls= new ListaStati();
		DatabaseEventi db= new DatabaseEventi();
		JsonObject JsonFinale= new JsonObject();
		JsonArray JsonStatistiche= new JsonArray();
		JsonFinale.add("Statistiche", JsonStatistiche);
		ArrayList<Evento> eve = db.getEventi();
		ArrayList<String> stati = ls.getStati();
		Statistiche stats= new Statistiche();
		eve= filtraEventi(body, eve);
		ArrayList<Stato> statisticheStato = new ArrayList<Stato>();
		for(String stringa: stati) {
			Stato s = stats.getStatsStato(eve, stringa);
			JsonStatistiche.add(jtc.getJsonFromStats(s));
			statisticheStato.add(s);
		}
		JsonArray JsonStatisticheGlobali= new JsonArray();
		JsonFinale.add("Statistiche Globali", JsonStatisticheGlobali);
		Global statisticheGlobali= stats.getStatsGlobal(statisticheStato);
		JsonStatisticheGlobali.add(jtc.getJsonFromGlobalStats(statisticheGlobali));
		return JsonFinale;
	}
	
	@GetMapping("/StatiStats")
	public ArrayList<String> getStatiStats(@RequestParam(name = "State")String stato,@RequestParam(name = "Activity")String attivita) throws IllegalRequest{
		ListaStati ls=new ListaStati();
		ArrayList<String> stati= new ArrayList<String>();
		switch(attivita) {
			case "Aggiunta":
				ls.aggiungiStato(stato);
				stati=ls.getStati();
				return stati;
			case "Rimozione":
				ls.rimuoviStato(stato);
				stati=ls.getStati();
				return stati;
			case "Stampa":
				stati=ls.getStati();
				return stati;
			default:
				throw new IllegalRequest(attivita);
		}		
	}
	
	@GetMapping("/StatiCerca")
	public JsonObject getStatiCerca() {
			JsonObject jo=new JsonObject();
			try {
				BufferedReader buffer = new BufferedReader(new FileReader(new File("sigle.json")));
				jo = JsonParser.parseReader(buffer).getAsJsonObject();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return jo;
	}
	
	private ArrayList<Evento> filtraEventi(JsonObject body,ArrayList<Evento> eve ) {
		body = body.getAsJsonObject();
		JsonObject attivo =body.get("stati").getAsJsonObject();
		if(attivo.get("attivo").getAsBoolean()) {
			System.out.println("filtroattivo");
			State filtroS= new State();
			eve= filtroS.filtra(attivo.get("filtro").getAsString(),eve);
		}
		 attivo =body.get("generi").getAsJsonObject();
		if(attivo.get("attivo").getAsBoolean()) {
			System.out.println("filtroattivo");
			Genere filtroG= new Genere();
			eve= filtroG.filtra(attivo.get("filtro").getAsString(),eve);
		}
		 attivo =body.get("source").getAsJsonObject();
		if(attivo.get("attivo").getAsBoolean()) {
			System.out.println("filtroattivo");
			Source filtroSo= new Source();
			eve= filtroSo.filtra(attivo.get("filtro").getAsString(),eve);
		}
		 attivo =body.get("periodo").getAsJsonObject();
		if(attivo.get("attivo").getAsBoolean()) {
			System.out.println("filtroattivo");
			Periodo filtroP= new Periodo();						
			eve= filtroP.filtra(attivo.get("filtro").getAsString(),eve);
		}		
		return eve;
	}
}