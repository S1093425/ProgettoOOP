package it.univpm.ProgettoOOP.exception;
import it.univpm.ProgettoOOP.log.*;

@SuppressWarnings("serial")
public class MalformedException extends Exception {
	
	
	public MalformedException(String a){
		super("Errore, consultare il file Log.txt");
		Log.report("JSON NON FORMATTATO CORRETTAMENTE","FILTRO " + a + " ERRATO");
	}
}
