package it.univpm.ProgettoOOP.exception;
import it.univpm.ProgettoOOP.log.Log;

/**
 * 
 * @author Rongoni Alessandro
 * Eccezione per errori sul filtraggio degli eventi
 */
@SuppressWarnings("serial")
public class EventException extends Exception {
	/**
	 * Costruttore dell'eccezione personalizzata 
	 */
	public EventException() {
			super("Errore, consultare il file Log.txt");
			Log.report("NON SONO DISPONIBILI EVENTI CON I FILTRI SPECIFICATI", getLocalizedMessage());
		}
	}

