package it.univpm.ProgettoOOP.services;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.univpm.ProgettoOOP.exception.EventException;
import it.univpm.ProgettoOOP.log.Log;
import it.univpm.ProgettoOOP.model.Evento;
import it.univpm.ProgettoOOP.model.Global;
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
									}catch(NullPointerException p){
										System.out.println("Non Definito");
										Log.report("DATA NON DEFINITA",p.getMessage());
									}
								try{
									JsonArray classification = objEvent.get("classifications").getAsJsonArray();
										JsonObject primary = classification.get(0).getAsJsonObject();
										JsonObject genere = primary.get("segment").getAsJsonObject();
										e.setGenere(genere.get("name").getAsString());
								}catch(NullPointerException p){
									e.setGenere("Non Definito");
									Log.report("GENERE NON DEFINITO",p.getMessage());
								}
								JsonObject ambedded2 = objEvent.get("_embedded").getAsJsonObject();
									JsonArray venues = ambedded2.get("venues").getAsJsonArray();
									JsonObject venueObj = venues.get(0).getAsJsonObject();
										JsonObject stato = venueObj.get("state").getAsJsonObject();
											e.setStato(stato.get("name").getAsString()); 
										JsonObject source= venueObj.get("upcomingEvents").getAsJsonObject();
											ArrayList<String> sourceName= new ArrayList<String>();
											try {
											if(source.get("ticketmaster").getAsInt()!=0)
												sourceName.add("Ticketmaster");
											}catch(NullPointerException p){
												
											}
											try {
												if(source.get("universe").getAsInt()!=0)
													sourceName.add("Universe");
											}catch(NullPointerException p) {
												
											}
											try {
												if(source.get("frontgate").getAsInt()!=0)
													sourceName.add("Frontgate Tickets");
											}catch(NullPointerException p) {
												
											}
											try {
												if(source.get("tmr").getAsInt()!=0)
													sourceName.add("Ticketmaster Resale");
											}catch(NullPointerException p) {
												
											}										
											e.setSourceName(sourceName);

			return e;	
		}
		
		public JsonObject getJsonFromStats(Stato s) {
			JsonObject JsonStato= new JsonObject();
			JsonStato.addProperty("Stato", s.getStato());
			JsonStato.addProperty("Eventi Totali", s.getEventi_Totali());
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
			JsonSource.addProperty("Frontgate Tickets", s.getSource()[2]);
			JsonSource.addProperty("Ticketmaster Resale", s.getSource()[3]);
			return JsonStato;
		}
		public JsonObject getJsonFromGlobalStats(Global Stats) throws EventException {
			JsonObject JsonStatGlob = new JsonObject();
				JsonArray JsonMaxStatiArr=new JsonArray();
				JsonStatGlob.add("Stato con più eventi", JsonMaxStatiArr);
					JsonObject JsonMaxStati=new JsonObject();
					JsonMaxStatiArr.add(JsonMaxStati);
					if(Stats.getTot()==null) throw new EventException();
					JsonMaxStati.addProperty("Nome", Stats.getTot().getMaxS());
					JsonMaxStati.addProperty("Eventi",Stats.getTot().getMax());
				JsonArray JsonMinStatiArr=new JsonArray();
				JsonStatGlob.add("Stato con meno eventi", JsonMinStatiArr);
					JsonObject JsonMinStati=new JsonObject();
					JsonMinStatiArr.add(JsonMinStati);
					JsonMinStati.addProperty("Nome", Stats.getTot().getMinS());
					JsonMinStati.addProperty("Eventi",Stats.getTot().getMin());
				JsonArray JsonSourceArray =new JsonArray();	
				JsonStatGlob.add("Statistiche per Source", JsonSourceArray);
					JsonObject JsonSourceObject = new JsonObject();
					JsonSourceArray.add(JsonSourceObject);
						JsonArray JsonTick = new JsonArray();
						JsonSourceObject.add("Ticketmaster", JsonTick);
							JsonObject JsonTickObj= new JsonObject();
							JsonTick.add(JsonTickObj);
								JsonArray JsonTickMax =new JsonArray();
								JsonTickObj.add("Stato con più eventi Ticketmaster", JsonTickMax);
									JsonObject JsonTickMaxObj= new JsonObject();
									JsonTickMax.add(JsonTickMaxObj);
									JsonTickMaxObj.addProperty("Nome", Stats.getSource()[0].getMaxS());
									JsonTickMaxObj.addProperty("Eventi", Stats.getSource()[0].getMax());
								JsonArray JsonTickMin =new JsonArray();
								JsonTickObj.add("Stato con meno eventi Ticketmaster", JsonTickMin);
									JsonObject JsonTickMinObj= new JsonObject();
									JsonTickMin.add(JsonTickMinObj);
									JsonTickMinObj.addProperty("Nome", Stats.getSource()[0].getMinS());
									JsonTickMinObj.addProperty("Eventi", Stats.getSource()[0].getMin());
						JsonArray JsonUni = new JsonArray();
						JsonSourceObject.add("Universe", JsonUni);
							JsonObject JsonUniObj= new JsonObject();
							JsonUni.add(JsonUniObj);
								JsonArray JsonUniMax =new JsonArray();
								JsonUniObj.add("Stato con più eventi Universe", JsonUniMax);
									JsonObject JsonUniMaxObj= new JsonObject();
									JsonUniMax.add(JsonUniMaxObj);
									JsonUniMaxObj.addProperty("Nome", Stats.getSource()[1].getMaxS());
									JsonUniMaxObj.addProperty("Eventi", Stats.getSource()[1].getMax());
								JsonArray JsonUniMin =new JsonArray();
								JsonUniObj.add("Stato con meno eventi Universe", JsonUniMin);
									JsonObject JsonUniMinObj= new JsonObject();
									JsonUniMin.add(JsonUniMinObj);
									JsonUniMinObj.addProperty("Nome", Stats.getSource()[1].getMinS());
									JsonUniMinObj.addProperty("Eventi", Stats.getSource()[1].getMin());							
						JsonArray JsonFront = new JsonArray();
						JsonSourceObject.add("Frontgate Tickets", JsonFront);
							JsonObject JsonFrontObj= new JsonObject();
							JsonFront.add(JsonFrontObj);
								JsonArray JsonFrontMax =new JsonArray();
								JsonFrontObj.add("Stato con più eventi Frontgate Tickets", JsonFrontMax);
									JsonObject JsonFrontMaxObj= new JsonObject();
									JsonFrontMax.add(JsonFrontMaxObj);
									JsonFrontMaxObj.addProperty("Nome", Stats.getSource()[2].getMaxS());
									JsonFrontMaxObj.addProperty("Eventi",Stats.getSource()[2].getMax());
								JsonArray JsonFrontMin =new JsonArray();
								JsonFrontObj.add("Stato con meno eventi Frontgate Tickets", JsonFrontMin);
									JsonObject JsonFrontMinObj= new JsonObject();
									JsonFrontMin.add(JsonFrontMinObj);
									JsonFrontMinObj.addProperty("Nome", Stats.getSource()[2].getMinS());
									JsonFrontMinObj.addProperty("Eventi", Stats.getSource()[2].getMin());
						JsonArray JsonTmr = new JsonArray();
						JsonSourceObject.add("Ticketmaster Resale", JsonTmr);
							JsonObject JsonTmrObj= new JsonObject();
							JsonTmr.add(JsonTmrObj);
								JsonArray JsonTmrMax =new JsonArray();
								JsonTmrObj.add("Stato con più eventi Tickemaster Resale", JsonTmrMax);
									JsonObject JsonTmrMaxObj= new JsonObject();
									JsonTmrMax.add(JsonTmrMaxObj);
									JsonTmrMaxObj.addProperty("Nome", Stats.getSource()[3].getMaxS());
									JsonTmrMaxObj.addProperty("Eventi", Stats.getSource()[3].getMax());
								JsonArray JsonTmrMin =new JsonArray();
								JsonTmrObj.add("Stato con meno eventi Ticketmaster Resale", JsonTmrMin);
									JsonObject JsonTmrMinObj= new JsonObject();
									JsonTmrMin.add(JsonTmrMinObj);
									JsonTmrMinObj.addProperty("Nome", Stats.getSource()[3].getMinS());
									JsonTmrMinObj.addProperty("Eventi", Stats.getSource()[3].getMin());				
				JsonArray JsonGenereArray =new JsonArray();	
				JsonStatGlob.add("Statistiche per Genere", JsonGenereArray);
					JsonObject JsonGenereObject = new JsonObject();
					JsonGenereArray.add(JsonGenereObject);
						JsonArray JsonMusic = new JsonArray();
						JsonGenereObject.add("Musica", JsonMusic);
							JsonObject JsonMusicObj= new JsonObject();
							JsonMusic.add(JsonMusicObj);
								JsonArray JsonMusicMax =new JsonArray();
								JsonMusicObj.add("Stato con più eventi Musica", JsonMusicMax);
									JsonObject JsonMusicMaxObj= new JsonObject();
									JsonMusicMax.add(JsonMusicMaxObj);
									JsonMusicMaxObj.addProperty("Nome", Stats.getGeneri()[0].getMaxS());
									JsonMusicMaxObj.addProperty("Eventi",Stats.getGeneri()[0].getMax());
								JsonArray JsonMusicMin =new JsonArray();
								JsonMusicObj.add("Stato con meno eventi Musica", JsonMusicMin);
									JsonObject JsonMusicMinObj= new JsonObject();
									JsonMusicMin.add(JsonMusicMinObj);
									JsonMusicMinObj.addProperty("Nome", Stats.getGeneri()[0].getMinS());
									JsonMusicMinObj.addProperty("Eventi", Stats.getGeneri()[0].getMin());
						JsonArray JsonSport = new JsonArray();
						JsonGenereObject.add("Sport", JsonSport);
							JsonObject JsonSportObj= new JsonObject();
							JsonSport.add(JsonSportObj);
								JsonArray JsonSportMax =new JsonArray();
								JsonSportObj.add("Stato con più eventi Sport", JsonSportMax);
									JsonObject JsonSportMaxObj= new JsonObject();
									JsonSportMax.add(JsonSportMaxObj);
									JsonSportMaxObj.addProperty("Nome", Stats.getGeneri()[1].getMaxS());
									JsonSportMaxObj.addProperty("Eventi", Stats.getGeneri()[1].getMax());
								JsonArray JsonSportMin =new JsonArray();
								JsonSportObj.add("Stato con meno eventi Sport", JsonSportMin);
									JsonObject JsonSportMinObj= new JsonObject();
									JsonSportMin.add(JsonSportMinObj);
									JsonSportMinObj.addProperty("Nome", Stats.getGeneri()[1].getMinS());
									JsonSportMinObj.addProperty("Eventi", Stats.getGeneri()[1].getMin());
						JsonArray JsonArt = new JsonArray();
						JsonGenereObject.add("Arte e Teatro", JsonArt);
							JsonObject JsonArtObj= new JsonObject();
							JsonArt.add(JsonArtObj);
								JsonArray JsonArtMax =new JsonArray();
								JsonArtObj.add("Stato con più eventi Arte e Teatro", JsonArtMax);
									JsonObject JsonArtMaxObj= new JsonObject();
									JsonArtMax.add(JsonArtMaxObj);
									JsonArtMaxObj.addProperty("Nome", Stats.getGeneri()[2].getMaxS());
									JsonArtMaxObj.addProperty("Eventi", Stats.getGeneri()[2].getMax());
								JsonArray JsonArtMin =new JsonArray();
								JsonArtObj.add("Stato con meno eventi Arte e Teatro", JsonArtMin);
									JsonObject JsonArtMinObj= new JsonObject();
									JsonArtMin.add(JsonArtMinObj);
									JsonArtMinObj.addProperty("Nome", Stats.getGeneri()[2].getMinS());
									JsonArtMinObj.addProperty("Eventi", Stats.getGeneri()[2].getMin());
						JsonArray JsonMix = new JsonArray();
						JsonGenereObject.add("Misto", JsonMix);
							JsonObject JsonMixObj= new JsonObject();
							JsonMix.add(JsonMixObj);
								JsonArray JsonMixMax =new JsonArray();
								JsonMixObj.add("Stato con più eventi Misti", JsonMixMax);
									JsonObject JsonMixMaxObj= new JsonObject();
									JsonMixMax.add(JsonMixMaxObj);
									JsonMixMaxObj.addProperty("Nome", Stats.getGeneri()[3].getMaxS());
									JsonMixMaxObj.addProperty("Eventi", Stats.getGeneri()[3].getMax());
								JsonArray JsonMixMin =new JsonArray();
								JsonMixObj.add("Stato con meno eventi Misti", JsonMixMin);
									JsonObject JsonMixMinObj= new JsonObject();
									JsonMixMin.add(JsonMixMinObj);
									JsonMixMinObj.addProperty("Nome", Stats.getGeneri()[3].getMin());
									JsonMixMinObj.addProperty("Eventi", Stats.getGeneri()[3].getMin());
			return JsonStatGlob;
		}		
}
