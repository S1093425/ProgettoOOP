package it.univpm.ProgettoOOP.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
import it.univpm.ProgettoOOP.statistiche.Statistiche;

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
		Statistiche statistiche=new Statistiche();
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
				s.setTicketmaster(array[0]);
				s.setUniverse(array[1]);
				s.setTmr(array[2]);
				s.setFrontgate(array[3]);
				s.setTicketweb(array[4]);
				int[] array1 = new int[4];
				for(int j=0;j<array_generi.size();j++) {
					String urlgenere="https://app.ticketmaster.com/discovery/v2/events.json?stateCode="+array_sigle.get(i).getAsString()+"&countryCode=US&apikey=02znw2Zzu1vGIRauqzXnI595CY7TlXX1&size=199&classificationName="+array_generi.get(j).getAsString();
					 String statgenere=CercaEvento.getEvento(urlgenere);
					 JsonObject Objgenere = (JsonObject)JsonParser.parseString(statgenere);
					 JsonObject pagegenere = Objgenere.get("page").getAsJsonObject();
					 array1[j]= pagegenere.get("totalElements").getAsInt();
				}
				s.setMusic(array1[0]);
				s.setSport(array1[1]);
				s.setArt(array1[2]);
				s.setMix(array1[3]);
				stati.add(s);
				JsonObject JsonStato = new JsonObject();
				JsonStatistiche.add(JsonStato);
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
			}
			buffer.close();
			
			
			ArrayList<Stato> statsTot = new ArrayList<Stato>();
			ArrayList<Stato> statsSourceMax = new ArrayList<Stato>();
			ArrayList<Stato> statsSourceMin = new ArrayList<Stato>();
			ArrayList<Stato> statsGeneriMin = new ArrayList<Stato>();
			ArrayList<Stato> statsGeneriMax = new ArrayList<Stato>();
			for(int i=0;i<stati.size();i++) {
				 if(i==0) {
					for(int j=0;j<2;j++)
						statsTot.add(stati.get(i));
					for(int j=0;j<5;j++) {
						statsSourceMin.add(stati.get(i));
						statsSourceMax.add(stati.get(i));
					}
					for(int j=0;j<8;j++) {
						statsGeneriMin.add(stati.get(i));
						statsGeneriMax.add(stati.get(i));
					}
				 }else {				 
					 if (statsTot.get(0).getEventi_Totali()<stati.get(i).getEventi_Totali())
						 statsTot.set(0, stati.get(i));
					 if (statsTot.get(1).getEventi_Totali()>stati.get(i).getEventi_Totali()) 
						 statsTot.set(1, stati.get(i));
					 statsSourceMax=statistiche.getMaxSource(statsSourceMax,stati.get(i));	
					 statsSourceMin=statistiche.getMinSource(statsSourceMin,stati.get(i));
					 statsGeneriMax=statistiche.getMaxGenere(statsGeneriMax, stati.get(i));
					 statsGeneriMin=statistiche.getMaxGenere(statsGeneriMin, stati.get(i));
				 }
			}
			System.out.println(statsTot.get(0).getEventi_Totali());
			System.out.println(statsTot.get(1).getEventi_Totali());
			System.out.println("---------------");
			System.out.println(statsSourceMax.get(0).getTicketmaster());
			System.out.println(statsSourceMin.get(0).getTicketmaster());
			System.out.println(statsSourceMax.get(1).getUniverse());
			System.out.println(statsSourceMin.get(1).getUniverse());
			System.out.println(statsSourceMax.get(2).getTmr());
			System.out.println(statsSourceMin.get(2).getTmr());
			System.out.println(statsSourceMax.get(3).getFrontgate());
			System.out.println(statsSourceMin.get(3).getFrontgate());
			System.out.println(statsSourceMax.get(4).getTicketweb());
			System.out.println("---------------");
			System.out.println(statsSourceMin.get(4).getTicketweb());
			System.out.println(statsGeneriMax.get(0).getMusic());
			System.out.println(statsGeneriMin.get(0).getMusic());
			System.out.println(statsGeneriMax.get(1).getSport());
			System.out.println(statsGeneriMin.get(1).getSport());
			System.out.println(statsGeneriMax.get(2).getArt());
			System.out.println(statsGeneriMin.get(2).getArt());
			System.out.println(statsGeneriMax.get(3).getMix());
			System.out.println(statsGeneriMin.get(3).getMix());
			
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
						JsonMinStati.addProperty("Eventi",statsTot.get(1).getEventi_Totali());
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
										JsonTickMaxObj.addProperty("Nome", statsSourceMax.get(0).getStateCode());
										JsonTickMaxObj.addProperty("Eventi", statsSourceMax.get(0).getTicketmaster());
									JsonArray JsonTickMin =new JsonArray();
									JsonTickObj.add("Stato con meno eventi Ticketmaster", JsonTickMin);
										JsonObject JsonTickMinObj= new JsonObject();
										JsonTickMin.add(JsonTickMinObj);
										JsonTickMinObj.addProperty("Nome", statsSourceMin.get(0).getStateCode());
										JsonTickMinObj.addProperty("Eventi", statsSourceMin.get(0).getTicketmaster());
							JsonArray JsonUni = new JsonArray();
							JsonSourceObject.add("Universe", JsonUni);
								JsonObject JsonUniObj= new JsonObject();
								JsonUni.add(JsonUniObj);
									JsonArray JsonUniMax =new JsonArray();
									JsonUniObj.add("Stato con più eventi Universe", JsonUniMax);
										JsonObject JsonUniMaxObj= new JsonObject();
										JsonUniMax.add(JsonUniMaxObj);
										JsonUniMaxObj.addProperty("Nome", statsSourceMax.get(1).getStateCode());
										JsonUniMaxObj.addProperty("Eventi", statsSourceMax.get(1).getUniverse());
									JsonArray JsonUniMin =new JsonArray();
									JsonUniObj.add("Stato con meno eventi Universe", JsonUniMin);
										JsonObject JsonUniMinObj= new JsonObject();
										JsonUniMin.add(JsonUniMinObj);
										JsonUniMinObj.addProperty("Nome", statsSourceMin.get(1).getStateCode());
										JsonUniMinObj.addProperty("Eventi", statsSourceMin.get(1).getUniverse());
							JsonArray JsonTmr = new JsonArray();
							JsonSourceObject.add("Ticketmaster Resale", JsonTmr);
								JsonObject JsonTmrObj= new JsonObject();
								JsonTmr.add(JsonTmrObj);
									JsonArray JsonTmrMax =new JsonArray();
									JsonTmrObj.add("Stato con più eventi Tickemaster Resale", JsonTmrMax);
										JsonObject JsonTmrMaxObj= new JsonObject();
										JsonTmrMax.add(JsonTmrMaxObj);
										JsonTmrMaxObj.addProperty("Nome", statsSourceMax.get(2).getStateCode());
										JsonTmrMaxObj.addProperty("Eventi", statsSourceMax.get(2).getTmr());
									JsonArray JsonTmrMin =new JsonArray();
									JsonTmrObj.add("Stato con meno eventi Ticketmaster Resale", JsonTmrMin);
										JsonObject JsonTmrMinObj= new JsonObject();
										JsonTmrMin.add(JsonTmrMinObj);
										JsonTmrMinObj.addProperty("Nome", statsSourceMin.get(2).getStateCode());
										JsonTmrMinObj.addProperty("Eventi", statsSourceMin.get(2).getTmr());		
							JsonArray JsonFront = new JsonArray();
							JsonSourceObject.add("Frontgate Tickets", JsonFront);
								JsonObject JsonFrontObj= new JsonObject();
								JsonFront.add(JsonFrontObj);
									JsonArray JsonFrontMax =new JsonArray();
									JsonFrontObj.add("Stato con più eventi Frontgate Tickets", JsonFrontMax);
										JsonObject JsonFrontMaxObj= new JsonObject();
										JsonFrontMax.add(JsonFrontMaxObj);
										JsonFrontMaxObj.addProperty("Nome", statsSourceMax.get(3).getStateCode());
										JsonFrontMaxObj.addProperty("Eventi", statsSourceMax.get(3).getFrontgate());
									JsonArray JsonFrontMin =new JsonArray();
									JsonFrontObj.add("Stato con meno eventi Frontgate Tickets", JsonFrontMin);
										JsonObject JsonFrontMinObj= new JsonObject();
										JsonFrontMin.add(JsonFrontMinObj);
										JsonFrontMinObj.addProperty("Nome", statsSourceMin.get(3).getStateCode());
										JsonFrontMinObj.addProperty("Eventi", statsSourceMin.get(3).getFrontgate());	
							JsonArray JsonWeb = new JsonArray();
							JsonSourceObject.add("Ticketweb", JsonWeb);
								JsonObject JsonWebObj= new JsonObject();
								JsonWeb.add(JsonWebObj);
									JsonArray JsonWebMax =new JsonArray();
									JsonWebObj.add("Stato con più eventi Ticketweb", JsonWebMax);
										JsonObject JsonWebMaxObj= new JsonObject();
										JsonWebMax.add(JsonTickMaxObj);
										JsonWebMaxObj.addProperty("Nome", statsSourceMax.get(4).getStateCode());
										JsonWebMaxObj.addProperty("Eventi", statsSourceMax.get(4).getTicketweb());
									JsonArray JsonWebMin =new JsonArray();
									JsonWebObj.add("Stato con meno eventi Ticketweb", JsonWebMin);
										JsonObject JsonWebMinObj= new JsonObject();
										JsonWebMin.add(JsonWebMinObj);
										JsonWebMinObj.addProperty("Nome", statsSourceMin.get(4).getStateCode());
										JsonWebMinObj.addProperty("Eventi", statsSourceMin.get(4).getTicketweb());
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
										JsonMusicMaxObj.addProperty("Nome", statsGeneriMax.get(0).getStateCode());
										JsonMusicMaxObj.addProperty("Eventi", statsGeneriMax.get(0).getMusic());
									JsonArray JsonMusicMin =new JsonArray();
									JsonMusicObj.add("Stato con meno eventi Musica", JsonMusicMin);
										JsonObject JsonMusicMinObj= new JsonObject();
										JsonMusicMin.add(JsonMusicMinObj);
										JsonMusicMinObj.addProperty("Nome", statsGeneriMin.get(0).getStateCode());
										JsonMusicMinObj.addProperty("Eventi", statsGeneriMin.get(0).getMusic());
							JsonArray JsonSport = new JsonArray();
							JsonGenereObject.add("Sport", JsonSport);
								JsonObject JsonSportObj= new JsonObject();
								JsonSport.add(JsonSportObj);
									JsonArray JsonSportMax =new JsonArray();
									JsonSportObj.add("Stato con più eventi Sport", JsonSportMax);
										JsonObject JsonSportMaxObj= new JsonObject();
										JsonSportMax.add(JsonSportMaxObj);
										JsonSportMaxObj.addProperty("Nome", statsGeneriMax.get(1).getStateCode());
										JsonSportMaxObj.addProperty("Eventi", statsGeneriMax.get(1).getSport());
									JsonArray JsonSportMin =new JsonArray();
									JsonSportObj.add("Stato con meno eventi Sport", JsonSportMin);
										JsonObject JsonSportMinObj= new JsonObject();
										JsonSportMin.add(JsonSportMinObj);
										JsonSportMinObj.addProperty("Nome", statsGeneriMin.get(1).getStateCode());
										JsonSportMinObj.addProperty("Eventi", statsGeneriMin.get(1).getSport());
							JsonArray JsonArt = new JsonArray();
							JsonGenereObject.add("Arte e Teatro", JsonArt);
								JsonObject JsonArtObj= new JsonObject();
								JsonArt.add(JsonArtObj);
									JsonArray JsonArtMax =new JsonArray();
									JsonArtObj.add("Stato con più eventi Arte e Teatro", JsonArtMax);
										JsonObject JsonArtMaxObj= new JsonObject();
										JsonArtMax.add(JsonArtMaxObj);
										JsonArtMaxObj.addProperty("Nome", statsGeneriMax.get(2).getStateCode());
										JsonArtMaxObj.addProperty("Eventi", statsGeneriMax.get(2).getArt());
									JsonArray JsonArtMin =new JsonArray();
									JsonArtObj.add("Stato con meno eventi Arte e Teatro", JsonArtMin);
										JsonObject JsonArtMinObj= new JsonObject();
										JsonArtMin.add(JsonArtMinObj);
										JsonArtMinObj.addProperty("Nome", statsGeneriMin.get(2).getStateCode());
										JsonArtMinObj.addProperty("Eventi", statsGeneriMin.get(2).getArt());
							JsonArray JsonMix = new JsonArray();
							JsonGenereObject.add("Misto", JsonMix);
								JsonObject JsonMixObj= new JsonObject();
								JsonMix.add(JsonMixObj);
									JsonArray JsonMixMax =new JsonArray();
									JsonMixObj.add("Stato con più eventi Misti", JsonMixMax);
										JsonObject JsonMixMaxObj= new JsonObject();
										JsonMixMax.add(JsonMixMaxObj);
										JsonMixMaxObj.addProperty("Nome", statsGeneriMax.get(3).getStateCode());
										JsonMixMaxObj.addProperty("Eventi", statsGeneriMax.get(3).getMix());
									JsonArray JsonMixMin =new JsonArray();
									JsonMixObj.add("Stato con meno eventi Misti", JsonMixMin);
										JsonObject JsonMixMinObj= new JsonObject();
										JsonMixMin.add(JsonMixMinObj);
										JsonMixMinObj.addProperty("Nome", statsGeneriMin.get(3).getStateCode());
										JsonMixMinObj.addProperty("Eventi", statsGeneriMin.get(3).getMix());
			
		
		}catch (JsonSyntaxException e) {
			Log.report("FILE sigle.json NON TROVATO", e.getMessage());
		} catch (IOException e) {
			Log.report("ERRORE LETTURA FILE", e.getMessage());
		}
		return JsonFinale;
	}
	
}
