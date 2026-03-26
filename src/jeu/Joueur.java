package jeu;

import cartes.Carte;

public class Joueur {
	private String nom;
	private ZoneDeJeu zoneDeJeu = new ZoneDeJeu();
	private MainJoueur main = new MainJoueur();
	
	public Joueur (String nom) {
		this.nom = nom;
	}
	
	public void donner(Carte carte) {
		main.prendre(carte);
	}
	
	public Carte prendreCarte(Sabot sabot) {
		Carte carte = sabot.piocher();
		if(carte!= null) {
			donner(carte);
		}
		return carte;
	}
	
	public int donnerKmParcourus() {
		return zoneDeJeu.donnerKmParcourus();
	}
	
	public boolean estDepotAutorise(Carte carte) {
		return zoneDeJeu.estDepotAutorise(carte);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Joueur) {
			Joueur joueur =(Joueur) obj;
			return nom==joueur.nom;
		}
		return false;
	}

	@Override
	public String toString() {
		return nom;
	}
	
	
}
