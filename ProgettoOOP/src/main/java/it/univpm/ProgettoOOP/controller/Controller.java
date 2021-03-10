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
		JsonFinale.add("Statistiche", JsonStatistiche);
		JsonArray JsonStatisticheGlobali= new JsonArray();
		JsonFinale.add("Statistiche Globali", JsonStatisticheGlobali);
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
				JsonStato.addProperty("Stato", s.getStateCode());
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
				JsonSource.addProperty("Ticketmaster Resale", s.getSource()[2]);
				JsonSource.addProperty("FrontGate Tickets", s.getSource()[3]);
				JsonSource.addProperty("Ticketweb", s.getSource()[4]);
			}
			buffer.close();
			
			
			ArrayList<Stato> statsTot = new ArrayList<Stato>();
			ArrayList<Stato> statsSource = new ArrayList<Stato>();
			ArrayList<Stato> statsGeneri = new ArrayList<Stato>();
			for(int i=0;i<stati.size();i++) {
				 if(i==0) {
					for(int j=0;j<2;j++)
						statsTot.add(stati.get(i));
					for(int j=0;j<10;j++)
						statsSource.add(stati.get(i));
					for(int j=0;j<8;j++)
						statsGeneri.add(stati.get(i));
				 }
				 
				 if (statsTot.get(0).getEventi_Totali()<stati.get(i).getEventi_Totali())
					 statsTot.set(0, stati.get(i));
				 if (statsTot.get(1).getEventi_Totali()>stati.get(i).getEventi_Totali()) 
					 statsTot.set(1, stati.get(i));
				 
				 for(int j=0;j<5;j++) {				
					 if (statsSource.get(j).getSource()[j]<stati.get(i).getSource()[j]) 
						 statsSource.set(j, stati.get(i));				 
					 if (statsSource.get(j).getSource()[j]>stati.get(i).getSource()[j])
						 statsSource.set(j, stati.get(i));
				 }
				 
				 for(int j=0;j<4;j++) {				
					 if (statsGeneri.get(j).getGeneri()[j]<stati.get(i).getGeneri()[j]) 
						 statsGeneri.set(j, stati.get(i));				 
					 if (statsGeneri.get(j).getGeneri()[j]>stati.get(i).getGeneri()[j])
						 statsGeneri.set(j, stati.get(i));
				 }
			 }
			
			
				JsonObject JsonStatGlob = new JsonObject();
				JsonStatisticheGlobali.add(JsonStatGlob);
					JsonArray JsonMaxStatiArr=new JsonArray();
					JsonStatGlob.add("Stato con più eventi", JsonMaxStatiArr);
						JsonObject JsonMaxStati=new JsonObject();
						JsonMaxStatiArr.add(JsonMaxStati);
						JsonMaxStati.addProperty("Nome", statsTot.get(0).getStateCode());
						JsonMaxStati.addProperty("Eventi",statsTot.get(0).getEventi_Totali());
					JsonArray JsonMinStatiArr=new JsonArray();
					JsonStatGlob.add("Stato con meno eventi", JsonMinStatiArr);
						JsonObject JsonMinStati=new JsonObject();
						JsonMinStatiArr.add(JsonMinStati);
						JsonMinStati.addProperty("Nome", statsTot.get(1).getStateCode());
						JsonMinStati.addProperty("Eventi",statsTot.get(2).getEventi_Totali());
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
										JsonTickMaxObj.addProperty("Nome", statsSource.get(0).getStateCode());
										JsonTickMaxObj.addProperty("Eventi", statsSource.get(0).getSource()[0]);
									JsonArray JsonTickMin =new JsonArray();
									JsonTickObj.add("Stato con meno eventi Ticketmaster", JsonTickMin);
										JsonObject JsonTickMinObj= new JsonObject();
										JsonTickMin.add(JsonTickMinObj);
										JsonTickMinObj.addProperty("Nome", statsSource.get(1).getStateCode());
										JsonTickMinObj.addProperty("Eventi", statsSource.get(1).getSource()[0]);
							JsonArray JsonUni = new JsonArray();
							JsonSourceObject.add("Universe", JsonUni);
								JsonObject JsonUniObj= new JsonObject();
								JsonUni.add(JsonUniObj);
									JsonArray JsonUniMax =new JsonArray();
									JsonUniObj.add("Stato con più eventi Universe", JsonUniMax);
										JsonObject JsonUniMaxObj= new JsonObject();
										JsonUniMax.add(JsonUniMaxObj);
										JsonUniMaxObj.addProperty("Nome", statsSource.get(2).getStateCode());
										JsonUniMaxObj.addProperty("Eventi", statsSource.get(2).getSource()[1]);
									JsonArray JsonUniMin =new JsonArray();
									JsonUniObj.add("Stato con meno eventi Universe", JsonUniMin);
										JsonObject JsonUniMinObj= new JsonObject();
										JsonUniMin.add(JsonUniMinObj);
										JsonUniMinObj.addProperty("Nome", statsSource.get(3).getStateCode());
										JsonUniMinObj.addProperty("Eventi", statsSource.get(3).getSource()[1]);
							JsonArray JsonFront = new JsonArray();
							JsonSourceObject.add("Frontgate Tickets", JsonFront);
								JsonObject JsonFrontObj= new JsonObject();
								JsonFront.add(JsonFrontObj);
									JsonArray JsonFrontMax =new JsonArray();
									JsonFrontObj.add("Stato con più eventi Frontgate Tickets", JsonFrontMax);
										JsonObject JsonFrontMaxObj= new JsonObject();
										JsonFrontMax.add(JsonFrontMaxObj);
										JsonFrontMaxObj.addProperty("Nome", statsSource.get(4).getStateCode());
										JsonFrontMaxObj.addProperty("Eventi", statsSource.get(4).getSource()[2]);
									JsonArray JsonFrontMin =new JsonArray();
									JsonFrontObj.add("Stato con meno eventi Frontgate Tickets", JsonFrontMin);
										JsonObject JsonFrontMinObj= new JsonObject();
										JsonFrontMin.add(JsonFrontMinObj);
										JsonFrontMinObj.addProperty("Nome", statsSource.get(5).getStateCode());
										JsonFrontMinObj.addProperty("Eventi", statsSource.get(5).getSource()[2]);
							JsonArray JsonTmr = new JsonArray();
							JsonSourceObject.add("Ticketmaster Resale", JsonTmr);
								JsonObject JsonTmrObj= new JsonObject();
								JsonTmr.add(JsonTmrObj);
									JsonArray JsonTmrMax =new JsonArray();
									JsonTmrObj.add("Stato con più eventi Tickemaster Resale", JsonTmrMax);
										JsonObject JsonTmrMaxObj= new JsonObject();
										JsonTmrMax.add(JsonTmrMaxObj);
										JsonTmrMaxObj.addProperty("Nome", statsSource.get(6).getStateCode());
										JsonTmrMaxObj.addProperty("Eventi", statsSource.get(6).getSource()[3]);
									JsonArray JsonTmrMin =new JsonArray();
									JsonTmrObj.add("Stato con meno eventi Ticketmaster Resale", JsonTmrMin);
										JsonObject JsonTmrMinObj= new JsonObject();
										JsonTmrMin.add(JsonTmrMinObj);
										JsonTmrMinObj.addProperty("Nome", statsSource.get(7).getStateCode());
										JsonTmrMinObj.addProperty("Eventi", statsSource.get(7).getSource()[3]);
							JsonArray JsonWeb = new JsonArray();
							JsonSourceObject.add("Ticketweb", JsonWeb);
								JsonObject JsonWebObj= new JsonObject();
								JsonWeb.add(JsonWebObj);
									JsonArray JsonWebMax =new JsonArray();
									JsonWebObj.add("Stato con più eventi Ticketweb", JsonWebMax);
										JsonObject JsonWebMaxObj= new JsonObject();
										JsonWebMax.add(JsonTickMaxObj);
										JsonWebMaxObj.addProperty("Nome", statsSource.get(8).getStateCode());
										JsonWebMaxObj.addProperty("Eventi", statsSource.get(8).getSource()[4]);
									JsonArray JsonWebMin =new JsonArray();
									JsonWebObj.add("Stato con meno eventi Ticketweb", JsonWebMin);
										JsonObject JsonWebMinObj= new JsonObject();
										JsonWebMin.add(JsonWebMinObj);
										JsonWebMinObj.addProperty("Nome", statsSource.get(9).getStateCode());
										JsonWebMinObj.addProperty("Eventi", statsSource.get(9).getSource()[4]);
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
										JsonMusicMaxObj.addProperty("Nome", statsGeneri.get(0).getStateCode());
										JsonMusicMaxObj.addProperty("Eventi", statsSource.get(0).getSource()[0]);
									JsonArray JsonMusicMin =new JsonArray();
									JsonMusicObj.add("Stato con meno eventi Musica", JsonMusicMin);
										JsonObject JsonMusicMinObj= new JsonObject();
										JsonMusicMin.add(JsonMusicMinObj);
										JsonMusicMinObj.addProperty("Nome", statsSource.get(1).getStateCode());
										JsonMusicMinObj.addProperty("Eventi", statsSource.get(1).getSource()[0]);
							JsonArray JsonSport = new JsonArray();
							JsonGenereObject.add("Sport", JsonSport);
								JsonObject JsonSportObj= new JsonObject();
								JsonSport.add(JsonSportObj);
									JsonArray JsonSportMax =new JsonArray();
									JsonSportObj.add("Stato con più eventi Sport", JsonSportMax);
										JsonObject JsonSportMaxObj= new JsonObject();
										JsonSportMax.add(JsonSportMaxObj);
										JsonSportMaxObj.addProperty("Nome", statsSource.get(2).getStateCode());
										JsonSportMaxObj.addProperty("Eventi", statsSource.get(2).getSource()[1]);
									JsonArray JsonSportMin =new JsonArray();
									JsonSportObj.add("Stato con meno eventi Sport", JsonSportMin);
										JsonObject JsonSportMinObj= new JsonObject();
										JsonSportMin.add(JsonSportMinObj);
										JsonSportMinObj.addProperty("Nome", statsSource.get(3).getStateCode());
										JsonSportMinObj.addProperty("Eventi", statsSource.get(3).getSource()[1]);
							JsonArray JsonArt = new JsonArray();
							JsonGenereObject.add("Arte e Teatro", JsonArt);
								JsonObject JsonArtObj= new JsonObject();
								JsonArt.add(JsonArtObj);
									JsonArray JsonArtMax =new JsonArray();
									JsonArtObj.add("Stato con più eventi Arte e Teatro", JsonArtMax);
										JsonObject JsonArtMaxObj= new JsonObject();
										JsonArtMax.add(JsonArtMaxObj);
										JsonArtMaxObj.addProperty("Nome", statsSource.get(4).getStateCode());
										JsonArtMaxObj.addProperty("Eventi", statsSource.get(4).getSource()[2]);
									JsonArray JsonArtMin =new JsonArray();
									JsonArtObj.add("Stato con meno eventi Arte e Teatro", JsonArtMin);
										JsonObject JsonArtMinObj= new JsonObject();
										JsonArtMin.add(JsonArtMinObj);
										JsonArtMinObj.addProperty("Nome", statsSource.get(5).getStateCode());
										JsonArtMinObj.addProperty("Eventi", statsSource.get(5).getSource()[2]);
							JsonArray JsonMix = new JsonArray();
							JsonGenereObject.add("Misto", JsonMix);
								JsonObject JsonMixObj= new JsonObject();
								JsonMix.add(JsonMixObj);
									JsonArray JsonMixMax =new JsonArray();
									JsonMixObj.add("Stato con più eventi Misti", JsonMixMax);
										JsonObject JsonMixMaxObj= new JsonObject();
										JsonMixMax.add(JsonMixMaxObj);
										JsonMixMaxObj.addProperty("Nome", statsSource.get(6).getStateCode());
										JsonMixMaxObj.addProperty("Eventi", statsSource.get(6).getSource()[3]);
									JsonArray JsonMixMin =new JsonArray();
									JsonMixObj.add("Stato con meno eventi Misti", JsonMixMin);
										JsonObject JsonMixMinObj= new JsonObject();
										JsonMixMin.add(JsonMixMinObj);
										JsonMixMinObj.addProperty("Nome", statsSource.get(7).getStateCode());
										JsonMixMinObj.addProperty("Eventi", statsSource.get(7).getSource()[3]);
			
		
		}catch (JsonSyntaxException e) {
			Log.report("FILE sigle.json NON TROVATO", e.getMessage());
		} catch (IOException e) {
			Log.report("ERRORE LETTURA FILE", e.getMessage());
		}
		return JsonFinale;
	}
	
}
