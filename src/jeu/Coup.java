package jeu;

import cartes.Attaque;
import cartes.Carte;
import cartes.Limite;

public class Coup {
	private Joueur joueurCour;
	private Carte carte;
	private Joueur cible;
	
	public Coup(Joueur joueurCour, Carte carte, Joueur cible) {
		this.joueurCour = joueurCour;
		this.carte = carte;
		this.cible = cible;
	}

	public Joueur getJoueurCour() {
		return joueurCour;
	}

	public Carte getCarte() {
		return carte;
	}

	public Joueur getCible() {
		return cible;
	}
	
	public boolean estValide() {
		if(carte instanceof Attaque || carte instanceof Limite)
			return !joueurCour.equals(cible);
		else return joueurCour.equals(cible);
	}
	
	@Override
	public String toString() {
		if(cible==null) return joueurCour +" defausse la carte " + carte;
		if(joueurCour.equals(cible)) return joueurCour + " depose la carte "+carte+" dans sa zone de jeu";
		return joueurCour + " depose la carte "+carte+" dans la zone de jeu de " + cible;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Coup) {
			Coup c = (Coup) o;
			return joueurCour.equals(c.getJoueurCour()) && carte.equals(c.getJoueurCour()) && cible.equals(c.getCible());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
	    if(cible!=null) return 67 * (joueurCour.hashCode()+carte.hashCode()+cible.hashCode());
	    return 67 * (joueurCour.hashCode()+carte.hashCode());
	}
}
