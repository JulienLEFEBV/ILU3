package jeu;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import cartes.Carte;

public class Sabot implements Iterable<Carte> {
	private Carte[] cartes;
	private int nbCartes;
	private Iterateur iterateur = new Iterateur();
	private int nombreOperations=0;

	@Override
	public Iterator<Carte> iterator() {
		return new Iterateur();
	}

	private class Iterateur implements Iterator<Carte> {
		private int indiceIterateur = 0;
		private boolean nextEffectue = false;
		private int nombreOperationsReference=nombreOperations;

		public boolean hasNext() {
			return indiceIterateur < nbCartes;
		}

		public Carte next() {
			verificationConcurrence();
			if (hasNext()) {
				Carte carte = cartes[indiceIterateur];
				indiceIterateur++;
				nextEffectue = true;
				return carte;
			} else {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			verificationConcurrence();
			if (nbCartes < 1 || !nextEffectue) {
				throw new IllegalStateException();
			}
			for (int i = indiceIterateur - 1; i < nbCartes - 1; i++) {
				cartes[i] = cartes[i + 1];
			}
			nextEffectue = false;
			indiceIterateur--;
			nbCartes--;
			nombreOperations++;
			nombreOperationsReference++;
		}
		
		private void verificationConcurrence(){
			if (nombreOperations != nombreOperationsReference)
			throw new ConcurrentModificationException();
		}
		
	}

	public Sabot(Carte[] cartes) {
		this.cartes = cartes;
		this.nbCartes = cartes.length;
	}

	public boolean estVide() {
		return nbCartes == 0;
	}

	public void ajouterCarte(Carte carte) {
		if (nbCartes >= cartes.length) {
			throw new IllegalStateException("Limite max de cartes atteinte");
		} else {
			cartes[nbCartes] = carte;
			nombreOperations++;
			nbCartes++;
		}
	}
	
	public Carte piocher() {
		Carte carte = null;
		try {
			carte = iterateur.next();
			iterateur.remove();
		}
		catch(IllegalStateException | ConcurrentModificationException | NoSuchElementException e) {
			iterateur = new Iterateur();
			if(e instanceof ConcurrentModificationException){
				carte = iterateur.next();
				iterateur.remove();
			}
		}
		return carte;
	}
	
}
