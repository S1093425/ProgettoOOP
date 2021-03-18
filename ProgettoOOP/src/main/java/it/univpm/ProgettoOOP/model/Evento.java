package it.univpm.ProgettoOOP.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import it.univpm.ProgettoOOP.log.Log;

/**
 * 
 * @author Rongoni Alessandro
 *
 * Classe per il salvataggio dei dati di ciascun evento
 */
public class Evento {
	
	/**
	 * Nome dell'evento
	 */
	private String Name;
	/**
	 * Genere dell'evento
	 */
	private String Genere;
	/*
	 * Data inizio evento
	 */
	private Date DataInizio;
	/**
	 * Stato dove si svolgerà l'evento
	 */
	private String Stato;
	/**
	 * Lista che contiene le source dell'evento
	 */
	private ArrayList<String> SourceName= new ArrayList<String>();
	
	/**
	 * Costruttore per creare l'oggetto evento
	 */
	public Evento() {
	}
	
	/**
	 * COstruttore per creare l'oggetto evento usato per i test
	 * @param name
	 * @param genere
	 * @param datainizio
	 * @param stato
	 * @param sourcename
	 */
	public Evento(String name, String genere, Date datainizio, String stato, ArrayList<String> sourcename) {
	Name=name;
	Genere=genere;
	DataInizio=datainizio;
	Stato=stato;
	SourceName=sourcename;
	
	}
	/**
	 * Metodo getter per ritornare lo stato
	 * @return stato contenente lo stato
	 */
	public String getStato() {
		return Stato;
	}

	/**
	 * Metodo setter per memorizzare uno stato
	 * @param stato nome del nuovo stato
	 */
	public void setStato(String stato) {
		Stato = stato;
	}
	/**
	 * Metodo getter per ritornare il nome dell'evento
	 * @return Name contenente il nome dell'evento
	 */
	public String getName() {
		return Name;
	}
	/**
	 * Metodo setter per memorizzare un nuovo nome dell'evento
	 * @param name nome del nuovo evento
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * Metodo getter per ritornare il genere dell'evento
	 * @return Genere contenente il genere dell'evento
	 */
	public String getGenere() {
		return Genere;
	}
	/**
	 * Metodo setter per memorizzare un nuovo genere
	 * @param genere nuovo genere 
	 */
	public void setGenere(String genere) {
		Genere = genere;
	}
	/**
	 * Metodo getter per ritornare la data d'inizio dell'evento
	 * @return Data d'inizio dell'evento
	 */
	public Date getDataInizio() {
		return DataInizio;
	}
	/**
	 * Metodo setter per memorizzare una nuova data d'inizio secondo la giusta formattazione
	 * @param dataInizio nuova data d'inizio
	 */
	public void setDataInizio(String dataInizio)  {
		SimpleDateFormat strFormat1= new SimpleDateFormat("yyyy-MM-dd");
		try {
			DataInizio= strFormat1.parse(dataInizio);
		} catch (ParseException e) {
			Log.report("FORMATTZIONE DATA ERRATA NEL JSON. CORRETTA: [yyyy-mm-dd]",e.getMessage());
		}		
	}
	/**
	 * Metodo getter per ritornare la source dell'evento
	 * @return Tipo Source dell'evento
	 */
	public ArrayList<String> getSourceName() {
		return SourceName;
	}
	/**
	 * Metodo setter per memorizzare una nuova source
	 * @param sourceName contenente il nuovo tipo di source
	 */	
	public void setSourceName(ArrayList<String> sourceName) {
		SourceName = sourceName;
	}
}