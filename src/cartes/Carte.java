package cartes;

public abstract class Carte  {
	
	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass());
	}
	
//	@Override
//	public int hashCode() {
//	    return 67 * getClass().hashCode();
//	}
}
