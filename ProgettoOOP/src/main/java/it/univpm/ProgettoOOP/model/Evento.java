package it.univpm.ProgettoOOP.model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Evento {

	private String Name;
	private String Genere;
	private Date DataInizio;
	private String Stato;
	private int[] SourceValue=new int[4];
	public Evento() {
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

	public void setDataInizio(String dataInizio) throws Exception {
		//DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");
		SimpleDateFormat strFormat1= new SimpleDateFormat("yyyy-MM-dd");
		DataInizio= strFormat1.parse(dataInizio);
		
	}

	public int[] getSourceValue() {
		return SourceValue;
	}

	public void setSourceValue(int[] sourceValue) {
		SourceValue = sourceValue;
	}
}
