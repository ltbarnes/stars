package edu.brown.cs032.ltbarnes.stars.startree;

import edu.brown.cs032.ltbarnes.stars.kdtree.KDElement;

public class Star extends KDElement {

	public final String name;

	public Star(String id, String name, double x, double y, double z) {
		super(id, x, y, z);
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Star))
			return false;

		Star s = (Star) o;
		return ((String) s.value).equals((String) value) && s.name.equals(name)
				&& s.coordinates.get(0).equals(coordinates.get(0)) && s.coordinates.get(1).equals(coordinates.get(1))
				&& s.coordinates.get(2).equals(coordinates.get(2));
	}

	@Override
	public String toString() {
		return String.format("Star: %s '%s' (%.2f, %.2f, %.2f)", value, name, coordinates.get(0), coordinates.get(1),
				coordinates.get(2));
	}

}
