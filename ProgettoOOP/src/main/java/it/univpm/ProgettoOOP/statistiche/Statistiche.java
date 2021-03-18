package it.univpm.ProgettoOOP.statistiche;

import java.util.ArrayList;

import it.univpm.ProgettoOOP.model.Evento;
import it.univpm.ProgettoOOP.model.Global;
import it.univpm.ProgettoOOP.model.MaxMin;
import it.univpm.ProgettoOOP.model.Stato;

public class Statistiche {
	
	public Statistiche() {	
	}
	
	public Stato getStatsStato(ArrayList<Evento> eventi,String stato) {
		Stato stat= new Stato();
		int[] generi= {0,0,0,0};
		int[] source= {0,0,0,0};
		for(Evento e: eventi) {
			if(e.getStato().equals(stato)) {
				stat.setStato(stato);
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
		}
		stat.setGeneri(generi);
		stat.setSource(source);
		return stat;
	}
	
	public Global getStatsGlobal(ArrayList<Stato> stati) {
		Global statisticheGlobali= new Global();
		for(int i=0;i<stati.size();i++) {
			 if(i==0) {
				 MaxMin maxmin=new MaxMin(stati.get(i).getStato(),stati.get(i).getEventi_Totali(),stati.get(i).getStato(),stati.get(i).getEventi_Totali());
				 statisticheGlobali.setTot(maxmin);
				 MaxMin[] maxmins= new MaxMin[4]; MaxMin[] maxming= new MaxMin[4];
				for(int j=0;j<4;j++) {
					maxmins[j]=new MaxMin(stati.get(i).getStato(),stati.get(i).getSource()[j],stati.get(i).getStato(),stati.get(i).getSource()[j]);
					maxming[j]=new MaxMin(stati.get(i).getStato(),stati.get(i).getGeneri()[j],stati.get(i).getStato(),stati.get(i).getGeneri()[j]);
				}
				statisticheGlobali.setGeneri(maxming); statisticheGlobali.setSource(maxmins);
			 }else {
				 statisticheGlobali.setTot(getEventiTotStats(statisticheGlobali.getTot(),stati.get(i)));
				 statisticheGlobali.setSource(getSourceStats(statisticheGlobali.getSource(),stati.get(i)));
				 statisticheGlobali.setGeneri(getGeneriStats(statisticheGlobali.getGeneri(),stati.get(i)));
			 }
		}
		return statisticheGlobali;
	}
	
	public MaxMin getEventiTotStats(MaxMin Stats,Stato s) {
		if(Stats.getMax()<s.getEventi_Totali()) {
			Stats.setMax(s.getEventi_Totali());
			Stats.setMaxS(s.getStato());
		}
		if(Stats.getMin()>s.getEventi_Totali()) {
			Stats.setMin(s.getEventi_Totali());
			Stats.setMinS(s.getStato());
		}
		return Stats;
	}
	
	public MaxMin[] getSourceStats(MaxMin[] Stats, Stato s) {
		for(int i=0;i<4;i++) {
			if(Stats[i].getMax()<s.getSource()[i]) {
				Stats[i].setMax(s.getSource()[i]);
				Stats[i].setMaxS(s.getStato());
			}
			if(Stats[i].getMin()>s.getSource()[i]) {
				Stats[i].setMin(s.getSource()[i]);
				Stats[i].setMinS(s.getStato());
			}
		}
		return Stats;
	}
	
	public MaxMin[] getGeneriStats(MaxMin[] Stats, Stato s) {
		for(int i=0;i<4;i++) {
			if(Stats[i].getMax()<s.getGeneri()[i]) {
				Stats[i].setMax(s.getGeneri()[i]);
				Stats[i].setMaxS(s.getStato());
			}
			if(Stats[i].getMin()>s.getGeneri()[i]) {
				Stats[i].setMin(s.getGeneri()[i]);
				Stats[i].setMinS(s.getStato());
			}
		}
		return Stats;
	}
}
