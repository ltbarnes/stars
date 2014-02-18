package edu.brown.cs032.ltbarnes.stars.kdtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Graphable {

	public final List<Double> coordinates;
	public final int dimension;

	public Graphable(List<Double> coordinates, int dimension) {
		if (coordinates.size() < dimension) {
			System.err.println("There aren't enough coordinates to match the dimension:");
			dimension = coordinates.size();
			System.err.println("Setting the dimension to " + dimension);
		}

		List<Double> temp = new ArrayList<>(dimension);
		for (double c : coordinates)
			temp.add(c);
		this.coordinates = Collections.unmodifiableList(temp);
		this.dimension = dimension;
	}

	public Graphable(double[] coordinates, int dimension) {
		if (coordinates.length < dimension) {
			System.err.println("There aren't enough coordinates to match the dimension");
			dimension = coordinates.length;
			System.err.println("Setting the dimension to " + dimension);
		}

		List<Double> temp = new ArrayList<>(dimension);
		for (double c : coordinates)
			temp.add(c);
		this.coordinates = Collections.unmodifiableList(temp);
		this.dimension = dimension;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(coordinates.get(0)).append(coordinates.get(1))
				.append(coordinates.get(2)).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Graphable))
			return false;

		Graphable s = (Graphable) o;
		return new EqualsBuilder().append(s.coordinates.get(0), coordinates.get(0))
				.append(s.coordinates.get(1), coordinates.get(1)).append(s.coordinates.get(2), coordinates.get(2))
				.isEquals();
	}
}
