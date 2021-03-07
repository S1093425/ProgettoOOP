package it.univpm.ProgettoOOP.model;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Evento {

	private String Name;
	private String Genere;
	private Date DataInizio;
	private String Stato;

	public Evento() {
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

	public void setDataInizio(String dataInizio) throws Exception {
		//DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");
		SimpleDateFormat strFormat1= new SimpleDateFormat("yyyy-MM-dd");
		DataInizio= strFormat1.parse(dataInizio);
		
	}
}
