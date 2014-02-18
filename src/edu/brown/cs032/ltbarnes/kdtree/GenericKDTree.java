package edu.brown.cs032.ltbarnes.kdtree;

import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

/**
 * An instance of {@link KDTree} capable of constructing a balanced KDTree in
 * any dimension.
 * 
 * <p>
 * NOTE: removeElement(T e) METHOD NOT FUNCTIONAL IN THIS IMPLEMENTATION OF
 * {@link KDTree}.
 * 
 * @author ltbarnes
 * 
 * @param <T>
 *            the type of {@link KDElement}s in the tree
 */
public class GenericKDTree<T extends KDElement> extends AbstractCollection<T> implements KDTree<T> {

	private final int dimension_;
	private KDNode<T> root_;
	private int size_;

	public GenericKDTree(List<T> elements, int dimension) {
		this.dimension_ = dimension;
		root_ = buildTree(0, elements);
	}

	private KDNode<T> buildTree(int currDepth, List<T> elements) {
		int dim = currDepth % dimension_;
		int midIndex = elements.size() / 2;
		Collections.sort(elements, new DimensionComparator(dim));
		KDNode<T> parent = new KDNode<>(elements.get(midIndex), currDepth);
		size_++;

		if (midIndex > 0)
			parent.setLeftChild(buildTree(currDepth + 1, elements.subList(0, midIndex)));
		else
			parent.setLeftChild(null);

		if (midIndex < elements.size() - 1)
			parent.setRightChild(buildTree(currDepth + 1, elements.subList(midIndex + 1, elements.size())));
		else
			parent.setRightChild(null);

		return parent;
	}

	@Override
	public void addElement(T e) {
		KDNode<T> element = new KDNode<>(e);
		KDNode<T> kdn = addcursion(root_, element);
		int dim = kdn.getDepth() % dimension_;

		if (element.equals(kdn))
			return;
		if (element.getDimension(dim) <= kdn.getDimension(dim))
			kdn.setLeftChild(element);
		else {
			kdn.setRightChild(element);
		}
		size_++;
	}

	private KDNode<T> addcursion(KDNode<T> current, KDNode<T> pnt) {
		int dim = (current.getDepth()) % dimension_;
		KDNode<T> result = current;

		if (current.equals(pnt))
			return result;
		if (pnt.getDimension(dim) <= current.getDimension(dim) && current.getLeftChild() != null)
			result = addcursion(current.getLeftChild(), pnt);
		else if (current.getRightChild() != null) {
			result = addcursion(current.getRightChild(), pnt);
		}
		return result;
	}

	@Override
	public boolean removeElement(T e) {
		return false;
	}

	@Override
	public List<T> kNNSearch(T e, int numNearest) {
		if (numNearest == 0)
			return new ArrayList<T>();
		List<KDEval<KDNode<T>, Double>> list = new ArrayList<>();
		search(list, root_, new KDNode<>(e), numNearest, null);
		return sortSearch(list);
	}

	@Override
	public List<T> kNNSearchWithRadius(T e, double radius) {
		List<KDEval<KDNode<T>, Double>> list = new ArrayList<>();
		search(list, root_, new KDNode<>(e), 1, new Double(radius * radius));
		return sortSearch(list);
	}

	private List<T> sortSearch(List<KDEval<KDNode<T>, Double>> list) {
		Collections.sort(list, new KDEvalComparator());
		List<T> result = new ArrayList<>();
		for (KDEval<KDNode<T>, Double> ke : list)
			result.add(ke.a.getElement());
		return result;
	}

	private void search(List<KDEval<KDNode<T>, Double>> list, KDNode<T> current, KDNode<T> pnt, int numNearest,
			Double radius) {
		KDNode<T> leftChild = current.getLeftChild();
		KDNode<T> rightChild = current.getRightChild();
		double currDist = pointDist2(current.getElement(), pnt.getElement());

		// base case leaf node
		if (leftChild == null && rightChild == null) {
			checkAdd(list, current, pnt, numNearest, currDist, radius);
			return;
		}

		int dim = (current.getDepth()) % dimension_;
		KDNode<T> otherChild;

		if (pnt.getDimension(dim) <= current.getDimension(dim) && leftChild != null) {
			search(list, leftChild, pnt, numNearest, radius);
			otherChild = rightChild;
		} else if (rightChild != null) {
			search(list, rightChild, pnt, numNearest, radius);
			otherChild = leftChild;
		} else {
			otherChild = leftChild;
		}

		checkAdd(list, current, pnt, numNearest, currDist, radius);

		double maxDist;
		if (list.isEmpty())
			maxDist = Double.MAX_VALUE;
		else
			maxDist = list.get(list.size() - 1).b;

		double distPlane = this.calcDist2Plane(current, pnt, dim);

		if ((radius != null && distPlane <= radius && otherChild != null)
				|| (radius == null && (list.size() < numNearest || distPlane < maxDist) && otherChild != null)) {
			search(list, otherChild, pnt, numNearest, radius);
		}
	}

	/**
	 * @param list
	 * @param current
	 * @param pnt
	 * @param numNearest
	 * @param currDist
	 * @param radius
	 */
	private void checkAdd(List<KDEval<KDNode<T>, Double>> list, KDNode<T> current, KDNode<T> pnt, int numNearest,
			double currDist, Double radius) {

		// only add an element if it's not the point given
		if (!current.getElement().equals(pnt.getElement())) {

			// if radius != null evaluate with radius distance
			if (radius != null && currDist <= radius)
				list.add(new KDEval<KDNode<T>, Double>(current, currDist));

			// if radius is null evaluate for nearest neighbors
			else if (radius == null) {

				// if the list doesn't contain enough neighbors add current
				if (list.size() < numNearest) {
					list.add(new KDEval<KDNode<T>, Double>(current, currDist));

					// otherwise check if currDist is smaller than the largest
					// distance in the list
				} else {
					Collections.sort(list, new KDEvalComparator());
					if (currDist < list.get(numNearest - 1).b) {
						list.remove(numNearest - 1);
						list.add(new KDEval<KDNode<T>, Double>(current, currDist));
					}
				}
			}
		}
	}

	/**
	 * Calculates the minimum squared magnitude from the point determined by
	 * {@code point} to the plane created by the {@code dim} dimension of
	 * {@code current}
	 * 
	 * @param current
	 *            - the point where the plane lies
	 * @param point
	 *            - the point from which to calculate the distance
	 * @param dim
	 *            - the dimension to use to create a plane
	 * @return the squared magnitude from the point to the plane
	 */
	private double calcDist2Plane(KDNode<T> current, KDNode<T> point, int dim) {
		KDElement e = point.getElement();
		Double[] coords = new Double[3];
		e.coordinates.toArray(coords);
		coords[dim] = current.getDimension(dim);
		return pointDist2(new KDElement("", coords[0], coords[1], coords[2]), e);
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

	/**
	 * An instance of {@link Comparator} used to compare the coordinates of two
	 * {@link KDElement}s at the given dimension.
	 * 
	 * @author ltbarnes
	 * 
	 */
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

	/**
	 * Compares two {@link KDEval}<{@link KDNode}, {@link Double}> objects by
	 * comparing the {@link Double} value associated with each.
	 * 
	 * @author ltbarnes
	 * 
	 */
	private class KDEvalComparator implements Comparator<KDEval<KDNode<T>, Double>> {

		@Override
		public int compare(KDEval<KDNode<T>, Double> se1, KDEval<KDNode<T>, Double> se2) {
			return Double.compare(se1.b, se2.b);
		}

	}

	/**
	 * An instance of {@link Iterator} used to iterate through the KDTree using
	 * an in-order traversal.
	 * 
	 * @author ltbarnes
	 * 
	 */
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

		/**
		 * "Slide" down the left side of a subtree until the left child is null.
		 */
		private void slide() {
			while (current_ != null) {
				dq_.push(current_);
				current_ = current_.getLeftChild();
			}
		}
	}

	/**
	 * Returns the squared magnitude between two {@link KDElement}s.
	 * 
	 * <p>
	 * mag^2 = (e1.x - e2.x)^2 + (e2.y - e2.y)^ 2 + ... + (e1.z - e2.z)^2
	 * 
	 * @param e1
	 *            - element 1
	 * @param e2
	 *            - element 2
	 * @return - the squared distance
	 */
	public static double pointDist2(KDElement e1, KDElement e2) {
		int sum = 0;
		for (int i = 0; i < 3; i++) {
			double diff = e1.coordinates.get(i) - e2.coordinates.get(i);
			sum += diff * diff;
		}
		return sum;
	}

	/**
	 * Helper class used to pass around and keep track of elements requiring a
	 * single mapping to another object.
	 * 
	 * @author ltbarnes
	 * 
	 * @param <A>
	 *            the first element
	 * @param <B>
	 *            the mapped object
	 */
	private static class KDEval<A, B> {

		A a;
		B b;

		KDEval(A a, B b) {
			this.a = a;
			this.b = b;
		}
	}
}
