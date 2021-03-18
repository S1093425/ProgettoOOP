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
			System.out.println(eve.size());
			for(Evento e:eve) {
				int i=0; int flag=0;
				while((i<e.getSourceName().size()) && (flag==0)) {
					if(s.equals(e.getSourceName().get(i))) {
						eventiFiltrati.add(e);
						flag=1;
					}
					
					i++;
				}
			}
		}
		return eventiFiltrati;
	}
}
