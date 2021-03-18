package it.univpm.ProgettoOOP.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Classe che gestisce il log per il salvataggio degli errori
 * @author Rongoni Alessandro
 *
 */
public class Log {
	
	public static String nome = "log.txt";
	
	/**
	 * Metodo per la scrittura del log
	 * 
	 * @param customError Stringa per il tipo di errore
	 * @param detail Stringa per l'aggiunta di dettagli riguardanti l'errore
	 */
	public static void report(String customError, String detail) {
		
		String error = "\nErrore avvenuto in data :" + new Date() +
			            "\n     Causa: " + customError +
			            "\n     Dettagli: " + detail + "\n";		
		
		try {		
			
			BufferedWriter buf = new BufferedWriter(new FileWriter(new File(nome),true));
			buf.write(error);
			buf.close();	
			
		} catch (IOException e) {
			System.out.println("ERRORE IN FASE DI SCRITTURA NEL FILE LOG");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}