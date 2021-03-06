package it.univpm.ProgettoOOP.filter;

import java.util.ArrayList;

import it.univpm.ProgettoOOP.model.Evento;

/**
 * 
 * @author Vecchiola Gregorio
 * @author Rongoni Alessandro
 *
 *	Filtro dei generi
 */
public class Genere extends Filtra {
	public Genere() {	
	}
	@Override
	public ArrayList<Evento> filtra(String genere, ArrayList<Evento> eve ) {
		ArrayList<Evento> eventiFiltrati = new ArrayList<Evento>();
		ArrayList<String> genereArray = new ArrayList<String>();
		genereArray= getString(genere);
		for(String s: genereArray) 
			for(Evento e:eve)
				if(e.getGenere().equals(s))
					eventiFiltrati.add(e);
		return eventiFiltrati;
	}
}
