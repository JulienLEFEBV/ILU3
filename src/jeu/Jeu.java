package jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cartes.Carte;
import cartes.JeuDeCartes;
import utils.GestionCartes;

public class Jeu {
	private Sabot sabot;
	
	public Jeu() {
		JeuDeCartes jeuDeCartes = new JeuDeCartes();
		Carte[] cartes = jeuDeCartes.donnerCartes();
		List<Carte> liste = new ArrayList<>();
		Collections.addAll(liste,cartes);
		liste=GestionCartes.melanger(liste);
		sabot= new Sabot((Carte[])liste.toArray());
	}
}
