package jeu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import cartes.Carte;

public class MainJoueur  implements Iterable<Carte>{
	private List<Carte> cartes = new ArrayList<>();
	
	public Iterator<Carte> iterator() { return cartes.iterator(); }
	
	public void prendre(Carte carte) {
		cartes.add(carte);
	}
	
	public void jouer(Carte carte) {
		assert cartes.indexOf(carte)!=-1;
		cartes.remove(carte);
	}

	@Override
	public String toString() {
		String affichage = "";
		for(Carte carte : cartes) {
			affichage+=carte;
		}
		return affichage;
	}
	
	
}
