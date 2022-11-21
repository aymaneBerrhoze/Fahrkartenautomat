package de.fhac.rn.berrhoze.klassen;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Buchung {
	
	static Eingabe e ;	
	static List<String> Dateizeilen = e.DateiEinlesen();
	static ArrayList<Buchung> buchungen;
	
	private int idBuchung ;
	private double Preis;
	private String Rueckgabe;
	private String Gesamtbestand;
	
	static Automat a= new Automat();
	
	public Buchung() {
		super();
	}

	public Buchung(int idBuchung, double preis, String rueckgabe, String gesamtbestand) {
		super();
		this.idBuchung = idBuchung;
		Preis = preis;
		Rueckgabe = rueckgabe;
		Gesamtbestand = gesamtbestand;
	}

	public int getIdBuchung() {
		return idBuchung;
	}

	public void setIdBuchung(int idBuchung) {
		this.idBuchung = idBuchung;
	}

	public double getPreis() {
		if(Preis<0.90 || Preis>19.90 ) {
			System.out.println("Fehler! Der Preis ist nicht zwischen 0.90 und 19.90");
			System.exit(0);
		}
		return Preis;
	}

	public void setPreis(double preis) {
		Preis = preis;
	}

	public String getRueckgabe() {
		return Rueckgabe;
	}
	
	public void setRueckgabe(String rueckgabe) {
		Rueckgabe = rueckgabe;
	}

	public String getGesamtbestand() {
		return Gesamtbestand;
	}

	public void setGesamtbestand(String gesamtbestand) {
		Gesamtbestand = gesamtbestand;
	}
	
	public static String RueckgabeZeigen(String rueckgabe) {
		String[] RueckgabeList = rueckgabe.split(";");
		String Ergebnis = "";
		for(int i=2; i<RueckgabeList.length-1; i++) {
			Ergebnis = Ergebnis + RueckgabeList[i] + ";";
		}
		Ergebnis = Ergebnis + RueckgabeList[RueckgabeList.length-1];
		//System.out.println(Ergebnis);
		return Ergebnis;
	}
	
	public static String Rueckgabe(double preis, String Bezahlung) {
		double dBezahlung = a.StringToDouble(Bezahlung);
		if(dBezahlung<preis) {
			System.out.println("Fehler! Zuwenig Geld eingeworfen");
			System.exit(0);
		}
		
		BigDecimal bd = new BigDecimal(dBezahlung-preis).setScale(2, RoundingMode.HALF_UP);
		double drueckgabe = bd.doubleValue();
		String Rueckgabe = a.DoubleToStringRueckgabe(drueckgabe);
		return Rueckgabe;
	}
	
	public static String BezahlungBerechnen(int i) {
		String Bezahlung = "";
		
		for(int j=1; j<Dateizeilen.get(i).split(";").length-1; j++) {
			Bezahlung = Bezahlung + Dateizeilen.get(i).split(";")[j]+";";
		}
		
		Bezahlung = Bezahlung + Dateizeilen.get(i).split(";")[Dateizeilen.get(i).split(";").length-1];
		return Bezahlung;
		
	}
	
	public static String PreisToDouble(String Preis) {
		String DPreis = Preis.replace(",", ".");
		return DPreis;
	}
	
	public static String DoubleToPreis(double Preis) {
		String SPreis = String.format("%.2f",Preis);
		return SPreis;
	}
	
	public static ArrayList<Buchung> BuchungZeigen() {
		buchungen = new ArrayList<Buchung>();
		int i = 4;
		int idBuchung = 1;
		while(i<Dateizeilen.size()) {
			if(!(Dateizeilen.get(i)).matches("\\d+,\\d+;\\d+;\\d+;\\d+;\\d+;\\d+;\\d+;\\d+")) {
				System.out.println("Fehler! Buchung falsch angegeben");
				System.exit(i);
			}
			Buchung b = new Buchung();
			
			b.setIdBuchung(idBuchung);
			b.setPreis(Double.parseDouble(PreisToDouble(Dateizeilen.get(i).split(";")[0])));
			String Bezahlung = BezahlungBerechnen(i);
			b.setRueckgabe(Rueckgabe(b.getPreis(),Bezahlung));
			b.setGesamtbestand(a.GesamtBestand(Bezahlung, b.getRueckgabe(), i));
			//System.out.println(b.Gesamtbestand);
			
			for(int k=0; k<b.Gesamtbestand.split(";").length; k++) {
				if(Integer.parseInt(b.Gesamtbestand.split(";")[k])<0) {
					System.out.println("Wechselgeld kann nicht passend ausgezahlt werden");
					System.exit(1);
				}
				}
			
			buchungen.add(b);
			//System.out.println(b.toString());
			idBuchung++;
			i++;
			
		}
		return buchungen;
	}
	
	public static void Ausgabe()  {
		
		ArrayList<Buchung> buchungen1 = BuchungZeigen();
	    FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("Ausgabedatei.txt");
			PrintWriter printWriter = new PrintWriter(fileWriter);
		    printWriter.println("Automat am " + a.getOrt());
		    printWriter.println("");
		    printWriter.println("");
		    printWriter.println("Anfangsbestand: " + DoubleToPreis(a.StringToDouble(a.getAnfangsbestand())) + "€ (" + a.getAnfangsbestand() + ")");
		    
		    for(Buchung e:buchungen1) {
				printWriter.println(e.toString());
			}
		    printWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	}
	
	@Override 
	public String toString() { 
		return idBuchung + ". Buchung: Preis:" +
				DoubleToPreis(getPreis()) +"€ / Rückgabe: " + RueckgabeZeigen(this.getRueckgabe()) +" / Gesamtbestand "+
				DoubleToPreis(a.StringToDouble(Gesamtbestand)) +"€ ("+Gesamtbestand +")";
		}
	 

}
