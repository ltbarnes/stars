package edu.brown.cs032.ltbarnes.stars.startree;

import edu.brown.cs032.ltbarnes.stars.kdtree.KDElement;

public class Star extends KDElement {

	public final String name;

	public Star(String id, String name, double x, double y, double z) {
		super(id, x, y, z);
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("Star: %s '%s' (%.2f, %.2f, %.2f)", value, name, coordinates.get(0), coordinates.get(1),
				coordinates.get(2));
	}

}
