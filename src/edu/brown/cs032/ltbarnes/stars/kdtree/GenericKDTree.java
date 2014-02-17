package edu.brown.cs032.ltbarnes.stars.kdtree;

import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import edu.brown.cs032.ltbarnes.stars.startree.Star;

public class GenericKDTree<T extends KDElement> extends AbstractCollection<T> implements KDTree<T> {

	private final int dimension_;
	private KDNode<T> root_;
	private int size_;

	public GenericKDTree(List<T> elements, int dimension) {
		this.dimension_ = dimension;
		root_ = buildTree(0, elements);
	}

	private KDNode<T> buildTree(int currDepth, List<T> stars) {
		int dim = currDepth % dimension_;
		int midIndex = stars.size() / 2;
		Collections.sort(stars, new DimensionComparator(dim));
		KDNode<T> parent = new KDNode<>(stars.get(midIndex), currDepth);
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
	public void addElement(T s) {
		KDNode<T> star = new KDNode<>(s);
		KDNode<T> sn = addcursion(root_, star);
		int dim = sn.getDepth() % dimension_;

		if (star.equals(sn))
			return;
		if (star.getDimension(dim) <= sn.getDimension(dim))
			sn.setLeftChild(star);
		else {
			sn.setRightChild(star);
		}
		size_++;
	}

	private KDNode<T> addcursion(KDNode<T> current, KDNode<T> star) {
		int dim = (current.getDepth()) % dimension_;
		KDNode<T> result = current;

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
	public boolean removeElement(T s) {
		return false;
	}

	@Override
	public List<T> kNNSearch(T s, int numNearest) {
		List<KDEval<KDNode<T>, Double>> list = new ArrayList<>();
		search(list, root_, new KDNode<>(s), numNearest, dimension_);
		Collections.sort(list, new KDEvalComparator());
		List<T> result = new ArrayList<>();
		for (KDEval<KDNode<T>, Double> se : list)
			result.add(se.node.getElement());
		return result;
	}

	private void search(List<KDEval<KDNode<T>, Double>> list, KDNode<T> current, KDNode<T> star, int numNearest,
			int treeDim) {
		KDNode<T> leftChild = current.getLeftChild();
		KDNode<T> rightChild = current.getRightChild();
		double currDist = starDist2(current.getElement(), star.getElement());

		// base case leaf node
		if (leftChild == null && rightChild == null) {
			if (!current.getElement().equals(star.getElement())) {
				if (list.size() < numNearest) {
					list.add(new KDEval<KDNode<T>, Double>(current, currDist));
				} else {
					Collections.sort(list, new KDEvalComparator());
					if (currDist < list.get(numNearest - 1).minDist) {
						list.remove(numNearest - 1);
						list.add(new KDEval<KDNode<T>, Double>(current, currDist));
					}
				}
			}
			return;
		}

		int dim = (current.getDepth()) % treeDim;
		KDNode<T> otherChild;

		if (star.getDimension(dim) <= current.getDimension(dim) && leftChild != null) {
			search(list, leftChild, star, numNearest, treeDim);
			otherChild = rightChild;
		} else if (rightChild != null) {
			search(list, rightChild, star, numNearest, treeDim);
			otherChild = leftChild;
		} else {
			otherChild = leftChild;
		}

		if (!current.getElement().equals(star.getElement())) {
			if (list.size() < numNearest) {
				list.add(new KDEval<KDNode<T>, Double>(current, currDist));
			} else {
				Collections.sort(list, new KDEvalComparator());
				if (currDist < list.get(numNearest - 1).minDist) {
					list.remove(numNearest - 1);
					list.add(new KDEval<KDNode<T>, Double>(current, currDist));
				}
			}
		}
		double maxDist = list.get(list.size() - 1).minDist;

		KDElement s = star.getElement();
		Double[] coords = new Double[3];
		s.coordinates.toArray(coords);
		coords[dim] = current.getDimension(dim);
		double distPlane = starDist2(new Star("", "", coords[0], coords[1], coords[2]), s);

		if ((list.size() < numNearest || distPlane < maxDist) && otherChild != null) {
			search(list, otherChild, star, numNearest, treeDim);
		}
	}

	@Override
	public List<T> kNNSearchWithRadius(KDElement s, int radius) {
		return null;
	}

	@Override
	public Iterator<T> iterator() {
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

	private static class DimensionComparator implements Comparator<KDElement> {

		private int dim_;

		public DimensionComparator(int dim) {
			this.dim_ = dim;
		}

		@Override
		public int compare(KDElement e1, KDElement e2) {
			return Double.compare(e1.coordinates.get(dim_), e2.coordinates.get(dim_));
		}

	}

	private class KDEvalComparator implements Comparator<KDEval<KDNode<T>, Double>> {

		@Override
		public int compare(KDEval<KDNode<T>, Double> se1, KDEval<KDNode<T>, Double> se2) {
			return Double.compare(se1.minDist, se2.minDist);
		}

	}

	private class InOrderIterator implements Iterator<T> {

		private Deque<KDNode<T>> dq_;
		private KDNode<T> current_;

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
		public T next() {
			KDNode<T> next = dq_.pop();
			current_ = next.getRightChild();
			slide();
			return next.getElement();
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

	public static double starDist2(KDElement e1, KDElement e2) {
		int sum = 0;
		for (int i = 0; i < 3; i++) {
			double diff = e1.coordinates.get(i) - e2.coordinates.get(i);
			sum += diff * diff;
		}
		return sum;
	}

	private static class KDEval<A, B> {

		A node;
		B minDist;

		KDEval(A node, B minDist) {
			this.node = node;
			this.minDist = minDist;
		}
	}

}
