package cartes;

public interface Cartes {
	Botte PRIORITAIRE = new Botte(Type.FEU);
	Attaque FEU_ROUGE = new Attaque(Type.FEU);
	Parade FEU_VERT = new Parade(Type.FEU);
}
