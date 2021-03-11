package it.univpm.ProgettoOOP.filter;

public class Genere implements Filtra {
	String param;

	public Genere(String param) {
		super();
		this.param = param;
		
	}
	@Override
	public String filtra(String link) {
		String filter = "&classificationName=";
		return link+filter+param;
	}
}
