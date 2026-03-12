package cartes;

public abstract class Probleme extends Carte {
	private Type type;

	public Probleme(Type type) {
		super();
		this.type = type;
	}

	public Type getType() {
		return type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Probleme) {
			Probleme probleme = (Probleme) obj;
			return this.getType().equals(probleme.getType()) && super.equals(obj);
		}
		return false;
	}
}
