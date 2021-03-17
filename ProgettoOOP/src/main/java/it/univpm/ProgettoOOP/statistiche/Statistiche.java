package it.univpm.ProgettoOOP.statistiche;

import java.util.ArrayList;

import it.univpm.ProgettoOOP.model.Evento;
import it.univpm.ProgettoOOP.model.Stato;

public class Statistiche {
	public Statistiche() {	
	}
	public Stato getStatsStato(ArrayList<Evento> eventi,String stato) {
		Stato stat= new Stato();
		int[] generi= {0,0,0,0};
		int[] source= {0,0,0,0};
		for(Evento e: eventi)
			if(e.getStato()==stato) {
				stat.setEventi_Totali(stat.getEventi_Totali()+1);
				switch(e.getGenere()) {
					case "Music": generi[0]++; break;
					case "Sport": generi[1]++; break;
					case "Arts & Theatre": generi[2]++; break;
					case "Miscellaneous": generi[3]++; break;
				}

				for(String sourceEvento: e.getSourceName()) {
					switch(sourceEvento) {
						case "Ticketmaster": source[0]++; break;
						case "Universe": source[1]++; break;
						case "Frontgate Tickets": source[2]++; break;
						case "Ticketmaster Resale": source[3]++; break;
					}
				}
			}
		stat.setGeneri(generi);
		stat.setSource(source);
		return stat;
	}
	
	
	/*public ArrayList<Stato> getMinSource(ArrayList<Stato> min, Stato stato) {
		if (min.get(0).getTicketmaster()>stato.getTicketmaster()) 
			min.set(0, stato);
		if (min.get(1).getUniverse()>stato.getUniverse()) 
			min.set(1, stato);
		if (min.get(2).getTmr()>stato.getTmr()) 
			min.set(2, stato);
		if (min.get(3).getFrontgate()>stato.getFrontgate()) 
			min.set(3, stato);
		return min;
	}
	public ArrayList<Stato> getMaxSource(ArrayList<Stato> max, Stato stato) {
		if (max.get(0).getTicketmaster()<stato.getTicketmaster()) 
			 max.set(0, stato);
		if (max.get(1).getUniverse()<stato.getUniverse()) 
			 max.set(1, stato);
		if (max.get(2).getTmr()<stato.getTmr()) 
			 max.set(2, stato);
		if (max.get(3).getFrontgate()<stato.getFrontgate()) 
			 max.set(3, stato);
		return max;
	}
	public ArrayList<Stato> getMaxGenere(ArrayList<Stato> max, Stato stato) {
		if (max.get(0).getMusic()<stato.getMusic()) 
			 max.set(0, stato);
		if (max.get(1).getSport()<stato.getSport()) 
			 max.set(1, stato);
		if (max.get(2).getArt()<stato.getArt()) 
			 max.set(2, stato);
		if (max.get(3).getMix()<stato.getMix()) 
			 max.set(3, stato);
		return max;
	}
	public ArrayList<Stato> getMinGenere(ArrayList<Stato> min, Stato stato) {
		if (min.get(0).getMusic()>stato.getMusic()) 
			min.set(0, stato);
		if (min.get(1).getSport()>stato.getSport()) 
			min.set(1, stato);
		if (min.get(2).getArt()>stato.getArt()) 
			min.set(2, stato);
		if (min.get(3).getMix()>stato.getMix()) 
			min.set(3, stato);
		return min;
	}*/
}
