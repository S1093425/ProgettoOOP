package it.univpm.ProgettoOOP.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class Log {
	
	public static String nome = "log.txt";
	
	
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
