package de.fhac.rn.berrhoze.klassen;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Automat {

	static Eingabe e = new Eingabe() ;	
	static List<String> Dateizeilen = e.DateiEinlesen();
	private String Anfangsbestand = e.AnfangsbestandBerechnen(Dateizeilen.get(3));
	private String Bestand = Anfangsbestand;
	String Ort = Dateizeilen.get(0).split(" ")[2];
	
	public Automat() {
		super();
	}

	public Automat(String bestand, String ort) {
		super();
		Bestand = bestand;
		Ort = ort;
	}

	public String getAnfangsbestand() {
		return Anfangsbestand;
	}

	public void setAnfangsbestand(String anfangsbestand) {
		Anfangsbestand = anfangsbestand;
	}

	public String getBestand() {
		return Bestand;
	}

	public void setBestand(String bestand) {
		Bestand = bestand;
	}

	public String getOrt() {
		return Ort;
	}

	public void setOrt(String ort) {
		Ort = ort;
	}
	
	public String GesamtBestand(String Bezahlung, String Rueckgabe, int i) {
		String[] AnfangsBestandList = this.Bestand.split(";");
		String[] BezahlungList = Bezahlung.split(";");
		String[] RueckgabeList = Rueckgabe.split(";");
		//System.out.println(Anfangsbestand.split(";").length);
		int [] Ergebnis = new int[Anfangsbestand.split(";").length];
		if(i>=4) {	
		for( int j=0 ; j<Anfangsbestand.split(";").length ; j++) {
			
			//System.out.println(AnfangsBestandList[j]+";"+BezahlungList[j]+";"+RueckgabeList[j]);
			Ergebnis[j] = Integer.parseInt(AnfangsBestandList[j]) + Integer.parseInt(BezahlungList[j]) - Integer.parseInt(RueckgabeList[j]);
		}
			//System.out.println("------------------");
		}
			setBestand(ListToString(Ergebnis));
			return ListToString(Ergebnis);
		}
		
		
	public String ListToString(int[] T) {
		String e = "";
		for (int i =0; i<T.length-1; i++) {
			e = e + Integer.toString(T[i]) +  ";";
		}
		e = e + Integer.toString(T[T.length-1]);
		
		return e;
		
	}
	
	public String DoubleToString(double preis) {
		int cpt10 = 0, cpt5 = 0, cpt2 = 0, cpt1 = 0, cpt050 = 0, cpt020 = 0, cpt010 = 0;
		
		double x = 0;
		double [] Zahlungmoeglichkeit = {10.00, 5.00, 2.00, 1.00, 0.50, 0.20, 0.10}; 
		while(Zahlungmoeglichkeit[0]<=preis) {
			cpt10++;
			preis = preis - Zahlungmoeglichkeit[0];
			BigDecimal bd = new BigDecimal(preis).setScale(2, RoundingMode.HALF_UP);
			preis = bd.doubleValue();
		}
		for(int i=0; i<Zahlungmoeglichkeit.length-1; i++) {
			while(Zahlungmoeglichkeit[i+1]<=preis && Zahlungmoeglichkeit[i]>=preis) {
				preis = preis - Zahlungmoeglichkeit[i+1];
				BigDecimal bd = new BigDecimal(preis).setScale(2, RoundingMode.HALF_UP);
				preis = bd.doubleValue();
				x = Zahlungmoeglichkeit[i+1];
				
				if(x==10.00) cpt10++;
				if(x==5.00) cpt5++;
				if(x==2.00) cpt2++;
				if(x==1.00) cpt1++;
				if(x==0.50) cpt050++;
				if(x==0.20) cpt020++;
				if(x==0.10) cpt010++;
			}
		}
		
		
		return cpt10+";"+cpt5+";"+cpt2+";"+cpt1+";"+cpt050+";"+cpt020+";"+cpt010;
		
	}
	
	public String DoubleToStringRueckgabe(double preis) {
		int  cpt10 = 0, cpt5 = 0, cpt2 = 0, cpt1 = 0, cpt050 = 0, cpt020 = 0, cpt010 = 0;
		double x = 0;
		double [] Zahlungmoeglichkeit = {2.00, 1.00, 0.50, 0.20, 0.10}; 
		while(Zahlungmoeglichkeit[0]<=preis) {
			cpt2++;
			preis = preis - Zahlungmoeglichkeit[0];
			BigDecimal bd = new BigDecimal(preis).setScale(2, RoundingMode.HALF_UP);
			preis = bd.doubleValue();
		}
		for(int i=0; i<Zahlungmoeglichkeit.length-1; i++) {
			while(Zahlungmoeglichkeit[i+1]<=preis && Zahlungmoeglichkeit[i]>=preis) {
				preis = preis - Zahlungmoeglichkeit[i+1];
				BigDecimal bd = new BigDecimal(preis).setScale(2, RoundingMode.HALF_UP);
				preis = bd.doubleValue();
				x = Zahlungmoeglichkeit[i+1];
				if(x==2.00) cpt2++;
				if(x==1.00) cpt1++;
				if(x==0.50) cpt050++;
				if(x==0.20) cpt020++;
				if(x==0.10) cpt010++;
			}
		}
		
		return cpt10+";"+cpt5+";"+cpt2+";"+cpt1+";"+cpt050+";"+cpt020+";"+cpt010;
		
	}
	
	public double StringToDouble(String bestand) {
		double Ergebnis=0;
		double [] Zahlungmoeglichkeit = {10.00, 5.00, 2.00, 1.00, 0.50, 0.20, 0.10}; 
		String [] b = bestand.split(";");
		
		for(int i=0; i< Zahlungmoeglichkeit.length ; i++) {
			Ergebnis = Ergebnis+(Double.parseDouble(b[i])*Zahlungmoeglichkeit[i]);
		}

		return Ergebnis;
	}
	
	
	
	
}
