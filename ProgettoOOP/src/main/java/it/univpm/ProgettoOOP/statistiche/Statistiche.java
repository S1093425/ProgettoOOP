package it.univpm.ProgettoOOP.statistiche;

import java.util.ArrayList;

import it.univpm.ProgettoOOP.model.Stato;

public class Statistiche {
	public Statistiche() {
		
	}
	public ArrayList<Stato> getMinSource(ArrayList<Stato> min, Stato stato) {
		if (min.get(0).getTicketmaster()>stato.getTicketmaster()) 
			min.set(0, stato);
		if (min.get(1).getUniverse()>stato.getUniverse()) 
			min.set(1, stato);
		if (min.get(2).getTmr()>stato.getTmr()) 
			min.set(2, stato);
		if (min.get(3).getFrontgate()>stato.getFrontgate()) 
			min.set(3, stato);
		if (min.get(4).getTicketweb()>stato.getTicketweb()) 
			min.set(4, stato);
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
		if (max.get(4).getTicketweb()<stato.getTicketweb()) 
			 max.set(4, stato);
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
	}
}
