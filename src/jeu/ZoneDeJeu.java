package jeu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cartes.Attaque;
import cartes.Bataille;
import cartes.Borne;
import cartes.Botte;
import cartes.Carte;
import cartes.DebutLimite;
import cartes.FinLimite;
import cartes.Limite;
import cartes.Parade;
import cartes.Type;

public class ZoneDeJeu {
	private static final int KM_MAX = 1000;
	private List<Limite> pileLimites = new ArrayList<>();
	private List<Bataille> pileBatailles = new ArrayList<>();
	private List<Borne> collecBornes = new ArrayList<>();
	private Set<Botte> ensBottes = new HashSet<>();

	public int donnerLimitationVitesse() {
		if (pileLimites.isEmpty() || pileLimites.get(0).equals(new FinLimite()) || estPrioritaire())
			return 200;
		else
			return 50;
	}

	public int donnerKmParcourus() {
		int totalKm = 0;
		for (Borne borne : collecBornes) {
			totalKm += borne.getKm();
		}
		return totalKm;
	}

	public void deposer(Carte c) {
		if (c instanceof Borne)
			collecBornes.add((Borne) c);
		if (c instanceof Bataille)
			pileBatailles.add(0, (Bataille) c);
		if (c instanceof Limite)
			pileLimites.add(0, (Limite) c);
		if (c instanceof Botte)
			ensBottes.add((Botte) c);
	}

	public boolean peutAvancer() {
		return !(pileBatailles.isEmpty()) && pileBatailles.get(0).equals(new Parade(Type.FEU))
				|| pileBatailles.isEmpty() && estPrioritaire()
				|| !(pileBatailles.isEmpty()) && estPrioritaire()
						&& (pileBatailles.get(0).equals(new Parade(Type.ACCIDENT))
								|| pileBatailles.get(0).equals(new Parade(Type.CREVAISON))
								|| pileBatailles.get(0).equals(new Parade(Type.ESSENCE))
								|| pileBatailles.get(0).equals(new Attaque(Type.FEU))
								|| pileBatailles.get(0).equals(new Attaque(Type.ACCIDENT)) && ensBottes.contains(new Botte(Type.ACCIDENT))
								|| pileBatailles.get(0).equals(new Attaque(Type.ESSENCE)) && ensBottes.contains(new Botte(Type.ESSENCE))
								|| pileBatailles.get(0).equals(new Attaque(Type.CREVAISON)) && ensBottes.contains(new Botte(Type.CREVAISON)));
	}

	private boolean estDepotFeuVertAutorise() {
		return !estPrioritaire() && pileBatailles.isEmpty()
				|| !pileBatailles.isEmpty() && (pileBatailles.get(0) instanceof Parade && !pileBatailles.get(0).equals(new Parade(Type.FEU)))
				|| !pileBatailles.isEmpty() && pileBatailles.get(0).equals(new Attaque(Type.FEU));
	}

	private boolean estDepotBorneAutorise(Borne borne) {
		return peutAvancer() && borne.getKm() < donnerLimitationVitesse()
				&& donnerKmParcourus() + borne.getKm() < KM_MAX;
	}

	private boolean estDepotLimiteAutorise(Limite limite) {
		if (estPrioritaire()) return false;
		if (limite instanceof DebutLimite)
			return pileLimites.isEmpty() || pileLimites.get(0).equals(new FinLimite());
		else
			return !pileLimites.isEmpty() && pileLimites.get(0).equals(new DebutLimite());
	}

	private boolean estDepotBatailleAutorise(Bataille bataille) {
		if (bataille instanceof Attaque) {
			if(bataille.equals(new Attaque(Type.CREVAISON)) && ensBottes.contains(new Botte(Type.CREVAISON))) return false;
			if(bataille.equals(new Attaque(Type.ACCIDENT)) && ensBottes.contains(new Botte(Type.ACCIDENT))) return false;
			if(bataille.equals(new Attaque(Type.ESSENCE)) && ensBottes.contains(new Botte(Type.ESSENCE))) return false;
			return peutAvancer();
		}
		if (bataille.equals(new Parade(Type.FEU)))
			return estDepotFeuVertAutorise();
		return !pileBatailles.isEmpty() && pileBatailles.get(0) instanceof Attaque
				&& bataille.getType() == pileBatailles.get(0).getType();
	}

	public boolean estDepotAutorise(Carte carte) {
		if (carte instanceof Borne)
			return estDepotBorneAutorise((Borne) carte);
		if (carte instanceof Limite)
			return estDepotLimiteAutorise((Limite) carte);
		if (carte instanceof Bataille)
			return estDepotBatailleAutorise((Bataille) carte);
		return (carte instanceof Botte);
	}

	public boolean estPrioritaire() {
		return ensBottes.contains(new Botte(Type.FEU));
	}
	
	public String info_zone() {
		String etat ="Il a les bottes : "+ensBottes+
				"\n Limitation de vitesse "+ (donnerLimitationVitesse()!=200) +
				"\nIl a sur sa pile de bataille : ";
		if (pileBatailles.isEmpty()) etat+="null";
		else etat+=pileBatailles.get(0);
		return etat;
	}
}
