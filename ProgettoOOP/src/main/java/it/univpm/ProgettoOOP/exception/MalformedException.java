package it.univpm.ProgettoOOP.exception;
import it.univpm.ProgettoOOP.log.*;
/**
 * 
 * @author Rongoni Alessandro
 *	Eccezione per la gestione di file Json formattati male
 */
@SuppressWarnings("serial")
public class MalformedException extends Exception {
	
	/**
	 * Costruttore dell'eccezione personalizzata
	 * @param a stringa contenente il filtro passato dentro al body
	 */
	public MalformedException(String a){
		super("Errore, consultare il file Log.txt");
		Log.report("JSON NON FORMATTATO CORRETTAMENTE","FILTRO " + a + " ERRATO");
	}
}
