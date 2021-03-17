package it.univpm.ProgettoOOP.filter;

import java.util.ArrayList;

import it.univpm.ProgettoOOP.model.Evento;

public abstract class Filtra {
	
	public abstract ArrayList<Evento>  filtra(String s,ArrayList<Evento> e);
	
	public ArrayList<String> getString(String s){
		ArrayList<String> stringhe = new ArrayList<String>();
		int startIndex = 0; boolean flag=false;
		for(int i=0;i<s.length();i++)
			if(s.charAt(i)==',') {
				stringhe.add(s.substring(startIndex, i-1));
				startIndex= i+1;
				flag=true;
			}
		if(flag==false)
			stringhe.add(s);
		return stringhe;
	}	
}
