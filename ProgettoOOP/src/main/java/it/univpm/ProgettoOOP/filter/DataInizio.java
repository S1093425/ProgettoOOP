package it.univpm.ProgettoOOP.filter;

public class DataInizio implements Filtra{
	String param;
	
	public DataInizio(String param) {
		super();
		this.param = param;
		
	}
	@Override
	public String filtra(String link) {
		String filter =  "&startDateTime=";
		return link+filter+param+"T00:00:00Z";
	}
}
