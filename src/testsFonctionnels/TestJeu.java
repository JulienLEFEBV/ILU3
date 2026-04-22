package testsFonctionnels;

import java.util.ArrayList;
import java.util.List;

import jeu.Jeu;
import jeu.Joueur;

public class TestJeu {
	public static void main(String[] args) {
		Jeu jeu=new Jeu();
		List<Joueur> joueurs = new ArrayList<>();
		joueurs.add(new Joueur("Jack"));
		joueurs.add(new Joueur("Bill"));
		joueurs.add(new Joueur("Luffy"));
		jeu.inscrire(joueurs);
		jeu.distribuerCartes();
//		for(Joueur joueur : joueurs) {
//			System.out.println(jeu.jouerTour(joueur));
//		}
		jeu.lancer();
	}
}
