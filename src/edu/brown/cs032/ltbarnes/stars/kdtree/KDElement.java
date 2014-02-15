package edu.brown.cs032.ltbarnes.stars.kdtree;

import java.util.List;

public class KDElement extends Graphable {

	public final Object value;

	public KDElement(Object value, double... coords) {
		super(coords, coords.length);
		this.value = value;
	}

	public KDElement(Object value, List<Double> coords) {
		super(coords, coords.size());
		this.value = value;
	}

}
