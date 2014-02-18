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
 * An instance of {@link KDTree} capable of constructing a balanced KDTree in any dimension.
 * 
 * <p>
 * NOTE: removeElement(T e) METHOD NOT FUNCTIONAL IN THIS IMPLEMENTATION OF {@link KDTree}.
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

	/**
	 * Recursively builds the {@link KDTree} based on the initial list of elements. At each depth a
	 * new dimension is used to sort the list and calculate a median element. All elements less than
	 * the median are used to build a sub tree to the left of the median and all the greater
	 * elements are built into a subtree to the right.
	 * 
	 * @param currDepth
	 *            - the current depth of the nodes
	 * @param elements
	 *            - the list of elements not yet added to the tree
	 * @return the root of the created tree
	 */
	private KDNode<T> buildTree(int currDepth, List<T> elements) {

		// set the dimension to use at this depth
		int dim = currDepth % dimension_;

		// sort the list and find the median element
		int midIndex = elements.size() / 2;
		Collections.sort(elements, new DimensionComparator(dim));
		KDNode<T> parent = new KDNode<>(elements.get(midIndex), currDepth);
		size_++;

		// use the smaller elements to build a left subtree
		if (midIndex > 0)
			parent.setLeftChild(buildTree(currDepth + 1, elements.subList(0, midIndex)));
		else
			parent.setLeftChild(null);

		// use the greater elements to build a right subtree
		if (midIndex < elements.size() - 1)
			parent.setRightChild(buildTree(currDepth + 1, elements.subList(midIndex + 1, elements.size())));
		else
			parent.setRightChild(null);

		// return the root node of the subtrees
		return parent;
	}

	@Override
	public void addElement(T e) {
		// get the leaf node where e should be appended
		KDNode<T> element = new KDNode<>(e);
		KDNode<T> kdn = addcursion(root_, element);

		// calculate the dimension to use based on the depth of the node
		int dim = kdn.getDepth() % dimension_;

		// if e is in the tree return
		if (element.equals(kdn))
			return;
		// append element as the left or right child depending the dimension being evaluated
		if (element.getDimension(dim) <= kdn.getDimension(dim))
			kdn.setLeftChild(element);
		else {
			kdn.setRightChild(element);
		}
		// increment size
		size_++;
	}

	/**
	 * Recursively descends into the tree based on the dimensions of {@code point} and returns the
	 * final leaf node.
	 * 
	 * @param current
	 *            - the node currently being evaluated
	 * @param point
	 *            - the goal point used to descend the tree
	 * @return the leaf node reached after descending the tree
	 */
	private KDNode<T> addcursion(KDNode<T> current, KDNode<T> point) {
		int dim = (current.getDepth()) % dimension_;
		KDNode<T> result = current;

		if (current.equals(point))
			return result;
		if (point.getDimension(dim) <= current.getDimension(dim) && current.getLeftChild() != null)
			result = addcursion(current.getLeftChild(), point);
		else if (current.getRightChild() != null) {
			result = addcursion(current.getRightChild(), point);
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

	/**
	 * Sorts the {@link KDEval}s returned by the {@code search} routine
	 * 
	 * @param list
	 *            - the list of {@link KDEval}s to sort
	 * @return a list of sorted tree elements
	 */
	private List<T> sortSearch(List<KDEval<KDNode<T>, Double>> list) {
		Collections.sort(list, new KDEvalComparator());
		List<T> result = new ArrayList<>();
		for (KDEval<KDNode<T>, Double> ke : list)
			result.add(ke.a.getElement());
		return result;
	}

	/**
	 * Recursively searches the tree adding and removing elements from the list of return values
	 * depending on the parameters specified. If a radius is given then all points within the radius
	 * from center {@code point} are added to the list. Otherwise only the closest
	 * {@code numNearest} points are added (if that many exist in the tree)
	 * 
	 * @param list
	 *            - the list of return values
	 * @param current
	 *            - the node to potentially be added
	 * @param point
	 *            - the point being used for the search
	 * @param numNearest
	 *            - the number of nearest neighbors to collect for the list (only used if radius is
	 *            null)
	 * @param currDist
	 *            - the squared magnitude between {@link current} and {@link point}
	 * @param radius
	 *            - the radius used to collect nearby elements (radius is null when using
	 *            {@code kNNSearch})
	 */
	private void search(List<KDEval<KDNode<T>, Double>> list, KDNode<T> current, KDNode<T> point, int numNearest,
			Double radius) {

		// get current's children and distance from point
		KDNode<T> leftChild = current.getLeftChild();
		KDNode<T> rightChild = current.getRightChild();
		double currDist = pointDist2(current.getElement(), point.getElement());

		// base case leaf node: potentially add current and return.
		if (leftChild == null && rightChild == null) {
			checkAdd(list, current, point, numNearest, currDist, radius);
			return;
		}

		// calculate the dimension to use based on depth
		int dim = (current.getDepth()) % dimension_;
		KDNode<T> otherChild;

		// check which direction to traverse the tree in depending on the current dimension
		if (point.getDimension(dim) <= current.getDimension(dim) && leftChild != null) {
			search(list, leftChild, point, numNearest, radius);
			otherChild = rightChild;
		} else if (rightChild != null) {
			search(list, rightChild, point, numNearest, radius);
			otherChild = leftChild;
		} else {
			otherChild = leftChild;
		}

		// potentially add the current node
		checkAdd(list, current, point, numNearest, currDist, radius);

		// set the maxDist to the max value in the list
		double maxDist;
		if (list.isEmpty())
			maxDist = Double.MAX_VALUE;
		else
			maxDist = list.get(list.size() - 1).b;

		// calculate the distance from the given point to the hyperplane created at current
		double distPlane = this.calcDist2Plane(current, point, dim);

		// if the radius intersects the plane or the maxDist is larger than distPlane then search
		// the other side of the sub tree
		if ((radius != null && distPlane <= radius && otherChild != null)
				|| (radius == null && (list.size() < numNearest || distPlane < maxDist) && otherChild != null)) {
			search(list, otherChild, point, numNearest, radius);
		}
	}

	/**
	 * Checks whether an element should be added to the list of return values from the current
	 * search.
	 * 
	 * @param list
	 *            - the list of return values
	 * @param current
	 *            - the node to potentially be added
	 * @param point
	 *            - the point being used for the search
	 * @param numNearest
	 *            - the number of nearest neighbors to collect for the list (only used if radius is
	 *            null)
	 * @param currDist
	 *            - the squared magnitude between {@link current} and {@link point}
	 * @param radius
	 *            - the radius used to collect nearby elements (radius is null when using
	 *            {@code kNNSearch})
	 */
	private void checkAdd(List<KDEval<KDNode<T>, Double>> list, KDNode<T> current, KDNode<T> point, int numNearest,
			double currDist, Double radius) {

		// only add an element if it's not the point given
		if (!current.getElement().equals(point.getElement())) {

			// if radius != null evaluate with radius distance
			if (radius != null && currDist <= radius)
				list.add(new KDEval<KDNode<T>, Double>(current, currDist));

			// if radius is null evaluate for nearest neighbors
			else if (radius == null) {

				// if the list doesn't contain enough neighbors add current
				if (list.size() < numNearest) {
					list.add(new KDEval<KDNode<T>, Double>(current, currDist));

					// otherwise check if currDist is smaller than the largest distance in the list
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
	 * Calculates the minimum squared magnitude from the point determined by {@code point} to the
	 * hyperplane created by the {@code dim} dimension of {@code current}
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
	 * An instance of {@link Comparator} used to compare the coordinates of two {@link KDElement}s
	 * at the given dimension.
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
	 * Compares two {@link KDEval}<{@link KDNode}, {@link Double}> objects by comparing the
	 * {@link Double} value associated with each.
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
	 * An instance of {@link Iterator} used to iterate through the KDTree using an in-order
	 * traversal.
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
	 * Helper class used to pass around and keep track of elements requiring a single mapping to
	 * another object.
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
