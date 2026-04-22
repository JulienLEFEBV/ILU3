package jeu;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import cartes.Carte;

public class Joueur {
	private String nom;
	private ZoneDeJeu zoneDeJeu = new ZoneDeJeu();
	private MainJoueur main = new MainJoueur();
	private Random rand = new Random();
	
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
	
	public Set<Coup> coupsPossibles(Set<Joueur>participants){
		Set<Coup> coupsPos = new HashSet<>();
		for(Joueur joueur : participants) {
			for(Carte carteAct : main) {
				if(joueur.getZoneDeJeu().estDepotAutorise(carteAct)) {
					Coup coup = new Coup(this,carteAct,joueur);
					if(coup.estValide()) coupsPos.add(coup);
				}
			}
		}
		return coupsPos;
	}
	
	public Set<Coup> coupsDefausse(){
		Set<Coup> coupsDef = new HashSet<>();
		for(Carte carteAct : main) {
			coupsDef.add(new Coup(this,carteAct,null));
		}
		return coupsDef;
	}
	
	public ZoneDeJeu getZoneDeJeu() {
		return zoneDeJeu;
	}

	public void retirerDeLaMain(Carte carte) {
		main.jouer(carte);
	}
	
	private Coup coupAleatoire(Set<Coup> coups) {
		int size = coups.size();
		int coupChoisi = rand.nextInt(size);
		int i=0;
		for(Coup coup : coups) {
			if(i==coupChoisi) {
				return coup;
			}
			i++;
		}
		return null;
	}
	
	public Coup choisirCoup(Set<Joueur> participants) {
		Set<Coup> coupsPos=coupsPossibles(participants);
		if(coupsPos.size()!=0) {
			return coupAleatoire(coupsPos);
		}
		coupsPos = coupsDefausse();
		return coupAleatoire(coupsPos);
	}
	
	public String afficherEtatJoueur() {
		String etat=zoneDeJeu.info_zone();
		etat+="\n Il a dans sa main "+main;
		return etat;
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
	
	@Override
	public int hashCode() {
	    return 67 * nom.hashCode();
	}
}
