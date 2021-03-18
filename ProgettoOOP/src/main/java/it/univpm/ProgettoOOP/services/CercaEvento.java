package it.univpm.ProgettoOOP.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import it.univpm.ProgettoOOP.log.Log;

/**
 * 
 * @author Rongoni Alessandro
 * Classe per fare le call all'API di TicketMaster, dove, dato un URL ritorna gli eventi 
 * sotto forma di stringa 
 */
public class CercaEvento {
		
		/**
		 * metodo che dato un URL personalizzato, richiede gli eventi di uno specifico satto  all'API di ticketMaster
		 * @param url contenente la richiesta di uno specifico stato
		 * @return stringa contenente tutti gli eventi di quello stato
		 */ 
		public static String getEvento(String url) {

		String evento_stato = "";

		try {
			URLConnection openConnection = (URLConnection) new URL(url).openConnection();
			Scanner in = new Scanner(new BufferedReader(new InputStreamReader(openConnection.getInputStream())));
			evento_stato += in.nextLine();
			in.close();
			
		} catch (IOException e) {
			Log.report("ERRORE DURANTE LA RICHIESTA EVENTO",e.getMessage());
		} catch (Exception e) {
			Log.report("ERRORE GENERICO NELLA CLASSE \"CercaEvento\"",e.getMessage());
		}		
		return evento_stato;
}}
