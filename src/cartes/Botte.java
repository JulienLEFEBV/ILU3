package cartes;

public class Botte extends Probleme {

	public Botte(Type type) {
		super(type);
	}

	@Override
	public String toString() {
		return super.getType().getBotte();
	}
	
	@Override
	public int hashCode() {
	    return 67 * super.getType().hashCode();
	}

}
