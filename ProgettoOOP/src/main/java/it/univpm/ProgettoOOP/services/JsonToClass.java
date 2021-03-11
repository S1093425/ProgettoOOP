package it.univpm.ProgettoOOP.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.univpm.ProgettoOOP.log.Log;
import it.univpm.ProgettoOOP.model.Evento;
import it.univpm.ProgettoOOP.model.Stato;

public class JsonToClass {
		public int getPage(String evento_stato) {
			 JsonObject Obj = (JsonObject)JsonParser.parseString(evento_stato);
			 JsonObject page = Obj.get("page").getAsJsonObject();
			 int pagine=page.get("totalPages").getAsInt();
			return pagine;
		}
		public int getTotalElements(String evento_stato) {
			 JsonObject Obj = (JsonObject)JsonParser.parseString(evento_stato);
			 JsonObject page = Obj.get("page").getAsJsonObject();
			 int tot= page.get("totalElements").getAsInt();
			return tot;
		}
		public String getNomeStato(String evento_stato) {
			JsonObject Objsigle = (JsonObject)JsonParser.parseString(evento_stato);
			 JsonObject embedded = Objsigle.get("_embedded").getAsJsonObject();
			 JsonArray arrEventi = embedded.get("events").getAsJsonArray();
			 JsonObject objEvent = arrEventi.get(0).getAsJsonObject();
			 JsonObject ambedded2 = objEvent.get("_embedded").getAsJsonObject();
			 JsonArray venues = ambedded2.get("venues").getAsJsonArray();
			 JsonObject stato = venues.get(0).getAsJsonObject();
			 JsonObject statecode = stato.get("state").getAsJsonObject();
			 String nome=statecode.get("name").getAsString();
			return nome;
		}
		public Evento getEventoFromJson(String evento_stato,int i){
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
		
		public JsonObject getJsonFromStats(Stato s) {
			JsonObject JsonStato= new JsonObject();
			JsonStato.addProperty("Stato", s.getStateCode());
			JsonStato.addProperty("Eventi Totali", s.getEventi_Totali());
			JsonArray JsonAGeneri= new JsonArray();
			JsonStato.add("Generi", JsonAGeneri);
			JsonObject JsonGeneri = new JsonObject();
			JsonAGeneri.add(JsonGeneri);
			JsonGeneri.addProperty("Musica", s.getMusic());
			JsonGeneri.addProperty("Sport", s.getSport());
			JsonGeneri.addProperty("Arte e Teatro", s.getArt());
			JsonGeneri.addProperty("Misto", s.getMix());
			JsonArray JsonASource= new JsonArray();
			JsonStato.add("Source", JsonASource);
			JsonObject JsonSource = new JsonObject();
			JsonASource.add(JsonSource);
			JsonSource.addProperty("Ticketmaster", s.getTicketmaster());
			JsonSource.addProperty("Universe", s.getUniverse());
			JsonSource.addProperty("Ticketmaster Resale", s.getTmr());
			JsonSource.addProperty("FrontGate Tickets", s.getFrontgate());
			JsonSource.addProperty("Ticketweb", s.getTicketweb());
			return JsonStato;
		}
		
}
