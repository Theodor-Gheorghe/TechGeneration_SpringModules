package ro.digitalnation.activitati;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ro.digitalnation.basic.Persoana;
import ro.digitalnation.basic.Trainer;

public class Curs implements Activitate {

	private String nume, dificultate;
	private Persoana trainer;
	private ArrayList<Persoana> exploreri;
	private int cost;
	private LinkedHashMap<String, Activitate> activitati = new LinkedHashMap<String, Activitate>();
	
	public Curs(String nume, String dificultate, Persoana trainer, List<Persoana> exploreri2, int cost) {
		super();
		this.nume = nume;
		this.dificultate = dificultate;
		this.trainer = trainer;
		this.exploreri = (ArrayList<Persoana>) exploreri2;
		this.cost = cost;
	}

	public Curs(String nume, String dificultate, int cost) {
		super();
		this.nume = nume;
		this.dificultate = dificultate;
		this.cost = cost;
	}

	@Override
	public String getDescriere() {
		return "Curs de " + nume + " cu o dificultate " + dificultate;
	}

	@Override
	public String getDurata() {
		if (dificultate.equals("usor")) {
			return "1 luna";
		} else if (dificultate.equals("mediu")) {
			return "3 luni";
		} else if (dificultate.equals("dificil")) {
			return "5 luni";
		} else {
			return "necunoscut"; 
		}
 	}

	public Persoana getTrainer() {
		return trainer;
	}

	public ArrayList<Persoana> getExploreri() {
		return exploreri;
	}

	public void setExploreri(ArrayList<Persoana> exploreri) {
		this.exploreri = exploreri;
	}

	public LinkedHashMap<String, Activitate> getActivitati() {
		return activitati;
	}

	public void setActivitati(Map<String, Activitate> activitati2) {
		this.activitati = (LinkedHashMap<String, Activitate>) activitati2;
	}

	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getCost(Persoana p) {
		if(p instanceof Trainer) {
			return 0;
		} else {
			return this.cost;
		}
	}
	
}
