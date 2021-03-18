package it.univpm.ProgettoOOP.filter;

import java.util.ArrayList;

import it.univpm.ProgettoOOP.model.Evento;

/**
 * 
 * @author Rongoni Alessandro
 * @author Vecchiola Gregorio
 *
 *
 * Filtro per stato
 */
public class State extends Filtra {
	/**
	 * 
	 */
	public State() {	
	}
	@Override
	public ArrayList<Evento> filtra(String stati, ArrayList<Evento> eve ) {
		ArrayList<Evento> eventiFiltrati = new ArrayList<Evento>();
		ArrayList<String> statiArray = new ArrayList<String>();
		statiArray= getString(stati);
		for(String s: statiArray)
			for(Evento e:eve)
				if(e.getStato().equals(s))
					eventiFiltrati.add(e);		
		return eventiFiltrati;
	}
}
