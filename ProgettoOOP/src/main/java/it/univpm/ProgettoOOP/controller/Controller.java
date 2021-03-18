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

import it.univpm.ProgettoOOP.exception.EventException;
import it.univpm.ProgettoOOP.exception.IllegalRequest;
import it.univpm.ProgettoOOP.exception.StateNotFound;
import it.univpm.ProgettoOOP.filter.*;
import it.univpm.ProgettoOOP.log.Log;
import it.univpm.ProgettoOOP.model.*;
import it.univpm.ProgettoOOP.services.CercaEvento;


import it.univpm.ProgettoOOP.services.JsonToClass;
import it.univpm.ProgettoOOP.services.ListaStati;
import it.univpm.ProgettoOOP.statistiche.Statistiche;

import it.univpm.ProgettoOOP.services.DatabaseEventi;

/*
 * <b>Classe</b> controller che gestisce tutte le chiamate al server
 *  @author Vecchiola Gregorio
 *  @author Rongoni Alessandro
 *  @version 1.0as
 *  
 *  
 *  Classe che gestisce le chiamate al nostro Server SpringBoot
 */


@RestController
public class Controller {
	
	/**
	 * Call che restituisce la lista degli eventi dello stato cercato dall'utente, prelevando i dati 
	 * in tempo reale da Ticket Master.
	 * 
	 * @param body contenente un JsonObject con la silga dello stato cercato
	 * @return ArrayList {@link Evento} contenente gli eventi dello stato richiesto
	 * @throws StateNotFound eccezione personalizzata che viene lanciata nel caso in cui lo stato cercato non esistesse
	 * 
	 */
	@PostMapping("/Cerca")
	 public static ArrayList<Evento> getEvento(@RequestBody JsonObject body) throws StateNotFound  {
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
		 if(eve.isEmpty()) throw new StateNotFound(stateCode);
		 else return eve;
	}
	/**
	 * Call che restituisce le statistiche degli stati appartenenti al file "stati", in base a dei filtri scelti dall'utente.
	 * 
	 * @param body contenente i filtri da applicare alle statistiche
	 * @return JsonObject con tutte le statistiche degli stati
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
			if(s.getEventi_Totali()!=0) {
				JsonStatistiche.add(jtc.getJsonFromStats(s));
				statisticheStato.add(s);
			}
		}
		JsonArray JsonStatisticheGlobali= new JsonArray();
		JsonFinale.add("Statistiche Globali", JsonStatisticheGlobali);
		Global statisticheGlobali= stats.getStatsGlobal(statisticheStato);
		try {
			JsonStatisticheGlobali.add(jtc.getJsonFromGlobalStats(statisticheGlobali));
		} catch (EventException e) {
			
			e.printStackTrace();
		}
		return JsonFinale;
	}
	
	/**
	 * Call per la gestione del file di contenente gli stati preferiti. L'utente può aggiungere, rimuovere e 
	 * stampare gli stati al suo interno.
	 * 
	 * @param stato nome dello stato su cui fare l'azione scelta
	 * @param attivita per gestire la lista degli stati
	 * @return ArrayList {@link String} della lista degli stati aggiornata
	 * @throws IllegalRequest eccezione personalizzata che gestisce una chimata illegita
	 */
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
	
	/**
	 * Call che permette di visualizzare le silge di tutti gli stati dell'USA
	 * 
	 * @return JsonObject contenente tutte le sigle
	 */
	@GetMapping("/StatiCerca")
	public JsonObject getStatiCerca() {
			JsonObject jo=new JsonObject();
			try {
				BufferedReader buffer = new BufferedReader(new FileReader(new File("sigle.json")));
				jo = JsonParser.parseReader(buffer).getAsJsonObject();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		return jo;
	}
	
	/**
	 * Metodo utilizzato per il filtraggio degli eventi, tramite i filtri passati nel body
	 * 
	 * @param body filtri passati dall'utente
	 * @param eve lista degli eventi da filtrare
	 * @return lista eventi filtrati
	 */
	private ArrayList<Evento> filtraEventi(JsonObject body,ArrayList<Evento> eve ) {
		body = body.getAsJsonObject();
		JsonObject attivo =body.get("stati").getAsJsonObject();
		if(attivo.get("attivo").getAsBoolean()) {
			State filtroS= new State();
			eve= filtroS.filtra(attivo.get("filtro").getAsString(),eve);
		}
		 attivo =body.get("generi").getAsJsonObject();
		if(attivo.get("attivo").getAsBoolean()) {
			Genere filtroG= new Genere();
			eve= filtroG.filtra(attivo.get("filtro").getAsString(),eve);
		}
		 attivo =body.get("source").getAsJsonObject();
		if(attivo.get("attivo").getAsBoolean()) {
			Source filtroSo= new Source();
			eve= filtroSo.filtra(attivo.get("filtro").getAsString(),eve);
			System.out.println(eve.size());
		}
		 attivo =body.get("periodo").getAsJsonObject();
		if(attivo.get("attivo").getAsBoolean()) {
			Periodo filtroP= new Periodo();						
			eve= filtroP.filtra(attivo.get("filtro").getAsString(),eve);
		}		
		return eve;
	}
}