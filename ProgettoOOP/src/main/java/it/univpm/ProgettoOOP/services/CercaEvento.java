package it.univpm.ProgettoOOP.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import it.univpm.ProgettoOOP.log.Log;

public class CercaEvento {

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
