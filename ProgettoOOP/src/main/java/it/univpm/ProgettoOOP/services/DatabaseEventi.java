package it.univpm.ProgettoOOP.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.univpm.ProgettoOOP.log.Log;
import it.univpm.ProgettoOOP.model.Evento;

/**
 * @author Vecchiola Gregorio
 * Classe che gestisce il database contenente gli eventi
 */
public class DatabaseEventi extends TimerTask {
	/**
	 * Metodo che, all'avvio del programma, aggiorna il database con gli eventi degli stati salvati
	 */
	public void run() {	
		ListaStati ls= new ListaStati();
		JsonToClass jtc= new JsonToClass();
		ArrayList<String> stati = ls.getStati();
		Gson gson= new Gson();
		Evento e= new Evento();
		Boolean b=true;
		for(String s: stati) {
			 String sigla= getSigla(s);
			 String urlpagine="https://app.ticketmaster.com/discovery/v2/events.json?stateCode="+sigla+"&countryCode=US&apikey=02znw2Zzu1vGIRauqzXnI595CY7TlXX1&page=0&size=199";
			 String evento_statopagine=CercaEvento.getEvento(urlpagine);
			 int x = jtc.getPage(evento_statopagine)-1;
			 int y= jtc.getTotalElements(evento_statopagine);
			 if(x>5) x=5;
			 for(int j=0;j<=x;j++) {
			 	String url="https://app.ticketmaster.com/discovery/v2/events.json?stateCode="+sigla+"&countryCode=US&apikey=02znw2Zzu1vGIRauqzXnI595CY7TlXX1&page="+j+"&size=199";
			 	String evento_stato=CercaEvento.getEvento(url);
			 	if((j==x)&&(x!=5)){
			 		y-= (j*199);
			 		for(int i=0; i<y;i++) {
			 			if(j!=0 || i!=0) b=false;
					 	e=jtc.getEventoFromJson(evento_stato,i);
					 	salvaEvento(gson.toJson(e),b);
					 }
			 	}else {
			 		for(int i=0; i<199;i++) {
			 			if(j!=0 || i!=0) b=false;
			 			e=jtc.getEventoFromJson(evento_stato,i);
			 			salvaEvento(gson.toJson(e),b);
			 		}
			 	}	 	
			 }
		}
		System.out.println("Database Aggiornato.");
	}
	
	/**
	 * Metodo che restituisce la sigla di uno stato dato il suo nome
	 * @param stato nome dello stato
	 * @return sigla dello stato
	 */
	public String getSigla(String stato) {
		String s= new String();
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(new File("sigle.json")));
			JsonObject jo = JsonParser.parseReader(buffer).getAsJsonObject();
			s=jo.get(stato).getAsString();
			buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * Metodo che scrive un evento nel file
	 * @param s stringa contenete l'evento in json
	 * @param b valore booleano che indica se l'evento è il primo della lista
	 */
	public void salvaEvento(String s,Boolean b) {
		try {
			BufferedWriter buffer;
			if(b)
				buffer= new BufferedWriter(new FileWriter("eventi.json"));
			else
				buffer= new BufferedWriter(new FileWriter("eventi.json",true));
			buffer.write(s+"\n");
			buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.report("Errore di scrittura","Errore nella scrittura del database");
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo per la lettura degli eventi dal file 
	 * @return lista contenente tutti gli eventi del file
	 */
	public ArrayList<Evento> getEventi() {
		ArrayList<Evento> eventi= new ArrayList<Evento>();
		Gson gson= new Gson();
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(new File("eventi.json")));			
			String riga= buffer.readLine();
			while(riga!=null) {
				eventi.add(gson.fromJson(riga, Evento.class));
				riga= buffer.readLine();
			}
			buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return eventi;
	}
}
