package edu.brown.cs032.ltbarnes.stars.startree;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import edu.brown.cs032.ltbarnes.stars.kdtree.KDElement;

public class Star extends KDElement {

	public final String name;

	public Star(String id, String name, double x, double y, double z) {
		super(id, x, y, z);
		this.name = name;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).appendSuper(super.hashCode()).append(name).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Star))
			return false;

		Star s = (Star) o;
		return new EqualsBuilder().appendSuper(super.equals(o)).append(s.name, name).isEquals();
	}

	@Override
	public String toString() {
		return String.format("Star: %s '%s' (%.2f, %.2f, %.2f)", value, name, coordinates.get(0), coordinates.get(1),
				coordinates.get(2));
	}

}
