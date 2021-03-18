package it.univpm.ProgettoOOP.model;

/**
 * 
 * @author Vecchiola Gregorio
 * @author Rongoni Alessandro
 * 
 * Classe che gestisce le statistiche globali degli stati
 * 
 */
public class Global {
	/**
	 * Massimo e minimo degli eventi totali
	 */
	private MaxMin tot;
	/**
	 * Massimo e minimo degli eventi totali divisi per source
	 */
	private MaxMin[] source= new MaxMin[4];
	/**
	 * Massimo e minimo degli eventi divisi per genere
	 */
	private MaxMin[] generi= new MaxMin[4];
	
	/**
	 * Costruttore dell'oggetto global
	 * 
	 */
	public Global() {
		
	}
	/**
	 * Metodo getter per ritornare il massimo e minimo degli eventi totali
	 * @return massimo e minimo degli eventi totali
	 */
	public MaxMin getTot() {
		return tot;
	}
	/**
	 * Metodo setter per memorizzare il massimo e minimo degli eventi totali
	 * @param tot
	 */
	public void setTot(MaxMin tot) {
		this.tot = tot;
	}
	/**
	 * Metodo getter per ritornare il massimo e minimo degli eventi totali divisi per source
	 * @return massimo e minimo degli eventi totali divisi per source
	 */
	public MaxMin[] getSource() {
		return source;
	}
	/**
	 * Metodo setter per memorizzare il massimo e minimo degli eventi totali divisi per source
	 * @param source
	 */
	public void setSource(MaxMin[] source) {
		this.source = source;
	}
	/**
	 * Metodo getter per ritornare il massimo e minimo degli eventi totali divisi per genere
	 * @return massimo e minimo degli eventi totali divisi per genere
	 */
	public MaxMin[] getGeneri() {
		return generi;
	}
	/**
	 * Metodo setter per memorizzare il massimo e minimo degli eventi totali divisi per genere
	 * @param generi
	 */
	public void setGeneri(MaxMin[] generi) {
		this.generi = generi;
	}
	
	
}
