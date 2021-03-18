package it.univpm.ProgettoOOP.filter;

import java.util.ArrayList;

import it.univpm.ProgettoOOP.model.Evento;
/**
 * @author Vecchiola Gregorio
 * @author Rongoni Alessandro
 * 
 * Classe astratta per l'implementazione dei filtri
 * 
 */
public abstract class Filtra {
	/**
	 * Metodo astratto filtra
	 * @param s stringa contenente il valore da filtrare
	 * @param e lista di eventi da filtrare
	 * @return lista eventi filtrati
	 */
	public abstract ArrayList<Evento>  filtra(String s,ArrayList<Evento> e);
	
	
	/**
	 * Metodo che divide in sottostringhe una stringa data. Serve per la chiamata di uno o più filtri.
	 * 
	 * @param s stringa da dividere
	 * @return lista di tutte le stringhe divise
	 */
	public ArrayList<String> getString(String s){
		ArrayList<String> stringhe = new ArrayList<String>();
		int startIndex = 0;
		for(int i=0;i<s.length();i++)
			if(s.charAt(i)==',') {
				stringhe.add(s.substring(startIndex, i));
				startIndex= i+1;
			}
		stringhe.add(s.substring(startIndex, s.length()));
		return stringhe;
	}	
}
