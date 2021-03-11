package it.univpm.ProgettoOOP.filter;

public class State implements Filtra{
	String param;
	
	public State(String param) {
		super();
		this.param = param;
	
	}
	@Override
	public String filtra(String link) {
		String filter = "&stateCode=";
		return link+filter+param;
	}
}
