package edu.brown.cs032.ltbarnes.stars.startree;

import java.util.AbstractCollection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.brown.cs032.ltbarnes.stars.kdtree.KDTree;

public class StarTree extends AbstractCollection<StarNode> implements KDTree<Star> {

	private final int dimension_;
	private StarNode root_;

	public StarTree(List<Star> stars) {
		this.dimension_ = 3;
		root_ = buildTree(0, stars);
	}

	public StarNode buildTree(int currDim, List<Star> stars) {
		int nextDim = (currDim + 1) % dimension_;
		int midIndex = stars.size() / 2;
		Collections.sort(stars, new DimensionComparator(currDim));
		StarNode parent = new StarNode(stars.get(midIndex));

		if (midIndex > 0)
			parent.setLeftChild(buildTree(nextDim, stars.subList(0, midIndex)));
		else
			parent.setLeftChild(null);

		if (midIndex < stars.size() - 1)
			parent.setRightChild(buildTree(nextDim, stars.subList(midIndex + 1, stars.size())));
		else
			parent.setRightChild(null);

		return parent;
	}

	@Override
	public void addElement(Star s) {}

	@Override
	public boolean removeElement(Star s) {
		return false;
	}

	@Override
	public Set<Star> findNearest(Star s) {
		return null;
	}

	@Override
	public Set<Star> findNearestWithRadius(Star s, int radius) {
		return null;
	}

	@Override
	public Iterator<StarNode> iterator() {
		return new InOrderIterator();
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public String toString() {
		return root_.toString();
	}

	private class DimensionComparator implements Comparator<Star> {

		private int dim_;

		public DimensionComparator(int dim) {
			this.dim_ = dim;
		}

		@Override
		public int compare(Star s1, Star s2) {
			return Double.compare(s1.coordinates.get(dim_), s2.coordinates.get(dim_));
		}

	}

	private class InOrderIterator implements Iterator<StarNode> {

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public StarNode next() {
			return null;
		}

		@Override
		public void remove() {}

	}

}
