package edu.brown.cs032.ltbarnes.kdtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class containing a {@code dimension} and list of coordinates corresponding
 * to that dimension.
 * 
 * @author ltbarnes
 * 
 */
public class Graphable {

	public final List<Double> coordinates;
	public final int dimension;

	public Graphable(List<Double> coordinates, int dimension) {
		if (coordinates.size() < dimension) {
			dimension = coordinates.size();
			System.err.println("There aren't enough coordinates to match the dimension:");
			System.err.println("Setting the dimension to '" + dimension + "'");
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinates == null) ? 0 : coordinates.hashCode());
		result = prime * result + dimension;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Graphable other = (Graphable) obj;
		if (coordinates == null) {
			if (other.coordinates != null)
				return false;
		} else if (!coordinates.equals(other.coordinates))
			return false;
		if (dimension != other.dimension)
			return false;
		return true;
	}

}
