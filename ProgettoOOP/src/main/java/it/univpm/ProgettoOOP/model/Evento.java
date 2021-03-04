package it.univpm.ProgettoOOP.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class Evento {

	private String Name;
	private String Genere;
	private Date DataInizio;
	private String Sito;
	private String Stato;

	public Evento() {
	}

	public Evento(String Name, String Genere, Date DataInizio, String Sito, String Stato) {

		this.Name = Name;
		this.Genere = Genere;
		this.DataInizio = DataInizio;
		this.Sito = Sito;
		this.Stato = Stato;
	}

	public String getStateCode() {
		return Stato;
	}

	public void setStateCode(String stato) {
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

	public void setDataInizio(String dataInizio) {
		//DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z");
		DataInizio = null;
	}

	public String getSito() {
		return Sito;
	}

	public void setSito(String sito) {
		Sito = sito;
	}
}
