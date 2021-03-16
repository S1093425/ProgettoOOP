package it.univpm.ProgettoOOP.exception;
import it.univpm.ProgettoOOP.log.Log;

@SuppressWarnings("serial")
public class EventException extends Exception {
	
	public EventException() {
			super("Errore, consultare il file Log.txt");
			Log.report("NON SONO DISPONIBILI EVENTI CON I FILTRI SPECIFICATI", getLocalizedMessage());
		}

	}

