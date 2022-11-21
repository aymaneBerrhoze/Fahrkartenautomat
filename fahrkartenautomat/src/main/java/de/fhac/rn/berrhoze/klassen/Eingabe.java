package de.fhac.rn.berrhoze.klassen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Eingabe {

	static List<String> Dateizeilen;
	
	public static List<String> DateiEinlesen() {
		
		try {
			Dateizeilen = Files.readAllLines(Paths.get("C:\\Users\\user\\ProgrammierpraktikumAMI\\fahrkartenautomat\\Eingabedatei"));
			if(Dateizeilen.size()<5) {
				System.out.println("Fehler! Zu wenig Zeilen");
				System.exit(0);
			}
		} catch (IOException e) {
			System.out.println("Fehler! Datei nicht vorhanden");
			System.exit(0);
		}
		return Dateizeilen;
	}
	
	public static String AnfangsbestandBerechnen(String anfangsbestand) {
		
		if(!anfangsbestand.matches("\\d{2};\\d{2};\\d{2};\\d{2};\\d{2}")) {
			System.out.println("Fehler! Anfangsbestand falsch angegeben");
			System.exit(0);
		}
		return "0;0;"+anfangsbestand;
	}
	
	
}
