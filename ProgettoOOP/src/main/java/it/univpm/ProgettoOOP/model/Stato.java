package it.univpm.ProgettoOOP.model;

public class Stato {
		
		private String Stato;
		private int Eventi_Totali;
		private int[] Source=new int[4];
		private int[] Generi=new int[4];
		public Stato() {
			
		}
		public String getStato() {
			return Stato;
		}
		public void setStato(String stato) {
			Stato = stato;
		}
		public int getEventi_Totali() {
			return Eventi_Totali;
		}
		public void setEventi_Totali(int eventi_Totali) {
			Eventi_Totali = eventi_Totali;
		}
		public int[] getSource() {
			return Source;
		}
		public void setSource(int[] source) {
			Source = source;
		}
		public int[] getGeneri() {
			return Generi;
		}
		public void setGeneri(int[] generi) {
			Generi = generi;
		}	
}
