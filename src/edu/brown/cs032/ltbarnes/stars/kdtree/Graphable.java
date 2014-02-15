package edu.brown.cs032.ltbarnes.stars.kdtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

}
