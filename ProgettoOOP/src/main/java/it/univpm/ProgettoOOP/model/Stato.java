package it.univpm.ProgettoOOP.model;

/**
 * 
 * @author Vecchiola Gregorio
 *
 * Classe per il salvataggio delle statistiche per ciascuno stato
 */
public class Stato {
		/**
		 * Nome dello stato
		 */
		private String Stato;
		/**
		 * variabile int contenente il numero totale di eventi dello stato
		 */
		private int Eventi_Totali;
		/**
		 * array contenente il numero degli eventi divisi per source
		 */
		private int[] Source=new int[4];
		/**
		 * array contenente il numero degli eventi divisi per genere
		 */
		private int[] Generi=new int[4];
		
		/**
		 * Costruttore dell'oggetto stato 
		 */
		public Stato() {
			
		}
		/**
		 * Metodo getter per ottenere il nome dello stato
		 * @return nome dello statp
		 */
		public String getStato() {
			return Stato;
		}
		/**
		 * Metodo setter per memorizzare un nuovo stato
		 * @param stato contenente il nome dello stato
		 */
		public void setStato(String stato) {
			Stato = stato;
		}
		/**
		 * Metodo getter per ottenere il numero totale degli eventi di uno stato
		 * @return numero totale di eventi
		 */
		public int getEventi_Totali() {
			return Eventi_Totali;
		}
		/**
		 * Metodo setter per memorizzare un nuovo numero totale di eventi
		 * @param eventi_Totali contenente il numero totale di eventi
		 */
		public void setEventi_Totali(int eventi_Totali) {
			Eventi_Totali = eventi_Totali;
		}
		/**
		 * Metodo getter per ottenere il numero di eventi  divisi per source
		 * @return numero di eventi divisi per source
		 */
		public int[] getSource() {
			return Source;
		}
		/**
		 * metodo setter per memorizzare il numero di eventi divisi per source 
		 * @param source contenente il numero totale di eventi divisi per source  
		 */
		public void setSource(int[] source) {
			Source = source;
		}
		/**
		 * Metodo getter per ottenere il numero totale di eventi  divisi per genere 
		 * @return numero di eventi divisi per genere
		 */
		public int[] getGeneri() {
			return Generi;
		}
		/**
		 * metodo setter per memorizzare il numero di eventi totali divisi per genere
		 * @param generi contenente il numero totale di eventi divisi per genere 
		 */
		public void setGeneri(int[] generi) {
			Generi = generi;
		}	
}
