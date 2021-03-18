package it.univpm.ProgettoOOP.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.univpm.ProgettoOOP.log.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Vecchiola Gregorio
 *
 * Classe che gestisce il DataBase contenente gli stati dei quali calcolare le statistiche
 */
public class ListaStati {
	public ListaStati() {
	}
	
	/**
	 * Metodo che legge dal file, e ritorna la lista degli stati
	 * @return lista degli stati
	 */
	public ArrayList<String> getStati(){
		ArrayList<String> stati= new ArrayList<String>();
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(new File("stati.json")));
			JsonObject jo = JsonParser.parseReader(buffer).getAsJsonObject();
			JsonArray ja = jo.get("stati").getAsJsonArray();
			for (int i=0; i<ja.size();i++) 
				stati.add(ja.get(i).getAsString());
			buffer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.report("Errore ricerca file","File con gli stati non trovato");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.report("Errore in scrittura/lettura", "Controllare file degli stati");
		}	
		return stati;
	}
	
	/**
	 * Metodo per la scrittura nel file
	 * @param stati Stati da scrivere nel file
	 */
	public void scritturaFile(ArrayList<String> stati) {
		Gson gson = new Gson();
		String file= "{\"stati\":" + gson.toJson(stati) + "}";
		try {			
			 BufferedWriter buffer = new BufferedWriter(new FileWriter(new File("stati.json")));
			 buffer.write(file);
			 buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.report("Errore in scrittura/lettura", "Controllare file degli stati");
		}	
	}
	
	/**
	 * Metodo per aggiungere lo stato nel file
	 * @param nuovo nome dello stato da aggiungere
	 */
	public void aggiungiStato(String nuovo) {
		ArrayList<String> stati= getStati();
		stati.add(nuovo);
		scritturaFile(stati);
	}
	
	/**
	 * Metodo per la rimozione di uno stato presente nel file
	 * @param rimosso nome dello stato rimosso
	 */
	public void rimuoviStato(String rimosso) {
		ArrayList<String> stati= getStati();
		stati.remove(rimosso);
		scritturaFile(stati);
	}
}
