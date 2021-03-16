package it.univpm.ProgettoOOP.exception;
import it.univpm.ProgettoOOP.log.*;

@SuppressWarnings("serial")
public class IllegalRequest extends Exception{
	public IllegalRequest(String a) {
		super();
		Log.report("LA SCELTA EFFETTUATA NON E'CONSENTITA", a + " NON E' UN'AZIONE CHE PUO' ESSERE ESEGUITA");
	}

}
