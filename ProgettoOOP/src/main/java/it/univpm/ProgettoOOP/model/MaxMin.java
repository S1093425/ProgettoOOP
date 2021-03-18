package it.univpm.ProgettoOOP.model;
/**
 * @author Vecchiola Gregorio
 * @author Rongoni ALessandro
 *
 *
 *	Classe che gestisce il massimo e minimo degli stati
 */
public class MaxMin {
	/**
	 * Nome dello stato massimo
	 */
	private String maxS;
	/**
	 * valore del massimo
	 */
	private int max;
	/**
	 * nome dello stato minimo
	 */
	private String minS;
	/**
	 *  valore del minimo
	 */
	private int min;
	/**
	 * Costruttore di default
	 */
	public MaxMin() {
	}
	/**
	 * Costruttore Dell'oggetto max e min
	 * @param maxS nome dello stato massimo
	 * @param max valore del massimo
	 * @param minS nome dello stato min
	 * @param min valore del minore
	 * 
	 */
	public MaxMin(String maxS, int max, String minS, int min) {
		this.maxS = maxS;
		this.max = max;
		this.minS = minS;
		this.min = min;
	}
	/**
	 * Metodo getter per ritronare il nome dello stato massimo
	 * @return nome dello stato massimo
	 */
	public String getMaxS() {
		return maxS;
	}
	/**
	 * Metodo setter per memorizzare lo stato massimo
	 * @param maxS nome dello stato massimo
	 */
	public void setMaxS(String maxS) {
		this.maxS = maxS;
	}
	/**
	 * Metodo getter per ottenere il valore massimo
	 * @return valore massimo
	 */
	public int getMax() {
		return max;
	}
	/**
	 * Metodo setter per memorizzare il valore massimo
	 * @param max valore massimo
	 */
	public void setMax(int max) {
		this.max = max;
	}
	public String getMinS() {
		return minS;
	}
	public void setMinS(String minS) {
		this.minS = minS;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
}
