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

public class ListaStati {
	public ListaStati() {
	}
	
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return stati;
	}
	
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
		}	
	}
	
	public void aggiungiStato(String nuovo) {
		ArrayList<String> stati= getStati();
		stati.add(nuovo);
		scritturaFile(stati);
	}
	
	public void rimuoviStato(String rimosso) {
		ArrayList<String> stati= getStati();
		stati.remove(rimosso);
		scritturaFile(stati);
	}
}
