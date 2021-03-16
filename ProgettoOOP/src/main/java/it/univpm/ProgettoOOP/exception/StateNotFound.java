package it.univpm.ProgettoOOP.exception;
import it.univpm.ProgettoOOP.log.*;


@SuppressWarnings("serial")
public class StateNotFound extends Exception {
	public StateNotFound(String stato){
		super();
		Log.report("LO STATO"+ stato +"NON E' STATO TROVATO", "Inserire uno stato dell'USA presente in lista o controllare la formattazione");
	}

}
