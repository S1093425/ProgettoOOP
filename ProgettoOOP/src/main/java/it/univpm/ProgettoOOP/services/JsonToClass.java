package it.univpm.ProgettoOOP.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.univpm.ProgettoOOP.log.Log;
import it.univpm.ProgettoOOP.model.Evento;

public class JsonToClass {
		public Evento getClassFromJson(String evento_stato,int i){
			Evento e= new Evento();
			JsonObject Obj = (JsonObject)JsonParser.parseString(evento_stato);
			 JsonObject embedded = Obj.get("_embedded").getAsJsonObject();
			JsonArray arrEventi = embedded.get("events").getAsJsonArray();
			 JsonObject objEvent = arrEventi.get(i).getAsJsonObject();
			 	e.setName(objEvent.get("name").getAsString());
			 JsonObject dates = objEvent.get("dates").getAsJsonObject();
			 JsonObject start = dates.get("start").getAsJsonObject();
			 try {
			 	e.setDataInizio(start.get("localDate").getAsString());
			 }catch(Exception l){
				 System.out.println("Formato data errato");
				 Log.report("ERRORE FORMATO DATA",l.getMessage());
			 }
			 try{
			 JsonArray classification = objEvent.get("classifications").getAsJsonArray();
			 JsonObject primary = classification.get(0).getAsJsonObject();
			 JsonObject genere = primary.get("segment").getAsJsonObject();
			  e.setGenere(genere.get("name").getAsString());
			 }catch(Exception p){
				 e.setGenere("Not Defined");
				 Log.report("GENERE NON DEFINITO",p.getMessage());
			 }
			  JsonObject ambedded2 = objEvent.get("_embedded").getAsJsonObject();
			 JsonArray venues = ambedded2.get("venues").getAsJsonArray();
			 JsonObject stato = venues.get(0).getAsJsonObject();
			 JsonObject statecode = stato.get("state").getAsJsonObject();
			  e.setStateCode(statecode.get("name").getAsString()); 
			return e;	
		}
		
		
		
}
