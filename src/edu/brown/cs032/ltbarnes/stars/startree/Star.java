package edu.brown.cs032.ltbarnes.stars.startree;

import edu.brown.cs032.ltbarnes.kdtree.KDElement;

public class Star extends KDElement {

	public final String name;

	public Star(String id, String name, double x, double y, double z) {
		super(id, x, y, z);
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Star other = (Star) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Star: %s '%s' (%.2f, %.2f, %.2f)", value, name, coordinates.get(0), coordinates.get(1),
				coordinates.get(2));
	}

}
