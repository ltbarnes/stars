package edu.brown.cs032.ltbarnes.stars.startree;

import java.util.AbstractCollection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
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

	public StarNode buildTree(int currDepth, List<Star> stars) {
		int dim = currDepth % dimension_;
		int midIndex = stars.size() / 2;
		Collections.sort(stars, new DimensionComparator(dim));
		StarNode parent = new StarNode(stars.get(midIndex), currDepth);

		if (midIndex > 0)
			parent.setLeftChild(buildTree(currDepth + 1, stars.subList(0, midIndex)));
		else
			parent.setLeftChild(null);

		if (midIndex < stars.size() - 1)
			parent.setRightChild(buildTree(currDepth + 1, stars.subList(midIndex + 1, stars.size())));
		else
			parent.setRightChild(null);

		return parent;
	}

	@Override
	public void addElement(Star s) {
		StarNode star = new StarNode(s);
		StarNode sn = addcursion(root_, star);
		int dim = sn.getDepth() % dimension_;

		if (star.equals(sn))
			return;
		if (star.getDimension(dim) <= sn.getDimension(dim))
			sn.setLeftChild(star);
		else {
			sn.setRightChild(star);
		}

	}

	private StarNode addcursion(StarNode current, StarNode star) {
		int dim = (current.getDepth()) % dimension_;
		StarNode result = current;

		if (current.equals(star))
			return result;
		if (star.getDimension(dim) <= current.getDimension(dim) && current.getLeftChild() != null)
			result = addcursion(current.getLeftChild(), star);
		else if (current.getRightChild() != null) {
			result = addcursion(current.getRightChild(), star);
		}

		return result;
	}

	@Override
	public boolean removeElement(Star s) {
		return false;
	}

	@Override
	public Set<Star> kNNSearch(Star s, int numNearest) {
		Set<Star> result = new HashSet<Star>();
		result.add(searchcursion(root_, new StarNode(s), 1).getStar());
		return result;
	}

	private StarNode searchcursion(StarNode current, StarNode star, int numNearest) {
		StarNode leftChild = current.getLeftChild();
		StarNode rightChild = current.getRightChild();
		// base case leaf node
		if (leftChild == null && rightChild == null)
			return current;

		int dim = (current.getDepth()) % dimension_;
		StarNode result;
		StarNode otherChild;

		if (star.getDimension(dim) <= current.getDimension(dim) && leftChild != null) {
			result = searchcursion(leftChild, star, numNearest);
			otherChild = rightChild;
		} else if (rightChild != null) {
			result = searchcursion(rightChild, star, numNearest);
			otherChild = leftChild;
		} else {
			result = current;
			otherChild = leftChild;
		}

		double minDist = starDist2(result.getStar(), star.getStar());
		double currDist = starDist2(current.getStar(), star.getStar());

		if (currDist < minDist) {
			result = current;
			minDist = currDist;
		}
		Star s = star.getStar();
		Double[] coords = new Double[3];
		s.coordinates.toArray(coords);
		coords[dim] = current.getDimension(dim);

		StarNode other;
		double distPlane = starDist2(new Star("", "", coords[0], coords[1], coords[2]), s);
		if (distPlane < minDist && otherChild != null) {
			other = searchcursion(otherChild, star, numNearest);

			double newDist = starDist2(other.getStar(), star.getStar());

			if (newDist < minDist) {
				result = other;
				minDist = newDist;
			}
		}

		return result;
	}

	@Override
	public Set<Star> kNNSearchWithRadius(Star s, int radius) {
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

	public static double starDist2(Star s1, Star s2) {
		int sum = 0;
		for (int i = 0; i < 3; i++) {
			double diff = s1.coordinates.get(i) - s2.coordinates.get(i);
			sum += diff * diff;
		}
		return sum;
	}

	// private static class StarEval<A, B> {
	//
	// A node;
	// B minDist;
	//
	// StarEval(A node, B minDist) {
	// this.node = node;
	// this.minDist = minDist;
	// }
	//
	// static <X,Y> StarEval<X,Y> make(X node, Y minDist) {
	// return new StarEval(x, y);
	// }
	// }

}
