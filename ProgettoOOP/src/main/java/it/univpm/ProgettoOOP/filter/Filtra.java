package it.univpm.ProgettoOOP.filter;

import java.util.ArrayList;

import it.univpm.ProgettoOOP.model.Evento;

public abstract class Filtra {
	
	public abstract ArrayList<Evento>  filtra(String s,ArrayList<Evento> e);
	
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
