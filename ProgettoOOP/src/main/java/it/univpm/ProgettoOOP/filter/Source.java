package it.univpm.ProgettoOOP.filter;

public class Source implements Filtra{
	String param;
	
	public Source(String param) {
		super();
		this.param = param;
		
	}
	@Override
	public String filtra(String link) {
		String filter = "&source=";
		return link+filter+param;
	}
}
