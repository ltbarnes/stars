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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		KDElement other = (KDElement) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
