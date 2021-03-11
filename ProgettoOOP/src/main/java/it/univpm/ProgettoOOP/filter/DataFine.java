package it.univpm.ProgettoOOP.filter;

public class DataFine implements Filtra{
	String param;
	
	public DataFine(String param) {
		super();
		this.param = param;
		
	}
	@Override
	public String filtra(String link) {
		String filter = "&endDateTime=";
		return link+filter+param+"T00:00:00Z";
	}
}
