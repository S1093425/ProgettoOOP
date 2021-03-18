package it.univpm.ProgettoOOP.exception;
import it.univpm.ProgettoOOP.log.*;
/**
 * 
 * @author Rongoni Alessandro
 * Eccezione per gestire le richieste sbagliate passate nel body
 */
@SuppressWarnings("serial")
public class IllegalRequest extends Exception{
	/**
	 * Costruttore dell'eccezione personalizzata
	 * @param a contiene l'azione passata dentro al body
	 */
	public IllegalRequest(String a) {
		super("Errore, consultare il file Log.txt");
		Log.report("LA SCELTA EFFETTUATA NON E'CONSENTITA", a + " NON E' UN'AZIONE CHE PUO' ESSERE ESEGUITA");
	}

}
