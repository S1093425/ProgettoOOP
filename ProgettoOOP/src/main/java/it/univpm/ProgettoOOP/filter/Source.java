package it.univpm.ProgettoOOP.filter;

import java.util.ArrayList;

import it.univpm.ProgettoOOP.model.Evento;

public class Source extends Filtra {
	public Source() {	
	}
	@Override
	public ArrayList<Evento> filtra(String source, ArrayList<Evento> eve ) {
		ArrayList<Evento> eventiFiltrati = new ArrayList<Evento>();
		ArrayList<String> sourceArray = new ArrayList<String>();
		sourceArray= getString(source);
		for(String s: sourceArray) {
			int index= -1;
			switch (s) {
				case "Ticketweb": index=0; break;
				case "Univerese": index=1; break;
				case "Frontgate Tickets": index=2; break;
				case "Ticketmaster Resale": index=3; break;
			}
				
		}
		return eventiFiltrati;
	}
}
