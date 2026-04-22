package jeu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cartes.Carte;
import cartes.JeuDeCartes;
import utils.GestionCartes;

public class Jeu {
	private static final int NBCARTES = 6;
	private Sabot sabot;
	private Set<Joueur> joueurs = new LinkedHashSet<>();
	private Iterator<Joueur> joueurCour;
	
	public Jeu() {
		JeuDeCartes jeuDeCartes = new JeuDeCartes();
		Carte[] cartes = jeuDeCartes.donnerCartes();
		List<Carte> liste = new ArrayList<>();
		Collections.addAll(liste,cartes);
		liste=GestionCartes.melanger(liste);
		sabot= new Sabot(liste.toArray(new Carte[liste.size()]));
	}
	
	public void inscrire(Collection<Joueur> newJoueurs) {
		joueurs.addAll(newJoueurs);
		joueurCour=joueurs.iterator();
	}
	
	public void distribuerCartes() {
		for(int i=0;i<NBCARTES;i++) {
			for(Joueur joueur : joueurs) {
				joueur.donner(sabot.piocher());
			}
		}
	}
	
	public String jouerTour(Joueur joueur) {
		Carte cartePioche = sabot.piocher();
		joueur.donner(cartePioche);
		Coup coup = joueur.choisirCoup(joueurs);
		Carte carteJouee = coup.getCarte();
		joueur.retirerDeLaMain(carteJouee);
		Joueur cible = coup.getCible();
		if(cible==null) sabot.ajouterCarte(carteJouee);
		else cible.getZoneDeJeu().deposer(carteJouee);
		return "Le joueur "+joueur+" a pioche "+cartePioche+"\n"
				+joueur.afficherEtatJoueur()+"\n"+coup;
	}
	 
	public Joueur donnerJoueurSuivant() {
		Joueur act = joueurCour.next();
		if (!joueurCour.hasNext()) joueurCour=joueurs.iterator();
		return act;
	}
	
	private boolean victoire() {
		for(Joueur joueur : joueurs) {
			if(joueur.donnerKmParcourus()>=1000)return true;
		}
		return false;
	}
	
	public void lancer() {
		while(!sabot.estVide()&&!victoire()) {
			System.out.println(jouerTour(donnerJoueurSuivant()));
		}
	}
}
