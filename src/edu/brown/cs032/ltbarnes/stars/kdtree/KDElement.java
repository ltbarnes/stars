package edu.brown.cs032.ltbarnes.stars.kdtree;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
		return new HashCodeBuilder(17, 31).appendSuper(super.hashCode()).append(value).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof KDElement))
			return false;

		KDElement s = (KDElement) o;
		return new EqualsBuilder().appendSuper(super.equals(o)).append(s.value, value).isEquals();
	}

}
