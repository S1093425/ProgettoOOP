package it.univpm.ProgettoOOP.model;

import java.sql.Date;

public class Evento {
	 
	 
	 private String Name;
	 private String Genere;
	 private Date DataInizio;
	 private String Sito;
	 private String StateCode;
	 
	 public Evento() {
	 }
	 public Evento( String Name, String Genere, Date DataInizio, String Sito, String StateCode) {
		 
		 this.Name=Name;
		 this.Genere=Genere;
		 this.DataInizio=DataInizio;
		 this.Sito=Sito;
		 this.StateCode=StateCode;
	 }

	public String getStateCode() {
		return StateCode;
	}

	public void setStateCode(String stateCode) {
		StateCode = stateCode;
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

	public void setDataInizio(String dataInizio) {  
		DataInizio = null;
	}


	public String getSito() {
		return Sito;
	}

	public void setSito(String sito) {
		Sito = sito;
	}
}
