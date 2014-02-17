package edu.brown.cs032.ltbarnes.stars.startree;

import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import edu.brown.cs032.ltbarnes.stars.kdtree.KDTree;

public class StarTree extends AbstractCollection<Star> implements KDTree<Star> {

	private static final int DIMENSION = 3;
	private StarNode root_;
	private int size_;

	public StarTree(List<Star> stars) {
		root_ = buildTree(0, stars);
	}

	private StarNode buildTree(int currDepth, List<Star> stars) {
		int dim = currDepth % DIMENSION;
		int midIndex = stars.size() / 2;
		Collections.sort(stars, new DimensionComparator(dim));
		StarNode parent = new StarNode(stars.get(midIndex), currDepth);
		size_++;

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
		int dim = sn.getDepth() % DIMENSION;

		if (star.equals(sn))
			return;
		if (star.getDimension(dim) <= sn.getDimension(dim))
			sn.setLeftChild(star);
		else {
			sn.setRightChild(star);
		}
		size_++;
	}

	private StarNode addcursion(StarNode current, StarNode star) {
		int dim = (current.getDepth()) % DIMENSION;
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
	public List<Star> kNNSearch(Star s, int numNearest) {
		List<StarEval<StarNode, Double>> list = new ArrayList<>();
		search(list, root_, new StarNode(s), numNearest);
		Collections.sort(list, new StarEvalComparator());
		List<Star> result = new ArrayList<>();
		for (StarEval<StarNode, Double> se : list)
			result.add(se.node.getStar());
		return result;
	}

	private static void search(List<StarEval<StarNode, Double>> list, StarNode current, StarNode star, int numNearest) {
		StarNode leftChild = current.getLeftChild();
		StarNode rightChild = current.getRightChild();
		double currDist = starDist2(current.getStar(), star.getStar());

		// base case leaf node
		if (leftChild == null && rightChild == null) {
			if (!current.getStar().equals(star.getStar())) {
				if (list.size() < numNearest) {
					list.add(new StarEval<StarNode, Double>(current, currDist));
				} else {
					Collections.sort(list, new StarEvalComparator());
					if (currDist < list.get(numNearest - 1).minDist) {
						list.remove(numNearest - 1);
						list.add(new StarEval<StarNode, Double>(current, currDist));
					}
				}
			}
			return;
		}

		int dim = (current.getDepth()) % DIMENSION;
		StarNode otherChild;

		if (star.getDimension(dim) <= current.getDimension(dim) && leftChild != null) {
			search(list, leftChild, star, numNearest);
			otherChild = rightChild;
		} else if (rightChild != null) {
			search(list, rightChild, star, numNearest);
			otherChild = leftChild;
		} else {
			otherChild = leftChild;
		}

		if (!current.getStar().equals(star.getStar())) {
			if (list.size() < numNearest) {
				list.add(new StarEval<StarNode, Double>(current, currDist));
			} else {
				Collections.sort(list, new StarEvalComparator());
				if (currDist < list.get(numNearest - 1).minDist) {
					list.remove(numNearest - 1);
					list.add(new StarEval<StarNode, Double>(current, currDist));
				}
			}
		}
		double maxDist = list.get(list.size() - 1).minDist;

		Star s = star.getStar();
		Double[] coords = new Double[3];
		s.coordinates.toArray(coords);
		coords[dim] = current.getDimension(dim);
		double distPlane = starDist2(new Star("", "", coords[0], coords[1], coords[2]), s);

		if ((list.size() < numNearest || distPlane < maxDist) && otherChild != null) {
			search(list, otherChild, star, numNearest);
		}
	}

	@Override
	public List<Star> kNNSearchWithRadius(Star s, int radius) {
		return null;
	}

	@Override
	public Iterator<Star> iterator() {
		return new InOrderIterator();
	}

	@Override
	public int size() {
		return size_;
	}

	@Override
	public String toString() {
		return root_.toString();
	}

	private static class DimensionComparator implements Comparator<Star> {

		private int dim_;

		public DimensionComparator(int dim) {
			this.dim_ = dim;
		}

		@Override
		public int compare(Star s1, Star s2) {
			return Double.compare(s1.coordinates.get(dim_), s2.coordinates.get(dim_));
		}

	}

	private static class StarEvalComparator implements Comparator<StarEval<StarNode, Double>> {

		@Override
		public int compare(StarEval<StarNode, Double> se1, StarEval<StarNode, Double> se2) {
			return Double.compare(se1.minDist, se2.minDist);
		}

	}

	private class InOrderIterator implements Iterator<Star> {

		private Deque<StarNode> dq_;
		private StarNode current_;

		public InOrderIterator() {
			dq_ = new ArrayDeque<>();
			current_ = root_;
			slide();
		}

		@Override
		public boolean hasNext() {
			return current_ == null && !dq_.isEmpty();
		}

		@Override
		public Star next() {
			StarNode next = dq_.pop();
			current_ = next.getRightChild();
			slide();
			return next.getStar();
		}

		@Override
		public void remove() {}

		private void slide() {
			while (current_ != null) {
				dq_.push(current_);
				current_ = current_.getLeftChild();
			}

		}

	}

	public static double starDist2(Star s1, Star s2) {
		int sum = 0;
		for (int i = 0; i < 3; i++) {
			double diff = s1.coordinates.get(i) - s2.coordinates.get(i);
			sum += diff * diff;
		}
		return sum;
	}

	private static class StarEval<A, B> {

		A node;
		B minDist;

		StarEval(A node, B minDist) {
			this.node = node;
			this.minDist = minDist;
		}

		// static <X, Y> StarEval<X, Y> make(X node, Y minDist) {
		// return new StarEval<>(node, minDist);
		// }
	}

}
