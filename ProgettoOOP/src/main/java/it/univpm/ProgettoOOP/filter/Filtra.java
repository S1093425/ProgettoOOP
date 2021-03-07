package it.univpm.ProgettoOOP.filter;
import it.univpm.ProgettoOOP.model.*;
public class Filtra {

	/*public static ArrayList<Evento> Filtra(JsonObject body,ArrayList<Evento>evento)throws MalformedException{
		 //Stringa che serve in caso di errore a poter stampare nel file log.txt su quale filtro e' il problema
		 String s = ""; 
		 try{
			 JsonObject jo = body.get("filtri").getAsJsonObject();
			 JsonObject jobject = jo.get("Stato/i").getAsJsonObject();
			 if(jobject.get("attivo").getAsBoolean()) {
				 s = "tempo";
				 Tempo t = new Tempo();
				 citta = t.filtra(citta, jobject.get("filtro").getAsString());
				 citta.get(0).setNome(jobject.get("filtro").getAsString());// Serve a far si che ci sia un nome appropiato quando si stampano i risulotati		 
			}	 
			 jobject = jo.get("nome").getAsJsonObject();
			 if(jobject.get("attivo").getAsBoolean()) {
				 s = "nome";
				 NomeId n = new NomeId();
				 citta = n.filtra(citta, jobject.get("filtro").getAsString());
				 return citta; //Se viene effettuato il filtraggio per nome non puÃ² essere effettuato anche il filtraggio per ZoneGeo
				 }
			 jobject = jo.get("ZoneGeografiche").getAsJsonObject();
			 if(jobject.get("attivo").getAsBoolean()) {
				 s = "ZoneGeografiche";
				 ZoneGeografiche z = new ZoneGeografiche();
				 citta = z.filtra(citta, jobject.get("filtro").getAsString());
				 citta.get(0).setNome(jobject.get("filtro").getAsString()); // Serve a far si che ci sia un nome appropiato quando si stampano i risulotati
				 }
			 }catch(NullPointerException e) {
				 throw new MalformedException(s);
			 }
		 return citta; 
		} */
}
