package it.univpm.ProgettoOOP.exception;
import it.univpm.ProgettoOOP.log.*;

/**
 * 
 * @author Rongoni Alessandro
 *	Eccezione per gestire gli errori dovuti alla ricerca di uno stato sbagliato
 */
@SuppressWarnings("serial")
public class StateNotFound extends Exception {
	/**
	 * Costruttore dell'eccezione personalizzata
	 * @param stato string contenente il nome dello stato passato in cerca
	 */
	public StateNotFound(String stato){
		super("Errore, consultare il file Log.txt");
		Log.report("LO STATO "+ stato +" NON E' STATO TROVATO", "Inserire uno stato dell'USA presente in lista o controllare la formattazione");
	}

}
