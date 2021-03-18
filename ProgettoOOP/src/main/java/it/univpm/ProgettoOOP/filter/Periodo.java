package it.univpm.ProgettoOOP.filter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import it.univpm.ProgettoOOP.log.Log;

import it.univpm.ProgettoOOP.model.Evento;

/**
 * @author Rongoni Alessandro
 * @author Vecchiola Gregorio
 *
 */
public class Periodo extends Filtra {
	
	/**
	 * Filtro per date, standard o personalizzate dall'utente
	 */
	public Periodo() {	
	}
	
	@Override
	public ArrayList<Evento> filtra(String periodo, ArrayList<Evento> eve ) {
		ArrayList<Evento> eventiFiltrati = new ArrayList<Evento>();
		LocalDate l = LocalDate.now().minusDays(1);
		Date inizio = Date.from(l.atStartOfDay(ZoneId.systemDefault()).toInstant());	 
		Date fine = new Date();	
		switch (periodo) {			
			case "Giornalieri":     			
				l = LocalDate.now().plusDays(1);
				fine = Date.from(l.atStartOfDay(ZoneId.systemDefault()).toInstant());
				break;				
			case "Settimanali":
				l = LocalDate.now().plusDays(7);
				fine = Date.from(l.atStartOfDay(ZoneId.systemDefault()).toInstant());
				break;				
			case "Mensili":
				l = LocalDate.now().plusDays(31);
				fine = Date.from(l.atStartOfDay(ZoneId.systemDefault()).toInstant());
				break;			
			case "Semestrale":
				l = LocalDate.now().plusDays(186);
				fine = Date.from(l.atStartOfDay(ZoneId.systemDefault()).toInstant());
				break;				
			case "Annuali":
				l = LocalDate.now().plusDays(365);
				fine = Date.from(l.atStartOfDay(ZoneId.systemDefault()).toInstant());
				break;			
			default:			
				ArrayList<String> pers= getString(periodo);
				SimpleDateFormat strFormat1= new SimpleDateFormat("yyyy-MM-dd");
				try {	
					inizio = strFormat1.parse(pers.get(0));
					fine = strFormat1.parse(pers.get(1));
				} catch (ParseException e) {
					Log.report("FORMATTAZIONE DATA ERRATO, CORRETTA: [yyyy-mm-dd]",e.getMessage());
				}			
			}
		for (Evento e: eve) {
			if((e.getDataInizio().after(inizio)) && (e.getDataInizio().before(fine)))
				eventiFiltrati.add(e);
		}
		return eventiFiltrati;
	}
}
