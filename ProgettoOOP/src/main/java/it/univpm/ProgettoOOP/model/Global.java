package it.univpm.ProgettoOOP.model;

public class Global {
	
	private MaxMin tot;
	private MaxMin[] source= new MaxMin[4];
	private MaxMin[] generi= new MaxMin[4];
	public Global() {
		
	}
	public MaxMin getTot() {
		return tot;
	}
	public void setTot(MaxMin tot) {
		this.tot = tot;
	}
	public MaxMin[] getSource() {
		return source;
	}
	public void setSource(MaxMin[] source) {
		this.source = source;
	}
	public MaxMin[] getGeneri() {
		return generi;
	}
	public void setGeneri(MaxMin[] generi) {
		this.generi = generi;
	}
	
	
}
