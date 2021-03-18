package it.univpm.ProgettoOOP.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import it.univpm.ProgettoOOP.log.Log;

public class Evento {

	private String Name;
	private String Genere;
	private Date DataInizio;
	private String Stato;
	private ArrayList<String> SourceName= new ArrayList<String>();
	public Evento() {
	}
	
	public Evento(String name, String genere, Date datainizio, String stato, ArrayList<String> sourcename) {
	Name=name;
	Genere=genere;
	DataInizio=datainizio;
	Stato=stato;
	SourceName=sourcename;
	
	}
	
	public String getStato() {
		return Stato;
	}

	public void setStato(String stato) {
		Stato = stato;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getGenere() {
		return Genere;
	}

	public void setGenere(String genere) {
		Genere = genere;
	}

	public Date getDataInizio() {
		return DataInizio;
	}

	public void setDataInizio(String dataInizio)  {
		SimpleDateFormat strFormat1= new SimpleDateFormat("yyyy-MM-dd");
		try {
			DataInizio= strFormat1.parse(dataInizio);
		} catch (ParseException e) {
			Log.report("FORMATTZIONE DATA ERRATA NEL JSON. CORRETTA: [yyyy-mm-dd]",e.getMessage());
		}		
	}
	public ArrayList<String> getSourceName() {
		return SourceName;
	}
		
	public void setSourceName(ArrayList<String> sourceName) {
		SourceName = sourceName;
	}
}